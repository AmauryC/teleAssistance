package com.soa.service.atomic;

import java.util.Map;

import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis1Service extends MedicalAnalysisService {

	public MedicalAnalysis1Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args) {
		MedicalAnalysis1Service medicalAnalysisService = new MedicalAnalysis1Service("MedicalAnalysisService", "se.lnu.course4dv109.service.medicalAnalysis1");

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