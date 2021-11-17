<html>
	<head>
	<title>Service Analytics</title>
		<link rel="stylesheet" type="text/css" href="/WmRoot/webMethods.css">
		<link rel="stylesheet" type="text/css" href="/WmRoot/top.css">
		<link rel="stylesheet" type="text/css" href="styles.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
		<!-- <meta http-equiv="refresh" content="30"> -->
	</head>
	<body>
%invoke wx.service.alerts.record:getAnalytics%
	%loop results%
	%loop computers%
	%loop history%
	<div style="margin-left: -5px; background-color: white">
	  <div class="collapsible">%value id% <div style="float: right; font-weight: bold; margin-right: 40px">%value ../type% %value ../source% (%value ../uom%)</div></div>
	  <div class="content" style="max-height: 300px; background-color: white; overflow-x: auto">

	<!-- goes here -->
		<table style="min-width: 100%; text-align: center;">
			<tr>
			  <td><img src="images/blank.gif" height="10" width="10"></td>
			  <td colspan="24"></td>
			</tr>
			<tr>
			  <td style="height: 200px">
			  </td>
			  <td></td>
			  %loop values%
				<td style="width: 10px; vertical-align: bottom; text-align: center">
				  %ifvar value equals('0.0')%
				  %else%
					%ifvar didFire equals('true')%
					  <p style="font-size: 8px; color: red">%value value%</p>
					%else%
					  <p style="font-size: 8px; color: gray">%value value%</p>
					  %endif%
				  %endif%
				  <img src=%ifvar didFire equals('true')%"images/pixel_red.gif"%else%"images/pixel_green.gif"%endif%
				   width="14"
				   height="%value scaledValue%"
				   alt="%value value%"/>
				</td>
			  %endloop%
			<td></td>
			</tr>
			<tr>
			  <td colspan="24"><hr/></td>
			<tr>
			  <td></td>
			  <td></td>
			  %loop values%
				<td style="width: 10px" class="evenrowdata"><div style="margin-left:-15px; width: 40px; height: 40px; transform: rotate(60deg);"> %value time%</div></td>
			  %endloop%
			</tr>
		  </table>
		  
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