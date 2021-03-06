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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that retrieves program resources.
 * 
 * @author  patrice_conil
 */
@Service("programsService")
public class ProgramsServiceImpl implements ProgramsService {


    /**
     * Constructor.
     */
    public ProgramsServiceImpl() {
    }

    @Override
    public List<Program> findAllPrograms(String date, String application, List<String> channels) {

            Program programLight = new Program("0", "EPISODE", "House of cards",
                    "Canal+", 0L, 1L, 1L, "comedy", "romantic comedy", "a beautiful synopsis",
                    "VF", false, false, 2L, "1", "HD", null, "PT1",
                    "catchupId");
            List<Program> programLights = new ArrayList<>();
            programLights.add(programLight);
            programLight = new Program("0", "EPISODE", "House of cards", "Canal+", 1L,
                    1L, 1L, "comedy", "romantic comedy", "a beautiful synopsis", "VF", false, false,
                    2L, "2", "HD", null, "PT1", "catchupId");
            programLights.add(programLight);
            return programLights;
        }
}
