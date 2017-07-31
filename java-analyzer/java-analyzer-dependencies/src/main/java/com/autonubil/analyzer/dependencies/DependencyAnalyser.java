package com.autonubil.analyzer.dependencies;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.autonubil.analyzer.api.Analyzer;
import com.autonubil.analyzer.dependencies.filters.ClassFilter;
import com.autonubil.analyzer.dependencies.filters.MatcherClassFilter;
import com.autonubil.analyzer.dependencies.filters.OrClassFilter;

public class DependencyAnalyser implements Analyzer {

	// private void Map<String, Object> packages;

	public Map<String, Object> getDefaultOptions() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sources", new ArrayList<String>());
		map.put("classpath", new ArrayList<String>());
		map.put("filter", "^javax?\\..+");
		map.put("recursive", true);
		map.put("classFilter",
				new OrClassFilter( new ClassFilter[] {
						new MatcherClassFilter("com.autonubil.*"),
						new MatcherClassFilter("de.autonubil.*"),
						new MatcherClassFilter("de.audi.*"),
						new MatcherClassFilter("com.audi.*"),
						new MatcherClassFilter("de.vw.*") })
				);

		map.put("output", "./plantuml.txt");
		return map;
	}

	public String getDescription() {
		return "Analyze Jar file for dependencies";
	}

	public String getHelp() {
		return "Feed with source";
	}

	private JdepsResult jarDependencies;

	@SuppressWarnings("unchecked")
	public void analyze(Map<String, Object> options)
			throws IOException, ClassNotFoundException, IllegalAccessException, 
			InvocationTargetException, NoSuchMethodException, InstantiationException {

		this.jarDependencies = new JdepsResult();

		Class<?> jdepsTaskClass = this.getClass().getClassLoader().loadClass("com.sun.tools.jdeps.JdepsTask");
		Constructor<?> constructor = jdepsTaskClass.getDeclaredConstructor();
		constructor.setAccessible(true);
		Method runMethod = jdepsTaskClass.getDeclaredMethod("run", String[].class);
		runMethod.setAccessible(true);
		Method setLogMethod = jdepsTaskClass.getDeclaredMethod("setLog", PrintWriter.class);
		setLogMethod.setAccessible(true);

		Object jdepsTask = constructor.newInstance();

		List<String> cmd = new ArrayList<String>();
		cmd.add("-apionly");
		cmd.add("-verbose:class");

		List<String> fullClassPath = new ArrayList<String>((List<String>) options.get("classpath"));

		for (String target : ((List<String>) options.get("sources"))) {
			if (!fullClassPath.contains(target)) {
				fullClassPath.add(target);
			}
		}
		String classpath = String.join(File.pathSeparator, fullClassPath);
		if (classpath.length() > 0) {
			cmd.add("-cp");
			cmd.add(classpath);
		}

		if (options.containsKey("filter") && (options.get("filter") != null)
				&& (options.get("filter").toString().length() > 0)) {
			cmd.add("-f");
			cmd.add(options.get("filter").toString());
		}

		if (options.containsKey("recursive") && (options.get("recursive") != null)
				&& ((Boolean) options.get("recursive"))) {
			cmd.add("-R");
		}

		if (options.containsKey("classFilter") && (options.get("classFilter") instanceof ClassFilter)) {
			this.jarDependencies.setClassFilter((ClassFilter) options.get("classFilter"));
		}

		for (String target : ((List<String>) options.get("sources"))) {
			cmd.add(target);
		}
		String[] args = new String[cmd.size()];
		cmd.toArray(args);
		System.out.println(String.join(" ", args));

		StringWriter archive = new StringWriter();
		PrintWriter localWriter = new PrintWriter(archive);
		setLogMethod.invoke(jdepsTask, new Object[] { localWriter });

		runMethod.invoke(jdepsTask, new Object[] { args });

		String[] lines = archive.toString().split(System.getProperty("line.separator"));

		JarDependencies curJar = null;
		ClassDependencies curDeps = null;
		for (String line : lines) {
			// System.err.println(line);
			if (!line.startsWith(" ") && line.indexOf("->") > 1) {
				String[] parts = line.split(" \\-\\> ");

				if (this.jarDependencies.containsKey(parts[0])) {
					curJar = this.jarDependencies.get(parts[0]);
					curJar.addTarget(parts[1]);
				} else {
					curJar = new JarDependencies(parts[0], parts[1]);
					this.jarDependencies.put(parts[0], curJar);
				}
			} else if (line.startsWith("   ") & line.indexOf("->") == -1) {
				String[] parts = line.trim().split("\\s+");
				String targetJar = "";
				if (parts.length > 1) {
					targetJar = parts[1].trim();
					if (targetJar.startsWith("(") && targetJar.endsWith(")")) {
						targetJar = targetJar.substring(1, targetJar.length() - 1);
					}
					if (targetJar.length() > 0) {
						if (curJar == null)  {
							curJar = new JarDependencies(targetJar);
							this.jarDependencies.put(targetJar, curJar);
						}
					}
 
				}
				curDeps = curJar.ensureDependencies(parts[0].trim());
				if (targetJar.length() > 0) {
					curDeps.setJar(targetJar);
				}

			} else if (line.trim().startsWith("-> ")) {
				String target = line.trim().substring(3);
				String[] parts = target.split("\\s+");
				String targetClassName = parts[0];
				String targetJar = "rt.jar";
				if (parts.length > 1) {
					targetJar = parts[1];
				}
				curDeps.getTargets().add(new ClassDepSpec(targetJar, targetClassName));
			}
		}

//		this.jarDependencies.getClassesByJarAndNamespace();

		System.out.println(archive.toString());

	}

	public JdepsResult getJarDependencies() {
		return jarDependencies;
	}

}
