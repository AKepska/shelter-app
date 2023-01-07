package com.javafee.shelter.front.controller.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Params {
	private static Params params = null;
	private Map<String, Object> parameters = new HashMap<>();

	private Params() {
	}

	public static Params getInstance() {
		if (Objects.isNull(params))
			params = new Params();
		return params;
	}

	public void add(String key, Object object) {
		parameters.put(key, object);
	}

	public Object get(String key) {
		return parameters.get(key);
	}

	public Object remove(String key) {
		return parameters.remove(key);
	}
}
