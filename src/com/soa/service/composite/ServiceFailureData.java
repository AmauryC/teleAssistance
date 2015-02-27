package com.soa.service.composite;

import java.util.HashMap;

public class ServiceFailureData {

	private static HashMap<String, int[]> stats;

	public ServiceFailureData(){
		if(stats==null){
			stats = new HashMap<String, int[]>();
		}
	}

	public static int[] getStats(String service){
		if(stats==null){
			stats = new HashMap<String, int[]>();
		}

		return stats.get(service);
	}

	
	/**
	 * Update the failure rate of the service
	 * @param service : the name of the service
	 * @param failed : whether the invokation failed or not
	 */
	public static void updateStats(String service, boolean failed){
		
		if(stats==null){
			stats = new HashMap<String, int[]>();
		}
		
		int[] temp = stats.get(service);

		if(temp!=null){
			if(failed){
				temp[0] = temp[0]+1;
			}
			temp[1] = temp[1]+1;
			stats.put(service, temp);
		}
	}

	/**
	 * Sets a new entry in the service stats. 
	 * @param service : the name of the service
	 * @param values : table. First cell is number of failed attempts, second is total number of attempts
	 */
	public static void setStats(String service, int[] values){
		if(stats==null){
			stats = new HashMap<String, int[]>();
		}
		stats.put(service, values);
	}
}
