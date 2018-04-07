package edu.sunhacks.recyclehub.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author pushkarladhe
 * Model for product data
 */

@Document
public class Product {
	@Id
	private String id;
	private String name;
	private String garbage;
	private String recycle;
	private String landfill;
	private String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGarbage() {
		return garbage;
	}
	public void setGarbage(String garbage) {
		this.garbage = garbage;
	}
	public String getRecycle() {
		return recycle;
	}
	public void setRecycle(String recycle) {
		this.recycle = recycle;
	}
	public String getLandfill() {
		return landfill;
	}
	public void setLandfill(String landfill) {
		this.landfill = landfill;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
