package com.soa.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class StrategyQoS implements AbstractQoSRequirement {
	private HashMap<String, int[]> weights;
	
	public void init(HashMap<String, int[]> weights) {
		this.weights = weights;
	}	
	
    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {
		double min = Double.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
			double cost = min;
		    if (properties.containsKey("Cost")){
		    	cost = (double)properties.get("Cost");
		    }
		    
		    double execTime = min;
		    if (properties.containsKey("Performance")){
		    	execTime = (double)properties.get("Performance");
		    }
		    
		    double failureRate = min;
		    if (properties.containsKey("Reliability")){
		    	failureRate = (double)properties.get("Reliability");		
		    }
		    
		    double weightedSum = min;
		    if(weights.containsKey(serviceDescriptions.get(i).getServiceName())) {
		    	int[] serviceWeights = weights.get(serviceDescriptions.get(i).getServiceName());
		    	
		    	weightedSum = cost * serviceWeights[0] + execTime * serviceWeights[1] + failureRate * serviceWeights[2];
		    }
		    if(weightedSum < min) {
		    	min = weightedSum;
		    	index = i;
		    }
		}
		
		return serviceDescriptions.get(index);
    }
}
