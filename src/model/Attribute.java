package model;

public enum Attribute {
	XLength ("XLength"),
	YLength ("YLength"),
	NumDays ("Number of Days"),
	NumYears ("Number of Years"),
	NumRecruits ("Number of Recruits"),
	MaxNodes ("Max Number of Nodes");
	
	String name;
	
	Attribute(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
