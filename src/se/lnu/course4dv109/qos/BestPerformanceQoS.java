package se.lnu.course4dv109.qos;

import java.util.HashMap;
import java.util.List;

import service.auxiliary.ServiceDescription;
import service.workflow.AbstractQoSRequirement;


public class BestPerformanceQoS implements AbstractQoSRequirement {

    @Override
    public ServiceDescription applyQoSRequirement(List<ServiceDescription> serviceDescriptions) {
		int min = Integer.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < serviceDescriptions.size(); i++) {
			HashMap<String, Object> properties = serviceDescriptions.get(i).getCustomProperties();
			
		    if (properties.containsKey("Performance")){
		    	int performance = (int)properties.get("Performance");
		    	
				if (performance < min){
				    min = performance;
				    index = i;
				}
		    }
		}
		
		return serviceDescriptions.get(index);
    }
}
