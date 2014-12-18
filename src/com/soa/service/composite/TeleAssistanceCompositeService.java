package com.soa.service.composite;

import activforms.engine.ActivFORMSEngine;
import activforms.gui.ActivFORMS;

import com.soa.forms.TeleAssistanceProbe;
import com.soa.object.AnalysisResult;
import com.soa.object.Decision;
import com.soa.object.HealthReport;
import com.soa.object.Patient;
import com.soa.qos.AutomaticStrategyQoS;
import com.soa.qos.CostQoS;
import com.soa.qos.PerformanceQoS;
import com.soa.qos.ReliabilityQoS;

import service.auxiliary.LocalOperation;
import service.composite.CompositeService;

public class TeleAssistanceCompositeService extends CompositeService {

	private String pathToWorkflow;
	private boolean workflowStarted;
	private Patient patient;
	private int choice=-2;
	
	private int[] serviceStats = new int[3];

	public static TeleAssistanceCompositeService main(String[] args) {
		TeleAssistanceCompositeService compositeService = new TeleAssistanceCompositeService(args[0]);
		compositeService.start();

		try {
			ActivFORMSEngine engine = new ActivFORMSEngine(args[1], 9000);
			//ActivFORMS activForms = new ActivFORMS();
			//activForms.main(args);
			engine.setRealTimeUnit(1000);
			engine.setCommittedLocationTime(1000);
			
			TeleAssistanceProbe probe = new TeleAssistanceProbe(engine, compositeService);
		
			engine.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compositeService;
	}

	public TeleAssistanceCompositeService(String path) {
		super("TeleAssistance", "se.lnu.course4dv110", path);
		this.pathToWorkflow = path;
		this.patient = Patient.getInstance();
		this.workflowStarted = false;
	}

	public void start() {
		this.addQosRequirement("Performance", new PerformanceQoS());
		this.addQosRequirement("Reliability", new ReliabilityQoS());
		this.addQosRequirement("Cost", new CostQoS());
		this.addQosRequirement("AutomaticStrategy", new AutomaticStrategyQoS());
		this.startService();
		this.register();
	}

	@LocalOperation
	public HealthReport getVitalParameters() {
		HealthReport metrics = patient.getHeartRateData();
		return metrics;
	}

	@LocalOperation
	public int pickTask(){
		try {
			System.out.println("Waiting for user input");
			this.workflowStarted = true;
			
			synchronized(this){
				this.wait();
			}
			
			if(this.choice == -2)
				return this.pickTask();

			this.workflowStarted = false;
			System.out.println("User chose choice n°"+choice);
			return this.choice;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@LocalOperation
	public int getDecision(AnalysisResult result){
		Decision decision = result.getDecision();

		switch(decision){
		case NOTHING:
			return -1;
		case CHANGE_DRUG:
			return 0;
		case CHANGE_DOSES:
			return 1;
		case ALARM:
			return 2;
		default:
			return -1;
		}
	}

	public void setChoice(int choice){
		this.choice = choice;
	}
	
	public boolean isWorkflowStarted() {
		return this.workflowStarted;
	}
	
	public int[] getServiceStats(){
		return this.serviceStats;
	}
	public void updateServiceStats(int[] stats){
		if(stats.length==3){
			this.serviceStats=stats;
		}
	}
}