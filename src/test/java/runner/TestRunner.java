package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/FunctionalTests",
        glue = {"stepDefinitions"},
        monochrome = true,
        strict = true
)



public class TestRunner {

}
