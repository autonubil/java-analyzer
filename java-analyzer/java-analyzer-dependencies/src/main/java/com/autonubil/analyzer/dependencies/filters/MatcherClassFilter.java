package com.autonubil.analyzer.dependencies.filters;

import java.util.regex.Pattern;

public class MatcherClassFilter implements ClassFilter {

	Pattern pattern;
	
	public MatcherClassFilter(String pattern) {
		this.pattern = Pattern.compile(wildcardToRegex(pattern));
	}
	
	
	public MatcherClassFilter(Pattern pattern) {
		this.pattern = pattern;
	}
	
	/* (non-Javadoc)
	 * @see com.autonubil.analyzer.maven.plugin.ClassFilter#accept(java.lang.String)
	 */
	public boolean accept(String className) {
		return this.pattern.matcher(className).matches();
	}

	private static String wildcardToRegex(String wildcard) {
		StringBuilder s = new StringBuilder(wildcard.length());
		s.append('^');
		for (int i = 0, is = wildcard.length(); i < is; i++) {
			char c = wildcard.charAt(i);
			switch (c) {
			case '*':
				s.append(".*?");
				break;
			case '?':
				s.append(".");
				break;
			// escape special regexp-characters
			case '(':
			case ')':
			case '[':
			case ']':
			case '$':
			case '^':
			case '.':
			case '{':
			case '}':
			case '|':
			case '\\':
				s.append("\\");
				s.append(c);
				break;
			default:
				s.append(c);
				break;
			}
		}
		s.append('$');
		return (s.toString());
	}
 
}
