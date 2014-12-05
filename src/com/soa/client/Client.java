package com.soa.client;

import service.composite.CompositeServiceClient;

public class Client extends CompositeServiceClient {

	private static int patientId = 5;

	public Client() {
		super("se.lnu.course4dv110");
	}

	public boolean start() {	

		//qosRequirements.add("BestPerformance");
		//List<String> qosRequirements = new ArrayList<String>(); //client.getQosRequirementNames();
		String qosRequirement;
		qosRequirement = "Performance";
		
		boolean stopped = false;

		stopped = (boolean)this.invokeCompositeService(qosRequirement, patientId);
		System.out.println("KABLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA : "+stopped);
		//Here we return because we only want to run once for the moment. Remove the return and it will run for each QoS requirement
		return stopped;
	}
}