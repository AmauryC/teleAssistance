package com.soa.client;

import service.composite.CompositeServiceClient;

public class Client extends CompositeServiceClient {

	private static int patientId = 5;

	public Client() {
		super("se.lnu.course4dv110");
	}

	public boolean start() {	

		String qosRequirement = "AnalysisStrategy";
		
		boolean stopped = false;
		try {
			stopped = (boolean)this.invokeCompositeService(qosRequirement, patientId);
		} catch(ClassCastException e) {
			
		}
		return stopped;
	}
}