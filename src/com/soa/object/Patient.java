package com.soa.object;

import java.util.Random;

public class Patient {

	private static Patient instance = null;
	private Drug drug;
	private double dose;
	private int previousRate;
	public static final int NORMAL_HEART_RATE = 65;
	public static final int NORMAL_RATE_VARIATION = 15;


	public static Patient getInstance(){
		if(instance==null){
			instance= new Patient();
		}
		return instance;

	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public void setDose(double dose) {
		this.dose = dose;
	}

	private Patient(){
		this.drug = Drug.NONE;
		this.dose = 0.0;
		this.previousRate=65;
	}

	public HealthReport getHeartRateData(){
		Random rand = new Random();
		int trend = rand.nextInt(11);
		System.out.println("TREND : "+trend);

		int evolution;
		if(this.drug == Drug.DRUG1){
			evolution = (int)(0.0-(this.dose*10));
		}
		else if(this.drug == Drug.DRUG2){
			evolution = (int)(0.0+(this.dose*10));
		}
		else{
			evolution=0;
		}
		int[] data = new int[50];

		if(trend==0){
			for(int i=0;i<50;i++){
				data[i] = this.previousRate - i + ( rand.nextInt(31) -15) + evolution;
			}
		}
		else if(trend==10){
			for(int i=0;i<50;i++){
				data[i] = this.previousRate + i + ( rand.nextInt(31) -15) + evolution;
			}
		}
		else{
			for(int i=0;i<50;i++){
				data[i] = this.previousRate +( rand.nextInt(31) -15) + evolution;
			}
		}


		this.previousRate = data[49];

		return new HealthReport(this.drug, this.dose, data);
	}

	public double getBodyTemperature(){
		return 37.2;
	}

}
