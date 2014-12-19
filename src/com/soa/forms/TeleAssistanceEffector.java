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

		this.launchWorkflowChannelId = engine.getChannelId("launchWorkflow");
	
		engine.register(launchWorkflowChannelId, this, "currentTask");
	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {
		this.composite = impl.getCompositeService();
		
		if(channelId == launchWorkflowChannelId){
			System.out.println("EFFFFFFFFFECTOR");
			composite.setChoice((int)arg1.get("currentTask"));
			composite.setAdapted(0);
			impl.createClient(1000);
		}
	}

}
