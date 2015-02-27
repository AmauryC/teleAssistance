package com.soa.forms;

import java.util.HashMap;

import service.adaptation.Effector;
import service.auxiliary.Operation;
import service.auxiliary.ServiceDescription;
import service.provider.AbstractService;

import com.soa.object.AnalysisResult;
import com.soa.service.composite.ServiceFailureData;
import com.soa.service.composite.TeleAssistanceCompositeService;
import com.webapp.client.event.State;
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
	private int labFailureRateChannelId;
	private int drugFailureRateChannelId;
	private int alarmFailureRateChannelId;

	public TeleAssistanceEffector(ActivFORMSEngine engine, WorkflowServiceImpl impl) {
		this.impl = impl;

		this.labChannelId = engine.getChannelId("selectLabWithStrategy");
		this.alarmChannelId = engine.getChannelId("selectAlarmWithStrategy");
		this.drugChannelId = engine.getChannelId("selectDrugWithStrategy");

		this.labFailureRateChannelId = engine.getChannelId("updateFailureRateLab");
		this.drugFailureRateChannelId = engine.getChannelId("updateFailureRateDrug");
		this.alarmFailureRateChannelId = engine.getChannelId("updateFailureRateAlarm");

		engine.register(labChannelId, this, "currentTask", "strategy");
		engine.register(alarmChannelId, this, "currentTask", "strategy");
		engine.register(drugChannelId, this, "currentTask", "strategy");
		engine.register(labFailureRateChannelId, this, "k.serviceToUpdate.id");
		engine.register(alarmFailureRateChannelId, this, "k.serviceToUpdate.id");
		engine.register(drugFailureRateChannelId, this, "k.serviceToUpdate.id");

	}
	@Override
	public void receive(int channelId, HashMap<String, Object> arg1) {
		this.composite = impl.getCompositeService();

		if(channelId == alarmFailureRateChannelId){

			
			double[] failureRates = new double[9];

			int id = ((int)arg1.get("k.serviceToUpdate.id"));

			AbstractService[] services = impl.getAlarmServices();

			Effector effector = new Effector(this.composite);
			ServiceDescription oldDescription, newDescription;

			oldDescription = services[id].getServiceDescription();

			services[id].getServiceDescription().getCustomProperties().put("Reliability", (ServiceFailureData.getStats("se.lnu.course4dv109.service.alarm"+id)[0]/ServiceFailureData.getStats("se.lnu.course4dv109.service.alarm"+id)[1] ));
			newDescription = services[id].getServiceDescription();
			for(Operation op : newDescription.getOperationList()){
				effector.updateServiceDescription(oldDescription, newDescription, op.getOpName());
			}	


			String[] textualRates = new String[9];
			for(int i=0;i<9;i++){
				textualRates[i] = ""+failureRates[i];
			}
			impl.updateClientUI(textualRates, State.UPDATE_FAILURE_STATS);
			impl.printRates();
		}
		else if(channelId == drugFailureRateChannelId){
			int id = ((int)arg1.get("k.serviceToUpdate.id"));

			AbstractService[] services = impl.getDrugServices();
			Effector effector = new Effector(this.composite);
			ServiceDescription oldDescription, newDescription;
			oldDescription = services[id].getServiceDescription();

			services[id].getServiceDescription().getCustomProperties().put("Reliability", (ServiceFailureData.getStats("se.lnu.course4dv109.service.drug"+id)[0]/ServiceFailureData.getStats("se.lnu.course4dv109.service.drug"+id)[1] ));
			newDescription = services[id].getServiceDescription();
			for(Operation op : newDescription.getOperationList()){
				effector.updateServiceDescription(oldDescription, newDescription, op.getOpName());
			}	

			impl.printRates();
		}
		else if(channelId == labFailureRateChannelId){
			int id = ((int)arg1.get("k.serviceToUpdate.id"));

			AbstractService[] services = impl.getLabServices();
			Effector effector = new Effector(this.composite);
			ServiceDescription oldDescription, newDescription;
			oldDescription = services[id].getServiceDescription();

			services[id].getServiceDescription().getCustomProperties().put("Reliability", (ServiceFailureData.getStats("se.lnu.course4dv109.service.lab"+id)[0]/ServiceFailureData.getStats("se.lnu.course4dv109.service.lab"+id)[1] ));
			newDescription = services[id].getServiceDescription();
			for(Operation op : newDescription.getOperationList()){
				effector.updateServiceDescription(oldDescription, newDescription, op.getOpName());
			}	

			impl.printRates();
		}

		if(channelId == labChannelId || channelId == alarmChannelId || channelId == drugChannelId){
			impl.executePlan((int)arg1.get("currentTask"), (int)arg1.get("strategy"));
		}
	}

}
