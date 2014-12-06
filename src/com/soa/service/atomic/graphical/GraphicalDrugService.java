package com.soa.service.atomic.graphical;

import com.soa.object.AnalysisResult;
import com.soa.service.atomic.DrugService;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalDrugService extends DrugService {

	private WorkflowServiceImpl impl;
	
	public GraphicalDrugService(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public void changeDrug(AnalysisResult result, int patientId){
		super.changeDrug(result, patientId);
		//impl.updateUI() ...
		
	}
	
	@ServiceOperation
	public void changeDoses(AnalysisResult result, int patientId){
		super.changeDoses(result, patientId);
		//impl.updateUI() ...
		
	}
}