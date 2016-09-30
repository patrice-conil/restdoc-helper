package com.orange.otml.controller;

import com.orange.otml.SpringbootSampleApplication;
import com.orange.otml.model.Program;
import com.orange.otml.model.ProgramFieldDescriptor;
import com.orange.otml.service.ProgramsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Unit test that document your API.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootSampleApplication.class)
@WebAppConfiguration
public class ProgramControllerTest {
    private MockMvc mockMvc;

    @Mock
    ProgramsService programsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    @Autowired
    private ProgramController programController;
    

    @Rule
    //Need to generate in parent project dir to collect all needed data to produce html
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler restDocumentationResultHandler;


    @Before
    public void setUp() throws Exception {
        // Creates snippets named using method-name expansion
        restDocumentationResultHandler = document("{method-name}", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        MockitoAnnotations.initMocks(this);
        this.mockMvc = webAppContextSetup(webApplicationContext).apply(documentationConfiguration(restDocumentation))
                .alwaysDo(restDocumentationResultHandler).build();

    }

    @After
    public void tearDown() throws Exception {
        //Reset Mock
       
    }
    
    
    @Test
    public void getPrograms() throws Exception {
        
        when(programsService.findAllPrograms(any(), any(), any()))
                .thenReturn(findAllPrograms(LocalDate.now().toString(), "PC", Collections.singletonList("19")));
        
        mockMvc.perform(get("/v1/programs").contentType(MediaType.APPLICATION_JSON_UTF8).header("Host", "localhost")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                /*
                 * Here's the magic doc
                 */
                .andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(
                                /*
                                 * Here we use the java code generated accordingly to swagger annotations
                                 */
                                ProgramFieldDescriptor.fdProgramList
                        ))
                )
                /*
                 * Expect your response is valid
                 */
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].programType").value("EPISODE"))
                .andExpect(status().isOk());
        
        //Checks that snippet exists
        assertEquals(true, new File("target/generated-snippets/get-programs").exists());
    }
    
    @Test
    public void getProgramsWithBadDate() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("date", "badDate");

        mockMvc.perform(get("/v1/programs").contentType(MediaType.APPLICATION_JSON_UTF8).header("Host", "localhost")
                .params(params)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                /*
                 * Here's the magic doc
                 */
                .andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        )
                )
                // Expect your response is an error
                .andExpect(status().is4xxClientError());

        //Checks that snippet exists
        assertEquals(true, new File("target/generated-snippets/get-programs").exists());
    }
    
    
    private List<Program> findAllPrograms(String date, String application, List<String> channels) {

        Program programLight = new Program("0", "EPISODE", "House of cards",
                "Canal+", 0L, 2400L, 1L, "comedy", "romantic comedy", "a beautiful synopsis",
                "VF", false, false, 2L, "1", "HD", null, "PT1",
                "catchupId");
        List<Program> programLights = new ArrayList<>();
        programLights.add(programLight);
        programLight = new Program("0", "EPISODE", "House of cards", "Canal+", 1L,
                2400L, 1L, "comedy", "romantic comedy", "a beautiful synopsis", "VF", false, false,
                2L, "2", "HD", null, "PT1", "catchupId");
        programLights.add(programLight);
        return programLights;
    }

}