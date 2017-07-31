package com.autonubil.analyzer.maven.plugin;

import java.io.PrintWriter;
import java.util.HashMap;

import com.autonubil.analyzer.dependencies.JdepsResult;

public class Packages extends HashMap<String, Artifacts> {

	private static final long serialVersionUID = 1699755186446387294L;

	public Artifacts ensure(String artifactId) {
		if (!this.containsKey(artifactId)) {
			this.put(artifactId, new Artifacts());
		}
		return this.get(artifactId);
	}
	
	
	public void write(PrintWriter writer, JdepsResult jarDependencies, String groupName)   {
		for(String artifactId : this.keySet()) {
	        // write node
			if (!groupName.equals(artifactId)) {
				writer.println( " package \""+ artifactId +"\" <<Folder>> {" );
			}
	    	this.get(artifactId).write(writer, jarDependencies);
	    	if (!groupName.equals(artifactId)) {
	    		writer.println( " }" );
	    	}
		}
	}


	public void writeDeps(PrintWriter writer, JdepsResult jarDependencies, String groupId) {
		for(String artifactId : this.keySet()) {
	    	this.get(artifactId).writeDeps(writer, jarDependencies);
		}		
	}
	
}
