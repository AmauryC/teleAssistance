package com.soa.object;

public class HealthReport {

	private Drug drug;
	private double dose;
	private int[] heartRate;
	
	public HealthReport(Drug drug, double dose, int[] heartRate){
		this.drug = drug;
		this.dose = dose;
		this.heartRate = heartRate;
	}

	public Drug getDrug() {
		return drug;
	}

	public double getDose() {
		return dose;
	}

	public int[] getHeartRate() {
		return heartRate;
	}
	
	
}
