# WxServiceAlerts

webMethods package to allow alerts to be triggered based on service usage i.e. number of executions, average delay and/or number of errors.

*local installation*

If you have an Integration Server or Micro Service Runtime running locally for development purposes, first navigate to your packages directory;

$cd /${SAG_HOME}/IntegrationServer/packages \
or \
$cd /${SAG_HOME}/IntegrationServer/instances/${INSTANCE}/packages \

If your packages directory is already under version control

$git submodule add https://github.com/johnpcarter/WxServiceAlerts.git WxServiceAlerts

or if you are not, then simply clone the repository

$git clone https://github.com/johnpcarter/WxServiceAlerts.git

*Setup*

You can then start configuring which services you want to monitor and trace via the package home directory.

http://localhost:5555/WxServiceAlerts

You will need to enable auditing at the service level if you want to track service performance i.e. duration or execution count. You will need to do this in Designer and for each service. Ensure that you set the audit setting "Log on" to "Error, Success end Start". However, you do not have to configure service monitoring in order for this package to work.

Data is collated in memory and volatile, I would only recommend using 1 minute collection intervals for testing purposes. In real world settings use a much larger interval and with small number of permissable slots in order to ensure that you do use too much memory. THe goal of this package is not to trace historical data but to instead detect performance/issues with service execution and trigger an alert.

*Source code*

webMethods services are directly editable. Supporting java library at code/jars/simple-rules.jar is in the
git repository [WmServiceRules](https://github.com/johnpcarter/WmServiceRules).
