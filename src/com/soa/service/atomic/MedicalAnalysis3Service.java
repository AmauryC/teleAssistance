package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis3Service extends MedicalAnalysisService {

	public MedicalAnalysis3Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args) {
		String serviceName="se.lnu.course4dv109.service.medicalAnalysis3";
		double reliability = 0.02;
		
		MedicalAnalysisService medicalAnalysisService = new MedicalAnalysis3Service("MedicalAnalysisService", serviceName);
		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 3.1);
		customProperties.put("Cost", 9.3);
		
		int[] temp = {((int)reliability*100), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		medicalAnalysisService.getServiceDescription().setResponseTime(4);
		
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}