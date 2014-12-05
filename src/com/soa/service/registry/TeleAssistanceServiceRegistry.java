package com.soa.service.registry;

import service.registry.ServiceRegistry;

public class TeleAssistanceServiceRegistry extends ServiceRegistry {
	
	public static TeleAssistanceServiceRegistry start() {
		TeleAssistanceServiceRegistry serviceRegistry = new TeleAssistanceServiceRegistry();
		serviceRegistry.startService();
		
		return serviceRegistry;
	}

	public static void main(String[] args) {
		
	}
}