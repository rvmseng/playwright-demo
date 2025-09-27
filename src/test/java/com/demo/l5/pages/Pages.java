package com.demo.l5.pages;

import com.microsoft.playwright.Page;

public class Pages {
	private final Page page;
	private LoginPage loginPage;
	private InventoryPage inventoryPage;

	public Pages(Page page) {
		this.page = page;
	}

	public LoginPage login() {
		if (loginPage == null)
			loginPage = new LoginPage(page);
		return loginPage;
	}

	public InventoryPage inventory() {
		if (inventoryPage == null)
			inventoryPage = new InventoryPage(page);
		return inventoryPage;
	}
	
}