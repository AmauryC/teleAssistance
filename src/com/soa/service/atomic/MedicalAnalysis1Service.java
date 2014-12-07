package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.atomic.graphical.GraphicalMedicalAnalysis1Service;
import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis1Service extends MedicalAnalysisService {

	public MedicalAnalysis1Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalMedicalAnalysis1Service medicalAnalysisService = new GraphicalMedicalAnalysis1Service("MedicalAnalysisService", "se.lnu.course4dv109.service.medicalAnalysis1", impl);

		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.0006);
		customProperties.put("Performance", 2.2);
		customProperties.put("Cost", 9.8);
		
		medicalAnalysisService.getServiceDescription().setResponseTime(3);
		
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}