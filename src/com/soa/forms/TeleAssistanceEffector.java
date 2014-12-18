package com.soa.forms;

import java.util.HashMap;

import com.soa.service.composite.TeleAssistanceCompositeService;

import activforms.engine.ActivFORMSEngine;
import activforms.engine.Synchronizer;

public class TeleAssistanceEffector extends Synchronizer{

	private ActivFORMSEngine engine;
	private int getServiceStatusChannelId, setServiceStatusChannelId;
	private TeleAssistanceCompositeService composite;
	
	public TeleAssistanceEffector(ActivFORMSEngine engine, TeleAssistanceCompositeService composite) {
		this.engine = engine;
		this.composite = composite;
		this.getServiceStatusChannelId = engine.getChannelId("getServicesStatus");
		this.setServiceStatusChannelId = engine.getChannelId("setServicesStatus");

		engine.register(getServiceStatusChannelId, this, "");
	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {

		if(channelId == getServiceStatusChannelId){
			engine.send(setServiceStatusChannelId, "currentFailure="+composite.getServiceStats()[0],
												   "currentServiceFailed.type="+composite.getServiceStats()[1],
												   "currentServiceFailed.id="+composite.getServiceStats()[2]);
		}
	}

}
