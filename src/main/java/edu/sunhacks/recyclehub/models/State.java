package edu.sunhacks.recyclehub.models;

public class State {
	private String name;
	private String value;

	protected State() {}

	public State(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	
	
}
