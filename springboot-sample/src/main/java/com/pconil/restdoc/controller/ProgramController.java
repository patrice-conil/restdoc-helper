package com.pconil.restdoc.controller;

import com.pconil.restdoc.model.Constant;
import com.pconil.restdoc.model.Program;
import com.pconil.restdoc.service.ProgramsService;
import com.pconil.restdoc.exception.SampleException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Controller that manages TV-EPG resources.
 */
@Controller
@RequestMapping(value = "/v1", produces = {APPLICATION_JSON_UTF8_VALUE})
@Api(value = "/programs", tags = {"/v1/programs"}, description = "the programs API")
public class ProgramController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramController.class);

    /**
     * Programs com.pconil.restdoc.service.
     */
    @Autowired
    private ProgramsService programsService;


    /**
     * Default constructor.
     */
    public ProgramController() {
    }

    /**
     * Get a list of programs (light information) for a range of channels (one, a list with commas or all)
     * and a specific date formatted as 'YYYY-MM-DD'.
     *
     * @param date        date: a specific date formatted as 'YYYY-MM-DD'
     * @param application application requesting
     * @param channels    channels for which you need to retrieve programs
     * @param offset      offset of first program to retrieve
     * @param limit       maximum number of items to retrieve
     * @return list of program summaries
     */
    @RequestMapping(value = "/programs", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get a list of programs given a date for a given range of channels possibly",
            notes = "Get a list of programs for a range of channels (one, a list with commas or "
                    + "all) a specific date formatted as 'YYYY-MM-DD'.",
            response = Program.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = Constant.OK, message = "OK", response = Program.class, responseContainer = "List",
                    responseHeaders = {
                            @ResponseHeader(name = "X-Result-Count",
                                    description = "The actual number of items contained in the response body.",
                                    response = String.class),
                            @ResponseHeader(name = "X-Total-Count",
                                    description = "The total number of items in the collection.", response = String.class),
                            @ResponseHeader(name = "Cache-Control[max-age,public]", 
                                    description = "Contains max-age in seconds", response =
                                    String.class),
                            @ResponseHeader(name = "ETag", description = "The Entity Tag", response = String.class)}),
            @ApiResponse(code = Constant.BAD_REQUEST, message = "Bad Request", response = Error.class),
            @ApiResponse(code = Constant.INTERNAL_EROR, message = "Internal Server Error", response = Error.class)})
    public ResponseEntity<List<Program>> getPrograms(
            @RequestParam(value = "date", required = false, defaultValue = "current") String date,
            @RequestParam(value = "application", required = false, defaultValue = "PC") String application,
            @RequestParam(value = "channels", required = false, defaultValue = "all") List<String> channels,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {

        LOGGER.info("[getPrograms] date={}, application={}, channels={}, offset={}, limit={}",
                date, application, channels.toString(), offset, limit);
        if (date.equals("current")) {
            date = LocalDate.now().toString();
        } else {
            try {
                LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                throw new SampleException("Bad date format");
            }
        }
        List<Program> lightPaginated = programsService.findAllPrograms(date, application, channels);
        ResponseEntity<List<Program>> responseEntity = ResponseEntity.ok()
                .body(lightPaginated);
        LOGGER.debug("[getPrograms] response = {}", responseEntity.toString());
        return responseEntity;
    }




}
