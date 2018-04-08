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
	private String productName;
	private String garbageGenerated;
	private String recycleableGarbage;
	private String landfillGarbage;
	private List<State> states;

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
