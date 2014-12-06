package com.soa.service.atomic.graphical;

import com.soa.object.AnalysisResult;
import com.soa.object.HealthReport;
import com.soa.service.atomic.MedicalAnalysis2Service;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class GraphicalMedicalAnalysis2Service extends MedicalAnalysis2Service {

	private WorkflowServiceImpl impl;
	
	public GraphicalMedicalAnalysis2Service(String serviceName, String serviceEndpoint, WorkflowServiceImpl impl) {
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