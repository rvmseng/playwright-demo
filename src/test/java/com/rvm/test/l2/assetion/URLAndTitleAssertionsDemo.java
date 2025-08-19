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
import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

public class URLAndTitleAssertionsDemo {

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
	public void test_title_assertion() {
		assertThat(page).hasTitle("Swag Labs");
	}

	@Test
	public void test_url_assertion() {
		fillForm("standard_user", "secret_sauce");

		assertThat(page).hasURL(Pattern.compile(".*inventory\\.html$"));
	}

	@Test
	public void test_multi_assertion() {
		fillForm("standard_user", "secret_sauce");
		Locator title = page.locator(".title");

		assertThat(title).hasText("Products", new LocatorAssertions.HasTextOptions().setTimeout(5000));
		assertThat(page.locator(".inventory_item_price").nth(0)).hasText("$29.99");
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
