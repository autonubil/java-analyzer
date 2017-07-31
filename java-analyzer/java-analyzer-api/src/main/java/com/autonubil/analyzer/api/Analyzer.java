package com.autonubil.analyzer.api;

import java.util.Map;

public interface Analyzer {

	Map<String, Object> getDefaultOptions();
	String getDescription();
	String getHelp();
	void analyze(Map<String, Object> options) throws Exception; 
}
