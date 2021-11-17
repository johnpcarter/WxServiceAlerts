<div style="margin:20px;">
	  <form id="filterForm" action="./edit.dsp" method="POST">
	<script>
	
		const form = document.querySelector("#filterForm");
		  
		function checkSaveButton() {  
				  
			const saveButton = document.getElementById('save-button');
			
			saveButton.disabled=form.filter.value.length == 0 || form.eventType.value.length == 0;  
		}
		
		function checkValidityButtons(prefix) {
			
			const computers = document.getElementsByName("type-switch");
			const addButtons = document.getElementsByName("add-rule-button"); 
					
			if (form.filter.value.length == 0) {
			  
			  for (var i = 0; i < computers.length; i++) {
				  computers[i].disabled = true;
				  addButtons[i].disabled = !addButtons[i].checked;
			  }
			 
			} else {
				
			  for (var i = 0; i < computers.length; i++) {
					computers[i].disabled = false;					
					addButtons[i].disabled = !computers[i].checked;
				}
				
				checkSaveButton();
			}
		}
		  
		function expandCountPanel(checkbox) {
			
			const p = checkbox.parentElement.parentElement.parentElement;			
			//const tbl = checkbox.closest("table > div");
			const tbl = p.getElementsByTagName('table')[0];
		
			const name = tbl.id;
			
			if (checkbox.checked) {
				tbl.style.display = 'inline-table';
			} else {
				tbl.style.display = 'none';
			}
		}
	</script>
	
		<table style="width: 100%">
		  <tr>
			<td style="width: 90px; text-align: right; padding-right: 10px">
			  Type
			</td>
			<td style="width: 190px">
			  <select class="filter-select" name="eventType" value="%value eventType%">
				<option value="Exception Event">Exception</option>
				<option value="Audit Event" %ifvar eventType equals('Audit Event')%selected%endif%>Audit</option>
			</select>
			</td>
			<td style="width: 120px; text-align: right; padding-right: 10px">
			  Filter 
			</td>
			<td>
			  <input type="text" name="filter" value="%value filter%" length="50" onchange="checkValidityButtons('count'); checkValidityButtons('duration')" />
			</td>
		  </tr>
		  <tr>
			<td style="text-align: right; padding-right: 10px">Time interval</td>
			<td>
			  <select class="filter-select" name="timeInterval" value="%value timeInterval%">
				<option value="30000" %ifvar timeInterval equals('30000')%selected%endif%>5 minutes</option>
				<option value="600000" %ifvar timeInterval equals('600000')%selected%endif%>10 minutes</option>
				<option value="900000" %ifvar timeInterval equals('900000')%selected%endif%>15 minutes</option>
				<option value="1800000" %ifvar timeInterval equals('1800000')%selected%endif%>30 minutes</option>
				<option value="3600000" %ifvar timeInterval equals('3600000')%selected%endif%>1 hour</option>
				<option value="43200000" %ifvar timeInterval equals('43200000')%selected%endif%>12 hour</option>
				<option value="86400000" %ifvar timeInterval equals('86400000')%selected%endif%>24 hour</option>
			  </select>
			</td>
		  </tr>
		  <tr>
			<td style="text-align: right; padding-right: 10px">
			  Max slots
			</td>
			<td>
			  <input type="number" name="maxSlots" value="%value maxSlots%" style="width: 50px"/>
			</td>
			<td style="width: 90px; text-align: right; padding-right: 10px">
			  Include empty slots
			</td>
			<td>
			  <select class="filter-select" name="countZeros">
				<option value="false">No</option>
				<option value="true" %ifvar countZeros equals('true')%selected%endif%>Yes</option>
			</select>
			</td>
		  </tr>
		  <tr>
			<td colspan="4">
			  <hr>
			</td>
		  </tr>
		  <tr>
			<td colspan="4">
			  <h2>Counters</h2>
			  <input type="hidden" name="editRuleId" value="%value editRuleId%"/>
			  <input type="hidden" name="editRuleSource" value="%value editRuleSource%"/>
			  <input type="hidden" name="editRuleComputerType" value="%value editRuleComputerType%"/>
			  %loop computers%
				<div style="border: 1.5px solid #cccccc; border-radius: 6px;margin-top: 10px; margin-bottom: 10px">
					<div style="margin: auto; width: 50%; text-align: center">
						<div style="display: inline-flex; align-items: center; gap: 5px">
							<h2>%value source% </h2>
							<input type="checkbox" class="jc-toggle" name="source-switch" %ifvar active equals('true')%checked%endif% onchange="expandCountPanel(this)" onclick="this.value='%value source%'" value="%value source%"/>
						</div>
					</div>
			  	    <table %ifvar active equals('true')%%else%class="hide"%endif% style="width:100%;">
				  		<tr>
						%loop types%
						  	<td style="width: 200px; text-align: center">
					  			%value type% 
					  		<input type="checkbox" class="cm-toggle" name="type-switch" %ifvar active equals('true')%checked%endif% onchange="checkValidityButtons('%value source%')" onclick="this.value='%value source%-%value type%'" value="%value source%-%value type%"/>
						   </td>
						 %endloop%
				  	   </tr>
				  	   <tr>
						 %loop types%
						 <td> <!-- count rules -->
					     %loop rules%
						 	%ifvar editMode equals('true')%
								%include edit-rule-frag.dsp%
							%else%
						 		%include rule-frag.dsp%
							%endif%
					  	 %endloop%
						 </td>
						 %endloop%
					</td>
				  </tr>
				  <tr>
				    %loop types%
					<td style="width: 200px; text-align: center">
					    %ifvar ruleToEdit -notempty%
					    %else%
							<button class="plain-button" name="add-rule-button" onclick="editRuleSource.value='%value source%';editRuleComputerType.value='%value type%'" disabled><i class="fas fa-plus-circle"></i></button>
					  	%endif%
					</td>
					%endloop%
				  </tr>
			  	    </table>
				</div>
				<script>
					checkValidityButtons('%value source%');
				</script>
			  %endloop%
			</td>
		  </tr>
		</table>
		<div style="float: right; padding: right">
			<button id="save-button" class="pill-button" name="save-changes" onClick="this.value=true" disabled>Save changes</button>
		</div>
		<script>
		checkSaveButton();
		</script>
	  </form>
	</div>