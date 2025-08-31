package com.rvm.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
  features = "src/test/resources/features",
  glue = {"com.rvm.stepdefs", "com.rvm.context"},
  plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-html-report"},
  monochrome = true
)

public class RunCucumberTest extends AbstractTestNGCucumberTests {
	// اگر نیاز به پیکربندی تست ان جی  خاص دارید، می‌توانید این کلاس را توسعه دهید.
}
