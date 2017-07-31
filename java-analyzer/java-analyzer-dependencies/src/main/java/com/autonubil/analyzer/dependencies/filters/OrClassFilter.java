package com.autonubil.analyzer.dependencies.filters;

import java.util.ArrayList;
import java.util.List;

public class OrClassFilter implements ClassFilter {

	private List<ClassFilter> filters = new ArrayList<ClassFilter>();
	
	
	public OrClassFilter() {
		
	}
	
	public OrClassFilter(Iterable<ClassFilter> fitlers) {
		for (ClassFilter classFilter : fitlers) {
			this.addFilter(classFilter);
		}
		
	}
	
	public OrClassFilter(ClassFilter[] fitlers) {
		for (ClassFilter classFilter : fitlers) {
			this.addFilter(classFilter);
		}
		
	}
	
	
	public void addFilter(ClassFilter filter) {
		this.filters.add(filter);
	}
	
	public boolean accept(String className) {
		for(ClassFilter filter : this.filters) {
			if (filter.accept(className)) {
				return true;
			}
		}
		return false;
	}

}
