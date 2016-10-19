package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rnztx on 19/10/16.
 */

@Entity public class PurchaseEntity {

	@Id private String order_id;
	private String user_id;
	private String product_id;
	private long time_placed;
	private long time_completed;
	private boolean isAccepted;
	private boolean isCompleted;

	public PurchaseEntity() {}

	public PurchaseEntity(String user_id, String product_id) {
		this.order_id = UUID.randomUUID().toString();
		this.user_id = user_id;
		this.product_id = product_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public void setTime_placed(long time_placed) {
		this.time_placed = time_placed;
	}

	public void setTime_completed(long time_completed) {
		this.time_completed = time_completed;
	}

	public void setAccepted(boolean accepted) {
		isAccepted = accepted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}

	public String getOrder_id() {
		return order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public long getTime_placed() {
		return time_placed;
	}

	public long getTime_completed() {
		return time_completed;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public boolean isCompleted() {
		return isCompleted;
	}
}
