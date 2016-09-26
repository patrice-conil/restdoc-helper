package com.orange.otml.service;

import com.orange.otml.model.Program;

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
