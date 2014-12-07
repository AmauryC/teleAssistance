package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.atomic.graphical.GraphicalMedicalAnalysis5Service;
import com.webapp.server.WorkflowServiceImpl;


public class MedicalAnalysis5Service extends MedicalAnalysisService {

	public MedicalAnalysis5Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	


	public static MedicalAnalysisService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalMedicalAnalysis5Service medicalAnalysisService = new GraphicalMedicalAnalysis5Service("MedicalAnalysisService", "se.lnu.course4dv109.service.medicalAnalysis5", impl);

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