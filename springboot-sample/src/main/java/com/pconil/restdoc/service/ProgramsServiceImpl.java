package com.pconil.restdoc.service;


import com.pconil.restdoc.model.Program;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that retrieves program resources.
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
