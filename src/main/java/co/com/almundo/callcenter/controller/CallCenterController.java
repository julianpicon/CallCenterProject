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
 * @jpicon
 *
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
	
	public synchronized void addEmployee(final Employee employee) {
		try {
			this.employees.put(employee);
		} catch (InterruptedException e) {
			logger.error("Error agregando empleando al call center ->", e);
		}
	}
	
	public void addCall(final Call call) {
		try {
			this.calls.put(call);
		} catch (InterruptedException e) {
			logger.error("Error agregando llamada al call center ->", e);
		}
	}

	public List<Future<?>> treatCalls() {
		logger.info("---- Iniciando procesamiento de llamadas ---");
		this.dispatcher = new Dispatcher(this);
		return calls.parallelStream().map(this.dispatcher::dispatchCall).collect(Collectors.toList());
//		return this.calls.forEach(dispatcher::dispatchCall).collect(Collectors.toList());
//		this.dispatcher.terminateDispatch();
	}
	
	public void terminateDispatch() {
		this.dispatcher.terminateDispatch();
	}

	public synchronized Employee getNextAvailableEmployee() throws InterruptedException {
		return this.employees.poll(TIMEOUT + 1, TIMEOUT_UNIT);
	}

	public BlockingQueue<Call> getCalls() {
		return calls;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}
	
}
