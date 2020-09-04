package com.vvp.sample.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vvp.sample.AccountApplication;
import com.vvp.sample.model.Account;
import com.vvp.sample.model.AccountResponse;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
public class IntegrationStepDefinitions implements En {
    /**
     * Path URL.
     */
    private String urlPath;
    /**
     * Response content.
     */
    private ResponseEntity<String> response;
    /**
     * Server URL.
     */
    private static final String BASE_URL = "http://localhost:6001";
    /**
     * Account response.
     */
    private AccountResponse accountResponse;
    /**
     * Test rest template.
     */
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Constructor.
     */
    public IntegrationStepDefinitions() {
        Before(() -> {
            urlPath = null;
        });

        After(() -> {
        });

        Given("^path '(.*)'$", (final String path) -> {
            urlPath = "/" + path;
        });

        When("^method (.*)$", (final String method) -> {
         response = restTemplate.getForEntity(BASE_URL + urlPath, String.class);
         if (!response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
             accountResponse = new ObjectMapper().readValue(response.getBody(), AccountResponse.class);
         }
        });

        Then("^status (.*)$", (final String responseStatus) -> {
            assertEquals(responseStatus, String.valueOf(response.getStatusCodeValue()));
        });

        Then("^match response.(.*) == (.*)$", (final String expectedParamName, final String expectedParamValue) -> {
            String paramValue = "";
            switch (expectedParamName) {
                case "accountId" :
                    paramValue = accountResponse.getAccountId();
                    break;
                case "status" :
                    paramValue = accountResponse.getStatus();
                    break;
                case "errorMessage" :
                    paramValue = accountResponse.getErrorMessage();
                    break;
                default :
                    break;
        }
        assertEquals(expectedParamValue.replace("'", ""), String.valueOf(paramValue));
        });

        Then("^match response '(.*)'$", (final String responsePath) -> {
            assertEquals(accountResponse.toString(), readFile(responsePath).replaceAll("\\s", ""));
        });

        Then("^match response.accounts contains expected$", (final DataTable expectedAccounts) -> {
            assertEquals(Arrays.asList(new ObjectMapper().convertValue(expectedAccounts.transpose().asMaps(),
                    Account[].class)).toString().replace("\\u0027", ""),
                    accountResponse.getAccounts().toString());
        });
    }

    /**
     * Return file content.
     * @param filePath path to file
     * @return file content
     * @throws IOException exception thrown during file read
     */
    private static String readFile(final String filePath) throws IOException {
        return IOUtils.toString(new ClassPathResource(filePath).getInputStream(), StandardCharsets.UTF_8);
    }
}
