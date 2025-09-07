package com.demo.l4.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {
	// Locators پایدار تا جای ممکن:
	private final Locator username;
	private final Locator password;
	private final Locator loginButton;
	private final Locator errorContainer;

	public LoginPage(Page page) {
		super(page);

		this.username = page.getByPlaceholder("Username"); // یا page.locator("#user-name")
		this.password = page.getByPlaceholder("Password"); // یا page.locator("#password")

		this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
		this.errorContainer = page.locator("[data-test='error'], .error-message-container");
	}

	/** بارگذاری صفحه لاگین */
	public void navigate() {
		open("https://www.saucedemo.com/");
	    shouldBeVisible(loginButton); // اطمینان از آماده‌بودن صفحه
	}

	/** اجرای عملیات لاگین */
	public void login(String user, String pass) {
		fill(username, user);
		fill(password, pass);
		click(loginButton);
	}

	/** آیا پیام خطا دیده می‌شود؟ */
	public boolean isErrorVisible() {
		return errorContainer.isVisible();
	}

	public String getErrorText() {
		return errorContainer.isVisible() ? errorContainer.innerText() : "";
	}
}
