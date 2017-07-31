package com.autonubil.analyzer.maven.plugin;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.shared.dependency.graph.DependencyNode;

import com.autonubil.analyzer.dependencies.ClassDepSpec;
import com.autonubil.analyzer.dependencies.ClassDependencies;
import com.autonubil.analyzer.dependencies.JarDependencies;
import com.autonubil.analyzer.dependencies.JdepsResult;

public class Artifacts extends ArrayList<DependencyNode> {

	private static final long serialVersionUID = -8573629842840170138L;

	public DependencyNode ensure(DependencyNode node) {
		if (!this.contains(node)) {
			this.add(node);
		}
		return node;
	}

	public void write(PrintWriter writer, JdepsResult jarDependencies) {
		for (DependencyNode node : this) {
			if ((node.getArtifact() == null) || (node.getArtifact().getFile() == null)) {
				continue;
			}
			java.util.Map<String, List<ClassDepSpec>> namespaceClasses = jarDependencies.getClassesByJarAndNamespace()
					.get(node.getArtifact().getFile().getName());

			if (namespaceClasses != null) {

				JarDependencies deps = jarDependencies.get(node.getArtifact().getFile().getName());

				for (String namespace : namespaceClasses.keySet()) {
					List<String> classes = new ArrayList<String>();
					for (ClassDepSpec classSpec : namespaceClasses.get(namespace)) {
						if ((jarDependencies.getClassFilter() == null)
								|| (jarDependencies.getClassFilter().accept(classSpec.getClassName()))) {
							classes.add(classSpec.getShortClassName());
						}
					}

					if (classes.size() > 0) {
						writer.println("  namespace " + namespace + " {");

						for (String className : classes) {
							writer.println("   class " + className);
						}

						writer.println("  }");
					}
				}
			} else {
				/// System.err.println(node.getArtifact().getFile().getName());
			}

		}
	}

	public void writeDeps(PrintWriter writer, JdepsResult jarDependencies) {
		for (DependencyNode node : this) {
			if ((node.getArtifact() == null) || (node.getArtifact().getFile() == null)) {
				continue;
			}
			java.util.Map<String, List<ClassDepSpec>> namespaceClasses = jarDependencies.getClassesByJarAndNamespace()
					.get(node.getArtifact().getFile().getName());

			if (namespaceClasses != null) {
				JarDependencies deps = jarDependencies.get(node.getArtifact().getFile().getName());
				for (String namespace : namespaceClasses.keySet()) {
					if ((deps != null) && (deps.getDependencies() != null)) {
						for (ClassDependencies classDeps : deps.getDependencies().values()) {
							for (ClassDepSpec targetSpec : classDeps.getTargets()) {

								// on must pass the filter
								if ((jarDependencies.getClassFilter() == null) || (jarDependencies.getClassFilter()
										.accept(classDeps.getClassName())
										&& jarDependencies.getClassFilter().accept(targetSpec.getClassName()))) {
									String link = classDeps.getClassName() + " <-- " + targetSpec.getClassName();
									
									if (jarDependencies.isNewLink(link)) {
										writer.println(link);
										jarDependencies.isNewLink(node.getArtifact().getGroupId() +" <-- " +targetSpec.getJar()); 
									}
								}

							}
						}
					}

				}

			}
			
			
		}
	}
}
