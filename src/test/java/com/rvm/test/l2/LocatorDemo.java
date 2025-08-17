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
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class LocatorDemo {

	private Playwright playwright;
	private Browser browser;

	private BrowserContext context;
	private Page page;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		playwright = Playwright.create();

		LaunchOptions lo = new BrowserType.LaunchOptions(); // use options
		lo.setHeadless(false); // headed mode run

		BrowserType bt = playwright.firefox();
		browser = bt.launch(lo);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		browser.close();
		playwright.close();
	}

	@BeforeMethod
	public void testSetup() throws InterruptedException {
		context = browser.newContext();
		page = context.newPage();
		page.navigate("https://www.saucedemo.com/");
	}
	
	@AfterMethod
	public void teardownMethod() {
		page.waitForLoadState(LoadState.NETWORKIDLE);
		System.out.println("page '" + page.title() + "' ready to test");

		page.close();
		context.close();
	}

	@Test
	public void test_locator_by_text() {
		page.getByText("Login").click(); // find by text
	}

	@Test
	public void test_locator_by_Role() {
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click(); // find by Role
	}

	@Test
	public void test_locator_by_css() {
		page.locator("#password").fill("my pass"); // by css selector
	}
	
	@Test
	public void test_locator_by_xpath() {
		page.locator("//*[@id=\"user-name\"]").fill("user_normal"); // by xpath selector
    }
	
	@Test
	public void test_locator_by_placeholder() {
		page.getByPlaceholder("Username").fill("admin_user"); // by placeholder
	}
	
	@Test
	public void test_locator_by_nth() {
		page.locator("input").nth(1).fill("my pass"); // by nth start from 0
	}
	
	@Test
	public void test_locator_by_Relative_Locator() {
		Locator form=page.locator("form");
		form.locator("[data-test=\"username\"]").fill("admin_user"); // by relative locator
	}

}
