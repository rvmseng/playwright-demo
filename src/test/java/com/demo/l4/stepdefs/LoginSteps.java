package com.demo.l4.stepdefs;

import io.cucumber.java.en.*;
import org.testng.Assert;
import com.rvm.context.TestContext;
import com.demo.l4.pages.InventoryPage;
import com.demo.l4.pages.LoginPage;

public class LoginSteps {
	private final TestContext ctx;
	private LoginPage loginPage;
	private InventoryPage inventoryPage;

	public LoginSteps(TestContext ctx) {
		this.ctx = ctx;
		// Page Objects با Page فعلی مقداردهی می‌شوند
		this.loginPage = new LoginPage(ctx.page);
		this.inventoryPage = new InventoryPage(ctx.page);
	}

	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() {
		loginPage.navigate();
	}

	@When("the user logs in with username {string} and password {string}")
	public void the_user_logs_in_with_credentials(String user, String pass) {
		loginPage.login(user, pass);
	}

	@Then("the inventory page should be visible")
	public void the_inventory_page_should_be_visible() {
		inventoryPage.shouldBeLoaded();
	}

	@Then("an error message should be shown")
	public void an_error_message_should_be_shown() {
		Assert.assertTrue(loginPage.isErrorVisible(), "Error message must be visible for invalid login");
	}
}
