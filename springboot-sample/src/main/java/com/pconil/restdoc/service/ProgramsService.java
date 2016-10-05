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
package com.pconil.restdoc.service;

import com.pconil.restdoc.model.Program;

import java.util.List;


/**
 * Interface that services must implement to comply with controller.
 */
public interface ProgramsService {

    /**
     * Retrieves all the programs from EPG.
     * @param date the date to consider
     * @param application the application that request the data
     * @param channels list of channel to consider
     * @return List of programs
     */
    List<Program> findAllPrograms(String date, String application, List<String> channels);
}
