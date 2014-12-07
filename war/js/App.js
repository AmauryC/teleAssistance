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
	this.graphValues = [ [ 't', 'BPM'] ]
}

App.prototype.start = function() {
	document.getElementById('app').style.display = "block";
	document.getElementById('homepage').style.display = "none";
	this.initiateSequence();
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
}

