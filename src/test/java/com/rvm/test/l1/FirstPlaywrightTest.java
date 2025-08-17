package com.rvm.test.l1;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FirstPlaywrightTest {
	static Playwright playwright;
	static Browser browser;
	static BrowserContext context;
	static Page page;

	@BeforeClass
	public void setup() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		context = browser.newContext();
		page = context.newPage();
	}

	@Test
	public void testGoogleSearch() {
		page.navigate("https://www.google.com");
		page.fill("textarea[name='q']", "Playwright Java");
		page.keyboard().press("Enter");
		page.waitForTimeout(2000);
		System.out.println("Page title: " + page.title());
	}

	@AfterClass
	public void tearDown() {
		browser.close();
		playwright.close();
	}
	

	
	
}
