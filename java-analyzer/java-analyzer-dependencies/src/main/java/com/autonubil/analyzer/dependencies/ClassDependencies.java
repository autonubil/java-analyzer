package com.autonubil.analyzer.dependencies;

import java.util.ArrayList;
import java.util.List;

public class ClassDependencies  {

	private String className;
	private String targetJar;
	private List<ClassDepSpec> targets;
	String namespace;
	String shortClassName;
	
	public ClassDependencies(String className) {
		this.className = className;
		int lastDot = className.lastIndexOf('.');
		this.namespace = className.substring(0, lastDot);
		this.shortClassName = className.substring(lastDot+1);
		this.targets = new ArrayList<ClassDepSpec>();
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getJar() {
		return targetJar;
	}
	public void setJar(String targetJar) {
		this.targetJar = targetJar;
	}
	public List<ClassDepSpec> getTargets() {
		return targets;
	}
	public String getNamespace() {
		return namespace;
	}

	public String getShortClassName() {
		return shortClassName;
	}
	
	 
	
}
