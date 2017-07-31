package com.autonubil.analyzer.dependencies;

import java.io.File;

public class ClassDepSpec {

	private String jar;
	private String className;

	String namespace;
	String shortClassName;

	
	public ClassDepSpec(String jar, String className) {
		this.jar = new File(jar).getName();
		this.className = className;
		int lastDot = className.lastIndexOf('.');
		if (jar.endsWith(".jar")) {
			this.namespace = className.substring(0, lastDot);
			this.shortClassName = className.substring(lastDot+1);
		}

	}

	public String getNamespace() {
		return namespace;
	}

	public String getShortClassName() {
		return shortClassName;
	}

	public String getJar() {
		return jar;
	}

	public void setJar(String jar) {
		this.jar = jar;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
    public boolean equals(Object object)
    {
		boolean sameSame = false;

        if (object != null && object instanceof ClassDepSpec)
        {
            sameSame = this.jar.equals( ((ClassDepSpec) object).getJar()) &&  this.className.equals( ((ClassDepSpec) object).getClassName());
        }

        return sameSame;
    }
	
	
	@Override
	public String toString() {
		return  this.getJar() +"@"+ this.getClassName();
	}
	
	
	
}
