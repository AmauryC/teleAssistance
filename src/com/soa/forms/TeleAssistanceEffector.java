package com.soa.forms;

import java.util.HashMap;

import com.soa.service.composite.TeleAssistanceCompositeService;
import com.webapp.server.WorkflowServiceImpl;

import activforms.engine.ActivFORMSEngine;
import activforms.engine.Synchronizer;

public class TeleAssistanceEffector extends Synchronizer{

	private int launchWorkflowChannelId;
	private TeleAssistanceCompositeService composite;
	private WorkflowServiceImpl impl;
	
	public TeleAssistanceEffector(ActivFORMSEngine engine, WorkflowServiceImpl impl) {
		this.impl = impl;
		this.composite = impl.getCompositeService();

		this.launchWorkflowChannelId = engine.getChannelId("launchWorkflow");
	
		engine.register(launchWorkflowChannelId, this);
	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {

		if(channelId == launchWorkflowChannelId){
			composite.setChoice((int)arg1.get("task"));
			composite.setAdapted((int)arg1.get("isAdapted"));
			impl.createClient(1000);
		}
	}

}
