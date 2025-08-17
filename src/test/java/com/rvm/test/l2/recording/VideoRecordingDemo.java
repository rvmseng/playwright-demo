package com.rvm.test.l2.recording;

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
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.LoadState;

public class VideoRecordingDemo {

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

	@Test
	public void test_video_recording() {
		Browser.NewContextOptions options = new Browser.NewContextOptions() // fill options
				.setRecordVideoDir(Paths.get("videos/")) // set path
				.setRecordVideoSize(1280, 720);

		context = browser.newContext(options);
		page = context.newPage();

		fillForm(page);
		System.out.println("Video saved at: " + page.video().path());
		
		page.close();
		context.close();
	}

	private void fillForm(Page page) {
		page.navigate("https://www.saucedemo.com/");

		page.getByPlaceholder("Username").fill("standard_user");
		page.getByPlaceholder("Password").fill("secret_sauce");
		page.locator("#login-button").click();

		page.waitForLoadState(LoadState.DOMCONTENTLOADED);
	}

	@Test
	public void test_capture_tracing() {
		context = browser.newContext();

		// شروع Tracing
		context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));

		page = context.newPage();
		fillForm(page);

		page.close();
		
		// Stop Tracing
		context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));

		context.close();
	}
}
