package co.com.almundo.callcenter.dispatcher;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.controller.CallCenterController;
import co.com.almundo.callcenter.model.Call;

/**
 * Clase encargada de recibir las diferentes llamadas,
 * generar los diferentes hilos de ejecución y administrar
 * el pool de hilos.
 */
public class Dispatcher {
	
	private static final Integer TIMEOUT = 10;
	private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	private static final Integer MAX_CONCURRENT_CALLS = 10;
	private static final Logger logger = LogManager.getLogger("Dispatcher");

	private CallCenterController callCenterController;
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(MAX_CONCURRENT_CALLS, MAX_CONCURRENT_CALLS, 0L,
			TIMEOUT_UNIT, new LinkedBlockingQueue<>());
	
//	private ExecutorService executor = Executors.newFixedThreadPool(MAX_CONCURRENT_CALLS);
	
	public Dispatcher(final CallCenterController callCenterController){
		this.callCenterController = callCenterController;
	}

	public Future<?> dispatchCall(final Call call) {
		call.setCallCenter(this.callCenterController);
		final Future<?> future = this.executor.submit(call); //Throw thread
		return future;
	}

	public void terminateDispatch() {
		try {
			this.executor.shutdown();
			this.executor.awaitTermination(TIMEOUT + 1, TimeUnit.SECONDS);
			logger.info("All threads finished.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}
	

}
