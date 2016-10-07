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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Generate restdoc files and java fieldDescriptor classes.
 * 
 * @author  patrice_conil
 */
@Mojo(name = "restdoc-helper")
public class RestdocMojo extends AbstractMojo {

    /**
     * Where we want to start searching sources.
     */
    @Parameter
    private String basePackageName;

    /**
     * Where to find sources (including subdirs).
     */
    @Parameter(defaultValue = "target/classes/")
    private String sourceDir;

    /**
     * Where to write adoc snippets.
     */
    @Parameter(defaultValue = "target/generated-snippets")
    private String adocDir;
    
    /**
     * Where to write java sources.
     */
    @Parameter(defaultValue = "target/generated-test-sources")
    private String javaDir;
    
    
    /**
     * Launches the parsers to generate restdoc and java files.
     * @throws MojoExecutionException if something goes wrong
     */
    public void execute() throws MojoExecutionException {
        
        getLog().info("restdoc-helper starts with basePackageName=" + basePackageName + ", sourceDir=" + sourceDir 
                      + ", adocDir=" + adocDir + ", javaDir=" + javaDir + "\n");
        Annotation2JavaParser parser = new Annotation2JavaParser(basePackageName, adocDir, javaDir, sourceDir);
        parser.parse();
        
        Annotation2AsciiDocParser annotation2AsciiDocParser = new Annotation2AsciiDocParser(basePackageName, adocDir, javaDir, sourceDir);
        annotation2AsciiDocParser.parse();
        getLog().info("restdoc-helper ends.");
    }

}


