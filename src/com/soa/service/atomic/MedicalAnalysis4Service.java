package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.atomic.graphical.GraphicalMedicalAnalysis4Service;
import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis4Service extends MedicalAnalysisService {

	public MedicalAnalysis4Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalMedicalAnalysis4Service medicalAnalysisService = new GraphicalMedicalAnalysis4Service("MedicalAnalysisService", "se.lnu.course4dv109.service.medicalAnalysis4", impl);

		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.0025);
		customProperties.put("Performance", 2.9);
		customProperties.put("Cost", 7.3);
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}