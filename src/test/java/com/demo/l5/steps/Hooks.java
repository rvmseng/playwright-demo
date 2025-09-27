package com.demo.l5.steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.demo.l5.context.TestContext;

public class Hooks {
	private final TestContext testContext;

	// پاپ کانتینر  این سازنده را می‌بیند و همان نمونه TestContext را به تمام
	// StepDefs تزریق می‌کند
	public Hooks(TestContext testContext) {
		this.testContext = testContext;
	}

	@Before
	public void beforeScenario() {
		// headless را از property می‌توان گرفت؛ در این مثال false برای دمو
		testContext.init(false);
	}

	@After
	public void afterScenario() {
		testContext.quit();
	}
}
