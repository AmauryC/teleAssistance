/* global document: false */

function App() {
	this.completed = true;
	
	this.runs = [0, 0, 0];
	this.currentStrategy = 0;

	this.charts = {
			alarms: {
				strategy: 1,
				divId: "alarm-chart",
				endpointRegex: /alarm(\d+)$/,
				columns: [0,1],
				chart: null,
				data: null,
				options: null,
				ticks: [{v:0, f:"Failure"}]
			},
			labs: {
				strategy: 1,
				divId: "lab-chart",
				endpointRegex: /medicalAnalysis(\d+)$/,
				columns: [0,1],
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
	window.setTimeout(isWorkflowStarted, 3000);
	
	//Toogle 
	var app = document.getElementById("startApp");
	app.addEventListener("click", this.displayApp, false); 
	
	var failure = document.getElementById("startFailure");
	failure.addEventListener("click", this.displayFailure, false); 
	
	this.drawChart();
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
		c.data.addColumn("number", "Strategy 1");
		c.data.addColumn("number", "Strategy 2");

		c.options = {
				title: 'Invocation results',
				vAxis: {
					title: 'Selection result',
					ticks: c.ticks
				},	
				isStacked: false
		};

		c.chart = new google.visualization.SteppedAreaChart(document.getElementById(c.divId));
		//c.chart.draw(c.data, c.options);
	}
	
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
};

App.prototype.addInvokePoint = function(description, value) {
	var serviceEndpoint = description[0];
	var serviceInfo = serviceEndpoint.split(".");
	var serviceName = serviceInfo[serviceInfo.length-1];

	for(var services in this.charts) {
		var c = this.charts[services];
		if(c.endpointRegex.test(serviceEndpoint)) {
			
			var chartValue = -1;
			if(value == undefined) {
				for(var i = 0; i < c.ticks.length; i++) {
					
					if(c.ticks[i].f == serviceName) {
						chartValue = c.ticks[i].v;
					}
				}

				if(chartValue == -1) {
					chartValue = c.ticks.length;
					c.ticks.push({
						v: c.ticks.length,
						f: serviceName
					});
				}
			} else {
				chartValue = value;
			}

			//c.data.addRow(["" + this.runs[this.currentStrategy], chartValue]);
			for(var j = 0; j <= this.currentStrategy; j++) {
				if(c.columns.indexOf(this.currentStrategy) == -1) {
					c.data.addColumn("number", "Strategy " + (this.currentStrategy+1));
					c.columns.push(this.currentStrategy);
				}
			}

			var rowNumber = this.runs[this.currentStrategy]-1;
			while(c.data.getNumberOfRows() <= rowNumber) {
				c.data.addRow();
			}
			c.data.setCell(rowNumber, 0, ""+this.runs[this.currentStrategy]);
			c.data.setCell(rowNumber, this.currentStrategy+1, chartValue);
			c.chart.draw(c.data,c.options);

			break;
		}
	}
}

App.prototype.serviceSuccessful = function(description) {
	this.addInvokePoint(description);
	
	var disablers = document.querySelectorAll('.disabler');
	for(var i = 0; i < disablers.length; i++) {
		disablers[i].style.display = "none";
	}
};

App.prototype.serviceInvoked = function(description) {
	var disablers = document.querySelectorAll('.disabler');
	for(var i = 0; i < disablers.length; i++) {
		disablers[i].style.display = "block";
	}
};

App.prototype.serviceTimeout = function(description) {
	this.addInvokePoint(description, 0);
	app.initiateSequence();
};

App.prototype.workflowStarted = function() {
	var disablers = document.querySelectorAll('.disabler');
	for(var i = 0; i < disablers.length; i++) {
		disablers[i].style.display = "none";
	}
};

App.prototype.workflowEnded = function() {
	var disablers = document.querySelectorAll('.disabler');
	for(var i = 0; i < disablers.length; i++) {
		disablers[i].style.display = "block";
	}
};

App.prototype.printDrugData = function(array) {
	console.log("<p><b>Drug : </b>"+array[0]+"<br/><b>Doses: </b>"+array[1]+" mg</p>");
	var drugDiv = document.getElementById("drugDiv").innerHTML="<p><b>Drug : </b>"+array[0]+"<br/><b>Doses: </b>"+array[1]+" mg</p>";
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
	document.getElementById("decision").innerHTML=""+array[0];
};

App.prototype.initiateSequence = function(){
	launchWorkflow(0);
};

App.prototype.pickTask = function(choice){
	sendTask(choice);
	++this.runs[choice];
	this.currentStrategy = choice;
	
	var disablers = document.querySelectorAll('.disabler');
	for(var i = 0; i < disablers.length; i++) {
		disablers[i].style.display = "block";
	}
};
