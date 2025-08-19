package com.rvm.test.l2.assetion;

import org.testng.Assert;
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

public class CountAssertionsDemo {

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
		fillForm("standard_user","secret_sauce");
	}

	@Test
	public void test_exact_count_assertion() {
		Locator inventory_items = page.locator("div.inventory_item");
		assertThat(inventory_items).hasCount(6);
	}

	@Test
	public void test_greater_than_assertion() {
		Locator inventory_items = page.locator("div.inventory_item");
		int count = inventory_items.count();
		Assert.assertTrue(count > 3, "Expected more than 3 items, but found " + count);
		
	}

	private void waiting(int mils) {
		try {
			Thread.sleep(mils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void fillForm(String user, String password) {
		page.fill("#user-name", user);
		page.fill("#password", password);
		page.click("#login-button");
	}

}
