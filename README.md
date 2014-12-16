TeleAssist
=========


## Installation and launch procedure


You need to configure Eclipse to be able to compile and run a GWT project. You only need a few additionnal packages as stated on the [documentation](http://www.gwtproject.org/usingeclipse.html) : 
- Install new software from Eclipse by using this website url : http://dl.google.com/eclipse/plugin/4.3 (change the version if needed)
- Check to install :
    - Plugin > Google plugin for Eclipse
    - SDKs > Google App Engine Java SDK and Google Web Toolkit SDK

Import the modified RSP version included in the archive.

Import the project in eclipse. Include the RSP project into this project's build path.

*> If there are some import problems with google packages, go to your project properties > Google > Web Toolkit > and make sure "Use GWT" is checked.*

Then launch the project (Right click on it > Run As > Web application)

Finally Eclipse will give you a local URL to access to webpage (like http://127.0.0.1:8888/tele_assistance.html?gwt.codesvr=127.0.0.1:9997)


## Libraries

GWTEventService (https://code.google.com/p/gwteventservice/) : to realize the communication between the GWT server and the GWT client.


## Authors

@AmauryC
@ohlala