package com.rvm.test.l2;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

public class BrowserTypesDemo {

	private Playwright playwright;

	@BeforeTest
	public void setup() {
		playwright = Playwright.create();
	}

	@AfterTest
	public void teardown() {
		if (playwright != null) {
			playwright.close();
		}
	}

	@Test
	public void test_chromium() throws InterruptedException {
		LaunchOptions lo = new BrowserType.LaunchOptions();
		lo.setHeadless(false); // اجرای در حالت آHeaded

		BrowserType bt = playwright.chromium();
		Browser chrome = bt.launch(lo);

		BrowserContext context = chrome.newContext();

		Page page = context.newPage();
		page.navigate("https://example.com");
		page.waitForLoadState(LoadState.NETWORKIDLE);

		waiting(2000);// Wait for 2 seconds to ensure the page loads completely
		System.out.println("Chromium Browser Title: " + page.title());

		context.close();
		chrome.close();
	}

	@Test
	public void test_edge() throws InterruptedException {
		LaunchOptions lo = new BrowserType.LaunchOptions();
		lo.setHeadless(false); // اجرای در حالت آHeaded
		lo.setChannel("msedge"); // نام کانال Edge در نسخه‌های جدید

		BrowserType bt = playwright.chromium();
		Browser chrome = bt.launch(lo);

		BrowserContext context = chrome.newContext();

		Page page = context.newPage();
		page.navigate("https://example.com");

		waiting(2000);// Wait for 2 seconds to ensure the page loads completely
		System.out.println("Chromium Browser Title: " + page.title());

		context.close();
		chrome.close();
	}

	@Test
	public void test_firefox() throws InterruptedException {
		LaunchOptions lo = new BrowserType.LaunchOptions();
		lo.setHeadless(false); // اجرای در حالت آHeaded

		BrowserType bt = playwright.firefox();
		Browser firefox = bt.launch(lo);

		BrowserContext context = firefox.newContext();

		Page page = context.newPage();
		page.navigate("https://example.com");

		waiting(2000);// Wait for 2 seconds to ensure the page loads completely
		System.out.println("Chromium Browser Title: " + page.title());

		context.close();
		firefox.close();
	}

	@Test
	public void test_webkit() throws InterruptedException {
		LaunchOptions lo = new BrowserType.LaunchOptions();
		lo.setHeadless(false); // اجرای در حالت آHeaded

		BrowserType bt = playwright.webkit();
		Browser safari = bt.launch(lo);

		BrowserContext context = safari.newContext();

		Page page = context.newPage();
		page.navigate("https://example.com");

		waiting(2000);// Wait for 2 seconds to ensure the page loads completely
		System.out.println("Chromium Browser Title: " + page.title());

		context.close();
		safari.close();
	}

	@Test
	public void test_chromium_headless_mode() throws InterruptedException {
		LaunchOptions lo = new BrowserType.LaunchOptions();
		lo.setHeadless(true); // اجرای در حالت headless

		BrowserType bt = playwright.chromium();
		Browser chrome = bt.launch(lo);

		BrowserContext context = chrome.newContext();

		Page page = context.newPage();
		page.navigate("https://example.com");
		page.waitForLoadState(LoadState.NETWORKIDLE);

		waiting(2000);// Wait for 2 seconds to ensure the page loads completely
		System.out.println("Chromium Browser Title: " + page.title());

		context.close();
		chrome.close();
	}

	@Test
	public void test_BrowserContext_options() {
		LaunchOptions lo = new BrowserType.LaunchOptions();
		lo.setHeadless(false); // اجرای در حالت headless

		BrowserType bt = playwright.chromium();
		Browser chrome = bt.launch(lo);

		NewContextOptions contextOptions = new Browser.NewContextOptions()
				.setViewportSize(320, 685)
				.setIgnoreHTTPSErrors(true) // بی‌توجهی به خطاهای TLS
				.setExtraHTTPHeaders(java.util.Map.of("X-Test", "true"));

		BrowserContext context = chrome.newContext(contextOptions);

		Page page = context.newPage();
		page.navigate("https://my.irancell.ir/");
		page.waitForLoadState(LoadState.NETWORKIDLE);

		waiting(2000);// Wait for 2 seconds to ensure the page loads completely
		System.out.println("Chromium Browser Title: " + page.title());

		context.close();
		chrome.close();
	}

	/*
	 * Custom method for waiting This method is used to pause the execution for a
	 * specified number of milliseconds.
	 * 
	 * @param mils The number of milliseconds to wait.
	 */
	public void waiting(long mils) {
		try {
			Thread.sleep(mils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
