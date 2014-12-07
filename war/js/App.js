/* global document: false */

function App() {
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
	this.healthChart = {
			divId: "bpm-chart",
			chart: null,
			data: null,
			options: null
	};
}

App.prototype.start = function() {
	this.initiateSequence();
	
	//HealthChart
	this.healthChart.chart = new google.visualization.LineChart(document.getElementById("bpm-chart"));
	this.healthChart.data = new google.visualization.DataTable();
	this.healthChart.data.addColumn('string', 'BPM');
	this.healthChart.data.addColumn('number', 'BPM');
	this.healthChart.options = {
			title: 'Health Data',
			vAxis: {
				title: 'Heartbeat'
			}
	};
	google.load("visualization", "1", {packages:["corechart"]});
	this.drawChart();
	
	//Toogle 
	var app = document.getElementById("startApp");
	app.addEventListener("click", this.displayApp, false); 
	
	var failure = document.getElementById("startFailure");
	failure.addEventListener("click", this.displayFailure, false); 
	
};

App.prototype.displayApp = function() {
	document.getElementById("failure").style.display="none";
	document.getElementById("app").style.display="block";
};

App.prototype.displayFailure = function() {
	document.getElementById("failure").style.display="block";
	document.getElementById("app").style.display="none";
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

App.prototype.addInvokePoint = function(description, value) {
	var serviceEndpoint = description[0];

	for(var services in this.charts) {
		var c = this.charts[services];
		if(c.endpointRegex.test(serviceEndpoint)) {
			
			var chartValue = -1;
			if(value == undefined) {
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
			} else {
				chartValue = value;
			}

			c.data.addRow([""+this.runs, chartValue]);
			c.chart.draw(c.data,c.options);

			break;
		}
	}
}

App.prototype.serviceSuccessful = function(description) {
	this.addInvokePoint(description);
};

App.prototype.serviceInvoked = function(description) {

};

App.prototype.serviceTimeout = function(description) {
	this.addInvokePoint(description, 0);
};

App.prototype.workflowStarted = function() {

};

App.prototype.workflowEnded = function() {
	this.initiateSequence();
};

App.prototype.printDrugData = function(array) {
	var drugDiv = document.getElementById("drugDiv").innerHTML="Current drug : "+array[1]+"mg of "+array[0];
};

App.prototype.printHealthData = function(array){

	for(var i=2;i<array.length;i++){
		this.healthChart.data.addRow([""+this.currentIdx, parseFloat(array[i])]);
		this.currentIdx++;
	}
	this.healthChart.chart.draw(this.healthChart.data, this.healthChart.options);
};

App.prototype.printAlarm = function(){
	var alarm = document.getElementById("alarm");
	alarm.style.textDecoration = "blink";
	alarm.style.color="red";
};

App.prototype.printDecision = function(array){

};

App.prototype.initiateSequence = function(){
	launchWorkflow(0);
};

App.prototype.pickTask = function(choice){
	sendTask(choice);
	this.runs++;
};
