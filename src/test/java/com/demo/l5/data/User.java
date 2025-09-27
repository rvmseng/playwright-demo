package com.demo.l5.data;

public class User {
	private String username;
	private String password;
	private String expected;

	// Constructors
	public User() {
	}

	User(String username, String password, String expected) {
		this.username = username;
		this.password = password;
		this.expected = expected;
	}

	// Getters & Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}
}
