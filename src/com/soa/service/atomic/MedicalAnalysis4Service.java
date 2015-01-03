package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;

public class MedicalAnalysis4Service extends MedicalAnalysisService {

	public MedicalAnalysis4Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args) {
		String serviceName="se.lnu.course4dv109.service.medicalAnalysis4";
		double reliability = 0.03;
		
		MedicalAnalysisService medicalAnalysisService = new MedicalAnalysis4Service("MedicalAnalysisService", serviceName);
		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 2.9);
		customProperties.put("Cost", 7.3);
		
		int[] temp = {((int)(reliability*100.0)), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		medicalAnalysisService.getServiceDescription().setResponseTime(3);
		
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}