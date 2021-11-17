<div style="margin: auto; width: 300px; text-align: center; padding: 10px;">
  <div style="display: inline-flex">
    <button onclick="editRuleId.value='%value id%';editRuleSource.value='%value computeSource%';editRuleType.value='%value computeType%'" class="pill-button show" style="min-width: 120px; display: flex; justify-content: space-between">
      <div style="width: 70px; margin-right: 10px; display: inline-flex" class="hovertext" data-hover="Condition">
        <!-- <i class="fa-solid fa-bell" style="color: gray; margin-right: 5px; margin-top: 2px"></i> -->
        %ifvar type equals('maxValue')%
          &gt;
        %else%
          %
        %endif%
        %value value%
        %ifvar minOccurrences equals('1')%
        %else%
        <div class="hovertext" data-hover="minimum occurrences">
          <i title="minimum occurrences" class="fas fa-hand-scissors" style="margin-left: 5px; margin-right: 2px; margin-top: 2px"></i>
           %value minOccurrences%
        </div>
       %endif%
       <div class="hovertext" data-hover="Alert level is %ifvar level equals('0')%Info%else%%ifvar level equals('1')%Warning%else%Error%endif%%endif%">
          <i class="fa %ifvar level equals('0')%fa-info-square%endif%
                    %ifvar level equals('1')%fa-exclamation-triangle%endif%
                    %ifvar level equals('2')%fa-times-octagon%endif%" aria-hidden="true" style="display: %ifvar level -notempty%inline-block%else%none%endif%; color: %ifvar level equals('0')%green%else%orange%endif%; padding-left: 5px; margin-top: 1px; margin-right: -10px"></i>
       </div>
      </div>
        <!-- <div class="hovertext" data-hover="%ifvar sendEmail equals('true')%Send email on activation%else%No email%endif%"><i class="fa fa-envelope" aria-hidden="true" style="color: %ifvar sendEmail -notempty%white%else%gray%endif%; margin-right: 10px"></i></div> -->
       <div class="hovertext" data-hover="rule triggers remains sticky, i.e. won't fire again until acknowledged."> <i style="color: %ifvar sticky -notempty%white%else%gray%endif%; margin-left: 10px; margin-right: -10px" class="fas fa-thumbtack"></i></div>
      </div>
    </button>
  </div>
<script>
  function confirmDelete() {
    if (window.confirm("Are you sure you want to delete ?")) {
      window.alert("ok");
    } else {
      window.alert("bugger");
    }
  }
</script>