package com.rvm.test.l2.mock;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class NetworkRecordingDemo {

	private Playwright playwright;
	private Browser browser;
	private BrowserContext context;

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

		context = browser.newContext(options);
		Page page = context.newPage();

		checkAvailability(page);
		page.close();

		context.close(); // فایل HAR ساخته می‌شود
	}

	@Test
	public void test_use_HAR_file() throws InterruptedException {
		context = browser.newContext();
		context.routeFromHAR(Paths.get("network.har"));

		Page page = context.newPage();
		checkAvailability(page);
		Thread.sleep(7000); // wait to see the result

		page.close();
		context.close();
	}

	@Test
	public void test_mock_specific_request() throws InterruptedException {
		context = browser.newContext();

		context.route("**/api/*", route -> {
			Request r = route.request();

			if (r.url().endsWith("/room?checkin=2025-08-20&checkout=2025-08-21")) {
				route.fulfill(new Route.FulfillOptions().setStatus(200).setContentType("application/json").setBody(
						"{\"rooms\":[{\"roomid\":1,\"roomName\":\"303\",\"type\":\"Single\","
						+ "\"accessible\":true,\"image\":\"/images/room2.jpg\",\"description\":\"Mock Room.\""
						+ ",\"features\":[\"TV\",\"WiFi\",\"Safe\"],\"roomPrice\":0}]}")); 
																																																												// response
				System.out.println("used mock response for: " + r.url());
			} else {
				route.resume(); // Continue with the original request for other URLs
			}
		});

		Page page = context.newPage();
		checkAvailabilityForSpecificDate(page);

		Thread.sleep(5000); // wait to see the result
		page.close();

		context.close();
	}

	private void checkAvailability(Page page) {
		page.navigate("https://automationintesting.online/");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Check Availability")).click();
		page.waitForLoadState(LoadState.NETWORKIDLE); // wait for network idle state
	}

	private void checkAvailabilityForSpecificDate(Page page) {
		page.navigate("https://automationintesting.online/");

		page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Check In$")))
				.getByRole(AriaRole.TEXTBOX).click();

		page.getByLabel("Choose Wednesday, 20 August").click();

		page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Check Out$")))
				.getByRole(AriaRole.TEXTBOX).click();

		page.getByLabel("Choose Thursday, 21 August").click();

		//context.setOffline(true);

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Check Availability")).click();

		page.waitForLoadState(LoadState.NETWORKIDLE); // wait for network idle state
		
	}
}
