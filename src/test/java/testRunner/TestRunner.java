package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/TestCases.feature",
        glue = "stepDefinitions",
        plugin = {"pretty", "html:output/cucumber-pretty"})
public class TestRunner {
}
