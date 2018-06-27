package co.com.almundo.callcenter.model;

public abstract class Person {
	
	protected String name;

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}

}
