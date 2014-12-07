/* global document: false */

function App() {
	this.locked = false;
	this.completed = true;
	this.assets = 1000.0;
	this.winnings = 0;
	this.DEFAULT_TIME = 0;
	this.currentIdx = 0;
	this.paid = 0;
	this.runs = 0;
	
	this.charts = {
			alarms: {
				divId: "alarm-chart",
				endpointRegex: /alarm(\d+)$/,
				chart: null,
				data: null,
				options: null,
				ticks: [{v:0, f:"Failure"}]
			},
			labs: {
				divId: "lab-chart",
				endpointRegex: /medicalAnalysis(\d+)$/,
				chart: null,
				data: null,
				options: null,
				ticks: [{v:0, f:"Failure"}]
			},
	};

	this.graphValues = [ [ 't', 'BPM'] ];
}

App.prototype.start = function() {
	this.initiateSequence();
	
	google.load("visualization", "1", {packages:["corechart"]});
    this.drawChart();
};

App.prototype.drawChart = function() {
	for(var services in this.charts) {
		var c = this.charts[services];
		c.data = new google.visualization.DataTable();

		// Declare columns
		c.data.addColumn('string', 'AA');
		c.data.addColumn('number', 'Strategy 1');


	    c.options = {
	      title: 'Invocation results',
	      vAxis: {
	    	  title: 'Selection result',
	    	  ticks: c.ticks
	      },	
	      isStacked: true
	    };

	    c.chart = new google.visualization.SteppedAreaChart(document.getElementById(c.divId));
	    c.chart.draw(c.data, c.options);
	}
};

App.prototype.serviceSuccessful = function(description) {
	var serviceEndpoint = description[0];
	
	for(var services in this.charts) {
		var c = this.charts[services];
		if(c.endpointRegex.test(serviceEndpoint)) {
			var chartValue = -1;
			for(var i = 0; i < c.ticks.length; i++) {
				if(c.ticks[i].f == description[0]) {
					chartValue = c.ticks[i].v;
				}
			}
			
			if(chartValue == -1) {
				chartValue = c.ticks.length;
				c.ticks.push({
					v: c.ticks.length,
					f: description[0]
				});
			}
			
			c.data.addRow([""+this.runs, chartValue]);
			c.chart.draw(c.data,c.options);
			
			break;
		}
	}
};

App.prototype.serviceInvoked = function(description) {
	
};

App.prototype.serviceTimeout = function(description) {
	
};

App.prototype.workflowStarted = function() {
	
};

App.prototype.workflowEnded = function() {
	
};

App.prototype.printDrugData = function(array) {
	var drugDiv = document.getElementById("drugDiv").innerHTML="Current drug : "+array[1]+"mg of "+array[0];
};

App.prototype.printHealthData = function(array){
	document.getElementById("bpm_chart").innerHTML="";
	var data = google.visualization.arrayToDataTable(this.graphValues);

	var options = {
			titlePosition: 'none',
			legend: { position: 'bottom' },
			height: 250,
			vAxis: {
				logScale: true
			}
	};

	var chart = new google.visualization.LineChart(document.getElementById('bpm_chart'));

	chart.draw(data, options);

	for(var i=2;i<array.length;i++){
		this.graphValues.push( [  this.currentIdx, 
		                          parseFloat(array[i]), 
		                          ]);
		this.currentIdx++;
	}

};

App.prototype.printAlarm = function(){
	alert("EMERGENCY CALLED !");
};

App.prototype.printDecision = function(array){

};

App.prototype.initiateSequence = function(){
	if (!this.locked) {
		this.locked = true;
		launchWorkflow(0);
	}
};

App.prototype.pickTask = function(choice){
	sendTask(choice);
	this.runs++;
};
