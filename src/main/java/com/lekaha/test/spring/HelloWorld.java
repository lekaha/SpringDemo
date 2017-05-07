package com.lekaha.test.spring;

public class HelloWorld {
	private String message;

	public String getMessage() {
		System.out.println("To you: " + message);
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
