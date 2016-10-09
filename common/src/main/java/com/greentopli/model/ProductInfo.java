package com.greentopli.model;


public class ProductInfo {
	private int id;
	private String name_english;
	private String name_hinglish;
	private ProductType productType;
	private PurchaseType purchaseType;

	public ProductInfo(){}

	public ProductInfo(int id, String name_english, String name_hinglish, ProductType productType, PurchaseType purchaseType) {
		this.id = id;
		this.name_english = name_english;
		this.name_hinglish = name_hinglish;
		this.productType = productType;
		this.purchaseType = purchaseType;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName_english(String name_english) {
		this.name_english = name_english;
	}

	public void setName_hinglish(String name_hinglish) {
		this.name_hinglish = name_hinglish;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public int getId() {
		return id;
	}

	public String getName_english() {
		return name_english;
	}

	public String getName_hinglish() {
		return name_hinglish;
	}

	public ProductType getProductType() {
		return productType;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}
}
