<!DOCTYPE HTML>
<html>
<head>
<title>Service Analytics</title>
    <link rel="stylesheet" type="text/css" href="/WmRoot/webMethods.css">
    <link rel="stylesheet" type="text/css" href="/WmRoot/top.css">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v6.0.0-beta2/css/all.css">
</head>
<body style="overflow-y: scroll;padding: 0px" topmargin="0" leftmargin="0" marginwidth="0" marginheight="0">
    <div class="tdmasthead" id="top" height="50px">
      <div style="float:right;padding: 10px">
          <img src="./resources/sag-logo-white@3x.png" height="25px"/>
      </div>
      <div class="saglogo" style="display: flex; align-items: center;">
          <img src="./resources/wm-microservice-runtime.svg" height="50px" alt="Micro Service Runtime"/>
      </div>
  </div>
    <table width="100%">
      <tr>
        <td class="menu-navigator" style="border:none" colspan=2>packages &gt; WxServiceAlerts  &gt; <a href="./configuration.dsp">Configuration</a> &gt; <b>Help</b>
          <div style="float:right">
            <a style="margin-left: 10px" href="./index.dsp">Analytics</a> 
          </div>
        </td>
      </tr>
	  </table>
    <div style="margin: 40px; margin-right: 60px">
      <h1>About</h1>
      <p>
        webMethods package to provide metrics on service usage and provide alerts based on number of executions, average delay and/or number of errors.
      </p>
      <h1>Installation</h1>
      <p>
        If you have an Integration Server or Micro Service Runtime running locally for development purposes, first navigate to your packages directory;
        
        <pre>
  $ cd /${SAG_HOME}/IntegrationServer/packages
        </pre>or
        <pre>
  $ cd /${SAG_HOME}/IntegrationServer/instances/${INSTANCE}/packages
        </pre>If your packages directory is already under version control
        <pre>
  $ git submodule add https://github.com/johnpcarter/WxServiceAlerts.git WxServiceAlerts
        </pre>or if you are not, then simply clone the repository
        <pre>
  $ git clone https://github.com/johnpcarter/WxServiceAlerts.git
        </pre>You will also need the package JcPublicTools via
        <pre>
  $ git clone https://github.com/johnpcarter/JcPublicTools.git
        </pre>
      </p>
      
      <h1>Configuration</h1>
      <p>
        Configuring a JDBC connection is the only post installation step after installing the packages.
        A database table is created automatically when the package is first loaded if the JDBC connection 'wx.service.alerts.db:conn' has been set appropriately. Once you have configured the connection, reload the package and check that the table 'wx_servicealerts_history' has been created in the schema that you selected in your connection.
      </p>
      <p><b>NOTE: </b> Both mysql 5.4+ and postgres 10.6+ have been tested with this package. Alerting system is only available if running the package in webMethods 10.11 or better.

      <h1>Usage</h1>
      <p>
        A service filter has to be created in order to track services, you can do this online via the package's home screen
      </p>
      <p clas="body">
        <a href="http://localhost:5555/WxServiceAlerts">http://localhost:5555/WxServiceAlerts</a>
      </p>
      <p>
        A default configuration is provided out of the box to ensure that all top-level services are tracked for both execution and failure as well as consumed transactions. A transaction being defined as 3000 milliseconds. Follow the following procedure to either create your own tracking configuration or modify the existing one. The configuration is stored in json file 'properties.cnf' in the package's config directory.
      </p>
      <h2>Creating a new service filter</h2>
      <p>
        Click on configuration and then click on the button "Add service filter"
      </p>
      <p>
        This will present you with a form with the following attributes
      </p>
      <img style="width: 1200px" src="resources/screenshot.png">
      <p>
        <b>Type</b> - Either audit or exception event i.e. do you want to track service execution or failure,
      </p>
      <p>
        <b>Trace</b> - Determines the type of services to be traced, i.e. all services, all services excluding webMethods internal services. Alternatively you can choose to only track top-level services or only top level services excluding webMethods internal services.
      </p>
      <p>
        <b>Filter</b> - Additional filter combined with 'Trace' to further isolate the services to track i.e. the service name's will have to start with the filter e.g. 'jc.pub' would only filter services beginning with this string such as 'jc.pub.orders:validate'
      </p>
      <p>
        <b>Time interval</b> - Determines the time interval between rule evaluation and persistent to the history table.
      </p>
      <p>
        <b>Max slots</b> - Specified how many intervals to maintain in memory, required for rule evaluation that spans intervals or uses sticky feature.
      </p>
      <p>
        <b>Include empty slots</b> - By default services are omitted from if it is not invoked during the time interval. Set this to true to record empty slots for any services that have been recorded at least once since the package was last reloaded.
      </p>
      <p>
        <b>Include empty slots</b> - By default services are omitted from if it is not invoked during the time interval. Set this to true to record empty slots for any services that have been recorded at least once since the package was last reloaded.
      </p>
      <p>
        <b>Transaction duration</b> - Value in milliseconds that determines a single transaction. Services that take longer than this will trigger multiple transaction counts. This attribute is only applicable if enabling the transaction counter.
      </p>
      <p>
        Enable the counters that are required, along with the collection type i.e. if you want to track the number of service executions, enable 'Count' and then select the switch for 'total'. Likewise to track the average execution delay of services, enable 'Duration' and then select the switch for 'average'.
      </p>
      <div class="quote">
        <p><b>NOTE: </b></p>
        <p>
          Data is collated in memory, it is only recommended to use 1 minute collection intervals for testing purposes. In real world settings use a much larger interval and with a small number of permissible slots in order to ensure that you do not use too much memory. The caveat being that with a larger interval the risk of lost tracing becomes a bigger risk if the server crashes mid-interval.
         </p>
      </div>
      <h2>Adding alerting rules</h2>
      <p>You can also choose to have explicit alerts triggered based on the tracked data that you configured in the previous section i.e. you could ensure that if the number of the exceptions for a critical service exceed some threshold over a chosen period of time (time interval) or even across multiple time slots. To add a rule click on the '+' button below the counter to which you want an alert in the edit page of the service filter. You can add as many rules to each counter as you wish.
      </p>
      <img style="width: 350px" src="resources/rule.png">
      <p>
        <b>Operator</b> - The first column allows you to pick the the operation either greater than '>' or percentage '%'. Choose greater than if you want to use a hard value such such as error count exceeds 5 over 5 minute interval. If you pick percentage then it will instead trigger an alert if the number exceeds the percentage of all event types for the same counter. This only makes sense if you are also tracking both audit and exception events with the same interval and filter.
      </p>
      <p>
        <b>Value</b> - Enter either the hard value or percentage into the second column.
      </p>
      <p>
        <b>Number of occurrences</b> - The third column prefixed with the crossed fingers is to determine how many occurrences to count before triggering the alert i.e. if 1 (default) then the alert will be triggered the first time the rule is true. Otherwise the rule will have to be true across the number of intervals indicated. This value should not exceed the 'max slots' value or it will never fire!
      </p>
      <p>
        <b>Alert type</b> - Next select the alert type, either Info, Warn or Error.
      </p>
      <p>
        <b>Sticky</b> - Click on the pin to determine if an error should be considered sticky or not. If sticky the alert will only be fired once even if new occurrences occur, otherwise the rule will trigger every time.
      </p>
      <div class="quote">
        <p><b>NOTE: </b></p>
        <p>
          Alerting is only available in webMethods 10.11 or better. Rules are applied only to the in memory cache and restricted by the max slots value.
        </p>
      </div>
      <p>
          Alerts are shown in the Admin console in the alerts popup. However, you cab use the service 'pub.alert.notifier:create' if you want to create a pre-emptive action to be executed when an alert is fired.
      </p>
      <h2>Editing an existing service filter</h2>
      <p>
        To edit an existing service filter, expand it by clicking on it in the configuration page. edit and delete buttons are included at the bottom of the panel below the counter/rules summary. Editing an existing rule presents the same editing features as mentioned before. However be aware that changing the filter will effectively result in a new service filter being added to your configuration!
      </p>
    </br>
      <h1>Dashboards</h1>
      <p>In the analytics page you have two views; Recent and History. These view allow you track what is going on without having to use APIs or perform SQL queries on the database.
      </p>
      <h2>Recent</h2>
      <p>
        Bar graphs showing the totals for each tracked service and counter over time. Audit and exception events are grouped into the same graph for each counter. Alerts are flagged via via warning triangles above the column to indicate at what time and why the alert was triggered.

      </p>
      <img style="width: 1200px" src="resources/analytics.png">
      <div class="quote">
        <p><b>NOTE: </b></p>
        <p>
          The recent tab only shows data cached in memory based on the max slots attribute that is defined in the service filters. The chart will be initially empty if restarting the server or reloading the package.
        </p>
      </div>
      <h2>History</h2>
      <p>
        Table summary of totals for each services for the selected time period. This table is based on the data traced to the table 'wx_servicealerts_history' and not the memory cache. You can download a csv version of the table via the 'Download' button.
      </p>
      <img style="width: 1200px" src="resources/totals.png">
      <div class="quote">
        You can automate the export of the file by scheduling the service 'wx.service.alerts.db:archive'. By default with no inputs it will generate a csv for all tracked services for the current month. The file is written to the packages '/resources' directory as 'SERVICE-STATS-MM-DD-YYYY.csv'
      </div>
    </div>
</body>
</html>