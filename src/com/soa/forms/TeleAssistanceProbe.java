package com.soa.forms;

import java.util.HashMap;

import com.soa.object.Drug;
import com.soa.service.composite.ServiceFailureData;
import com.soa.service.composite.TeleAssistanceCompositeService;

import activforms.engine.ActivFORMSEngine;
import activforms.engine.Synchronizer;

public class TeleAssistanceProbe extends Synchronizer{

	private ActivFORMSEngine engine;
	private int getServiceStatusChannelId, setServiceStatusChannelId;
	private TeleAssistanceCompositeService composite;

	public TeleAssistanceProbe(ActivFORMSEngine engine, TeleAssistanceCompositeService composite) {
		this.engine = engine;
		this.composite = composite;
		this.getServiceStatusChannelId = engine.getChannelId("getServicesStatus");
		this.setServiceStatusChannelId = engine.getChannelId("setServicesStatus");

		engine.register(getServiceStatusChannelId, this, null);
	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {

		int[] failureRate = new int[9];
		int[] temp;
		for(int i=0;i<3; i++){
			temp = ServiceFailureData.getStats("se.lnu.course4dv109.service.alarm"+(i+1));
			failureRate[i] = ((temp[0]/temp[1])*10000);
		}
		for(int i=0; i<5;i++){
			temp = ServiceFailureData.getStats("se.lnu.course4dv109.service.medicalAnalysis"+(i+1));
			failureRate[i+3] = ((temp[0]/temp[1])*10000);
		}

		temp = ServiceFailureData.getStats("se.lnu.course4dv109.service.drug");
		failureRate[9] = ((temp[0]/temp[1])*10000);

		if(channelId == getServiceStatusChannelId){
			System.out.println("PROBE : " + composite.getServiceStats()[0]);
			engine.send(setServiceStatusChannelId, 
					"currentFailure="+composite.getServiceStats()[0],
					"currentServiceFailed.type="+composite.getServiceStats()[1],
					"currentServiceFailed.id="+composite.getServiceStats()[2], 
					"failureRate.alarms[0]="+failureRate[0],
					"failureRate.alarms[1]="+failureRate[1],
					"failureRate.alarms[2]="+failureRate[2],
					"failureRate.labs[0]="+failureRate[3],
					"failureRate.labs[1]="+failureRate[4],
					"failureRate.labs[2]="+failureRate[5],
					"failureRate.labs[3]="+failureRate[6],
					"failureRate.labs[4]="+failureRate[7],
					"failureRate.drug[0]="+failureRate[8]
					);
		}
	}
}
