package com.soa.service.atomic.graphical;

import com.soa.service.atomic.Alarm3Service;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalAlarm3Service extends Alarm3Service {

	private WorkflowServiceImpl impl;
	
	public GraphicalAlarm3Service(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public void triggerAlarm(){
		super.triggerAlarm();
		//impl.updateUI() ...
	}
}