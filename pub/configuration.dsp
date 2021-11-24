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
        <td class="menu-navigator" style="border:none" colspan=2>packages &gt; WxServiceAlerts  &gt; <b>Configuration</b>
          <div style="float:right">
            <a href="./index.dsp">Analytics</a> 
          </div>
        </td>
      </tr>
	  </table>
    <div style="margin:20px;">
      
      %invoke wx.service.alerts.dsp:getFilters%
      %loop filters%
        <div class="sag-content">
          <div class="collapsible" style="margin-top: 10px; background-color: color(srgb 0.378 0.3449 0.775)">
            <div style="float: right; display: inline-flex; align-items: center; gap: 10px">
              <div><i class="fas fa-clock"></i></div>
              <div style="font-size: 10px;">
                %ifvar timeInterval equals('300000')%5 minutes%endif%
                %ifvar timeInterval equals('600000')%10 minutes%endif%
                %ifvar timeInterval equals('900000')%15 minutes%endif%
                %ifvar timeInterval equals('1800000')%30 minutes%endif%
                %ifvar timeInterval equals('3600000')%1 hour%endif%
                %ifvar timeInterval equals('43200000')%12 hour%endif%
                %ifvar timeInterval equals('86400000')%24 hour%endif%
              </div>
              <div><i class="fa-solid fa-gauge-simple"></i></div>
              <div style="font-size: 10px">
                %value maxSlots%
              </div>
              %ifvar countZeros equals('true')%
              <div>
                <i class="fa-solid fa-empty-set"></i>
              </div>
              %endif%
            </div>
            <div>%value eventType% (%value filter%)</div>
          </div>
          <div class="content">
            %loop computers%
            <div> 
            %ifvar active equals('true')%
              <div class="collapsible" style="margin-top: 10px; background-color: color(srgb 0.378 0.3449 0.775)">%value type% %ifvar source equals('count')%count%else%duration%endif%</div>
              <div style="width: 100%; display: inline-flex; justify-content: space-around">
                %loop types%
                  <div style="display: flex; flex-direction: column; justify-content: flex-start; align-items: center; text-align: center; font-weight: bold; min-width: 300px; margin-top: 20px">
                    %value type%
                    %ifvar active equals('true')%
                      %ifvar rules -notempty%
                        <div style="margin-top: 0px">
                          %loop rules%
                            %include rule-frag.dsp%
                            %endloop%
                        </div>
                      %else%
                        <div style="margin-top: 10px; padding-top: 5px; height: 30px">
                          %ifvar active equals('true')%<div style="text-align: center; font-weight: lighter"><u>active</u></div>%endif%
                        </div>
                      %endif%
                    %else%
                      <div style="margin-top: 20px; text-align: center; font-weight: lighter"><i>inactive</i></div>
                    %endif%
                  </div>
                %endloop%
              </div>
            %endif%
            %endloop%
            </div>
            <div style="width: 100%; margin-top: 20px">
              <div style="width: 100px; padding-bottom: 15px; float: right; text-align: right; display: inline-flex; gap: 20px; justify-content: flex-end">
                <a href="edit.dsp?eventType=%value eventType%&filter=%value filter%"><i class="fa-solid fa-pen" style="color: gray"></i></a>
                <i onclick="confirmDelete('%value eventType%', '%value filter%')" class="fa fa-trash" aria-hidden="true" style="color: gray"></i>
              </div>
            </div>
          </div>
        </div>
      %endloop%
      %endinvoke%
      <form action="edit.dsp">
        <button class="pill-button" style="margin-top: 20px">Add service filter</button>
      </form>
    </div>
    <script>
      var coll = document.getElementsByClassName("collapsible");
      var i;
      
      for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function() {
          this.classList.toggle("active");
          var content = this.nextElementSibling;
          if (content.style.maxHeight){
            content.style.maxHeight = null;
          } else {
            content.style.maxHeight = content.scrollHeight + "px";
          } 
        });
      }
      
      function confirmDelete(eventType, filter) {
        const del = window.confirm("Are you sure you want to delete the filter ?");
        
        if (del) {
          window.location = "/invoke/wx.service.alerts.dsp/removeFilter?eventType=" + eventType + "&filter=" + filter;
        }
      }
      </script>
</body>
</html>