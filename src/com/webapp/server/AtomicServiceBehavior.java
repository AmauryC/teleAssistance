package com.webapp.server;

import com.soa.object.AnalysisResult;
import com.soa.object.Drug;
import com.soa.object.HealthReport;
import com.soa.object.Patient;
import com.webapp.client.event.State;

import service.atomic.ExtraBehavior;

public class AtomicServiceBehavior extends ExtraBehavior {

	private WorkflowServiceImpl impl;

	public AtomicServiceBehavior(WorkflowServiceImpl impl){
		super();
		this.impl = impl;
	}

	public boolean preInvokeOperation(String operationName, Object... args) {
		super.preInvokeOperation(operationName, args);
		String[] tab;
		switch(operationName){
		case "analyzeData": 
			tab = new String[51];
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

	public Object postInvokeOperation(String operationName, Object result, Object... args) {
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
			tab = new String[2];
			tab[0] = drugToString(ar.getDrug());
			tab[1] = ""+ar.getDoses();
			impl.updateClientUI(tab, State.POST_ANALYZE_DATA);
			break;
		case "sendAlarm":
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
}
