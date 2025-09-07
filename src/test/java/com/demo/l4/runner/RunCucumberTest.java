package com.demo.l4.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
  features = "src/test/resources/features",
  glue = {"com.demo.l4.stepdefs", "com.demo.l4.pages", "com.demo.l4.context"},
  plugin = {"pretty", "html:target/cucumber-html-report.html", "json:target/cucumber.json"},
  monochrome = true
)
public class RunCucumberTest extends AbstractTestNGCucumberTests { }