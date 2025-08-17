package com.rvm.test.l2;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class IsolationDemo {

	private Playwright playwright;
	private Browser browser;

	private BrowserContext context;
	private Page page;

	@BeforeTest
	public void beforeTest() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions() // optional arguments
				.setHeadless(false) // headed mode run
				.setSlowMo(200)); // Slow down operations by 50ms

	}
	
	@AfterTest
	public void afterTest() {
		if (browser != null) {
			browser.close();
		}
		if (playwright != null) {
			playwright.close();
		}
	}

	@Test
	public void test1() {
		context = browser.newContext();
		page = context.newPage();

		page.navigate("https://www.saucedemo.com/");

		page.close();
		context.close();
	}

	@Test
	public void test2() {
		context = browser.newContext();
		page = context.newPage();

		page.navigate("https://automationintesting.online/");

		page.close();
		context.close();
		
	}

}
