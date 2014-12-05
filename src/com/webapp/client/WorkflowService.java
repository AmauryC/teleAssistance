package com.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("workflow")
public interface WorkflowService extends RemoteService {
	String initialize();
	boolean createClient(int waitingTime);
	boolean sendTask(int choice);
}
