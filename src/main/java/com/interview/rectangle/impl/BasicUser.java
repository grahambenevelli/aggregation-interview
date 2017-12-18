package com.interview.rectangle.impl;

import com.interview.rectangle.User;

public class BasicUser implements User {

	private final long id;
	private final String name;

	public BasicUser(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override 
	public long getId() {
		return id;
	}

	@Override 
	public String getName() {
		return name;
	}
}
