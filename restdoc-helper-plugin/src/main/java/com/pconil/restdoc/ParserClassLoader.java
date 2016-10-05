/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pconil.restdoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * This class is used to include directory in classpath and to load java .class.
 */
class ParserClassLoader {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserClassLoader.class);

    /**
     * The previous Classloader that must be used to restore context.
     */
    private ClassLoader previous = null;
    
    /**
     * The current one used to load and parse classes. 
     */
    private ClassLoader current = null;

    /**
     * Constructor that adds the URL sourceDir to the classpath. 
     * @param sourceDir where to find classes
     * @throws MalformedURLException if something goes wrong with URL construction.
     */
    ParserClassLoader(File sourceDir) throws MalformedURLException {
        previous = Thread.currentThread().getContextClassLoader();
        current = changeLoader(new URL[]{new URL("file:" + sourceDir.getAbsolutePath() + "/")});
    }
    
    /**
     * Adds urls to classLoader.
     *
     * @param urls urls to add to classpath
     * @return updated ClassLoader
     * @throws MalformedURLException if one of urls is bad formatted
     */
    private ClassLoader changeLoader(URL[] urls) throws MalformedURLException {

        // Create class loader using given codebase
        // Use prevCl as parent to maintain current visibility
        ClassLoader newCl = URLClassLoader.newInstance(urls, previous);

        // Sets class loader 
        Thread.currentThread().setContextClassLoader(newCl);

        return newCl;
    }


    /**
     * Restores previous classLoader.
     * @throws MalformedURLException if something goes wrong while restoring classloader
     */
    void restoreLoader() throws MalformedURLException {

        ClassLoader tmp = current;
        Thread.currentThread().setContextClassLoader(previous);
        current = previous;
        previous = tmp;
        
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @param dir the directory where to find classes
     * @return The classes
     * @throws ClassNotFoundException if no class are found
     * @throws IOException            if an IO error occur
     */
    List<Class> getClasses(String dir, String packageName)
            throws ClassNotFoundException, IOException {

        ArrayList<Class> classes = new ArrayList<>();
        try {
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = current.getResources(path);
            List<File> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }

            for (File dire : dirs) {
                classes.addAll(findClasses(current, dire, packageName));
            }

            //List<Class> mesClasses = findClasses(cl, directory, "" );
            for (Class clazz : classes) {
                LOGGER.debug("Found class {}", clazz.getSimpleName());
            }
        } catch (IOException cnfe) {
            cnfe.printStackTrace();
        }
        return classes;
    }


    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param cl          The ClassLoader to load class into
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException if no class are found
     */
    private List<Class> findClasses(ClassLoader cl, File directory,
                                           String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        String classSuffix = ".class";
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                if ("".equals(packageName)) {
                    classes.addAll(findClasses(cl, file, packageName + file.getName()));
                } else {
                    classes.addAll(findClasses(cl, file, packageName + "." + file.getName()));
                }
            } else if (file.getName().endsWith(classSuffix)) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length()
                                                                                   - classSuffix.length());
                try {
                    classes.add(cl.loadClass(className));
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    LOGGER.error("Unable to load classes");
                }
            }
        }
        return classes;
    }

}
