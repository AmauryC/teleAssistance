package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis1Service extends MedicalAnalysisService {

	public MedicalAnalysis1Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args) {
		
		String serviceName="se.lnu.course4dv109.service.medicalAnalysis1";
		double reliability = 0.01;
		
		MedicalAnalysis1Service medicalAnalysisService = new MedicalAnalysis1Service("MedicalAnalysisService", serviceName);

		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 2.2);
		customProperties.put("Cost", 9.8);
		
		int[] temp = {((int)reliability*100), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		medicalAnalysisService.getServiceDescription().setResponseTime(3);
		
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}