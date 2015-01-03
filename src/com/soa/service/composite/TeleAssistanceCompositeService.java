package com.soa.service.composite;

import activforms.engine.ActivFORMSEngine;
import activforms.goalmanagement.Goal;
import activforms.goalmanagement.goalmanager.GoalManager;

import com.soa.forms.TeleAssistanceEffector;
import com.soa.forms.TeleAssistanceProbe;
import com.soa.object.AnalysisResult;
import com.soa.object.Decision;
import com.soa.object.HealthReport;
import com.soa.object.Patient;
import com.soa.qos.AutomaticStrategyQoS;
import com.soa.qos.CostQoS;
import com.soa.qos.PerformanceQoS;
import com.soa.qos.ReliabilityQoS;
import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.LocalOperation;
import service.composite.CompositeService;

public class TeleAssistanceCompositeService extends CompositeService {

	private String pathToWorkflow;
	private boolean workflowStarted;
	private Patient patient;
	private int choice=-2;
	
	//Variables for self-adaptation. Parameters sent and taken from MAPE-K loop plans
	private int[] serviceStats = new int[3];
	private boolean isAdapted = false;
	private int strategy = -2;
	private AnalysisResult analysisResult;
	

	public static TeleAssistanceCompositeService main(String[] args, WorkflowServiceImpl impl) {
		TeleAssistanceCompositeService compositeService = new TeleAssistanceCompositeService(args[0]);
		compositeService.start();

		try {
			ActivFORMSEngine engine = new ActivFORMSEngine("adapation", args[1], 9000);
			engine.setRealTimeUnit(1000);

			TeleAssistanceProbe probe = new TeleAssistanceProbe(engine, compositeService);
			TeleAssistanceEffector effector = new TeleAssistanceEffector(engine, impl);
			
			//Decision tree
			GoalManager goalManager = engine.getGoalManager();
			goalManager.addModelFromFile("adaptation", args[1]);
			goalManager.addModelFromFile("correction", args[2]);
			
			Goal topGoal = new Goal("Top Goal", "");
			topGoal.addSubGoal(new Goal("adaptation", "correctModel == false"));
			topGoal.addSubGoal(new Goal("correction", "correctModel == true"));

			goalManager.addGoal(topGoal);
			//---------------------------
			
			
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
	
	public TeleAssistanceCompositeService(String path, int task) {
		super("TeleAssistance", "se.lnu.course4dv110", path);
		this.pathToWorkflow = path;
		this.patient = Patient.getInstance();
		this.workflowStarted = false;
		
		this.choice = task;
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
			
			if(!this.isAdapted ){
				synchronized(this){
					this.wait();
				}
			}
			
			if(this.choice == -2)
				return this.pickTask();

			this.workflowStarted = false;
			System.out.println("User chose choice n°"+choice);
			this.isAdapted=false;
			
			return this.choice;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@LocalOperation
	public int getDecision(AnalysisResult result){
		if(result == null)
			return -1;
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

	@LocalOperation
	public int getStrategy(int defaultValue){
		int strat;
		
		if(this.strategy != -2){
			strat = this.strategy ;
		}
		else{
			strat = defaultValue;
		}
		this.strategy = -2;
		
		return strat;
	}
	
	public void setStrategy(int strategy){
		this.strategy = strategy;
	}
	
	public void setChoice(int choice){
		this.choice = choice;
	}
	
	public int getChoice(){
		return this.choice;
	}
	
	public void setAdapted(int isAdapted){
		if(isAdapted==1){
			this.isAdapted = true;
		}
		else{
			this.isAdapted = false;
		}
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
	
	public void resetServiceStats(){
		this.serviceStats = new int[3];
	}
	
	@LocalOperation
	public void resetNormalState(){
		this.isAdapted = false;
		this.strategy = -2;
	}
	
	public void updateMedication(AnalysisResult ar) {
		this.analysisResult = ar;
	}
	
	@LocalOperation
	public AnalysisResult getAnalysisResult(){
		return this.analysisResult;
	}
}
