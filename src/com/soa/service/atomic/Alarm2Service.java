package com.soa.service.atomic;

import java.util.Map;

import com.soa.service.atomic.graphical.GraphicalAlarm2Service;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class Alarm2Service extends AlarmService {

	public Alarm2Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	@ServiceOperation
	public void triggerAlarm() {
		System.out.println("CALLING EMERGENCY ... !");
	}


	public static AlarmService main(String[] args, WorkflowServiceImpl impl) {
		GraphicalAlarm2Service alarmService = new GraphicalAlarm2Service("AlarmService", "se.lnu.course4dv109.service.alarm2", impl);

		Map<String, Object> customProperties = alarmService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.04);
		customProperties.put("Performance", 0.9);
		customProperties.put("Cost", 2.5);
		
		alarmService.getServiceDescription().setResponseTime(1);
		
		alarmService.startService();
		alarmService.register();

		return alarmService;
	}

}