package com.autohero.bdd;

import static org.junit.Assert.assertTrue;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"html:target/cucumber-report", "json:target/cucumber.json"}, features = "src/test/resources/features", glue = "com.autohero.bdd.steps")
public class CucumberIntegrationTest {

}
