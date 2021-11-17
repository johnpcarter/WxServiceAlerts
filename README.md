# WxServiceAlerts

webMethods package to allow alerts to be triggered based on service usage i.e. number of executions, average delay and/or number of errors.

*local installation*

If you have an Integration Server or Micro Service Runtime running locally for development purposes, first navigate to your packages directory;

$cd /${SAG_HOME}/IntegrationServer/packages
or $cd /${SAG_HOME}/IntegrationServer/instances/${INSTANCE}/packages

If your packages directory is already under version control

$git submodule add https://github.com/johnpcarter/WxServiceAlerts.git WxServiceAlerts

or if you are not, then simply clone the repository

$git clone https://github.com/johnpcarter/WxServiceAlerts.git

*Setup*

You can then start configuring which services you want to monitor and trace via the package home directory.

http://localhost:5555/WxServiceAlerts
