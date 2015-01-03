package com.soa.forms;

import java.util.HashMap;

import service.adaptation.Effector;
import service.auxiliary.Operation;
import service.auxiliary.ServiceDescription;
import service.provider.AbstractService;

import com.soa.object.AnalysisResult;
import com.soa.service.composite.TeleAssistanceCompositeService;
import com.webapp.server.WorkflowServiceImpl;

import activforms.engine.ActivFORMSEngine;
import activforms.engine.Synchronizer;

public class TeleAssistanceEffector extends Synchronizer{

	private int labChannelId;
	private int alarmChannelId;
	private int drugChannelId;
	private TeleAssistanceCompositeService composite;
	private WorkflowServiceImpl impl;
	private int failureRateChannelId;
	
	public TeleAssistanceEffector(ActivFORMSEngine engine, WorkflowServiceImpl impl) {
		this.impl = impl;

		this.labChannelId = engine.getChannelId("selectLabWithStrategy");
		this.alarmChannelId = engine.getChannelId("selectAlarmWithStrategy");
		this.drugChannelId = engine.getChannelId("selectDrugWithStrategy");
		this.failureRateChannelId = engine.getChannelId("updateFailureRate");
		
		engine.register(labChannelId, this, "currentTask", "strategy");
		engine.register(alarmChannelId, this, "currentTask", "strategy");
		engine.register(drugChannelId, this, "currentTask", "strategy");
		engine.register(failureRateChannelId, this, "failureRate.alarms[0]",
													"failureRate.alarms[1]",
													"failureRate.alarms[2]",
													"failureRate.labs[0]",
													"failureRate.labs[1]",
													"failureRate.labs[2]",
													"failureRate.labs[3]",
													"failureRate.labs[4]",
													"failureRate.drug[0]");

	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {
		this.composite = impl.getCompositeService();
		
		if(channelId == failureRateChannelId){
			
			int DIVISION = 10000;
			double[] failureRates = new double[9];
			failureRates[0] = (double)arg1.get("failureRate.labs[0]")/DIVISION;
			failureRates[1] = (double)arg1.get("failureRate.labs[1]")/DIVISION;
			failureRates[2] = (double)arg1.get("failureRate.labs[2]")/DIVISION;
			failureRates[3] = (double)arg1.get("failureRate.labs[3]")/DIVISION;
			failureRates[4] = (double)arg1.get("failureRate.labs[4]")/DIVISION;
			
			failureRates[5] = (double)arg1.get("failureRate.alarms[0]")/DIVISION;
			failureRates[6] = (double)arg1.get("failureRate.alarms[1]")/DIVISION;
			failureRates[7] = (double)arg1.get("failureRate.alarms[2]")/DIVISION;
			
			failureRates[8] = (double)arg1.get("failureRate.drug[0]")/DIVISION;
			
			AbstractService[] services = impl.getServices();
			Effector effector = new Effector(composite);
			ServiceDescription oldDescription, newDescription;
			for(int i=1;i<10;i++){
				oldDescription = services[i].getServiceDescription();
				
				services[i].getServiceDescription().getCustomProperties().put("Reliability", failureRates[i-1]);
				newDescription = services[i].getServiceDescription();
				for(Operation op : newDescription.getOperationList()){
					effector.updateServiceDescription(oldDescription, newDescription, op.getOpName());
				}	
			}
		}
		
		if(channelId == labChannelId || channelId == alarmChannelId || channelId == drugChannelId){
			System.out.println("EFFFFFFFFFECTOR");
			impl.executePlan((int)arg1.get("currentTask"), (int)arg1.get("strategy"));
		}
	}

}
