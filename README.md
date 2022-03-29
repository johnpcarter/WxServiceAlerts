# WxServiceAlerts

webMethods package to allow alerts to be triggered based on service usage i.e. number of executions, average delay and/or number of errors.

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

Data is collated in memory and volatile, I would only recommend using 1 minute collection intervals for testing purposes. In real world settings use a much larger interval and with a small number of permissible slots in order to ensure that you do not use too much memory. The goal of this package is not to trace historical data but to instead detect performance/issues with service execution and trigger an alert.

A database table is created at startup called 'wx_servicealerts_history' if the JDBC connection 'wx.service.alerts.db:conn' has been set appropriately. Reload the package after updating the connection to ensure that the table gets created in the current schema.

*Transaction Monitoring*

I have now introduced transaction counting, and added persistent support for tracking totals over time via the aforementioned datavbase table. Totals for each service are written to the database with each interval. Counts be viewed online and downloaded in CSV format from;

http://localhost:5555/WxServiceAlerts/?selectedTab=history


*Source code*

webMethods services are directly editable. Supporting java library at code/jars/simple-rules.jar is in the
git repository [WmServiceRules](https://github.com/johnpcarter/WmServiceRules).

> This is a webMethods Micro Service Runtime package and requires a Software AG Micro Service Runtime or Integration Server to host it. Package versioning and configuration can be referenced in the package [manifest](./manifest.v3)  file.
