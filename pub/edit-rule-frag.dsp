<div style="display: flex; flex-direction: column; align-items: center">
<div class="pill-button show" style="margin: auto; width: 250px; text-align: center; padding: 10px; display: flex; flex-direction: column">
  <div style="display: inline-flex; justify-content: space-between">
    <div style="margin-left: 10px; display: flex; row-gap: 15px;">
      <i class="fa-solid fa-bell" style="color: gray; margin-right: 5px; margin-top: 2px"></i>
      <select class="rule-select" name="editRuleType" style="margin-left: 5px; margin-right: 5px">
        <option class="rule-select" value="maxValue">&gt;</option>
        <option class="rule-select" value="percentage" %ifvar type equals('percentage')%selected%endif%>%</option>
        </select>
      <input type="number" class="rule-input" name="editRuleValue" value="%value value%" onchange="checkRuleSaveButton()" style="width: 40px"/>     
    </div>
    <div style="width: 80px;" class="hovertext" data-hover="Minimum occurences">
      <i title="minimum occurrences" class="fas fa-hand-scissors"></i>
      <input type="number" class="rule-input" name="editRuleMinOccurences" value="%value minOccurrences%" style="width: 20px; text-align: center"/>     
    </div>
    <div style="margin-right: 5px; display: inline-flex">
      <input type="hidden" name="editRuleSticky"/>
      <input type="hidden" name="editRuleSendEmail"/>
      <div style="margin-right: 5px;">
        <i class="fas fa-info-square" style="position: absolute; margin-top: 1px; margin-left: -20px; width: 11px; height: 11px;"></i>
        <i class="fas fa-exclamation-triangle" style="position: absolute; margin-top: 1px; margin-left: -6px; width: 11px; height: 11px;"></i>
        <i class="fas fa-times-octagon" style="position: absolute; margin-top: 1px; margin-left: 9px; width: 11px; height: 11px;"></i>


        <div class="hovertext" data-hover="Alert level, info, warning or error">
          <input id="level-range" type="range" name="editRuleLevel" min="0" max="2" name="level" value="%value level%" class="slider"/>
        </div>
      </div>
     <!-- <div class="hovertext" data-hover="Send email toggle"><i onclick="stickIt(this, editRuleSendEmail)" class="fa fa-envelope" aria-hidden="true" style="color: %ifvar sendEmail -notempty%white%else%gray%endif%; margin-right: 10px"></i></div> -->
     <div class="hovertext" style="margin-left: 10px; margin-right: -5px" data-hover="rule triggers remains sticky, i.e. won't fire again until acknowledged."> <i onclick="stickIt(this, editRuleSticky)" style="color: %ifvar sticky -notempty%white%else%gray%endif%" class="fas fa-thumbtack"></i></div>
    </div>
  </div>
</div>
<div>
  <button class="plain-button hovertext" data-hover="Cancel changes"><i class="fas fa-undo" onclick="editRuleId.value=null" style="color: orange"></i></button>
  <button class="plain-button hovertext" data-hover="Save rule" id="save-rule-button" name="saveRuleButton" onclick="editRuleId.value='%value id%'; editRuleComputerType.value='%value computeType%'; this.value='true'" disabled><i class="fas fa-save"></i></button>
  <button class="plain-button hovertext" data-hover="Remove rule from counter" name="removeRuleButton" onclick="editRuleComputerType.value='%value computeType%'; editRuleId.value='%value id%'; this.value='true'" %ifvar value -notempty%%else%disabled%endif%><i class="fa fa-trash" aria-hidden="true" style="color: %ifvar value -notempty%black%else%gray%endif%"></i></button>
</div>
</div>
<script>

  const input = document.getElementById('level-range');

  const info = document.getElementById('level-info');
  const warning = document.getElementById('level-warning');
  const error = document.getElementById('level-error');

  input.addEventListener("input", event => {
    
    console.log("poop: " + input.value);

    info.style.setProperty("filter", "invert(.5) saturate(5);");
    warning.style.setProperty("filter", "invert(.5) saturate(5);");
    error.style.setProperty("filter", "invert(.5) saturate(5);");
    
    if (input.value == 0) {
      info.style.setProperty("filter", "invert(1.0) saturate(5);");
    } else if (input.value == 1) {
      warning.style.setProperty("filter", "invert(1.0) saturate(5);");
    } else {
      error.style.setProperty("filter", "invert(1.0) saturate(5);");
    }
  });

  function checkRuleSaveButton() {  
        
    const saveButton = document.getElementById('save-rule-button');
    
    saveButton.disabled=form.editRuleValue.value.length == 0;  
  }
  
  function stickIt(obj, value) {
                
    if (value.value != 'true') {
      value.value = 'true';
      obj.style.color = 'white';
    } else {
      value.value = 'false';
      obj.style.color = 'gray';
    }
  }
  
  checkRuleSaveButton();
</script>