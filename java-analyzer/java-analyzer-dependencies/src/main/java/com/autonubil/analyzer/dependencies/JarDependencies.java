package com.autonubil.analyzer.dependencies;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JarDependencies {

	private String jarName;

	private Map<String,ClassDependencies> dependencies;
	
	private Map<String,Map<String, ClassDependencies>> dependenciesByNamespace;
	
	private List<String> targetJars;

	public JarDependencies(String jarName) {
		this.jarName = new File(jarName).getName();
		this.targetJars = new ArrayList<String>();
		this.dependencies = new HashMap<String,ClassDependencies>();
		this.dependenciesByNamespace = new HashMap<String, Map<String,ClassDependencies>>();
	}

	public JarDependencies(String jarName, String firstTarget) {
		this(jarName);
		this.addTarget(firstTarget);
	}

	
	public void addTarget(String jar) {
		this.targetJars.add(new File(jar).getName());
		
	}
	
	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

 

	public List<String> getTargetJars() {
		return targetJars;
	}

	public void setTargetJars(List<String> targetJars) {
		this.targetJars = targetJars;
	}

	public Map<String, ClassDependencies> getDependencies() {
		return dependencies;
	}
	
	public ClassDependencies ensureDependencies(String className) {
		if (!this.dependencies.containsKey(className)) {
			ClassDependencies dep = new ClassDependencies(className);
			this.dependencies.put(className,dep);
			if (!this.dependenciesByNamespace.containsKey(dep.getNamespace())) {
				this.dependenciesByNamespace.put(dep.getNamespace(), new HashMap<String, ClassDependencies>() );
			}
			this.dependenciesByNamespace.get(dep.getNamespace()).put(dep.getShortClassName(), dep);
			
		}
		return this.dependencies.get(className);
	}

	public Map<String, Map<String, ClassDependencies>> getDependenciesByNamespace() {
		return dependenciesByNamespace;
	}
	
	
	public Map<String, List<ClassDepSpec>> getClassesByNamespace() {
		Map<String, List<ClassDepSpec>>  result = new HashMap<String, List<ClassDepSpec>>();

		// things that are internal
		Map<String, Map<String, ClassDependencies>> depsByNs = this.getDependenciesByNamespace();
		for (String namespace : depsByNs.keySet()) {
			ArrayList<ClassDepSpec> classList = new ArrayList<ClassDepSpec>();
			
			for (ClassDependencies dep : depsByNs.get(namespace).values()) {
				classList.add(new ClassDepSpec(dep.getJar(), dep.getClassName() ) );
			}
			result.put(namespace, classList);
		}
		
		// now get al referenced classes that are external
		
		
		return result;
	}

}
