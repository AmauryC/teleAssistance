package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.atomic.graphical.GraphicalMedicalAnalysis2Service;
import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis2Service extends MedicalAnalysisService {

	public MedicalAnalysis2Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalMedicalAnalysis2Service medicalAnalysisService = new GraphicalMedicalAnalysis2Service("MedicalAnalysisService", "se.lnu.course4dv109.service.medicalAnalysis2", impl);

		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.001);
		customProperties.put("Performance", 2.7);
		customProperties.put("Cost", 8.9);
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}