package com.autonubil.analyzer.dependencies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.autonubil.analyzer.dependencies.filters.ClassFilter;

public class JdepsResult extends HashMap<String, JarDependencies>{

	private static final long serialVersionUID = -9163047226370148051L;

	
	private ClassFilter classFilter;
	
	
	private List<String> links = new ArrayList<String>();
	
	public List<String> getLinks() {
		return links;
	}


	public ClassFilter getClassFilter() {
		return this.classFilter;
	}


	public void setClassFilter(ClassFilter classFilter) {
		if (classFilter == null) {
			throw new NullPointerException("classFilter");
		}
		this.classFilter = classFilter;
	}


	public JarClassMap getClassesByJarAndNamespace() {
		
		JarClassMap result = new JarClassMap();

		// referencing classes
		for(JarDependencies jarDeps : this.values()) {
			result.put(jarDeps.getJarName(),  jarDeps.getClassesByNamespace() );
		}
		
		// referenced classes
		for(JarDependencies jarDeps : this.values()) {
			
			for (ClassDependencies classDeps : jarDeps.getDependencies().values() ) {
				Map<String, List<ClassDepSpec>> jarClassesByNamespace;
				
				for (ClassDepSpec depSpec : classDeps.getTargets() ) {
				
					
					if (!result.containsKey(depSpec.getJar())) {
						jarClassesByNamespace = new HashMap<String, List<ClassDepSpec>>();
						List<ClassDepSpec> initialSpecs =  new ArrayList<ClassDepSpec>();
						initialSpecs.add(depSpec);
						jarClassesByNamespace.put(depSpec.getNamespace(),initialSpecs);
						
						result.put(depSpec.getJar(),  jarClassesByNamespace);
					} else {
						jarClassesByNamespace = result.get(depSpec.getJar());
					}
					
					List<ClassDepSpec>  classesForNamespace;
					if (!jarClassesByNamespace.containsKey(depSpec.getNamespace())) {
						classesForNamespace = new ArrayList<ClassDepSpec>();
						jarClassesByNamespace.put(depSpec.getNamespace(), classesForNamespace);
					} else {
						classesForNamespace =jarClassesByNamespace.get(depSpec.getNamespace()); 
					}
					if (!classesForNamespace.contains(depSpec)) {
						classesForNamespace.add(depSpec);
					}	
					
				}
				
			}
		}
		
		return result;
		
	}
	
	public boolean isNewLink(String link) {
		if (!this.links.contains(link)) {
			this.links.add(link);
			return true;
		}
		return false;
		
	}

}
