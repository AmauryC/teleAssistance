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
	this.graphValues = [ [ 'No.', 'Bet', 'Profit', 'Assets'] ]
}

App.prototype.start = function() {
	document.getElementById('app').style.display = "block";
	document.getElementById('homepage').style.display = "none";
	this.initiateSequence();
};

App.prototype.displayProfit = function(profit){
	console.log("displaying result ...");
	var div = document.getElementById("workflow_result");
	console.log(div);
	div.innerText = "Result " +profit;
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

