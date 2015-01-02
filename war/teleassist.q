//This file was generated from (Commercial) UPPAAL 4.0.14 (rev. 5615), May 2014

/*

*/
A[] (ta.emergencyFailure || ta.labFailure || ta.drugFailure) imply failureOccured == 1

/*

*/
A[] failureOccured imply sFailed.id > 0 && (sFailed.type == 0 || sFailed.type == 1 || sFailed.type == 2)

/*

*/
A[] failureOccured == 1 || failureOccured == 0
