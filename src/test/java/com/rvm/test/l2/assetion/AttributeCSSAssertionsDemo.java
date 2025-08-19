package com.rvm.test.l2.assetion;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AttributeCSSAssertionsDemo {

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

	@AfterMethod
	public void afterMethod() {
		waiting(1000);

		if (page != null) {
			page.close();
		}
		if (context != null) {
			context.close();
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		context = browser.newContext();
		page = context.newPage();

		page.navigate("https://www.saucedemo.com/");
	}

	@Test
	public void test_attribute_assertion() {
		Locator username = page.getByPlaceholder("Username");

		assertThat(username).hasAttribute("autocorrect", "off");
	}

	@Test
	public void test_class_assertion() {
		Locator loginButton = page.locator("#login-button");

		assertThat(loginButton).hasClass("submit-button btn_action");
		
	}

	private void waiting(int mils) {
		try {
			Thread.sleep(mils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
