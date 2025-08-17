package com.rvm.test.l2;

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
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class EventDemo {

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
	public void beforMe() {
		context = browser.newContext();
		page = context.newPage();
	}

	@AfterMethod
	public void afterMe() {
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
	public void test_consol_log_capture() {
		// گرفتن رویداد کنسول
		page.onConsoleMessage(msg -> System.out.println("CONSOLE: " + msg.text()));

		fillForm(page); // fill login form
	}

	@Test
	public void test_response_capture() {
		// گرفتن رویداد درخواست
		page.onResponse(res -> System.out.println("onResponse >> " + res.status() + " " + res.statusText()));

		fillForm(page); // fill login form
	}

	@Test
	public void test_request_capture() {
		// گرفتن رویداد درخواست
		page.onRequest(req -> System.out.println("onRequest >> " + req.method() + " " + req.url()));

		fillForm(page); // fill login form
	}

	@Test
	public void test_failed_request_capture() {
		// گرفتن رویداد درخواست
		page.onRequestFailed(req -> System.out.println("onRequest >> " + req.method() + " " + req.url()));

		fillForm(page); // fill login form
	}

	@Test
	public void test_completed_request_capture() {
		// گرفتن رویداد درخواست
		page.onRequestFinished(req -> System.out.println("onRequest >> " + req.method() + " " + req.url()));

		fillForm(page); // fill login form
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

	@Test
	public void test_websocket_capture() throws InterruptedException {
		page.navigate("https://piehost.com/websocket-tester");

		page.onWebSocket(ws -> {
			ws.onFrameReceived(payload -> System.out.println("WS <= " + payload));
		});

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Connect")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send")).click();
		
		Thread.sleep(2000); // wait for messages to be received)
	}

}
