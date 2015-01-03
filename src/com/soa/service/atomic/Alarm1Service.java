package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class Alarm1Service extends AlarmService {

	public Alarm1Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static AlarmService main(String[] args) {
		String serviceName="se.lnu.course4dv109.service.alarm1";
		double reliability = 0.03;
		
		AlarmService alarmService = new Alarm1Service("AlarmService", serviceName);
		Map<String, Object> customProperties = alarmService.getServiceDescription().getCustomProperties();
		
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 1.1);
		customProperties.put("Cost", 4.1);
		
		int[] temp = {((int)(reliability*100.0)), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		alarmService.getServiceDescription().setResponseTime(2);
		
		alarmService.startService();
		alarmService.register();

		return alarmService;
	}

}