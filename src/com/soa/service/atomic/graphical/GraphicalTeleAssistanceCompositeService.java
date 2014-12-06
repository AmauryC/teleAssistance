package com.soa.service.atomic.graphical;

import com.soa.object.HealthReport;
import com.soa.service.composite.TeleAssistanceCompositeService;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.LocalOperation;

public class GraphicalTeleAssistanceCompositeService extends TeleAssistanceCompositeService {

	private WorkflowServiceImpl impl;
	
	public GraphicalTeleAssistanceCompositeService(String path, WorkflowServiceImpl impl) {
		super(path);
		this.impl = impl;
	}

	@LocalOperation
	public HealthReport getVitalParameters(){
		HealthReport metrics = super.getVitalParameters();
		//impl.updateUI() ...
		
		return metrics;
	}
	
	@LocalOperation
	public int pickTask(){
		//impl.updateUi
		
		return super.pickTask();
	}
}