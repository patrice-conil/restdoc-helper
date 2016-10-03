import com.orange.otml.SpringbootSampleApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Adoc generation from swagger /v2/api-docs.
 * This test produces 3 docs : definitions.adoc, overview.adoc, paths.adoc.
 * These docs can be referenced by other adoc files to produce more precise documentation on your API
 * (for example sequence diagram, or other additional information).
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootSampleApplication.class)
@ActiveProfiles("test")
@WebAppConfiguration
public class SwaggerStaticDocGeneratorTest {

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    /**
     * Calls Swagger2MarkupResultHandler to generate adoc files from swagger.json/swagger.yaml.
     * @throws Exception if something goes wrong
     */
    @Test
    public void generateAsciiDoc() throws Exception {
        String outDir = System.getProperty("asciiDocOutputDir", "target/generated-snippets");
        Swagger2MarkupResultHandler resultHandler = Swagger2MarkupResultHandler
                .outputDirectory(outDir)
                .build();
        this.mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
                .andDo(resultHandler)
                .andExpect(status().isOk());
        
        assertTrue(allDocsAreGenerated(outDir));
    }

    /**
     * Verify that the three expected docs are generated.
     * definitions.adoc, overview.adoc, paths.adoc
     * @param outDir the directory where the doc must be generated
     * @return true if the three docs are present
     */
    private boolean allDocsAreGenerated(String outDir) {
        boolean result = true;
        String[] names = {"overview.adoc", "paths.adoc", "definitions.adoc"};
        List<String> fileNames = Arrays.asList(names);
        for (String file : fileNames) {
            if (!new File(outDir + '/' + file).exists()) {
                result = false;
            }
        }
        return result;
    }
}