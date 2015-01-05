package com.webapp.client.event;

public class State {

	public static final int WORKFLOW_STARTED = 100;
	public static final int WORKFLOW_ENDED = 101;
	public static final int SERVICE_INVOKED = 102;
	public static final int SERVICE_SUCCESSFUL = 103;
	public static final int SERVICE_TIMEOUT = 104;

	public static final int PICK_TASK = 0;
	public static final int CHANGE_DOSES = 1;
	public static final int CHANGE_DRUG = 2;
	public static final int SEND_ALARM = 3;
	public static final int PRE_ANALYZE_DATA = 4;
	public static final int POST_ANALYZE_DATA = 5;
	
	public static final int UPDATE_FAILURE_STATS = 6;
}
