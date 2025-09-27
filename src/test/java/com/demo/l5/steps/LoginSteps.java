package com.demo.l5.steps;

import io.cucumber.java.en.*;
import java.util.List;

import org.testng.Assert;

import com.demo.l5.context.TestContext;
import com.demo.l5.data.User;
import com.demo.l5.pages.InventoryPage;
import com.demo.l5.pages.LoginPage;
import com.demo.l5.utils.DataUtils;

public class LoginSteps {

    private List<User> users;
    private TestContext ctx;

    public LoginSteps(TestContext ctx) {
		this.ctx = ctx;
    }
    
    @Given("I have user data from json file")
    public void i_have_user_data_from_json_file() {
        // Read users from external json
        users = DataUtils.readUsersFromJson("data/users.json");

        // Logging for debug
        System.out.println("Loaded users from json:");
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getExpected());
        }
    }

    @When("I perform login for each user")
    public void i_perform_login_for_each_user() {
        // Loop through each user and simulate login
        for (User user : users) {
            System.out.println("Attempting login for user: " + user.getUsername());
            LoginPage login = new LoginPage(ctx.page);
    		
            login.navigate();
    		login.login(user.getUsername(), user.getPassword());

    		if ("success".equalsIgnoreCase(user.getExpected())) {
    			InventoryPage inv = new InventoryPage(ctx.page);
    			inv.shouldBeLoaded();
    		} else {
    			Assert.assertTrue(login.isErrorVisible());
    		}
        }
    }

    @Then("I should see the login result in logs")
    public void i_should_see_the_login_result_in_logs() {
        System.out.println("All login attempts completed. Check above logs for results.");
    }
    
}
