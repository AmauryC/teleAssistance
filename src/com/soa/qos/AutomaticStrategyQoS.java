package com.soa.qos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class AutomaticStrategyQoS implements AbstractQoSRequirement {
	private HashMap<String, HashMap<String, Integer>> weights;
	
	@Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions, String opName, Object[] params) {
		AutomaticStrategyQoS strategy;
		if(params.length > 0) {
			try {
				int context = (int)params[params.length-1];
				if(context < 0) {
					strategy = new DirectStrategyQoS();
				} else if(context==0) {
					strategy = new AnalysisStrategyQoS();
				}
				else {
					for(ServiceDescription service : serviceDescriptions){
						if(service.getServiceEndpoint().endsWith(""+context)){
							return service;
						}
					}
					strategy = new AnalysisStrategyQoS();
				}
	
			} catch(ClassCastException e) {
				strategy = new AnalysisStrategyQoS();
			}
		} else {
			strategy = new AnalysisStrategyQoS();
		}
		this.weights = strategy.getWeights();
		
		
		double minValue, maxValue, diff;
		HashMap<String, Object> qosList = serviceDescriptions.get(0).getCustomProperties();
		qosList.remove("ResponseTime");
		
		int serviceCount = serviceDescriptions.size();
		int qosCount = qosList.keySet().size();
		ArrayList<HashMap<String, Double>> normalizedQoS = new ArrayList<HashMap<String, Double>>(serviceCount);
		
		for(String currentQoS : qosList.keySet()) { 
			minValue = Double.MAX_VALUE;
			maxValue = 0;
			
			for(int i = 0; i < serviceCount; i++) {
				HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
				properties.remove("ResponseTime");
				
				if (properties.containsKey(currentQoS)){
					double value = (double)properties.get(currentQoS);
			    	if(value > maxValue) {
			    		maxValue = value;
			    	}
			    	if(value < minValue) {
			    		minValue = value;
			    	}
			    }
			}
			
			diff = maxValue - minValue;
			
			for(int i = 0; i < serviceCount; i++) {
				HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
				properties.remove("ResponseTime");
				
				if (properties.containsKey(currentQoS)){
					double value = (double)properties.get(currentQoS);
					
					if(normalizedQoS.size() < i+1) {
						normalizedQoS.add(i, new HashMap<String, Double>(qosCount));
					}
					normalizedQoS.get(i).put(currentQoS, value);
			    }
			}
		}
		
		double min = Double.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < serviceCount; i++) {
			
			if(weights.containsKey(serviceDescriptions.get(i).getServiceName())) {
		    	HashMap<String, Integer> serviceWeights = weights.get(serviceDescriptions.get(i).getServiceName());
		    	
		    	double sum = 0;
				for(String currentQoS : normalizedQoS.get(i).keySet()) {
					sum = sum + serviceWeights.get(currentQoS) * normalizedQoS.get(i).get(currentQoS); 
				}
				
				if(sum < min) {
					min = sum;
					index = i;
				}
		    }
		}
		
		return serviceDescriptions.get(index);
    }
	
	public HashMap<String, HashMap<String,Integer>> getWeights() {
		return null;
	}
}
