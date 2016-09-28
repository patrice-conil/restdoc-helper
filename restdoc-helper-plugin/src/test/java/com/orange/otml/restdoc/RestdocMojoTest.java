package com.orange.otml.restdoc;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.codehaus.plexus.PlexusTestCase.getTestFile;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Mojo execution test.
 */
@RunWith(JUnit4.class)
public class RestdocMojoTest {

    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
        }

        @Override
        protected void after() {
        }
    };
    
    @Test
    public void execute() throws Exception {
        File pom = getTestFile("src/test/resources/pom.xml");
        assertNotNull(pom);
        assertTrue(pom.exists());
        RestdocMojo myMojo = (RestdocMojo) rule.lookupMojo("restdoc-helper", pom);
        assertNotNull(myMojo);
        myMojo.execute();
    }
    
}