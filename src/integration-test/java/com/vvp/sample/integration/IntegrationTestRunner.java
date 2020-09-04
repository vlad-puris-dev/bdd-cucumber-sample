package com.vvp.sample.integration;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ActiveProfiles("test")
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/account_integration.feature",
        tags = "not @ignore",
        plugin = {"pretty",
                  "json:target/cucumber/integration/account.json",
                  "junit:target/cucumber/integration/account.xml",
                  "html:target/cucumber/integration/account.html"})
public class IntegrationTestRunner {
    /**
     * Generate rich format html report with unit tests results.
     */
    @AfterClass
    public static void generateReport() {
        Collection<File> jsonFiles = FileUtils.listFiles(
                new File("target/cucumber/integration/"), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(
                new File("target/output/testing-reports/integration"), "account");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}
