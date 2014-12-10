package com.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface WorkflowServiceAsync {
	void initialize(AsyncCallback<String> callback);
	void createClient(int waitingTime, AsyncCallback<Boolean> callback);
	void sendTask(int choice, AsyncCallback<Boolean> callback);
	void isWorkflowStarted(AsyncCallback<Boolean> callback);
}
