package com.rvm.stepdefs;

import io.cucumber.java.en.*;
import org.testng.Assert;
import com.rvm.context.TestContext;
import com.rvm.pages.LoginPage;

public class LoginSteps {
	private final TestContext testContext;
	private final LoginPage loginPage;

	public LoginSteps(TestContext testContext) {
		this.testContext = testContext;
		this.loginPage = new LoginPage(testContext.page);
	}

	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() {
		loginPage.navigate();
	}

	@When("the user logs in with username {string} and password {string}")
	public void the_user_logs_in_with_credentials(String username, String password) {
		loginPage.performlogin(username, password);
	}

	@Then("the user should see the inventory page")
	public void the_user_should_see_the_inventory_page() {
		Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in and inventory visible");
	}

	@Then("the user should see an error message containing {string}")
	public void the_user_should_see_an_error_message_containing(String expectedText) {
		String actual = loginPage.getErrorText();
		Assert.assertTrue(actual != null && actual.toLowerCase().contains(expectedText.toLowerCase()),
				"Expected error to contain: " + expectedText + " but was: " + actual);
	}
}
