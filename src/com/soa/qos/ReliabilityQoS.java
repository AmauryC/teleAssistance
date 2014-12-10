package com.soa.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class ReliabilityQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions, String opName, Object[] params) {
		double min = Double.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
		    if (properties.containsKey("Reliability")){
		    	double reliability = (double)properties.get("Reliability");
		    	
				if (reliability < min){
				    min = reliability;
				    index = i;
				}
		    }
		}
		
		return serviceDescriptions.get(index);
    }
}
