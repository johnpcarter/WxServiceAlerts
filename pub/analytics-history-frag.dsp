<div style="width: 100%; display: flex; justify-content: space-between">
	<div style="width: 75%">
		<input class="searchTerm" style="width: 50%" type="text" placeholder="service name" name="filter" value="%value filter%">
		<select class="filter-select" name="timePeriod" value="%value timePeriod%" onChange="document.getElementById('form').submit()">
			<option value="CACHE" %ifvar timePeriod equals('CACHE')%selected%endif%>Since restart</option>
			<option value="TODAY" %ifvar timePeriod equals('TODAY')%selected%endif%>Today</option>
			<option value="YESTERDAY" %ifvar timePeriod equals('YESTERDAY')%selected%endif%>Yesterday</option>
			<option value="CURRENT_WEEK" %ifvar timePeriod equals('CURRENT_WEEK')%selected%endif%>This week</option>
			<option value="LAST_WEEK" %ifvar timePeriod equals('LAST_WEEK')%selected%endif%>Last week</option>
			<option value="CURRENT_MONTH" %ifvar timePeriod equals('CURRENT_MONTH')%selected%endif%>Current month</option>
			<option value="LAST_MONTH" %ifvar timePeriod equals('LAST_MONTH')%selected%endif%>Last month</option>
	  	  </select>
	</div>
    <div>
	  <button class="pill-button" type="submit">filter</button>
	  <a href="/invoke/wx.service.alerts.db:archive?timePeriod=%value timePeriod%&download=true" class="pill-button" style="background-color: orange">Download</a>
	 </div>
</div>
%invoke wx.service.alerts.dsp:serviceTotals%
  %ifvar results -notempty%
  <div style="margin-left: 5px; margin-top: 20px; margin-bottom: 20px; padding: 30px; border: 1px solid #eeeeee; display: flex; justify-content: space-between;">
	<div style="width: 50%">
		<h1>%value timePeriodLabel%</h1>
		<p style="margin-left: 15px">From: <b>%value results/from%</b></p>
		<p style="margin-left: 15px">To: <b>%value results/to%</b></p>
	</div>
	<div style="width: 50%">
		<p>Tracked services: <b>%value results/trackedServices%</b></p>
		<p>Successful: <b>%value results/totalCount%</b></p>
		<p>Failed: <b>%value results/totalErrors%</b></p>
		<p>Transactions: <b>%value results/totalTransactions%</b></p>
	</div>
</div>
<table style="width: 100%">
  <tr>
	  <th style="background-color: #3774b9; color :white; padding: 15px; text-align: left">Service</th>
	  <th style="background-color: #3774b9; color: white; padding: 15px; width: 250px">Successful</th>
	  <th style="background-color: #3774b9; color: white; padding: 15px; width: 250px">Transactions</th>
	  <th style="background-color: #3774b9; color: white; padding: 15px; width: 250px">Failed</th>
	  <th style="background-color: #3774b9; color: white; padding: 15px; width: 250px">Avg duration (ms)</th>
  </tr>
  %loop results/rows%
	  <tr>
		  <td style="padding: 10px; text-align: left">%value name%</td>
		  <td style="padding: 10px; text-align: center">%value totalCount%</td>
		  <td style="padding: 10px; text-align: center">%value totalTransactions%</td>
		  <td style="padding: 10px; text-align: center">%value totalErrors%</td>
		  <td style="padding: 10px; text-align: center">%value averageDuration%</td>
	  </tr>
	  %endloop%
</table>