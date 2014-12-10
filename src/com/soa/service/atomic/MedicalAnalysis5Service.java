package com.soa.service.atomic;

import java.util.Map;

import com.webapp.server.WorkflowServiceImpl;


public class MedicalAnalysis5Service extends MedicalAnalysisService {

	public MedicalAnalysis5Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	


	public static MedicalAnalysisService main(String[] args) {
		MedicalAnalysis5Service medicalAnalysisService = new MedicalAnalysis5Service("MedicalAnalysisService", "se.lnu.course4dv109.service.medicalAnalysis5");

		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.0005);
		customProperties.put("Performance", 2.0);
		customProperties.put("Cost", 11.9);
		
		medicalAnalysisService.getServiceDescription().setResponseTime(2);
		
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}