package com.soa.service.atomic;

import com.soa.object.AnalysisResult;
import com.soa.object.Decision;
import com.soa.object.Drug;
import com.soa.object.HealthReport;
import com.soa.object.Patient;

import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class MedicalAnalysisService extends AtomicService {

	public MedicalAnalysisService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}
	
	@ServiceOperation
	public AnalysisResult analyzeData(HealthReport healthReport) {
		
		int normalRate = Patient.NORMAL_HEART_RATE;
		int normalVariation = Patient.NORMAL_RATE_VARIATION;
		
		int first = healthReport.getHeartRate()[0];
		int last = healthReport.getHeartRate()[49];
		
		if(last+normalVariation > 170 && last-normalVariation < 15){
			return new AnalysisResult(Decision.ALARM);
		}
		else if(last<=first+normalVariation && last>=first-normalVariation){
			//Everything is fine, let's do nothing
			return new AnalysisResult(Decision.NOTHING);
		}
		else if(first>last){
			//HeartRate decreases a lot (about 50 bpm), there is a risk
			Drug drug = healthReport.getDrug();
			if(drug == Drug.DRUG2 || drug == Drug.NONE){
				double doses = (normalRate-last)/10;
				if(drug == Drug.NONE){
					return new AnalysisResult(Decision.CHANGE_DRUG, Drug.DRUG2, doses);
				}
				else{
					return new AnalysisResult(Decision.CHANGE_DOSES, Drug.DRUG2, doses);
				}
			}
			//Case DRUG1
			else {
				double doses = healthReport.getDose();
				double doseToChange = (normalRate-last)/10;

				if(doses< doseToChange){
					return new AnalysisResult(Decision.CHANGE_DRUG, Drug.DRUG2, doseToChange-doses);
				}
				else{
					return new AnalysisResult(Decision.CHANGE_DOSES, Drug.DRUG2, doses-doseToChange);
				}
			}
		}
		else{
			//If it increases, it is also a problem
			Drug drug = healthReport.getDrug();
			if(drug == Drug.DRUG1 || drug == Drug.NONE ){
				double doses = (last-normalRate)/10;
				if(drug == Drug.NONE){
					return new AnalysisResult(Decision.CHANGE_DRUG, Drug.DRUG1, doses);
				}
				else{
					return new AnalysisResult(Decision.CHANGE_DOSES, Drug.DRUG1, doses);
				}
				
			}
			//Case DRUG2
			else {
				double doses = healthReport.getDose();
				double doseToChange = (last-normalRate)/10;

				if(doses< doseToChange){
					return new AnalysisResult(Decision.CHANGE_DRUG, Drug.DRUG1, doseToChange-doses);
				}
				else{
					return new AnalysisResult(Decision.CHANGE_DOSES, Drug.DRUG1, doses-doseToChange);
				}
			}
			
		}
	}

}
