package com.demo.l4.pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.demo.l4.pages.BasePage;

public class HeaderComponent extends BasePage {
	private final Locator cartIcon;
	private final Locator cartBadge;
	private final Locator appLogo;

	public HeaderComponent(Page page) {
		super(page);
		this.cartIcon = page.locator(".shopping_cart_link");
		this.cartBadge = page.locator(".shopping_cart_badge");
		this.appLogo = page.locator(".app_logo");
	}

	public void openCart() {
		click(cartIcon);
	}

	public int getCartCount() {
		if (cartBadge.isVisible()) {
			return Integer.parseInt(cartBadge.innerText().trim());
		}
		return 0;
	}

	public boolean isLogoVisible() {
		return appLogo.isVisible();
	}
}
