package com.webapp.server;

import java.util.Map;
import java.util.Random;

import com.soa.object.AnalysisResult;
import com.soa.object.Decision;
import com.soa.object.Drug;
import com.soa.object.HealthReport;
import com.webapp.client.event.State;

import service.atomic.ExtraBehavior;
import service.auxiliary.ServiceDescription;

public class AtomicServiceBehavior extends ExtraBehavior {

	private WorkflowServiceImpl impl;

	public AtomicServiceBehavior(WorkflowServiceImpl impl){
		super();
		this.impl = impl;
	}

	public boolean preInvokeOperation(ServiceDescription description, String operationName, Object... args) {
		super.preInvokeOperation(description, operationName, args);
		
		Map<String, Object> customProperties = description.getCustomProperties();
		if(customProperties.containsKey("Reliability")) {
			double failureRate = (double)customProperties.get("Reliability");
			double r = new Random().nextDouble()*100;
			
			failureRate = 0.7;
			System.out.println("FAILURE ? " + r + " <= " + failureRate*100);
			if(r <= failureRate*100)
				return false;
		}
		
		String[] tab;
		switch(operationName){
		case "analyzeData": 
			tab = new String[52];
			HealthReport hr = (HealthReport)args[0];
			tab[0] = drugToString(hr.getDrug());
			tab[1] = ""+hr.getDose();
			int j=0;
			for(int i=2; i<tab.length;i++){
				tab[i] = ""+hr.getHeartRate()[j];
				j++;
			}
			impl.updateClientUI(tab, State.PRE_ANALYZE_DATA);
			break;
		}
		return true;
	}

	public Object postInvokeOperation(ServiceDescription description, String operationName, Object result, Object... args) {
		System.out.println("OPERATION NAME : "+operationName);
		String[]  tab;
		AnalysisResult ar;
		switch(operationName){
		case "changeDoses" :
			ar = (AnalysisResult) result;
			tab = new String[2];
			tab[0] = drugToString(ar.getDrug());
			tab[1] = ""+ar.getDoses();
			impl.updateClientUI(tab, State.CHANGE_DOSES);				
			break;
		case "changeDrug" :
			ar = (AnalysisResult) result;
			tab = new String[2];
			tab[0] = drugToString(ar.getDrug());
			tab[1] = ""+ar.getDoses();
			impl.updateClientUI(tab, State.CHANGE_DRUG);
			break;
		case "pickTask":
			int task = ((int)result);
			tab =  new String[1];
			tab[0] = ""+task;
			impl.updateClientUI(tab, State.PICK_TASK);
			break;
		case "analyzeData":
			ar = ((AnalysisResult) result);
			tab = new String[1];
			tab[0] = decisionToString(ar.getDecision());
			impl.updateClientUI(tab, State.POST_ANALYZE_DATA);
			break;
		case "triggerAlarm":
			tab = new String[1];
			tab[0] = "Alarm";
			impl.updateClientUI(tab, State.SEND_ALARM);
			break;
		}

		return result;
	}

	private String drugToString(Drug drug){
		if(drug==Drug.DRUG1){
			return "Drug n°1";
		}
		else if(drug==Drug.DRUG2){
			return "Drug n°2";
		}
		else{
			return "No drug";
		}
	}
	
	private String decisionToString(Decision decision){
		switch(decision){
		case CHANGE_DOSES:
			return "Change doses";
		case CHANGE_DRUG:
			return "Change drug";
		case NOTHING:
			return "Do nothing";
		default:
			return "Do nothing";
		}
	}
}
