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

	public static native void printSportEventTable(JsArrayString events)/*-{
		$wnd.app.printSportEventTable(events);
	}-*/;

	public static native void printOdds(JsArrayString jsArrayString)/*-{
		$wnd.app.printOdds(jsArrayString);
	}-*/;

	public static native void printBet(JsArrayString jsArrayString)/*-{
		$wnd.app.printBet(jsArrayString);
	}-*/;

	public static native void printProfit(JsArrayString jsArrayString)/*-{
		$wnd.app.printProfit(jsArrayString);
	}-*/;

	public static native void displayProfit(String s)/*-{
		$wnd.app.displayProfit(s);
	}-*/;

	public static native void computeAssets(double paid)/*-{
		$wnd.app.computeAssets(paid);
	}-*/;

	public static native void highlight(String call)/*-{
		$wnd.parser.highlight(call);
	}-*/;

	public static native void workflowInExecution()/*-{
		$wnd.parser.workflowInExecution();
	}-*/;

	public static native void workflowFinished()/*-{
		$wnd.parser.workflowFinished();
	}-*/;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		exportJSFunction();

		/*RemoteEventService theRemoteEventService = RemoteEventServiceFactory
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
							case State.GET_SPORT_EVENTS:
								printSportEventTable(jsArrayString);
								break;
							case State.REQUEST_ODDS:
								printOdds(jsArrayString);
								break;
							case State.PLACE_BET:
								printBet(jsArrayString);
								break;
							case State.REQUEST_PROFIT:
								printProfit(jsArrayString);
								break;
							case State.MAKE_PAYMENT:
								double paid = Double.parseDouble(tab[0]);
								computeAssets(paid);
								break;
							case State.LOCAL_OPERATION:
								System.out.println("### LOCAL " + tab[0]);
							}

							highlight(tab[tab.length - 1]);
						}
					}
				});*/

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
		System.out.println("CONAN 1");
		workflowInExecution();
		System.out.println("CONAN 2");
		workflowService.createClient(waitingTime, clientCallback);
		System.out.println("CONAN 3");
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
			//displayProfit(-1.0);
			System.out.println("Failure :/");
		}

		@Override
		public void onSuccess(Boolean result) {
			System.out.println("SUCCESS !");
			displayProfit("true");
			workflowFinished();
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
