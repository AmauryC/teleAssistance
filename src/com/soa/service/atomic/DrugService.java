package com.soa.service.atomic;

import java.util.Map;

import com.soa.object.AnalysisResult;
import com.soa.object.Drug;
import com.soa.object.Patient;
import com.soa.service.atomic.graphical.GraphicalDrugService;
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
	public void changeDrug(AnalysisResult result, int patientId) {
		Patient patient = Patient.getInstance();
		patient.setDrug(result.getDrug());
		patient.setDose(result.getDoses());
		System.out.println("Patient n°"+patientId+" changing drugs. New drug is "+this.drugToString(result.getDrug())+" with doses = "+result.getDoses());
	}
	
	@ServiceOperation
	public void changeDoses(AnalysisResult result, int patientId) {
		Patient patient = Patient.getInstance();
		patient.setDose(result.getDoses());
		System.out.println("Patient n°"+patientId+" changing doses. New doses is "+result.getDoses());
	}


	public static DrugService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalDrugService drugService = new GraphicalDrugService("DrugService", "se.lnu.course4dv109.service.drug", impl);

		Map<String, Object> customProperties = drugService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.03);
		customProperties.put("Performance", 1.1);
		customProperties.put("Cost", 4.1);
		drugService.startService();
		drugService.register();

		return drugService;
	}

}