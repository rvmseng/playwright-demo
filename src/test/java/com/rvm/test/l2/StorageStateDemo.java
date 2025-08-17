package com.rvm.test.l2;

import java.nio.file.Paths;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class StorageStateDemo {

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

	@Test
	public void test_save_storage_state() {
		context = browser.newContext();
		page = context.newPage();

		page.navigate("https://www.saucedemo.com/");

		page.fill("#user-name", "standard_user");
		page.fill("#password", "secret_sauce");
		page.click("#login-button");

		waiting(2000);
		// Save storage state into a file
		context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("storage-state.json")));
	}

	@Test
	public void test_load_storage_state() {
		// Load storage state from a file
		context = browser
				.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get("storage-state.json")));
		page = context.newPage();

		page.navigate("https://www.saucedemo.com/inventory.html"); // directly navigate to a inventory page
		waiting(4000);

		System.out.println("Page URL: " + page.url());

	}

	private void waiting(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
