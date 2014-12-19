package com.soa.service.composite;

import com.webapp.client.event.State;
import com.webapp.server.WorkflowServiceImpl;

import service.adaptation.Probe;
import service.auxiliary.ServiceDescription;

public class TeleAssistanceProbe implements Probe {
	private WorkflowServiceImpl impl;
	
	public TeleAssistanceProbe(WorkflowServiceImpl impl) {
		this.impl = impl;
	}

	@Override
	public void serviceOperationInvoked(ServiceDescription arg0, String arg1,
			Object[] arg2) {
		String[] parameters = {arg0.getServiceEndpoint()};
		impl.updateClientUI(parameters, State.SERVICE_INVOKED);
	}

	@Override
	public void serviceOperationReturned(ServiceDescription arg0, Object arg1,
			String arg2, Object[] arg3) {
		String[] parameters = {arg0.getServiceEndpoint()};
		impl.updateClientUI(parameters, State.SERVICE_SUCCESSFUL);
		int[] stats = {0,0,0};
		impl.updateServicesStats(stats);
		System.out.println("IT IS A SUCCESS");
	}

	@Override
	public void serviceOperationTimeout(ServiceDescription arg0, String arg1,
			Object[] arg2) {
		System.out.println("IT IS A TIMEOUT");
		String[] parameters = {arg0.getServiceEndpoint()};
		
		int[] stats = new int[3];
		stats[0]=1;
		if(parameters[0].contains("alarm")){
			stats[1]=1;
			stats[2]=Integer.valueOf((parameters[0].toCharArray()[parameters[0].length()-1]));
		}
		else if(parameters[0].contains("medicalAnalysis")){
			stats[1]=0;
			stats[2]=Integer.valueOf((parameters[0].toCharArray()[parameters[0].length()-1]));
		}
		else{
			stats[1]=2;
			stats[2]=0;
		}
		impl.updateServicesStats(stats);
		impl.updateClientUI(parameters, State.SERVICE_TIMEOUT);
	}

	@Override
	public void workflowEnded(Object arg0, String arg1, Object[] arg2) {
		String[] parameters = {};
		impl.updateClientUI(parameters, State.WORKFLOW_ENDED);
	}

	@Override
	public void workflowStarted(String arg0, Object[] arg1) {
		String[] parameters = {};
		impl.updateClientUI(parameters, State.WORKFLOW_STARTED);
		System.out.println("WORKFLOW STARTED");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
