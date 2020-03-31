package com.test.gateway.service.model;

import java.util.HashMap;
import java.util.Map;

public class GatewayThreadContext {
	private static final ThreadLocal<Map<String, Object>> CONTEXT_MAP = new ThreadLocal<>();

	public static void put(String key, Object value) {
		if (CONTEXT_MAP.get() == null) {
			CONTEXT_MAP.set(new HashMap<>());
		}
		CONTEXT_MAP.get().put(key, value);
	}

	public static Object get(String key) {
		if (CONTEXT_MAP.get() == null) {
			CONTEXT_MAP.set(new HashMap<>());
		}
		return CONTEXT_MAP.get().get(key);
	}

	public static void clearContext() {
		if (CONTEXT_MAP.get() == null) {
			CONTEXT_MAP.set(new HashMap<>());
		} else {
			CONTEXT_MAP.get().clear();
			CONTEXT_MAP.remove();
		}
	}
}
