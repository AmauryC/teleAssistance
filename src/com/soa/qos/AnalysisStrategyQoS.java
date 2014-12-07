package com.soa.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class AnalysisStrategyQoS extends StrategyQoS {
	private int costW, timeW, failureW;
	
	public AnalysisStrategyQoS() {
		super();
		
		HashMap<String, int[]> weights = new HashMap<String, int[]>();
		
		int[] alarmServices = {10, 20, 70};
		weights.put("AlarmService", alarmServices);
		int[] analysisServices = {30, 10, 60};
		weights.put("MedicalAnalysisService", analysisServices);
		int[] drugServices = {33, 33, 33};
		weights.put("DrugService", drugServices);
		
		this.init(weights);
	}
}
