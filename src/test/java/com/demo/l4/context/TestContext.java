package com.demo.l4.context;

import com.microsoft.playwright.*;

public class TestContext {
	public Playwright playwright;
	public Browser browser;
	public BrowserContext context;
	public Page page;

	// مقداردهی اولیه منابع برای هر سناریو
	public void init(boolean headless) {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
		context = browser.newContext();
		page = context.newPage();
	}

	// آزادسازی منابع
	public void quit() {
		try {
			if (page != null)
				page.close();
			if (context != null)
				context.close();
			if (browser != null)
				browser.close();
			if (playwright != null)
				playwright.close();
		} catch (Exception e) {
			System.err.println("Error during quit: " + e.getMessage());
		}
	}
}
