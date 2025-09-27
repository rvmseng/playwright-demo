package com.demo.l5.tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.demo.l5.context.TestContext;
import com.demo.l5.data.User;
import com.demo.l5.pages.InventoryPage;
import com.demo.l5.pages.LoginPage;
import com.demo.l5.utils.CSVUtils;
import com.demo.l5.utils.DataUtils;
import com.demo.l5.utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class LoginDataDrivenTest {

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

	@DataProvider(name = "jsonUsers")
	public Object[][] jsonUsers() {
		List<User> users = DataUtils.readUsersFromJson("data/users.json");
		Object[][] result = new Object[users.size()][1];

		for (int i = 0; i < users.size(); i++)
			result[i][0] = users.get(i);

		return result;
	}

	@Test(dataProvider = "jsonUsers")
	public void loginWithJson(User user) {
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

	@DataProvider(name = "csvUsers")
	public Object[][] csvUsers() {
		List<Map<String, String>> rows = CSVUtils.readCsvAsMap("data/users.csv");
		Object[][] out = new Object[rows.size()][1];
		for (int i = 0; i < rows.size(); i++)
			out[i][0] = rows.get(i);
		return out;
	}

	@Test(dataProvider = "csvUsers")
	public void loginWithCsv(Map<String, String> user) {
		LoginPage login = new LoginPage(ctx.page);
		login.navigate();
		login.login(user.get("username"), user.get("password"));

		if ("success".equalsIgnoreCase(user.get("expected"))) {
			InventoryPage inv = new InventoryPage(ctx.page);
			inv.shouldBeLoaded();
		} else {
			Assert.assertTrue(login.isErrorVisible());
		}
	}
	
	@DataProvider(name = "excelUsers")
	public Object[][] excelUsers() {
		List<Map<String, String>> rows = ExcelUtils.readExcelAsMap("data/users.xlsx");
		Object[][] out = new Object[rows.size()][1];
		
		for (int i = 0; i < rows.size(); i++)
			out[i][0] = rows.get(i);
		return out;
	}

	@Test(dataProvider = "excelUsers")
	public void loginWithExcel(Map<String, String> user) {
		LoginPage login = new LoginPage(ctx.page);
		login.navigate();
		login.login(user.get("username"), user.get("password"));

		if ("success".equalsIgnoreCase(user.get("expected"))) {
			InventoryPage inv = new InventoryPage(ctx.page);
			inv.shouldBeLoaded();
		} else {
			Assert.assertTrue(login.isErrorVisible());
		}
	}
}
