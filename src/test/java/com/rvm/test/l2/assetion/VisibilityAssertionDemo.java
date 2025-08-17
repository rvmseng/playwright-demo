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

public class VisibilityAssertionDemo {

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
	public void test_message_visible_assertion() {
		fillForm("standard", "secret_sauce");
		Locator message=page.locator("[data-test=\"error\"]");
		
		assertThat(message).isVisible();
		waiting(3000);
	}
	
	@Test
	public void test_message_hidden_assertion() {
		fillForm("standard", "secret_sauce");
		Locator message=page.locator("[data-test=\"error\"]");
		
		page.locator("[data-test=\"error-button\"]").click();
		
		assertThat(message).isHidden();
		waiting(2000);
	}
	
	@Test
	public void test_assertThat_loginButton_is_enable() {
		assertThat(page.locator("#login-button")).isEnabled();
		waiting(1000);
	}

	private void fillForm(String user, String password) {
		page.fill("#user-name", user);
		page.fill("#password", password);
		page.click("#login-button");
	}
	
	private void waiting(int mils) {
		try {
			Thread.sleep(mils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
