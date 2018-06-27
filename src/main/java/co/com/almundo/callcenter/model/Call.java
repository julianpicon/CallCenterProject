package co.com.almundo.callcenter.model;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.controller.CallCenterController;
import co.com.almundo.callcenter.exception.NoEmployeesException;

public class Call implements Runnable {
	
	private static final Logger logger = LogManager.getLogger("Call");
	
	public static final int MIN_DURACION = 5;
	public static final int MAX_DURACION = 10;
	
	private int id;
	private Employee employee;
	private Customer customer;
	private int duration;
	private CallCenterController callCenterController;
	
	public Call(final int id, final Customer customer) {
		this.id = id;
		this.customer = customer;
		this.duration = new Random().nextInt(MAX_DURACION - MIN_DURACION + 1) + MIN_DURACION;
	}
	
	
	@Override
	public void run()  {
		logger.info("Call # " + this.id + " - STATUS: Waiting for employee assignment.");
		try {
			//Se modifica la asignación del empleado para darle tiempo al hilo de esperar cuando no haya uno disponible.
			final Employee nextAvailableEmployee = this.callCenterController.getNextAvailableEmployee();
			if (nextAvailableEmployee != null) {
				
				this.employee = nextAvailableEmployee;
				logger.info("Call # " + this.id + " - STATUS: Being processed by " + this.employee.getName());
				
				TimeUnit.SECONDS.sleep(this.duration);
				
				logger.info("Call #" + this.id + " - STATUS: Completed in " + this.duration + " seconds. ");
				this.callCenterController.addEmployee(this.employee);
				
			} else {
				throw new NoEmployeesException("No employees are available.");
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public int getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setCallCenter(final CallCenterController callCenterController) {
		this.callCenterController = callCenterController;
	}
	
}
