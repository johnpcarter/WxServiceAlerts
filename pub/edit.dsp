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
        <td class="menu-navigator" style="border:none" colspan=2>packages &gt; WxServiceAlerts &gt; <a href="./configuration.dsp">Configuration</a> &gt; <b>%ifvar filter -notempty% Edit filter %else% New filter%endif%</b>
        <div style="float:right">
          <a href="./index.dsp">Analytics</a> 
        </div>
        </td>
      </tr>
	  </table>
    
    %ifvar removeRuleButton -notempty%
    %invoke wx.service.alerts.dsp:removeRuleFromComputer%%endinvoke%
    %endif%
    %ifvar save-changes -notempty%
      %invoke wx.service.alerts.dsp:saveFilter%%endinvoke%
      <script>
      window.location = "/WxServiceAlerts/configuration.dsp";
      </script>
    %endif%
    %ifvar saveRuleButton -notempty%
      %invoke wx.service.alerts.dsp:updateRuleForComputer%
        %include edit-filter-frag.dsp%
      %endinvoke%
    %else%
      %invoke wx.service.alerts.dsp:getFilter%
        %include edit-filter-frag.dsp%
      %endinvoke%
    %endif%
    
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
      </script>
</body>
</html>