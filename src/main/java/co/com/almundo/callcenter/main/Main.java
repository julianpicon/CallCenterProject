package co.com.almundo.callcenter.main;

import java.util.List;

import co.com.almundo.callcenter.controller.CallCenterController;
import co.com.almundo.callcenter.model.Call;
import co.com.almundo.callcenter.test.util.TestUtil;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println(" ---- Start main thread ---- ");
		
		final CallCenterController callCenterController = new CallCenterController();
		TestUtil.getAllEmployees().forEach(callCenterController::addEmployee);
		final List<Call> calls = TestUtil.getCalls(11);
		calls.forEach(callCenterController::addCall);
		callCenterController.treatCalls();
		callCenterController.terminateDispatch();
		
		System.out.println(" ---- End main thread ---- ");
		
	}

}
