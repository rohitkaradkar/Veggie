package com.greentopli.model;

/**
 * Created by rnztx on 20/11/16.
 */

public class DataMessage {
	private String title;
	private String message;

	public DataMessage(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public DataMessage() {}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isEmpty(){
		return (title.isEmpty() || message.isEmpty());
	}
}
