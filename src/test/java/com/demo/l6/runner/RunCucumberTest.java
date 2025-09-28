package com.demo.l6.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
  features = "src/test/resources/features",
  glue = {"com.demo.l5.steps", "com.demo.l5.pages", "com.demo.l5.context"},
  plugin = {"pretty", "html:target/l5/cucumber-html-report.html", "json:target/l5/cucumber.json"},
  monochrome = true
)
public class RunCucumberTest extends AbstractTestNGCucumberTests { }