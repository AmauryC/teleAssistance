package com.soa.object;

public class AnalysisResult {

	private Decision decision;
	private Drug drug;
	private double doses;
	
	public AnalysisResult(Decision decision){
		this.decision = decision;
	}
	
	public AnalysisResult(Decision decision, Drug drug, double doses){
		this.decision = decision;
		this.drug = drug;
		this.doses = doses;
	}

	public Decision getDecision() {
		return decision;
	}

	public Drug getDrug() {
		return drug;
	}

	public double getDoses() {
		return doses;
	}
	
	
}
