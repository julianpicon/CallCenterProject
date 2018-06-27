package co.com.almundo.callcenter.model;

public class Customer extends Person implements Comparable<Customer>{
	
	public Customer(final String name) {
		super.name = name;
	}

	@Override
	public int compareTo(final Customer cust) {
		return 0;
	}

}
