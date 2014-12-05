package com.soa.service.atomic.graphical;

import com.soa.service.atomic.Alarm1Service;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalAlarm1Service extends Alarm1Service {

	private WorkflowServiceImpl impl;
	
	public GraphicalAlarm1Service(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public void triggerAlarm(){
		super.triggerAlarm();
		//impl.updateUI() ...
	}
}