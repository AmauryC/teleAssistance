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
		System.out.println("IT IS A SUCCESS");
	}

	@Override
	public void serviceOperationTimeout(ServiceDescription arg0, String arg1,
			Object[] arg2) {
		System.out.println("IT IS A TIMEOUT");
		String[] parameters = {arg0.getServiceEndpoint()};
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
