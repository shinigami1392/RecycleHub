package edu.sunhacks.recyclehub.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author pushkarladhe
 * Model for product data
 */

@Document(collection="Product")
public class Product {
	@Id
	private String id;
	private String pid;

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setGarbageGenerated(String garbageGenerated) {
		this.garbageGenerated = garbageGenerated;
	}

	public void setRecycleableGarbage(String recycleableGarbage) {
		this.recycleableGarbage = recycleableGarbage;
	}

	public void setLandfillGarbage(String landfillGarbage) {
		this.landfillGarbage = landfillGarbage;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	private String productName;
	private String garbageGenerated;
	private String recycleableGarbage;
	private String landfillGarbage;
	private List<State> states;

	@Override
	public String toString() {
		return "Product{" +
				"id='" + id + '\'' +
				", pid='" + pid + '\'' +
				", productName='" + productName + '\'' +
				", garbageGenerated='" + garbageGenerated + '\'' +
				", recycleableGarbage='" + recycleableGarbage + '\'' +
				", landfillGarbage='" + landfillGarbage + '\'' +
				", states=" + states +
				'}';
	}

	protected Product(){
		this.states = new ArrayList<>();
	}
	public Product(String pid, String productName, String garbageGenerated, String recycleableGarbage,
			String landfillGarbage, List<State> states) {
		super();
		this.pid = pid;
		this.productName = productName;
		this.garbageGenerated = garbageGenerated;
		this.recycleableGarbage = recycleableGarbage;
		this.landfillGarbage = landfillGarbage;
		this.states = states;
	}

	public String getId() {
		return id;
	}

	public String getPid() {
		return pid;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public String getGarbageGenerated() {
		return garbageGenerated;
	}
	
	public String getRecycleableGarbage() {
		return recycleableGarbage;
	}
	
	public String getLandfillGarbage() {
		return landfillGarbage;
	}
	
	public List<State> getStates() {
		return states;
	}
	

}
