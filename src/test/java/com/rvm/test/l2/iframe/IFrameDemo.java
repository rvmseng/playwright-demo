package com.rvm.test.l2.iframe;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class IFrameDemo {

	private Playwright playwright;
	private BrowserContext context;
	private Browser chrome;
	private Page page;

	@BeforeTest
	public void setup() {
		playwright = Playwright.create();
		LaunchOptions lo = new BrowserType.LaunchOptions();
		lo.setHeadless(false); // اجرای در حالت آHeaded

		BrowserType bt = playwright.chromium();
		chrome = bt.launch(lo);

		context = chrome.newContext();
		page = context.newPage();

		page.navigate("https://demo.automationtesting.in/Frames.html");
		page.waitForLoadState(LoadState.NETWORKIDLE);

	}

	@AfterTest
	public void teardown() {
		if (page != null) {
			page.close();
		}
		
		if (context != null) {
			context.close();
		}

		if (chrome != null) {
			chrome.close();
		}

		if (playwright != null) {
			playwright.close();
		}
		
		System.out.println("Test completed successfully.");
	}

	@Test
	public void test_frameLocator() throws InterruptedException {
		FrameLocator singleframe =  page.frameLocator("#singleframe");
		singleframe.getByRole(AriaRole.TEXTBOX).fill("Hello from IFrame!");
		
		waiting(2000); // Pause for 2 seconds to see the result
	}

	@Test
	public void test_iframe() throws InterruptedException {

		Frame singleframe = page.frame("SingleFrame");
		singleframe.getByRole(AriaRole.TEXTBOX).fill("Hello from IFrame!");
		
		waiting(2000); // Pause for 2 seconds to see the result
	}

	
	/*
	 * Custom method for waiting
	 * This method is used to pause the execution for a specified number of milliseconds.
	 * @param mils The number of milliseconds to wait.	
	 * */
	public void waiting(long mils) {
		try {
			Thread.sleep(mils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
