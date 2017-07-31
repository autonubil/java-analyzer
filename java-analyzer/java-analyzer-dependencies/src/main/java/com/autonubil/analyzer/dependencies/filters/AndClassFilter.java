package com.autonubil.analyzer.dependencies.filters;

import java.util.ArrayList;
import java.util.List;

public class AndClassFilter implements ClassFilter {

	private List<ClassFilter> filters = new ArrayList<ClassFilter>();
	
	public void addFilter(ClassFilter filter) {
		this.filters.add(filter);
	}
	
	public boolean accept(String className) {
		for(ClassFilter filter : this.filters) {
			if (!filter.accept(className)) {
				return false;
			}
		}
		return true;
	}

}
