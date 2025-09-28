package com.demo.l6.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demo.l5.context.TestContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.MouseButton;

public class KeyboardMouseTests {

	private TestContext ctx;
	private Page page;

	@BeforeTest(alwaysRun = true)
	public void setup() {
		ctx = new TestContext();
		ctx.init(false);
		this.page = ctx.page;

	}

	@AfterTest(alwaysRun = true)
	public void teardown() {
		ctx.quit();
	}

	@Test
	public void keyboardTypeTest() {
		page.navigate("https://www.google.com");
		Locator inputBox = page.locator("textarea[name='q']");

		inputBox.click();
		page.keyboard().type("Playwright keyboard simulation");

		wait(2000);
		page.keyboard().press("Enter");
	}

	@Test
	public void keyboardPressKeysTest() {
		page.navigate("https://www.google.com");
		Locator inputBox = page.locator("textarea[name='q']");

		inputBox.click();
		page.keyboard().type("Hello World!");

		wait(1000);
		page.keyboard().press("Control+A");

		page.keyboard().press("Delete");
		wait(2000);

	}

	@Test
	public void keyboardShortcuts() {
		page.navigate("https://www.google.com");
		Locator inputBox = page.locator("textarea[name='q']");

		inputBox.click();
		page.keyboard().type("Playwright keyboard simulation");

		/*
		 * page.keyboard().down("Control"); page.keyboard().press("C"); // Copy
		 * page.keyboard().up("Control");
		 */

		page.keyboard().down("Control");
		page.keyboard().press("V"); // Paste
		page.keyboard().up("Control");

		wait(2000);
	}

	/*
	 * Start Mouse Actions Samples
	 */

	@Test
	public void dragAndDropTest() {
		// Hover and drag-and-drop demo (example page needed)
		page.navigate("https://crossbrowsertesting.github.io/drag-and-drop");
		Locator source = page.locator("#draggable");

		Locator target = page.locator("#droppable");
		source.dragTo(target); // high-level API for drag & drop

		wait(2500);
	}

	@Test
	public void clickMouse() {
		page.navigate("https://www.google.com");
		page.mouse().click(200, 150); // کلیک در مختصات مشخص
	}
	
	@Test
    public void hoverMouse() {
		page.navigate("https://qa-practice.netlify.app/mouse-hover");
        page.hover("#button-hover-over"); // حرکت ماوس

        wait(2500);
    }
	
	@Test
    public void doubleClick() {
		page.navigate("https://qa-practice.netlify.app/double-click");
        page.dblclick("#double-click-btn"); // دابل کلیک روی دکمه
        
        wait(2500);
    }

    @Test
    public void rightClick() {
    	page.navigate("file:///D:/workspace/playwright-demo/htmls/rightClick.html");

        // Right Click روی المان
        page.locator("#box").click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));

        // خواندن متن پیام
        String message = page.textContent("#message");
        System.out.println("Message: " + message);
        wait(2500);

    }

    @Test
    public void dragAndDrop() {
    	page.navigate("https://crossbrowsertesting.github.io/drag-and-drop");
        page.dragAndDrop("#draggable", "#droppable"); // درگ و دراپ
        
        wait(2500);
    }

	private void wait(int mil) {
		try {
			Thread.sleep(mil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
