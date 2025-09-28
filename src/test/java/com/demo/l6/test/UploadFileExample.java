package com.demo.l6.test;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demo.l5.context.TestContext;
import com.microsoft.playwright.Page;

public class UploadFileExample {

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
	public void uploadTest1() throws InterruptedException {
		Page page = ctx.page;
		String url = "file:///D:/workspace/playwright-demo/htmls/upload.html";
		
		page.navigate(url);
		page.setInputFiles("#fileInput", Path.of(getFilePath()));

		// کلیک روی دکمه Upload
		page.click("#uploadBtn");

		// نمایش متن وضعیت
		String status = page.textContent("#status");
		System.out.println("Status text: " + status);
		
		wait(5000);
	}
	
	private void wait(int mil) {
		try {
			Thread.sleep(mil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private String getFilePath() {
		URL resource = UploadFileExample.class.getClassLoader().getResource("data/users.json");
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        }

        String filePath = new File(resource.getFile()).getAbsolutePath();
        return filePath;
	}
}
