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

Data is collated in memory and persisted based on the time interval configured by you. I would only recommend using 1 minute collection intervals for testing purposes. 
In real world settings use a much larger interval along with a small number of permissible historical slots to ensure that you do not use too much memory. 
The goal of this package is not to trace individual transactions but to track trends and allow you to fire rules based on these activities.

Persistence is performed by a the service 'wx.service.alerts.db:persistSnapshot' and the data is written to a database table that is created at startup called 'wx_servicealerts_history'.
This requires that you have configured the JDBC connection 'wx.service.alerts.db:conn' correctly. Make sure to reload the package after configuring the
connection to a database of your choice. The package has been tested with mySQL 8.x, postgres 10.6, and Microsoft SQL Server.

*Customising the persistence service*

You can choose to persist the service statistics to an alternative service of your choice (e.g. redirect trace to ELK via an API call for instance), by creating a service that implemented the specification 'wx.service.alerts.configuration:persistServiceSignature'
and then configuring the extended setting 'watt.service.alerts.snapshot.service' to register your service with the package. 

Don't forget to reload the package afterwards. The database connection will not be required afterwards and can be disabled. The startup service will no longer attempt to create the database
table if you have set this setting. 

*Online Analytics*

You can view the service analytics directly for each service from the page home page of the package

http://localhost:5555/WxServiceAlerts

Alternatively if you want too see only the transaction totals for each service then an online transaction report is also available

http://localhost:5555/WxServiceAlerts/?selectedTab=history

The report can be downloaded as a CSV file.

NOTE: Any time period other than "since last restart" requires the aforementioned database and so won't provide any results if you have configured your 
own persistence service.

*Alerts*
This feature is only available to webMethods 10.7 or better. You can configure rules to detect trends and hence trigger system alerts to detect services 
that are slowing or being invoked too often. You can then in turn subscribe to these alerts to implement custom actions if required. Refer to the usage guide for more info.

*Usage*

Online documentation is provided by the package here

http://localhost:5555/WxServiceAlerts/help.dsp

*Source code*

webMethods services are directly editable. Supporting java library at code/jars/simple-rules.jar is in the
git repository [WmServiceRules](https://github.com/johnpcarter/WmServiceRules).

> This is a webMethods Micro Service Runtime package and requires a Software AG Micro Service Runtime or Integration Server (10.11+) to host it. Package versioning and configuration can be referenced in the package [manifest](./manifest.v3)  file. Alerts will only work if you are hosting in the package in a runtime that is 10.11 or better.
