package com.autonubil.analyzer.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.plugins.dependency.tree.DOTDependencyNodeVisitor;
import org.apache.maven.plugins.dependency.tree.GraphmlDependencyNodeVisitor;
import org.apache.maven.plugins.dependency.tree.TGFDependencyNodeVisitor;
import org.apache.maven.plugins.dependency.utils.DependencyUtil;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.artifact.filter.ScopeArtifactFilter;
import org.apache.maven.shared.artifact.filter.StrictPatternExcludesArtifactFilter;
import org.apache.maven.shared.artifact.filter.StrictPatternIncludesArtifactFilter;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilderException;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.filter.AncestorOrSelfDependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.filter.AndDependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.filter.ArtifactDependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.filter.DependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.traversal.BuildingDependencyNodeVisitor;
import org.apache.maven.shared.dependency.graph.traversal.CollectingDependencyNodeVisitor;
import org.apache.maven.shared.dependency.graph.traversal.DependencyNodeVisitor;
import org.apache.maven.shared.dependency.graph.traversal.FilteringDependencyNodeVisitor;
import org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor;
import org.apache.maven.shared.dependency.graph.traversal.SerializingDependencyNodeVisitor.GraphTokens;

import com.autonubil.analyzer.dependencies.filters.AndClassFilter;
import com.autonubil.analyzer.dependencies.filters.ClassFilter;
import com.autonubil.analyzer.dependencies.filters.MatcherClassFilter;
import com.autonubil.analyzer.dependencies.filters.NotClassFilter;
import com.autonubil.analyzer.dependencies.filters.OrClassFilter;

@Mojo(name = "jdeps", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyCollection = ResolutionScope.RUNTIME_PLUS_SYSTEM, requiresDependencyResolution = ResolutionScope.RUNTIME_PLUS_SYSTEM )
public class JdepsMojo extends AbstractMojo {
	
	
    // fields -----------------------------------------------------------------

    /**
     * The Maven project.
     */
    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;
    
    @Parameter( defaultValue = "${session}", readonly = true, required = true )
    private MavenSession session;
    
    /**
     * Contains the full list of projects in the reactor.
     */
    @Parameter( defaultValue = "${reactorProjects}", readonly = true, required = true )
    private List<MavenProject> reactorProjects;

    
	   /**
     * The scope to filter by when resolving the dependency tree, or <code>null</code> to include dependencies from
     * all scopes. Note that this feature does not currently work due to MSHARED-4
     *
     * @see <a href="https://issues.apache.org/jira/browse/MSHARED-4">MSHARED-4</a>
     * @since 2.0-alpha-5
     */
    @Parameter( property = "scope" )
    private String scope;

    /**
     * The token set name to use when outputting the dependency tree. Possible values are <code>whitespace</code>,
     * <code>standard</code> or <code>extended</code>, which use whitespace, standard (ie ASCII) or extended
     * character sets respectively.
     *
     * @since 2.0-alpha-6
     */
    @Parameter( property = "tokens", defaultValue = "standard" )
    private String tokens;
    
    /**
     * If specified, this parameter will cause the dependency tree to be written to the path specified, instead of
     * writing to the console.
     *
     * @since 2.0-alpha-5
     */
    @Parameter( property = "outputFile", defaultValue="target/dependencies.svg" )
    private File outputFile;

    /**
     * If specified, this parameter will cause the dependency tree to be written using the specified format. Currently
     * supported format are: <code>text</code>, <code>dot</code>, <code>graphml</code> and <code>tgf</code>.
     * <p/>
     * These formats can be plotted to image files. An example of how to plot a dot file using
     * pygraphviz can be found <a href="http://networkx.lanl.gov/pygraphviz/tutorial.html#layout-and-drawing">here</a>.
     *
     * @since 2.2
     */
    @Parameter( property = "outputType", defaultValue = "plantuml-svg" )
    private String outputType;
    
    /**
     * Skip plugin execution completely.
     *
     * @since 2.7
     */
    @Parameter( property = "skip", defaultValue = "false" )
    private boolean skip;
    

    /**
     * The dependency tree builder to use.
     */
    @Component( hint = "default" )
    private DependencyGraphBuilder dependencyGraphBuilder;
    
    /**
     * Whether to append outputs into the output file or overwrite it.
     *
     * @since 2.2
     */
    @Parameter( property = "appendOutput", defaultValue = "false" )
    private boolean appendOutput;

    

    /**
     * A comma-separated list of artifacts to filter the serialized dependency tree by, or <code>null</code> not to
     * filter the dependency tree. The filter syntax is:
     * 
     * <pre>
     * [groupId]:[artifactId]:[type]:[version]
     * </pre>
     * 
     * where each pattern segment is optional and supports full and partial <code>*</code> wildcards. An empty pattern
     * segment is treated as an implicit wildcard.
     * <p>For example, <code>org.apache.*</code> will match all artifacts whose group id starts with
     * <code>org.apache.</code>, and <code>:::*-SNAPSHOT</code> will match all snapshot artifacts.</p>
     * 
     * @see StrictPatternIncludesArtifactFilter
     * @since 2.0-alpha-6
     */
    @Parameter( property = "includes" )
    private String includes;

