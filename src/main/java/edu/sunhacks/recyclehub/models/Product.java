package edu.sunhacks.recyclehub.models;

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
	
	
	
	public Product(String id, String pid, String productName, String garbageGenerated, String recycleableGarbage,
			String landfillGarbage, List<State> states) {
		super();
		this.id = id;
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
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getGarbageGenerated() {
		return garbageGenerated;
	}
	public void setGarbageGenerated(String garbageGenerated) {
		this.garbageGenerated = garbageGenerated;
	}
	public String getRecycleableGarbage() {
		return recycleableGarbage;
	}
	public void setRecycleableGarbage(String recycleableGarbage) {
		this.recycleableGarbage = recycleableGarbage;
	}
	public String getLandfillGarbage() {
		return landfillGarbage;
	}
	public void setLandfillGarbage(String landfillGarbage) {
		this.landfillGarbage = landfillGarbage;
	}
	public List<State> getStates() {
		return states;
	}
	public void setStates(List<State> states) {
		this.states = states;
	}
	
	
	
}
