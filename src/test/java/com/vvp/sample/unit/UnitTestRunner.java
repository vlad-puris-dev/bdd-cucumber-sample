package com.vvp.sample.unit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@ActiveProfiles("test")
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/account.feature",
        tags = "not @ignore",
        plugin = {"pretty",
                  "json:target/cucumber/unit/account.json",
                  "junit:target/cucumber/unit/account.xml",
                  "html:target/cucumber/unit/account.html"})
public class UnitTestRunner {
    /**
     * Generate rich format html report with unit tests results.
     */
    @AfterClass
    public static void generateReport() {
        Collection<File> jsonFiles = FileUtils.listFiles(
                new File("target/cucumber/unit/"), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(
                new File("target/output/testing-reports/unit"), "account");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}
