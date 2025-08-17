package com.rvm.test.l2.autowait;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.CDPSession;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;

public class AutoWaitDemo {

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

		page.setDefaultTimeout(45000); // تنظیم زمان پیش‌فرض برای عملیات‌ها (در میلی‌ثانیه)
		page.setDefaultNavigationTimeout(60000); // تنظیم زمان پیش‌فرض برای ناوبری (در میلی‌ثانیه)

	}

	@Test
	public void test_wait_for_url() {
		page.navigate("https://www.saucedemo.com/");

		page.fill("#user-name", "standard_user"); // 👉 به‌صورت خودکار صبر می‌کند تا المان آماده شود

		page.fill("#password", "secret_sauce"); // 👉 به‌صورت خودکار صبر می‌کند تا المان آماده شود

		page.click("#login-button"); // 👉 به‌صورت خودکار صبر می‌کند تا المان آماده شود

		page.waitForURL("**/inventory.html"); // 👉 به‌صورت خودکار صبر می‌کند تا URL تغییر کند
	}

	@Test
	public void test_auto_wait_for_specific_element() {
		page.navigate("https://www.saucedemo.com/");

		page.fill("#user-name", "standard_user"); // 👉 به‌صورت خودکار صبر می‌کند تا المان آماده شود

		page.fill("#password", "secret_sauce1"); // 👉 به‌صورت خودکار صبر می‌کند تا المان آماده شود

		page.click("#login-button"); // 👉 به‌صورت خودکار صبر می‌کند تا المان آماده شود

		String messageElement = "[data-test=\"error\"]"; // انتخاب المان پیام خطا

		page.waitForSelector(messageElement); // صبر تا پیام ظاهر شود

		String message = page.locator(messageElement).innerText();

		System.out.println("Result: " + message);
	}

	@Test
	public void test_wait_snipper_hiden() {
        simulateNetwork3g(page);
        
		page.navigate("https://bahman.iranecar.com/");

		Locator loadingSpinner = page.locator("#preloader"); // انتخاب spinner بارگذاری
		
		// صبر تا زمانی که spinner ناپدید شود
		loadingSpinner.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
		
		page.getByText("هنگام خرید خودرو").click();

		page.waitForTimeout(5000); // Keep open to observe
	}

	private void simulateNetwork3g(Page page) {
		// Attach to CDP session for Chrome DevTools Protocol
		CDPSession cdp = context.newCDPSession(page);

		// Enable network domain
		cdp.send("Network.enable", new JsonObject());

		// Build conditions for "Regular 3G"
		JsonObject conditions = new JsonObject();
		conditions.addProperty("offline", false);
		conditions.addProperty("latency", 200); // ms
		conditions.addProperty("downloadThroughput", 250 * 1024 / 8); // 750 kbps
		conditions.addProperty("uploadThroughput", 150 * 1024 / 8); // 250 kbps

		// Apply throttling
		cdp.send("Network.emulateNetworkConditions", conditions);

	}

}
