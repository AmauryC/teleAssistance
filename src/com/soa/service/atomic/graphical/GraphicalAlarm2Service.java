package com.soa.service.atomic.graphical;

import com.soa.service.atomic.Alarm2Service;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalAlarm2Service extends Alarm2Service {

	private WorkflowServiceImpl impl;
	
	public GraphicalAlarm2Service(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public void triggerAlarm(){
		super.triggerAlarm();
		//impl.updateUI() ...
	}
}