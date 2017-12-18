package com.interview.rectangle.impl;

import com.interview.rectangle.Metric;

public class BasicMetric implements Metric {

	private final long userId;
	private final String name;
	private final long value;

	public BasicMetric(long userId, String name, long value) {
		this.userId = userId;
		this.name = name;
		this.value = value;
	}

	@Override
	public long getUserId() {
		return userId;
	}

	@Override 
	public String getName() {
		return name;
	}

	@Override 
	public long getValue() {
		return value;
	}
}
