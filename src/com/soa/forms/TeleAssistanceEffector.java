package com.soa.forms;

import java.util.HashMap;

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
	
	public TeleAssistanceEffector(ActivFORMSEngine engine, WorkflowServiceImpl impl) {
		this.impl = impl;

		this.labChannelId = engine.getChannelId("selectLabWithStrategy");
		this.alarmChannelId = engine.getChannelId("selectAlarmWithStrategy");
		this.drugChannelId = engine.getChannelId("selectDrugWithStrategy");
	
		engine.register(labChannelId, this, "currentTask", "strategy");
		engine.register(alarmChannelId, this, "currentTask", "strategy");
		engine.register(drugChannelId, this, "currentTask", "strategy");
	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {
		this.composite = impl.getCompositeService();
		
		if(channelId == labChannelId || channelId == alarmChannelId || channelId == drugChannelId){
			System.out.println("EFFFFFFFFFECTOR");
			impl.executePlan((int)arg1.get("currentTask"), (int)arg1.get("strategy"));
		}
	}

}
