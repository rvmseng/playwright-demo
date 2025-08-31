package com.rvm.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class LoginPage {
	private final Page page;
	private final Locator username;
	private final Locator password;
	private final Locator loginButton;
	private final Locator errorMessage;

	public LoginPage(Page page) {
		this.page = page;
		this.username = page.locator("#user-name");
		this.password = page.locator("#password");
		this.loginButton = page.locator("#login-button");
		this.errorMessage = page.locator("div.error-message-container");
	}

	public void navigate() {
		page.navigate("https://www.saucedemo.com");
	}

	public void performlogin(String user, String pass) {
		username.fill(user);
		password.fill(pass);
		loginButton.click();
	}

	public boolean isLoggedIn() {
		// صفحه inventory پس از لاگین دارای عنصر با کلاس inventory_list است
		return page.locator(".inventory_list").isVisible();
	}

	public String getErrorText() {
		return errorMessage.textContent();
	}
}
