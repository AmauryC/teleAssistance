package com.soa.forms;

import java.util.HashMap;

import com.soa.object.Drug;
import com.soa.service.composite.ServiceFailureData;
import com.soa.service.composite.TeleAssistanceCompositeService;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.webapp.server.WorkflowServiceImpl;

import activforms.engine.ActivFORMSEngine;
import activforms.engine.Synchronizer;

public class TeleAssistanceProbe extends Synchronizer{

	private ActivFORMSEngine engine;
	private int getServiceStatusChannelId, setServiceStatusChannelId;
	private TeleAssistanceCompositeService composite;
	private WorkflowServiceImpl impl;

	public TeleAssistanceProbe(ActivFORMSEngine engine, TeleAssistanceCompositeService composite, WorkflowServiceImpl impl) {
		this.engine = engine;
		this.composite = composite;
		this.impl = impl;
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
			failureRate[i] = (int)(((double)temp[0]/(double)temp[1])*10000);
		}
		for(int i=0; i<5;i++){
			temp = ServiceFailureData.getStats("se.lnu.course4dv109.service.medicalAnalysis"+(i+1));
			failureRate[i+3] = (int)(((double)temp[0]/(double)temp[1])*10000);
		}

		temp = ServiceFailureData.getStats("se.lnu.course4dv109.service.drug");
		failureRate[8] = (int)(((double)temp[0]/(double)temp[1])*10000);

		if(channelId == getServiceStatusChannelId){
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
					"failureRate.drug[0]="+failureRate[8],
					"cachedRate.alarms[0]="+impl.getAlarmServices()[0].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.alarms[1]="+impl.getAlarmServices()[1].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.alarms[2]="+impl.getAlarmServices()[2].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.labs[0]="+impl.getLabServices()[0].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.labs[1]="+impl.getLabServices()[1].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.labs[2]="+impl.getLabServices()[2].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.labs[3]="+impl.getLabServices()[3].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.labs[4]="+impl.getLabServices()[4].getServiceDescription().getCustomProperties().get("Reliability"),
					"cachedRate.drug[0]="+impl.getDrugServices()[0].getServiceDescription().getCustomProperties().get("Reliability")
					
					
					);
		}
	}
}
