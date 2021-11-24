<html>
	<head>
	<title>Service Analytics</title>
		<link rel="stylesheet" type="text/css" href="/WmRoot/webMethods.css">
		<link rel="stylesheet" type="text/css" href="/WmRoot/top.css">
		<link rel="stylesheet" type="text/css" href="styles.css">
		<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v6.0.0-beta2/css/all.css">
		<meta http-equiv="refresh" content="60">
	</head>
	<body>
%invoke wx.service.alerts.record:getServiceAnalytics%
	%loop results%
	%loop types%
	<div style="margin-left: -5px; background-color: white">
	  <div class="collapsible">%value name% %value source% (%value uom%)
		  <div style="float: right; font-weight: bold; margin-top: -3px; margin-right: 40px; line-height: 25px; vertical-align: middle;">
			%loop eventTypes%
				<img src="images/pixel_%value color%.gif" width="10" height="10" alt="%value label%"/> %value label%
			%endloop%	  
	  	</div>
	  </div>
	  <div class="content" style="max-height: 300px; background-color: white; overflow-x: auto">

	<!-- goes here -->
		<div style="min-width: 100%; min-height: 200px; display: inline-flex; justify-content: flex-end; align-items: flex-end">
			  %loop intervals%
			  <div style="display: flex; flex-direction: column; justify-content: flex-end; text-align: center;">
				<div style="display: inline-flex; justify-content: space-between; align-items: flex-end; border-bottom: 1px solid gray; margin-bottom: 10px; padding-right: 10px">
					%loop values%
					  <div style="padding-left: 5px;">
				      %ifvar value equals('0.0')%
				  	  %else%
						%ifvar didFire equals('true')%
					  	  <div style="width: 15px; text-align-center; color: gray; margin-bottom: 5px">
								<i style="color: red" class="%ifvar violationLevel equals('0')%fa fa-info-square%endif%
								%ifvar violationLevel equals('1')%fa fa-exclamation-triangle%endif%
								%ifvar violationLevel equals('2')%fa fa-times-octagon%endif%" aria-hidden="true"></i>
								<p style="font-size: 8px; margin-bottom: 1px;">%value value%</p>
							</div>
						%else%
					  	  <p style="font-size: 8px; color: gray">%value value%</p>
					  	%endif%
				  	  %endif%
				  	  	<img src="images/pixel_%value color%.gif" width="14" height="%value scaledValue%" alt="%value value%"/>
				   	  </div>
				   	%endloop%
				</div>
				<div style="margin-left: -25px; height: 40px; transform: rotate(60deg);"> %value time%</div>
			  </div>
			  %endloop%
		</div>
		  
	<!-- bam -->
	  </div>
	  <div style="padding:10px"></div>
	</div>
  %endloop%
  %endloop%
  %endloop%
  %endinvoke%
	</body>
</html>