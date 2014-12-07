package com.webapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.webapp.client.event.State;
import com.webapp.client.event.UpdateUIEvent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TeleAssistance implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final static WorkflowServiceAsync workflowService = GWT
			.create(WorkflowService.class);	
	

	public static native void workflowStarted()/*-{
		$wnd.app.workflowStarted();
	}-*/;
	
	public static native void workflowEnded()/*-{
		$wnd.app.workflowEnded();
	}-*/;
	
	public static native void serviceInvoked(JsArrayString description)/*-{
		$wnd.app.serviceInvoked(description);
	}-*/;
	
	public static native void serviceSuccessful(JsArrayString description)/*-{
		$wnd.app.serviceSuccessful(description);
	}-*/;
	
	public static native void serviceTimeout(JsArrayString description)/*-{
		$wnd.app.serviceTimeout(description);
	}-*/;
	
	public static native void printHealthData(JsArrayString data)/*-{
		$wnd.app.printHealthData(data);
	}-*/;

	public static native void printDrugData(JsArrayString jsArrayString)/*-{
		$wnd.app.printDrugData(jsArrayString);
	}-*/;

	public static native void printBet(JsArrayString jsArrayString)/*-{
		$wnd.app.printBet(jsArrayString);
	}-*/;

	public static native void printDecision(JsArrayString jsArrayString)/*-{
		$wnd.app.printDecision(jsArrayString);
	}-*/;

	public static native void printAlarm()/*-{
		$wnd.app.printAlarm();
	}-*/;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		exportJSFunction();

		RemoteEventService theRemoteEventService = RemoteEventServiceFactory
				.getInstance().getRemoteEventService();
		// add a listener to the SERVER_MESSAGE_DOMAIN
		theRemoteEventService.addListener(UpdateUIEvent.SERVER_MESSAGE_DOMAIN,
				new RemoteEventListener() {
					public void apply(Event anEvent) {
						if (anEvent instanceof UpdateUIEvent) {
							UpdateUIEvent event = (UpdateUIEvent) anEvent;
							int state = ((UpdateUIEvent) anEvent).getState();

							String[] tab = event.getGeneratedObject();
							JsArrayString jsArrayString = arrayToJsArray(tab);

							switch (state) {
							case State.WORKFLOW_STARTED:
								workflowStarted();
								break;
							case State.WORKFLOW_ENDED:
								workflowEnded();
								break;
							case State.SERVICE_INVOKED:
								serviceInvoked(jsArrayString);
								break;
							case State.SERVICE_SUCCESSFUL:
								serviceSuccessful(jsArrayString);
								break;
							case State.SERVICE_TIMEOUT:
								serviceTimeout(jsArrayString);

							case State.CHANGE_DOSES:
								printDrugData(jsArrayString);
								break;
							case State.CHANGE_DRUG:
								printDrugData(jsArrayString);
								break;
							case State.PRE_ANALYZE_DATA:
								printHealthData(jsArrayString);
								break;
							case State.POST_ANALYZE_DATA:
								printDecision(jsArrayString);
								break;
							case State.SEND_ALARM:
								printAlarm();
								break;
							}
						}
					}
				});

		workflowService.initialize(callback);
	}

	private JsArrayString arrayToJsArray(String[] tab) {
		JsArrayString jsArrayString = JsArrayString.createArray().cast();
		for (String s : tab) {
			jsArrayString.push(s);
		}
		return jsArrayString;
	}

	public static native void exportJSFunction()/*-{
		$wnd.launchWorkflow = @com.webapp.client.TeleAssistance::launchWorkflow(*);
		$wnd.sendTask = @com.webapp.client.TeleAssistance::sendTask(*);
	}-*/;

	public static void sendTask(int choice){
		workflowService.sendTask(choice, pickTaskCallback);
	}
	public static void launchWorkflow(int waitingTime) {
		workflowService.createClient(waitingTime, clientCallback);
	}

	static AsyncCallback<String> callback = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(String result) {
			
		}

	};

	static AsyncCallback<Boolean> clientCallback = new AsyncCallback<Boolean>() {

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("Failure :/");
		}

		@Override
		public void onSuccess(Boolean result) {
			System.out.println("SUCCESS !");
			System.out.println("Finished");
		}

	};
	
	static AsyncCallback<Boolean> pickTaskCallback = new AsyncCallback<Boolean>() {

		@Override
		public void onFailure(Throwable caught) {
			//displayProfit(-1.0);
			System.out.println("Failure :/");
		}

		@Override
		public void onSuccess(Boolean result) {
			System.out.println("task picked and sent to workflow engine");
		}

	};
}
