package com.soa.forms;

import java.util.HashMap;

import com.soa.service.composite.TeleAssistanceCompositeService;
import com.webapp.server.WorkflowServiceImpl;

import activforms.engine.ActivFORMSEngine;
import activforms.engine.Synchronizer;

public class TeleAssistanceEffector extends Synchronizer{

	private int getTaskChannelId, getEmergencyChannelId, getAdaptedChannelId, launchWorkflowChannelId;
	private TeleAssistanceCompositeService composite;
	private WorkflowServiceImpl impl;
	
	public TeleAssistanceEffector(ActivFORMSEngine engine, WorkflowServiceImpl impl) {
		this.impl = impl;
		this.composite = impl.getCompositeService();
		this.getTaskChannelId = engine.getChannelId("getTask");
		this.getEmergencyChannelId = engine.getChannelId("getEmergency");
		this.getAdaptedChannelId = engine.getChannelId("getAdapted");
		this.launchWorkflowChannelId = engine.getChannelId("launchWorkflow");
		
		engine.register(getTaskChannelId, this);
		engine.register(getEmergencyChannelId, this);
		engine.register(getAdaptedChannelId, this);
		engine.register(launchWorkflowChannelId, this);
	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {

		if(channelId == getTaskChannelId ){
			composite.setChoice((int)arg1.get("task"));
		}
		else if(channelId == getEmergencyChannelId){
			composite.setEmergency((int)arg1.get("emergency"));
		}
		else if(channelId == getAdaptedChannelId){
			composite.setAdapted((int)arg1.get("isAdapted"));
		}
		else if(channelId == launchWorkflowChannelId){
			impl.start();
		}
	}

}
