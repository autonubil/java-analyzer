package com.autonubil.analyzer.dependencies.filters;

public class NotClassFilter implements ClassFilter {

	ClassFilter innerClassFilter;
	
	public NotClassFilter(ClassFilter innerClassFilter) {
		this.innerClassFilter = innerClassFilter;
	}
	
	public boolean accept(String className) {
		return !innerClassFilter.accept(className);
	}

	
	
}
