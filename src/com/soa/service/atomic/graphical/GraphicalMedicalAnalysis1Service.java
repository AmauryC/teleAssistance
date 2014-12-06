package com.soa.service.atomic.graphical;

import com.soa.object.AnalysisResult;
import com.soa.object.HealthReport;
import com.soa.service.atomic.MedicalAnalysis1Service;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalMedicalAnalysis1Service extends MedicalAnalysis1Service {

	private WorkflowServiceImpl impl;
	
	public GraphicalMedicalAnalysis1Service(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
		super(serviceName, serviceEndpoint);
		this.impl = impl;
	}

	@ServiceOperation
	public AnalysisResult analyzeData(HealthReport report){
		AnalysisResult analysis = super.analyzeData(report);
		//impl.updateUI() ...
		
		return analysis;
	}
}