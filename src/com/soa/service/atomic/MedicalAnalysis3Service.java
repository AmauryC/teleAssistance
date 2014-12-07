package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.atomic.graphical.GraphicalMedicalAnalysis3Service;
import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis3Service extends MedicalAnalysisService {

	public MedicalAnalysis3Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysis3Service main(String[] args, WorkflowServiceImpl impl) {
		GraphicalMedicalAnalysis3Service medicalAnalysisService = new GraphicalMedicalAnalysis3Service("MedicalAnalysisService", "se.lnu.course4dv109.service.medicalAnalysis3", impl);

		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.0015);
		customProperties.put("Performance", 3.1);
		customProperties.put("Cost", 9.3);
		
		medicalAnalysisService.getServiceDescription().setResponseTime(4);
		
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}