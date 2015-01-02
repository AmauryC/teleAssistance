package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;


public class MedicalAnalysis5Service extends MedicalAnalysisService {

	public MedicalAnalysis5Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static MedicalAnalysisService main(String[] args) {
		String serviceName="se.lnu.course4dv109.service.medicalAnalysis5";
		double reliability = 0.05;
		
		MedicalAnalysisService medicalAnalysisService = new MedicalAnalysis5Service("MedicalAnalysisService", serviceName);		
		Map<String, Object> customProperties = medicalAnalysisService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 2.0);
		customProperties.put("Cost", 11.9);
		
		int[] temp = {((int)reliability*100), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		medicalAnalysisService.getServiceDescription().setResponseTime(2);
		
		medicalAnalysisService.startService();
		medicalAnalysisService.register();

		return medicalAnalysisService;
	}

}