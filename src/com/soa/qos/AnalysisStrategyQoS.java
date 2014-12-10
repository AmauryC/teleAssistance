package com.soa.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class AnalysisStrategyQoS extends AutomaticStrategyQoS {
	
	public HashMap<String, HashMap<String,Integer>> getWeights() {
		System.out.println("ANALYSIS STRATEGY");
		HashMap<String, HashMap<String,Integer>> weights = new HashMap<String, HashMap<String,Integer>>();
		
		HashMap<String, Integer> alarmsW = new HashMap<String, Integer>();
		alarmsW.put("Cost", 10);
		alarmsW.put("Performance", 20);
		alarmsW.put("Reliability", 70);
		weights.put("AlarmService", alarmsW);
		
		HashMap<String, Integer> labW = new HashMap<String, Integer>();
		labW.put("Cost", 30);
		labW.put("Performance", 10);
		labW.put("Reliability", 60);
		weights.put("MedicalAnalysisService", labW);
		
		HashMap<String, Integer> drugW = new HashMap<String, Integer>();
		drugW.put("Cost", 33);
		drugW.put("Performance", 33);
		drugW.put("Reliability", 33);
		weights.put("DrugService", drugW);
		
		return weights;
	}
}
