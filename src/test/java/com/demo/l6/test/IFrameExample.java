package com.demo.l6.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demo.l5.context.TestContext;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class IFrameExample {

	private TestContext ctx;

	@BeforeTest(alwaysRun = true)
	public void setup() {
		ctx = new TestContext();
		ctx.init(false);
	}

	@AfterTest(alwaysRun = true)
	public void teardown() {
		ctx.quit();
	}

	@Test
	public void iFrameTest() throws InterruptedException {
		Page page = ctx.page;
		String url = "file:///D:/workspace/playwright-demo/htmls/iframe-sample.html";
		page.navigate(url);

		FrameLocator frame = page.frameLocator("#myFrame");
		frame.locator("input[name='username']").fill("reza");
		
		frame.locator("button#login").click();
		wait(1000);
	}

	private void wait(int mil) {
		try {
			Thread.sleep(mil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
