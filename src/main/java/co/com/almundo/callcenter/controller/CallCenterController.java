package co.com.almundo.callcenter.controller;


import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.dispatcher.Dispatcher;
import co.com.almundo.callcenter.model.Call;
import co.com.almundo.callcenter.model.Employee;

/**
 * Clase encargada de centralizar el funcionamiento de la funcionalidad
 * CallCenter.
 */
public class CallCenterController {
	
	private static final Logger logger = LogManager.getLogger("CallCenterController");
	
	private static final Integer TIMEOUT = 10;
	private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	private Dispatcher dispatcher;
	
	private BlockingQueue<Employee> employees;
	private BlockingQueue<Call> calls;
	
	public CallCenterController() {
		super();
		this.employees = new PriorityBlockingQueue<>();
		this.calls = new LinkedBlockingQueue<>();
	}
	
	public void addEmployee(final Employee employee) {
		try {
			this.employees.put(employee);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error("Error agregando empleando al call center ->", e);
		}
	}
	
	public void addCall(final Call call) {
		try {
			this.calls.put(call);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error("Error agregando llamada al call center ->", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public List<Future> treatCalls() {
		logger.info("---- Iniciando procesamiento de llamadas ---");
		this.dispatcher = new Dispatcher(this);
		return calls.parallelStream().map(this.dispatcher::dispatchCall).collect(Collectors.toList());
	}
	
	public void terminateDispatch() {
		this.dispatcher.terminateDispatch();
	}

	public Employee getNextAvailableEmployee() throws InterruptedException {
		return this.employees.poll(TIMEOUT + 1L, TIMEOUT_UNIT);
	}

	public BlockingQueue<Call> getCalls() {
		return calls;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}
	
}
