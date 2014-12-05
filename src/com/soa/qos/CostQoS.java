package com.soa.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class CostQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {
		double min = Double.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
		    if (properties.containsKey("Cost")){
		    	double cost = (double)properties.get("Cost");
		    	
				if (cost < min){
				    min = cost;
				    index = i;
				}
		    }
		}
		
		return serviceDescriptions.get(index);
    }
}