    /**
     * A comma-separated list of artifacts to filter from the serialized dependency tree, or <code>null</code> not to
     * filter any artifacts from the dependency tree. The filter syntax is:
     * 
     * <pre>
     * [groupId]:[artifactId]:[type]:[version]
     * </pre>
     * 
     * where each pattern segment is optional and supports full and partial <code>*</code> wildcards. An empty pattern
     * segment is treated as an implicit wildcard.
     * <p>For example, <code>org.apache.*</code> will match all artifacts whose group id starts with
     * <code>org.apache.</code>, and <code>:::*-SNAPSHOT</code> will match all snapshot artifacts.</p>
     *
     * @see StrictPatternExcludesArtifactFilter
     * @since 2.0-alpha-6
     */
    @Parameter( property = "excludes" )
    private String excludes;

    
    /**
     * A comma-separated list of classes to filter from the result, or <code>null</code> not to
     * filter any artifacts from the dependency tree. The filter syntax is:
     * 
     * <pre>
     * regex class name including namespacve
     * </pre>
     * 
     * where each pattern segment is optional and supports full and partial <code>*</code> wildcards. An empty pattern
     * segment is treated as an implicit wildcard.
     * <p>For example, <code>org.apache.*</code> will match all artifacts whose group id starts with
     *
     */
    @Parameter( property = "classIncludes" )
    private String classIncludes;

    @Parameter( property = "classExcludes", defaultValue = "java.*,javax.*,org.bouncycastle" )
    private String classExcludes;
    
    
    
    
    /**
     * The computed dependency tree root node of the Maven project.
     */
    private DependencyNode rootNode;
    
    
    
    /*
     * @see org.apache.maven.plugin.Mojo#execute()
     */

    public void execute() throws MojoExecutionException {
		  if ( isSkip() )
	        {
	            getLog().info( "Skipping plugin execution" );
	            return;
	        }

	        try
	        {
	            String dependencyTreeString;

	            // TODO: note that filter does not get applied due to MSHARED-4
	            ArtifactFilter artifactFilter = createResolvingArtifactFilter();

	    
	            ProjectBuildingRequest buildingRequest =
	                new DefaultProjectBuildingRequest( session.getProjectBuildingRequest() );
	            
	            buildingRequest.setProject( project );
	            
	            // non-verbose mode use dependency graph component, which gives consistent results with Maven version
	            // running
	            rootNode = dependencyGraphBuilder.buildDependencyGraph( buildingRequest, artifactFilter, reactorProjects );
	            dependencyTreeString = serializeDependencyTree( rootNode );

	            if ( outputFile != null )
	            {
	                DependencyUtil.write( dependencyTreeString, outputFile, this.appendOutput, getLog() );

	                getLog().info( "Wrote dependency tree to: " + outputFile );
	            }
	            else
	            {
	                DependencyUtil.log( dependencyTreeString, getLog() );
	            }
	        }
	        catch ( DependencyGraphBuilderException exception )
	        {
	            throw new MojoExecutionException( "Cannot build project dependency graph", exception );
	        }
	        catch ( IOException exception )
	        {
	            throw new MojoExecutionException( "Cannot serialise project dependency graph", exception );
	        }
		
		
	}
	

    // public methods ---------------------------------------------------------

    /**
     * Gets the Maven project used by this mojo.
     *
     * @return the Maven project
     */
    public MavenProject getProject()
    {
        return project;
    }


    public boolean isSkip()
    {
        return skip;
    }

    public void setSkip( boolean skip )
    {
        this.skip = skip;
    }
    // private methods --------------------------------------------------------

    /**
     * Gets the artifact filter to use when resolving the dependency tree.
     *
     * @return the artifact filter
     */
    private ArtifactFilter createResolvingArtifactFilter()
    {
        ArtifactFilter filter;

        // filter scope
        if ( scope != null )
        {
            getLog().debug( "+ Resolving dependency tree for scope '" + scope + "'" );

            filter = new ScopeArtifactFilter( scope );
        }
        else
        {
            filter = null;
        }

        return filter;
    }
    
    
    /**
     * Serializes the specified dependency tree to a string.
     *
     * @param rootNode the dependency tree root node to serialize
     * @return the serialized dependency tree
     */
    private String serializeDependencyTree( DependencyNode rootNode )
    {
        StringWriter writer = new StringWriter();

        DependencyNodeVisitor visitor = getSerializingDependencyNodeVisitor( writer );

        // TODO: remove the need for this when the serializer can calculate last nodes from visitor calls only
        visitor = new BuildingDependencyNodeVisitor( visitor );

        DependencyNodeFilter filter = createDependencyNodeFilter();

        if ( filter != null )
        {
            CollectingDependencyNodeVisitor collectingVisitor = new CollectingDependencyNodeVisitor();
            DependencyNodeVisitor firstPassVisitor = new FilteringDependencyNodeVisitor( collectingVisitor, filter );
            rootNode.accept( firstPassVisitor );

            DependencyNodeFilter secondPassFilter = new AncestorOrSelfDependencyNodeFilter( collectingVisitor.getNodes() );
            visitor = new FilteringDependencyNodeVisitor( visitor, secondPassFilter );
        }

        rootNode.accept( visitor );

        return writer.toString();
    }
    
