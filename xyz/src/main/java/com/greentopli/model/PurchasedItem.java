package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rnztx on 19/10/16.
 */

@Entity public class PurchasedItem {

	@Id private String orderId;
	@Index private String userId;
	@Index private String productId;
	@Index private long dateRequested;
	@Index private long dateCompleted;
	@Index private boolean accepted;
	@Index private boolean completed;

	public String getOrderId() {
		return orderId;
	}

	public String getUserId() {
		return userId;
	}

	public String getProductId() {
		return productId;
	}

	public long getDateRequested() {
		return dateRequested;
	}

	public long getDateCompleted() {
		return dateCompleted;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setDateRequested(long dateRequested) {
		this.dateRequested = dateRequested;
	}

	public void setDateCompleted(long dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public PurchasedItem() {}

	public PurchasedItem(String userId, String productId) {
		this.orderId = UUID.randomUUID().toString();
		this.userId = userId;
		this.productId = productId;
	}
}
