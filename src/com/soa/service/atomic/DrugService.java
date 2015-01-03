package com.soa.service.atomic;

import java.util.Map;

import com.soa.object.AnalysisResult;
import com.soa.object.Drug;
import com.soa.object.Patient;
import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;

import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class DrugService extends AtomicService {

	public DrugService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	private String drugToString(Drug drug){
		if(drug==Drug.DRUG1){
			return "Drug n°1";
		}
		else{
			return "Drug n°2";
		}
	}
	@ServiceOperation
	public AnalysisResult changeDrug(AnalysisResult result, int patientId) {
		Patient patient = Patient.getInstance();
		patient.setDrug(result.getDrug());
		patient.setDose(result.getDoses());
		System.out.println("Patient n°"+patientId+" changing drugs. New drug is "+this.drugToString(result.getDrug())+" with doses = "+result.getDoses());
		return result;
	}
	
	@ServiceOperation
	public AnalysisResult changeDoses(AnalysisResult result, int patientId) {
		Patient patient = Patient.getInstance();
		patient.setDose(result.getDoses());
		System.out.println("Patient n°"+patientId+" changing doses. New doses is "+result.getDoses());
		return result;
	}


	public static DrugService main(String[] args) {
		String serviceName = "se.lnu.course4dv109.service.drug";
		double reliability = 0.02;
		
		DrugService drugService = new DrugService("DrugService", serviceName);
		
		Map<String, Object> customProperties = drugService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 0.0);
		customProperties.put("Cost", 0.1);
		
		int[] temp = {((int)(reliability*100.0)), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		drugService.getServiceDescription().setResponseTime(2);
		
		drugService.startService();
		drugService.register();

		return drugService;
	}

}