package com.cognixia.jump.exception;

public class SameUserAndPlatformException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SameUserAndPlatformException() {
		super("Account Already Exist. Must have a unique username and platform");
	}
}
