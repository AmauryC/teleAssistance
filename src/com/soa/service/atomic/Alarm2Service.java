package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.composite.ServiceFailureData;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class Alarm2Service extends AlarmService {

	public Alarm2Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static AlarmService main(String[] args) {
		String serviceName="se.lnu.course4dv109.service.alarm2";
		double reliability = 0.04;
		
		AlarmService alarmService = new Alarm2Service("AlarmService", serviceName);

		Map<String, Object> customProperties = alarmService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", reliability);
		customProperties.put("Performance", 0.9);
		customProperties.put("Cost", 2.5);
		
		int[] temp = {((int)(reliability*100.0)), 100};
		ServiceFailureData.setStats(serviceName, temp);
		
		alarmService.getServiceDescription().setResponseTime(1);
		
		alarmService.startService();
		alarmService.register();

		return alarmService;
	}

}