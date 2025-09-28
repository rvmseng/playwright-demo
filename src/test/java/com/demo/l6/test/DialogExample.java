package com.demo.l6.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demo.l5.context.TestContext;
import com.microsoft.playwright.Page;

public class DialogExample {

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
	public void dialogTest() throws InterruptedException {
		Page page = ctx.page;
		String url = "file:///D:/workspace/playwright-demo/htmls/dialog-sample.html";
		page.navigate(url);

		// Handle Alert
		page.onceDialog(dialog -> {
			System.out.println("Dialog message: " + dialog.message());
			dialog.accept();
		});
		page.click("text=Show Alert");
		wait(1000);

		// Handle Confirm
		page.onceDialog(dialog -> {
			System.out.println("Confirm message: " + dialog.message());
			dialog.dismiss(); // try dialog.accept() also
		});
		page.click("text=Show Confirm");
		wait(1000);

		// Handle Prompt
		page.onceDialog(dialog -> {
			System.out.println("Prompt message: " + dialog.message());
			dialog.accept("Reza");
		});
		page.click("text=Show Prompt");
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
