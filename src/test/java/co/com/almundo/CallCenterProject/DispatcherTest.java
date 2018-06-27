package co.com.almundo.CallCenterProject;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.com.almundo.callcenter.controller.CallCenterController;
import co.com.almundo.callcenter.dispatcher.Dispatcher;
import co.com.almundo.callcenter.test.util.TestUtil;

public class DispatcherTest {
	
	private static final int TIMEOUT = 10 + 1;
	private static final int MAX_CONCURRENT_CALLS = 10;
	
	private CallCenterController callCenterController;
	
	@Before
	public final void baseSetUp() {
		callCenterController = new CallCenterController();
	}
	
	@After
	public final void baseTearDown() { 
		callCenterController.terminateDispatch();
	}
	
	/**
	 * Prueba para verificar la capacidad del pool de hilods configurados.
	 */
	@Test
    public void testMaximumPoolSize() {
       
        TestUtil.getEmployees(1).forEach(callCenterController::addEmployee);
        TestUtil.getCalls(1).forEach(callCenterController::addCall);
        callCenterController.treatCalls();
        final Dispatcher dispatcher = callCenterController.getDispatcher();
        final ThreadPoolExecutor executor = dispatcher.getExecutor();
        
        assertEquals(MAX_CONCURRENT_CALLS, executor.getMaximumPoolSize());
    }

    @Test
    public void testOneCallOneEmployee() throws InterruptedException {
       
        TestUtil.getEmployees(1).forEach(callCenterController::addEmployee);
        TestUtil.getCalls(1).forEach(callCenterController::addCall);
        callCenterController.treatCalls();
        final Dispatcher dispatcher = callCenterController.getDispatcher();
        final ThreadPoolExecutor executor = dispatcher.getExecutor();
        TimeUnit.SECONDS.sleep(TIMEOUT);
        assertEquals(1, executor.getCompletedTaskCount());
    }
    
    @Test
    public void testTenCallsTenEmployees() throws InterruptedException {
       
        TestUtil.getEmployees(10).forEach(callCenterController::addEmployee);
        TestUtil.getCalls(10).forEach(callCenterController::addCall);
        callCenterController.treatCalls();
        final Dispatcher dispatcher = callCenterController.getDispatcher();
        final ThreadPoolExecutor executor = dispatcher.getExecutor();
        TimeUnit.SECONDS.sleep(TIMEOUT);
        assertEquals(10, executor.getCompletedTaskCount());
        
    }
    
    @Test
    public void testElevenCallsTenEmployees() throws InterruptedException {
       
        TestUtil.getEmployees(10).forEach(callCenterController::addEmployee);
        TestUtil.getCalls(11).forEach(callCenterController::addCall);
        callCenterController.treatCalls();
        final Dispatcher dispatcher = callCenterController.getDispatcher();
        final ThreadPoolExecutor executor = dispatcher.getExecutor();
        TimeUnit.SECONDS.sleep(TIMEOUT + 1);
        
        assertEquals(10, executor.getCompletedTaskCount());
    }
    
    @Test 
    public void testNoEmployees() throws InterruptedException {
        TestUtil.getCalls(1).forEach(callCenterController::addCall);
        final List<Future<?>> result = callCenterController.treatCalls();
        final Dispatcher dispatcher = callCenterController.getDispatcher();
        final ThreadPoolExecutor executor = dispatcher.getExecutor();
        TimeUnit.SECONDS.sleep(TIMEOUT + 2);
        final FutureTask<?> futureTask = (FutureTask<?>) result.get(0);
        assertTrue(futureTask.isDone());
    }


}