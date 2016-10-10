package com.greentopli.model;

/**
 * Created by rnztx on 10/10/16.
 */

public class BackendResult {
	private boolean success;
	private String message;

	public BackendResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean getResult() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "BackendResult{" +
				"success=" + success +
				", message='" + message + '\'' +
				'}';
	}
}
