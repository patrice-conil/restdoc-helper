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

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utilities.
 * 
 * @author  patrice_conil
 */
public class Utils {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    /**
     * Load a resource as an UTF-8 string.
     *
     * @param name name of the resource
     * @return the content of the resource
     */
     public static String loadResource(String name) {
        try {
            URL url = Utils.class.getClassLoader().getResource(name);
            if (url == null) {
                return "";
            }
            return new String(Files.readAllBytes(Paths.get(url.toURI())), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }


}
