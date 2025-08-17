package com.rvm.test.l2;

import java.nio.file.Paths;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class PlaywrightArchitecture {

	private Playwright playwright;
	private Browser browser;

	private BrowserContext context;
	private Page page;

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

	@BeforeMethod
	public void testSetup() throws InterruptedException {
		Browser.NewContextOptions options = new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/"))
				.setRecordVideoSize(1280, 720);

		context = browser.newContext(options);

		// شروع Tracing
		context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));

		page = context.newPage();
	}

	@Test
	public void test_navigate() {
		page.navigate("https://example.com");
		System.out.println("page '" + page.title() + "' ready to test");
	}
	
	@Test
	public void test_locator() {
		page.navigate("https://example.com");
		
		// Locator Examples
		// page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("More
		// information...")).click(); //find by role
		// page.locator("body > div > p:nth-child(3) > a").click(); // css selector
		// page.locator("//html/body/div/p[2]/a").click(); // xpath selector
		page.getByText("More information...").click(); // find by text
		
		System.out.println("page '" + page.title() + "' ready to test");
	}

	@Test
	public void test_request_capture() {
		page.navigate("https://example.com");

		// گرفتن رویداد درخواست
		page.onRequest(req -> System.out.println("onRequest >> " + req.method() + " " + req.url()));

		page.getByText("More information...").click(); // find by text
		System.out.println("page '" + page.title() + "' ready to test");
	}

	@Test
	public void test_NETWORKIDLE() {
		page.navigate("https://example.com");
		page.getByText("More information...").click(); // find by text

		// رویدادهای وضعیت بارگذاری
		page.waitForLoadState(LoadState.NETWORKIDLE);
		System.out.println("page '" + page.title() + "' ready to test");
	}

	
	@Test
	public void test_event_dialog() {
		page.navigate("https://practice.expandtesting.com/js-dialogs");
		
		// define dialog listener
		page.onDialog(dialog -> {
			System.out.println("Dialog type: " + dialog.type());
			System.out.println("Dialog message: " + dialog.message());
			dialog.accept(); // Accept the dialog
		});
		
		page.locator("#js-alert").click();
	}

	@AfterMethod
	public void teardownMethod() {
		System.out.println("Video saved at: " + page.video().path());
		page.close();

		// Stop Tracing
		context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
		context.close();
	}

}
