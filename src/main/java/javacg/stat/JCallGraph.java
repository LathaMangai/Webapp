/*
 * Copyright (c) 2011 - Georgios Gousios <gousiosg@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package javacg.stat;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.bcel.classfile.ClassParser;

import javassist.bytecode.stackmap.TypeData.ClassName;

//import org.neo4j.graphdb.Node;


// end::hello-world-import[]

// tag::hello-world[]

/**
 * Constructs a callgraph out of a JAR archive. Can combine multiple archives
 * into a single call graph.
 *
 * @author Georgios Gousios <gousiosg@gmail.com>
 */
public class JCallGraph  {

	public static void main(String[] args) throws Exception {

		String results = " ";
		Function<ClassParser, ClassVisitor> getClassVisitor = (ClassParser cp) -> {
			try {
				return new ClassVisitor(cp.parse());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		};

		try {
			for (String arg : args) {

				File f = new File(arg);

				if (!f.exists()) {
					System.err.println("Jar file " + arg + " does not exist");
				}

				try (JarFile jar = new JarFile(f)) {
					Stream<JarEntry> entries = enumerationAsStream(jar.entries());

					String methodCalls = entries.flatMap(e -> {
						if (e.isDirectory() || !e.getName().endsWith(".class"))
							return (new ArrayList<String>()).stream();

						ClassParser cp = new ClassParser(arg, e.getName());
						return getClassVisitor.apply(cp).start().methodCalls().stream();
					}).map(s -> s + "\n").reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append)
							.toString();
					results = methodCalls;

					// BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
					FileWriter fw = new FileWriter(new File("d:/cloudify/jcg.txt"));
					BufferedWriter log = new BufferedWriter(fw);

					log.write(methodCalls);

					System.out.println(methodCalls);
					log.close();

				}

			}
	
		}
	
		catch (IOException e) {
			System.err.println("Error while processing jar: " + e.getMessage());
			e.printStackTrace();
		}

		String result1 = readFileAsData("d:/cloudify/jcg.txt");
		extractService(result1, args[0]);

	}

	public static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<T>() {
			public T next() {
				return e.nextElement();
			}

			public boolean hasNext() {
				return e.hasMoreElements();
			}
		}, Spliterator.ORDERED), false);
	}

	//private final Driver driver;
	private String methodCalls;

	

	public static String readFileAsData(String fileName) throws Exception {
		String extractdata = " ";
		extractdata = new String(Files.readAllBytes(Paths.get(fileName)));
		return extractdata;
	}
	
	public static void  extractService(String outputfile , String methodName ) {
		 
		try {
			   HashMap<String ,ArrayList<String>> extractdata = new HashMap<>();
			   extractdata.put(methodName, new ArrayList<String>());
	   	for(String control:outputfile.split("\n"))
	   	    {
	   		String className = control.split(":")[1];
	   		String method =control.split(":")[2];
	   		if(method.contains(methodName)) {
	   		if(!extractdata.get(methodName).contains(className))
	   		{
	   			//extractdata.put(methodName.split(" ")[0],new ArrayList<String>());
	   			extractdata.get(methodName).add(className);
	   		}
	   		
	   		}
	   	    }
	   	    
	   		System.out.println( extractdata);
		   }
	   		catch(Exception e) {
	   			System.out.println("Some thing is wrong with input!\n"+outputfile+ e.getMessage());
	   			e.printStackTrace();
	   		}	 
	}
	
//	public void getlocation(String extractdata) {
//		final URL location;
//		
//		final String classLocation = ClassName.class.getName().replace('.', '/')+".class";
//		
//		final ClassLoader loader = ClassName.class.getClassLoader();
//		if(loader == null) {
//			System.out.println("cannot load the class");
//		}
//		else {
//			location = loader.getResource(ClassName);
//			System.out.println("Class"+ location);
//		}
//		
//	}
	
	
	
}
	
	
	
	


