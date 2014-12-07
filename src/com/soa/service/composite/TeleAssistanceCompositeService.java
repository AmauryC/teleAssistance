package com.soa.service.composite;

import com.soa.object.AnalysisResult;
import com.soa.object.Decision;
import com.soa.object.HealthReport;
import com.soa.object.Patient;
import com.soa.qos.AnalysisStrategyQoS;
import com.soa.qos.CostQoS;
import com.soa.qos.DirectStrategyQoS;
import com.soa.qos.PerformanceQoS;
import com.soa.qos.ReliabilityQoS;
import com.soa.service.atomic.graphical.GraphicalTeleAssistanceCompositeService;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.LocalOperation;
import service.composite.CompositeService;

public class TeleAssistanceCompositeService extends CompositeService {

	private String pathToWorkflow;
	private Patient patient;
	private int choice=-2;

	public static TeleAssistanceCompositeService main(String[] args, WorkflowServiceImpl impl) {
		TeleAssistanceCompositeService compositeService = new GraphicalTeleAssistanceCompositeService(args[0], impl);
		compositeService.start();

		return compositeService;
	}

	public TeleAssistanceCompositeService(String path) {
		super("TeleAssistance", "se.lnu.course4dv110", path);
		this.pathToWorkflow = path;
		this.patient = Patient.getInstance();

	}

	public void start() {
		this.addQosRequirement("Performance", new PerformanceQoS());
		this.addQosRequirement("Reliability", new ReliabilityQoS());
		this.addQosRequirement("Cost", new CostQoS());
		this.addQosRequirement("DirectStrategy", new DirectStrategyQoS());
		this.addQosRequirement("AnalysisStrategy", new AnalysisStrategyQoS());
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
			synchronized(this){
				this.wait();
			}

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
}