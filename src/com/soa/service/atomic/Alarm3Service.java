package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class Alarm3Service extends AlarmService {

	public Alarm3Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static AlarmService main(String[] args) {
		String serviceName="se.lnu.course4dv109.service.alarm3";
		double reliability = 0.01;
		
		AlarmService alarmService = new Alarm3Service("AlarmService", serviceName);
		
		Map<String, Object> customProperties = alarmService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 0.3);
		customProperties.put("Cost", 6.8);
		
		int[] temp = {((int)(reliability*100.0)), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		alarmService.getServiceDescription().setResponseTime(1);
		
		alarmService.startService();
		alarmService.register();

		return alarmService;
	}

}