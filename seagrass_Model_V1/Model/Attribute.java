package seagrass_Model_V1.Model;

public enum Attribute {
	XLength ("XLength"),
	YLength ("YLength"),
	NumDays ("Number of Days"),
	NumYears ("Number of Years"),
	NumRecruits ("Number of Recruits");
	
	String name;
	
	Attribute(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
