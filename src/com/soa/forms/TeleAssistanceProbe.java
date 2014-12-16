package com.soa.forms;

import java.util.HashMap;

import activforms.engine.ActivFORMSEngine;
import activforms.engine.Synchronizer;

public class TeleAssistanceProbe extends Synchronizer{

	private ActivFORMSEngine engine;
	private int getFailureChannelId, setFailureChannelId;
	
	public TeleAssistanceProbe(ActivFORMSEngine engine) {
		this.engine = engine;
		this.getFailureChannelId = engine.getChannelId("getFailure");
		this.setFailureChannelId = engine.getChannelId("setFailure");
		
		engine.register(getFailureChannelId, this, "");
	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {

		if(channelId == getFailureChannelId){
			engine.send(setFailureChannelId, "alarmFailureRate= ["+);
		}
	}

}
