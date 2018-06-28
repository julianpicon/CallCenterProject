package co.com.almundo.callcenter.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.com.almundo.callcenter.model.Call;
import co.com.almundo.callcenter.model.Customer;
import co.com.almundo.callcenter.model.Employee;
import co.com.almundo.callcenter.model.EmployeeType;

public class TestUtil {

	public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Violeta", EmployeeType.OPERATOR));
        employees.add(new Employee("Julian", EmployeeType.SUPERVISOR));
        employees.add(new Employee("Karla", EmployeeType.OPERATOR));
        employees.add(new Employee("Manuel", EmployeeType.OPERATOR));
        employees.add(new Employee("Ludy", EmployeeType.DIRECTOR));
        employees.add(new Employee("Javier", EmployeeType.OPERATOR));
        employees.add(new Employee("Ana", EmployeeType.OPERATOR));
        employees.add(new Employee("Kim", EmployeeType.SUPERVISOR));
        employees.add(new Employee("Javi", EmployeeType.OPERATOR));
        employees.add(new Employee("Rebeca", EmployeeType.SUPERVISOR));
        return employees;
    }
	
	public static List<Employee> getEmployees(Integer count) {
        return getAllEmployees().stream().limit(count).collect(Collectors.toList());
    }
	
	public static List<Call> getCalls(int count) {
        final List<Call> calls = new ArrayList<>();
        for(int i = 1; i <= count; i++){
        	calls.add(new Call(i, new Customer("Customer " + i)));
        }
        return calls;
    }

    public static List<Customer> getAllCustomers(int count) {
        List<Customer> customers = new ArrayList<>();
        for(int i = 0; i < count; i++){
        	customers.add(new Customer(i+""));
        }
        return customers;
    }

}
