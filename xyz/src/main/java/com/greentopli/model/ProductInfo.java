package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.UUID;

@Entity public class ProductInfo{
	public enum Type {
		LEAF_VEGETABLE, FRUIT_VEGETABLE, FRUIT, FLOUR, SPROUT
	}
	public enum Volume {
		WEIGHT, QUANTITY
	}

	@Id private String id;
	@Index private String name_english;
	private String name_hinglish;
	private int minimumVolume;
	private int maximumVolume;
	private int volumeSet; // Fruits can be purchased in 3, 6, 9, 12 quantity
	private String pictureName;
	@Index private Type type;
	@Index private Volume volume;

	public ProductInfo(){}

	public ProductInfo(String name_english, String name_hinglish, Type type, Volume volume) {
		this.id = UUID.randomUUID().toString();
		this.name_english = name_english;
		this.name_hinglish = name_hinglish;
		this.type = type;
		this.volume = volume;
		this.maximumVolume = 0;
		this.minimumVolume = 0;
		this.volumeSet = 0;
		this.pictureName = "default picture name";
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName_english(String name_english) {
		this.name_english = name_english;
	}

	public void setName_hinglish(String name_hinglish) {
		this.name_hinglish = name_hinglish;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public void setMinimumVolume(int minimumVolume) {
		this.minimumVolume = minimumVolume;
	}

	public void setMaximumVolume(int maximumVolume) {
		this.maximumVolume = maximumVolume;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getPictureName() {
		return pictureName;
	}

	public int getMinimumVolume() {
		return minimumVolume;
	}

	public int getMaximumVolume() {
		return maximumVolume;
	}

	public void setVolumeSet(int volumeSet) {
		this.volumeSet = volumeSet;
	}

	public int getVolumeSet() {
		return volumeSet;
	}

	public String getId() {
		return id;
	}

	public String getName_english() {
		return name_english;
	}

	public String getName_hinglish() {
		return name_hinglish;
	}

	public Type getType() {
		return type;
	}

	public Volume getVolume() {
		return volume;
	}
}
