package co.com.almundo.callcenter.main;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.almundo.callcenter.controller.CallCenterController;
import co.com.almundo.callcenter.model.Call;
import co.com.almundo.callcenter.test.util.TestUtil;

public class Main {
	
	private static final Logger logger = LogManager.getLogger("CallCenterController");
	
	public static void main(String[] args) {
		
		logger.info(" ---- Start main thread ---- ");
		
		final CallCenterController callCenterController = new CallCenterController();
		TestUtil.getAllEmployees().forEach(callCenterController::addEmployee);
		final List<Call> calls = TestUtil.getCalls(11);
		calls.forEach(callCenterController::addCall);
		callCenterController.treatCalls();
		callCenterController.terminateDispatch();
		
		logger.info(" ---- End main thread ---- ");
		
	}

}
