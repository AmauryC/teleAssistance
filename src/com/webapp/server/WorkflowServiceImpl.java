package com.webapp.server;


import service.provider.AbstractService;

import com.soa.client.Client;
import com.soa.service.atomic.Alarm3Service;
import com.soa.service.atomic.Alarm1Service;
import com.soa.service.atomic.Alarm2Service;
import com.soa.service.atomic.DrugService;
import com.soa.service.atomic.MedicalAnalysis1Service;
import com.soa.service.atomic.MedicalAnalysis2Service;
import com.soa.service.atomic.MedicalAnalysis3Service;
import com.soa.service.atomic.MedicalAnalysis4Service;
import com.soa.service.atomic.MedicalAnalysis5Service;
import com.soa.service.composite.TeleAssistanceCompositeService;
import com.soa.service.registry.TeleAssistanceServiceRegistry;
import com.webapp.client.ServerMessageGeneratorService;
import com.webapp.client.WorkflowService;
import com.webapp.client.event.UpdateUIEvent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.service.RemoteEventServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class WorkflowServiceImpl extends RemoteEventServiceServlet implements
WorkflowService, ServerMessageGeneratorService {
	private AbstractService[] services = new AbstractService[11];

	private Client client;

	public String result;
	public int waitingTime = 1000;

	@Override
	public String initialize() {
		String[] args = {};

		if(services[0] == null) {
			services[0] = TeleAssistanceServiceRegistry.start();
			System.out.println("PATATE 1 ");
		}
		if(services[1] == null) {
			services[1] = MedicalAnalysis1Service.main(args, this);
			System.out.println("PATATE 2 ");
		}
		if(services[2] == null) {
			services[2] = MedicalAnalysis2Service.main(args, this);
			System.out.println("PATATE 3 ");
		}
		if(services[3] == null) {
			services[3] = MedicalAnalysis3Service.main(args, this);
			System.out.println("PATATE 3 ");
		}
		if(services[4] == null) {
			services[4] = MedicalAnalysis4Service.main(args, this);
			System.out.println("PATATE 3 ");
		}
		if(services[5] == null) {
			services[5] = MedicalAnalysis5Service.main(args, this);
			System.out.println("PATATE 3 ");
		}
		if(services[6] == null) {
			services[6] = Alarm1Service.main(args, this); 
			System.out.println("PATATE 4 ");
		}
		if(services[7] == null){
			services[7] = Alarm2Service.main(args, this);
			System.out.println("PATATE 7 ");
		}
		if(services[8] == null) {
			services[8] = Alarm3Service.main(args, this);
			System.out.println("PATATE 5 ");
		}
		if(services[9] == null){
			services[9] = DrugService.main(args, this);
			System.out.println("PATATE 7 ");
		}
		if(services[10] == null) {
			String[] path = {getServletContext().getRealPath("tele_assistance-workflow.txt")};
			System.out.println("PATATE 6 ");
			System.out.println(path);
			services[10] = TeleAssistanceCompositeService.main(path, this); 
		}


		return "OK";
	}

	public boolean createClient(int waitingTime) {
		this.waitingTime = waitingTime;

		if(client == null) {
			client = new Client();
		}
		System.out.println("Starting the client ...");
		boolean result = client.start();

		System.out.println("Returns the result");
		return result;
	}

	public void updateClientUI(String[] object, int state) {
		Event theEvent = new UpdateUIEvent(object, state);
		//add the event, so clients can receive it
		addEvent(UpdateUIEvent.SERVER_MESSAGE_DOMAIN, theEvent);

		try {
			Thread.sleep(this.waitingTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void start() {
		System.out.println("Workflow start ???");
	}

	@Override
	public boolean sendTask(int choice) {
		TeleAssistanceCompositeService composite = ((TeleAssistanceCompositeService)services[10]);
		composite.setChoice(choice);
		synchronized(composite){
			composite.notify();
		}
		return true;
	}
}
