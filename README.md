# WxServiceAlerts

webMethods package to provide metrics on service usage and allows you configure alert rules based alerts based on the number of executions, 
average delay and/or number of errors.

*local installation*

If you have an Integration Server or Micro Service Runtime running locally for development purposes, first navigate to your packages directory;

$ cd /${SAG_HOME}/IntegrationServer/packages

or

$ cd /${SAG_HOME}/IntegrationServer/instances/${INSTANCE}/packages

If your packages directory is already under version control

$ git submodule add https://github.com/johnpcarter/WxServiceAlerts.git WxServiceAlerts

or if you are not, then simply clone the repository

$ git clone https://github.com/johnpcarter/WxServiceAlerts.git

You will also need the package JcPublicTools via

$ git clone https://github.com/johnpcarter/JcPublicTools.git

*Setup*

You can then start configuring which services you want to monitor and trace via the package home directory.

http://localhost:5555/WxServiceAlerts

Data is collated in memory and volatile, I would only recommend using 1 minute collection intervals for testing purposes. 
In real world settings use a much larger interval and with a small number of permissible slots in order to ensure that you do not use too much memory. 
The goal of this package is not to trace individual transactions but to track .

An optional database table is created at startup called 'wx_servicealerts_history' if the JDBC connection 'wx.service.alerts.db:conn' 
has been set appropriately and that you haven't configured the extended setting 'wx.service.alerts.configuration:persistServiceSignature' (see below). 

Do not forget to reload the package after updating the connection to ensure that the table gets created.

*Transaction Monitoring*

Transaction totals for services are collated based on the collection interval and then persisted via the service 'wx.service.alerts.db:persistSnapshot'.
You can replace this persistence service with your own by setting the extended setting 'watt.service.alerts.snapshot.service'. Your service should implement
the specification 'wx.service.alerts.configuration:persistServiceSignature'.

You can view the service analytics directly for each service from the page home page of the package

http://localhost:5555/WxServiceAlerts

Alternatively if you want too see only the transaction totals for each service then an online transaction report is also available

http://localhost:5555/WxServiceAlerts/?selectedTab=history

The report can be downloaded as a CSV file.

NOTE: Any time period other than "since last restart" requires the aforementioned database and so won't provide any results if you have configured your 
own persistence service.


*Usage*

Online documentation is provided by the package here

http://localhost:5555/WxServiceAlerts/help.dsp

*Source code*

webMethods services are directly editable. Supporting java library at code/jars/simple-rules.jar is in the
git repository [WmServiceRules](https://github.com/johnpcarter/WmServiceRules).

> This is a webMethods Micro Service Runtime package and requires a Software AG Micro Service Runtime or Integration Server (10.11+) to host it. Package versioning and configuration can be referenced in the package [manifest](./manifest.v3)  file. Alerts will only work if you are hosting in the package in a runtime that is 10.11 or better.
