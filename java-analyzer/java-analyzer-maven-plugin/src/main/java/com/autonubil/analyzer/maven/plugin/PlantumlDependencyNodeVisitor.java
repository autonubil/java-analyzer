package com.autonubil.analyzer.maven.plugin;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.dependency.tree.AbstractSerializingVisitor;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.traversal.DependencyNodeVisitor;

import com.autonubil.analyzer.dependencies.ClassDepSpec;
import com.autonubil.analyzer.dependencies.DependencyAnalyser;
import com.autonubil.analyzer.dependencies.JarClassMap;
import com.autonubil.analyzer.dependencies.JarDependencies;
import com.autonubil.analyzer.dependencies.JdepsResult;
import com.autonubil.analyzer.dependencies.filters.ClassFilter;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

	/**
	 * A dependency node visitor that serializes visited nodes to a writer using the
	 * <a href="http://graphml.graphdrawing.org/">graphml format</a>.
	 * 
	 * @author <a href="mailto:jerome.creignou@gmail.com">Jerome Creignou</a>
	 * @since 2.1
	 */
	public class PlantumlDependencyNodeVisitor 
	    extends AbstractSerializingVisitor
	    implements DependencyNodeVisitor
	{
		
		private String format = "puml";
		private ClassFilter classFilter;
		private ArtifactGroups artifactGroups;
		private Map<String, DependencyNode> jarToNodeMap = new HashMap<String, DependencyNode>();
		private JdepsResult jarDependencies; 

		Artifact rootArtifact;
		
	    /**
	     * Graphml xml file header. Define Schema and root element. We also define 2 key as meta data.
	     */
	    private static final String PLANTUML_HEADER =
	        "@startuml\n";

	    /**
	     * Graphml xml file footer.
	     */
	    private static final String PLANTUML_FOOTER = "center footer Generated by autonubil's java-analyzer-maven-plugin\n"
	    		+ "@enduml";

	    /**
	     * Constructor.
	     *
	     * @param writer the writer to write to.
	     */
	    public PlantumlDependencyNodeVisitor(Artifact rootArtifact, Writer writer, ClassFilter classFilter )
	    {
	        super( writer );
	        this.classFilter = classFilter;
	        this.rootArtifact = rootArtifact;
	        this.artifactGroups = new ArtifactGroups(rootArtifact);
	    }
	    
	    public PlantumlDependencyNodeVisitor(Artifact rootArtifact, Writer writer, ClassFilter classFilter, String format )
	    {
	        this (rootArtifact, writer, classFilter );
	        this.format = format;
	    }

	    /**
	     * {@inheritDoc}
	     */
	    public boolean endVisit( DependencyNode node )
	    {
	        if ( node.getParent() == null || node.getParent() == node )
	        {
	        	// resolve class dependencies
	        	DependencyAnalyser analyzer  = new DependencyAnalyser();
	        	Map<String, Object> options = analyzer.getDefaultOptions();
	        	
	        	List<String> sources =artifactGroups.getCompiledSources();
	        	for (String path : artifactGroups.getClasspath()) {
					if (!sources.contains(path)) {
						sources.add(path);
					}
				}
	        	options.put("sources", sources);
	        	options.put("classpath", artifactGroups.getClasspath());
	        	options.put("recursicve", true);
	        	try {
					analyzer.analyze(options);
					this.jarDependencies = analyzer.getJarDependencies();
				} catch (Exception e) {
					e.printStackTrace();
				}  
	        
	        	PrintWriter targetWriter;
	        	if ("svg".equals(this.format)) {
	        		
	        		System.setProperty("PLANTUML_LIMIT_SIZE", "12576");
	        		 
	        		StringWriter sw = new StringWriter();
	        		targetWriter = new PrintWriter(sw);
	        		this.writePlantUml(targetWriter);
	        		
	        		SourceStringReader reader = new SourceStringReader(sw.toString());
	        		
	        		
	        		// System.out.println(sw.toString());
	        		
	        		try {
		        		ByteArrayOutputStream os = new ByteArrayOutputStream();
						reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
						
//						FileOutputStream fos = new FileOutputStream("C:\\temp\\test.svg");
//						fos.write(os.toString().getBytes());
//						fos.close();
						
		        		this.writer.print(os.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}

	        		
	        	} else {
	        		this.writePlantUml(this.writer);
	        	}
	            
	        }

	        return true;
	    }

	    /**
	     * {@inheritDoc}
	     */
	    public boolean visit( DependencyNode node )
	    {
	    	if (node != null) {
		    	artifactGroups.ensure(node);
		    	if ( (node.getArtifact() != null) && (node.getArtifact().getFile() != null) ) {
		    		jarToNodeMap.put(node.getArtifact().getFile().getName(), node);
		    	}
	    	}
	        return true;
	    }

 
	    
	    private void writePlantUml(PrintWriter writer) {
	    	writer.write( PLANTUML_HEADER );
	    	List<String> jarsAlreadyHandled = new ArrayList<String>();
	    	
	    	for(Packages packages : artifactGroups.values()) {
	    		for(Artifacts artifacts : packages.values()) {
	    			for(DependencyNode node : artifacts) {
	    				if ( (node.getArtifact() != null) && (node.getArtifact().getFile() != null) ) {
	    					jarsAlreadyHandled.add(node.getArtifact().getFile().getName());
	    				}
	    			}
	    		}
	    		
	    	}
	    	
	    	/*for(JarDependencies jardDeps : jarDependencies.values()) {
	    		jarsAlreadyHandled.add(jardDeps.getJarName());
	    		
	    	}*/
	    	
	    	
	    	// write referenced packages
	    	JarClassMap jarClassMap =  jarDependencies.getClassesByJarAndNamespace(); 
	    	for (String jar : jarClassMap.keySet()) {
	    		if (jarsAlreadyHandled.contains(jar)) {
	    			continue;
	    		}
	    		
	    		// "Not found"
	    		if ("not".equals(jar)) {
	    			continue;
	    		}
	    		
	    		writer.println("package \"" + jar + "\" <<Frame>> {");
	    		java.util.Map<String, List<ClassDepSpec>> namespaceClasses =   jarDependencies.getClassesByJarAndNamespace().get(jar);
	    		
	    		for (String namespace : namespaceClasses.keySet()) {
	    			List<String> classes = new ArrayList<String>();
					for(ClassDepSpec classSpec: namespaceClasses.get(namespace) ) {
						if ((jarDependencies.getClassFilter() == null)
								|| jarDependencies.getClassFilter().accept(classSpec.getClassName()) ) {
							classes.add(classSpec.getClassName());
						}
					}	
					if (classes.size() > 0) {
						writer.println("  namespace "+ namespace +" {");
						for (String className : classes) {
							writer.println("   class "+ className +"");
						}
						writer.println("  }");
					}
				}
				writer.println("}");
	    	}
	    	artifactGroups.write(writer, jarDependencies);
	    	
	    	for(JarDependencies deps : jarDependencies.values()) {
	    		for (String target : deps.getTargetJars()) {
	    			DependencyNode left = jarToNodeMap.get(deps.getJarName());
	    			if (left != null) {
		    			DependencyNode right = jarToNodeMap.get(target);
		    			if (right != null) {
		    				if (!left.getArtifact().getGroupId().equals(right.getArtifact().getGroupId())) {
				    			String link =  "\""+ right.getArtifact().getArtifactId() + "\" +-- \"" + left.getArtifact().getArtifactId() + "\"";
				    			if (jarDependencies.isNewLink(link))  {
				    				writer.println(link);
				    			}
		    				}
		    			}
	    			}
	    			
	    		}
	    	}
	        writer.write( PLANTUML_FOOTER );
	    }
	    
	}