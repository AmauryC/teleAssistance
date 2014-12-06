package com.soa.service.atomic.graphical;

import com.soa.object.AnalysisResult;
import com.soa.object.HealthReport;
import com.soa.service.atomic.MedicalAnalysis4Service;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalMedicalAnalysis4Service extends MedicalAnalysis4Service {

	private WorkflowServiceImpl impl;
	
	public GraphicalMedicalAnalysis4Service(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
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