    public DependencyNodeVisitor getSerializingDependencyNodeVisitor( Writer writer )
    {
    	if ( "plantuml-svg".equals( outputType ) )
        {
            return new PlantumlDependencyNodeVisitor(this.getProject().getArtifact(),  writer, this.createClassFilter(), "svg");
        }
        else if ( "plantuml".equals( outputType ) )
        {
    		
            return new PlantumlDependencyNodeVisitor(this.getProject().getArtifact(), writer, this.createClassFilter() );
        }
        else if ( "graphml".equals( outputType ) )
        {
            return new GraphmlDependencyNodeVisitor( writer );
        }
        else if ( "tgf".equals( outputType ) )
        {
            return new TGFDependencyNodeVisitor( writer );
        }
        else if ( "dot".equals( outputType ) )
        {
            return new DOTDependencyNodeVisitor( writer );
        }
        else
        {
            return new SerializingDependencyNodeVisitor( writer, toGraphTokens( tokens ) );
        }
    }
    
    
    
    /**
     * Gets the dependency node filter to use when serializing the dependency graph.
     *
     * @return the dependency node filter, or <code>null</code> if none required
     */
    private ClassFilter createClassFilter()
    {
        AndClassFilter result = new AndClassFilter();

        // filter includes
        if ( classIncludes != null )
        {
            List<String> patterns = Arrays.asList( classIncludes.split( "," ) );

            getLog().debug( "+ Filtering dependency tree by class  include patterns: " + patterns );
            OrClassFilter includesFilter = new OrClassFilter();
            
            for(String pattern : patterns) {
	            MatcherClassFilter classFulter = new MatcherClassFilter( pattern );
	            includesFilter.addFilter(classFulter );
            }
            result.addFilter(includesFilter);
        }

        // filter excludes
        if ( classExcludes != null )
        {
        	List<String> patterns = Arrays.asList( classExcludes.split( "," ) );

            getLog().debug( "+ Filtering dependency tree by class exclude patterns: " + patterns );
            OrClassFilter includesFilter = new OrClassFilter();
            
            for(String pattern : patterns) {
	            MatcherClassFilter classFulter = new MatcherClassFilter( pattern );
	            includesFilter.addFilter(classFulter );
            }
            result.addFilter(new NotClassFilter(  includesFilter) );
        }

        return result;
    }
    
    
    /**
     * Gets the dependency node filter to use when serializing the dependency graph.
     *
     * @return the dependency node filter, or <code>null</code> if none required
     */
    private DependencyNodeFilter createDependencyNodeFilter()
    {
        List<DependencyNodeFilter> filters = new ArrayList<DependencyNodeFilter>();

        // filter includes
        if ( includes != null )
        {
            List<String> patterns = Arrays.asList( includes.split( "," ) );

            getLog().debug( "+ Filtering dependency tree by artifact include patterns: " + patterns );

            ArtifactFilter artifactFilter = new StrictPatternIncludesArtifactFilter( patterns );
            filters.add( new ArtifactDependencyNodeFilter( artifactFilter ) );
        }

        // filter excludes
        if ( excludes != null )
        {
            List<String> patterns = Arrays.asList( excludes.split( "," ) );

            getLog().debug( "+ Filtering dependency tree by artifact exclude patterns: " + patterns );

            ArtifactFilter artifactFilter = new StrictPatternExcludesArtifactFilter( patterns );
            filters.add( new ArtifactDependencyNodeFilter( artifactFilter ) );
        }

        return filters.isEmpty() ? null : new AndDependencyNodeFilter( filters );
    }
    
    /**
     * Gets the graph tokens instance for the specified name.
     *
     * @param tokens the graph tokens name
     * @return the <code>GraphTokens</code> instance
     */
    private GraphTokens toGraphTokens( String tokens )
    {
        GraphTokens graphTokens;

        if ( "whitespace".equals( tokens ) )
        {
            getLog().debug( "+ Using whitespace tree tokens" );

            graphTokens = SerializingDependencyNodeVisitor.WHITESPACE_TOKENS;
        }
        else if ( "extended".equals( tokens ) )
        {
            getLog().debug( "+ Using extended tree tokens" );

            graphTokens = SerializingDependencyNodeVisitor.EXTENDED_TOKENS;
        }
        else
        {
            graphTokens = SerializingDependencyNodeVisitor.STANDARD_TOKENS;
        }

        return graphTokens;
    }

}
