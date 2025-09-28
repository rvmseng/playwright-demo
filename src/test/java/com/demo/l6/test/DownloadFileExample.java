package com.demo.l6.test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demo.l5.context.TestContext;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DownloadFileExample {

	private TestContext ctx;
	private Path downloadDir;

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
	public void downloadAndVerify() throws InterruptedException, IOException {
		Page page = ctx.page;
		page.navigate("https://demo.automationtesting.in/FileDownload.html");

		// Wait for a download to start when clicking the file link
		Download download = page.waitForDownload(() -> {
			page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Download"))).first().click();
		});

		// get filename in server side
		String suggested = download.suggestedFilename();
		System.out.println("Suggested filename: " + suggested);

		// Save the download to a specific path
		Path target = Paths.get(System.getProperty("user.home"), "Downloads", suggested);
		download.saveAs(target);

		// Read and assert the file is non-empty (or compare with fixture)
		byte[] bytes = Files.readAllBytes(target);
		assertTrue(bytes.length > 0, "Downloaded file should not be empty");

		System.out.println("Downloaded file saved to: " + target.toAbsolutePath());
		wait(3000);
	}

	private void wait(int mil) {
		try {
			Thread.sleep(mil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
