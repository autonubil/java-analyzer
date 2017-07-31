package com.autonubil.analyzer.maven.plugin;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.dependency.graph.DependencyNode;

import com.autonubil.analyzer.dependencies.JdepsResult;

public class ArtifactGroups extends HashMap<String, Packages> {
	private static final long serialVersionUID = 5694386939832754633L;

	
	Artifact rootArtifact;
	DependencyNode rootNode;
	
	public ArtifactGroups(Artifact rootArtifact) {
		this.rootArtifact = rootArtifact;
	}
	
	
	public Packages ensure(String groupId) {
		if (!this.containsKey(groupId)) {
			this.put(groupId, new Packages());
		}
		return this.get(groupId);
	}

	public DependencyNode ensure(DependencyNode node) {
	 
		if (node.getParent() == null) {
			this.rootNode = node;
		}
			
		Packages packages = this.ensure(node.getArtifact().getGroupId());
		Artifacts artifacts = packages.ensure(node.getArtifact().getArtifactId());
		return artifacts.ensure(node);
	}
	
	private boolean isPackageNode(DependencyNode node) {
		System.err.println(String.format("%s => %s (%s)",  this.rootArtifact.getGroupId(), node.getArtifact().getGroupId(), node.getArtifact().getFile() ) );
		return  ( ( (node==this.rootNode) || (node.getParent() == this.rootNode) )  ||   (    (this.rootArtifact != null) && ( this.rootArtifact.getGroupId().equals( node.getArtifact().getGroupId()) )) );
	}
	
	

	public void write(PrintWriter writer, JdepsResult jarDependencies) {
		for (String groupId : this.keySet()) {
			// write node
			writer.println("package \"" + groupId + "\" <<Frame>> {");
			this.get(groupId).write(writer, jarDependencies, groupId);
			writer.println("}");
		}
		
		for (String groupId : this.keySet()) {
			this.get(groupId).writeDeps(writer, jarDependencies, groupId);
		}
	}

	public List<String> getClasspath() {
		List<String> result = new ArrayList<String>();
		if ( (this.rootArtifact != null) && (this.rootArtifact.getFile() != null) ) {
			result.add(this.rootArtifact.getFile().getPath().replaceAll("^[A-Z]:\\\\", "/").replace('\\', '/'));
		}
		
		for (Packages packages : this.values()) {
			for (Artifacts artifacts : packages.values()) {
				for (DependencyNode node : artifacts) {
					if   ( ((node!= null) && (node.getArtifact() != null) && (node.getArtifact().getFile() != null)  ) &&   "jar".equals(node.getArtifact().getType()) || Artifact.SCOPE_COMPILE.equals(node.getArtifact().getScope())) {
						String path = node.getArtifact().getFile().getPath().replaceAll("^[A-Z]:\\\\", "/")
								.replace('\\', '/');
						if (!result.contains(path)) {
							result.add(path);
						}
					}
				}
			}
		}
		return result;
	}

	public List<String> getCompiledSources() {
		List<String> result = new ArrayList<String>();
		
		if ( (this.rootArtifact != null) && (this.rootArtifact.getFile() != null) ) {
			result.add(this.rootArtifact.getFile().getPath().replaceAll("^[A-Z]:\\\\", "/").replace('\\', '/'));
		}
		
		for (Packages packages : this.values()) {
			for (Artifacts artifacts : packages.values()) {
				for (DependencyNode node : artifacts) {
					if (isPackageNode(node) && (node.getArtifact().getFile() != null)) {
						String path = node.getArtifact().getFile().getPath().replaceAll("^[A-Z]:\\\\", "/")
								.replace('\\', '/');
						if (!result.contains(path)) {
							result.add(path);
						}
					}

				}

			}
		}
		if (result.size() == 0) {
			result.add("target/classes");
		}
		
		return result;

	}

}
