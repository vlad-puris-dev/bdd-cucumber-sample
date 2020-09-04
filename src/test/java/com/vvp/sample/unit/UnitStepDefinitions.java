package com.vvp.sample.unit;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.vvp.sample.model.Account;
import com.vvp.sample.model.AccountResponse;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class UnitStepDefinitions implements En {
    /**
     * Base URL.
     */
    private String baseUrl;
    /**
     * Path URL.
     */
    private String urlPath;
    /**
     * Server URL.
     */
    private static final String SERVER_URL = "localhost";
    /**
     * Mock server.
     */
    private WireMockServer wireMockServer;
    /**
     * Mock http client.
     */
    private CloseableHttpClient httpClient;
    /**
     * Http response.
     */
    private HttpResponse response;

    /**
     * Constructor.
     */
    public UnitStepDefinitions() {
        Before(() -> {
            urlPath = null;
            response = null;
            httpClient = HttpClients.createDefault();
            wireMockServer = new WireMockServer(options()
                    .withRootDirectory("src/test/resources")
                    .dynamicPort());
            wireMockServer.start();
            configureFor(SERVER_URL, wireMockServer.port());
            baseUrl = "http://" + SERVER_URL + ":" + wireMockServer.port();
        });

        After(() -> {
            this.wireMockServer.stop();
        });

        Given("^path '(.*)'$", (final String path) -> {
            urlPath = "/" + path;
            ResponseDefinitionBuilder aResponse = new ResponseDefinitionBuilder();
            aResponse.withHeader("Content-Type", "application/json");
            switch (path) {
                case "error" :
                    aResponse.withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    break;
                case "v1/accounts/0123456789" :
                    aResponse.withBody(readFile("/data/unitAccountResponse.json"))
                        .withStatus(HttpStatus.OK.value());
                    break;
                case "v1/accounts" :
                    aResponse.withBody(readFile("/data/unitAccountsResponse.json"))
                        .withStatus(HttpStatus.OK.value());
                    break;
                default :
                    break;
            }
            stubFor(get(urlEqualTo(urlPath)).willReturn(aResponse));
        });

        When("^method (.*)$", (final String method) -> {
            response = httpClient.execute(new HttpGet(baseUrl + urlPath));
        });

        Then("^status (.*)$", (final String responseStatus) -> {
            assertEquals(responseStatus, String.valueOf(response.getStatusLine().getStatusCode()));
        });

        Then("^match response '(.*)'$", (final String responsePath) -> {
            assertEquals(getResponseContent(response), readFile(responsePath));
        });

        Then("^match response.accounts contains expected $expectedAccounts", (final DataTable expectedAccounts) -> {
            assertEquals(Arrays.asList(new ObjectMapper().convertValue(expectedAccounts.transpose().asMaps(),
                                    Account[].class)).toString().replace("\\u0027", ""),
                            new ObjectMapper().readValue(getResponseContent(response),
                                    AccountResponse.class).getAccounts().toString());
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

    /**
     * Return response body content.
     * @param response response
     * @return response body content
     * @throws Exception exception thrown during response body content read
     */
    private static String getResponseContent(final HttpResponse response) throws Exception {
        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    }
}
