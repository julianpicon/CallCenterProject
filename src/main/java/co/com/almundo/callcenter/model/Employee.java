package co.com.almundo.callcenter.model;

public class Employee extends Person implements Comparable<Employee>{
	
	protected EmployeeType employeeType;
	
	public Employee(final String name, final EmployeeType employeeType) {
		super.name = name;
		this.employeeType = employeeType;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	@Override
	public int compareTo(Employee employee) {
		if (this.getEmployeeType().getPriority() < employee.getEmployeeType().getPriority()){
			return -1;
		}
		if (this.getEmployeeType().getPriority() > employee.getEmployeeType().getPriority()){
			return 1;
		}
		return 0;	
	}

}
