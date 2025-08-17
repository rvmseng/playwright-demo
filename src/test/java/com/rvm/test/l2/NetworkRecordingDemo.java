package com.rvm.test.l2;

import java.nio.file.Paths;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class NetworkRecordingDemo {

	private Playwright playwright;
	private Browser browser;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		playwright = Playwright.create();

		LaunchOptions lo = new BrowserType.LaunchOptions(); // use options
		lo.setHeadless(false); // headed mode run
		lo.setSlowMo(200); // Slow down operations by 50ms

		BrowserType chromium = playwright.chromium();
		browser = chromium.launch(lo);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		browser.close();
		playwright.close();
	}

	@Test
	public void test_network_recording() {
		Browser.NewContextOptions options = new Browser.NewContextOptions() // ایجاد تنظیمات جدید برای مرورگر
				.setRecordHarPath(Paths.get("network.har")) // مسیر ذخیره فایل HAR
				.setRecordHarOmitContent(false); // شامل محتوای درخواست‌ها در فایل HAR

		BrowserContext context = browser.newContext(options);
		Page page = context.newPage();

		checkAvailability(page);
		page.close();
		
		context.close(); // فایل HAR ساخته می‌شود
	}

	@Test
	public void test_use_HAR_file() throws InterruptedException {
		BrowserContext context = browser.newContext();
		context.routeFromHAR(Paths.get("network.har"));
		
		Page page = context.newPage();
		checkAvailability(page);
		Thread.sleep(5000); // wait to see the result

		page.close();
		context.close(); 
	}
	
	private void checkAvailability(Page page) {
		page.navigate("https://automationintesting.online/");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Check Availability")).click();
		
		page.waitForLoadState(LoadState.NETWORKIDLE); // wait for network idle state
    }
	
}
