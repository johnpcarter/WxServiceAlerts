<!DOCTYPE HTML>
<html>
<head>
<title>Service Analytics</title>
    <link rel="stylesheet" type="text/css" href="/WmRoot/webMethods.css">
    <link rel="stylesheet" type="text/css" href="/WmRoot/top.css">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">

<script>
  function expandshrink(content) {
          
    if (content.style.maxHeight){    
      content.style.maxHeight = null;
    } else {          
      content.style.maxHeight = content.scrollHeight + "px";
    }   
  }
</script>
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
        <td class="menu-navigator" style="border:none" colspan=2>packages &gt; WxServiceAlerts &gt; <b>Analytics</b>
          <div style="float:right">
            <a href="./configuration.dsp">Configuration</a> 
          </div>
        </td>
      </tr>
	  </table>
    <div style="margin:20px;">
      
      %invoke wx.service.alerts.record:getServiceAnalytics%
      %ifvar results -notempty%
      %loop results%
      <div style="width: 100%; background-color: #777; margin-bottom: 15px">
        <div class="collapsible">%value name%</div>
        <div class="content">
        %ifvar types -notempty%
        %loop types%
            <div style="margin-top:20px; background-color: white">
              <div style="position: relative; z-index: 99; float:right; width: 40px; height: 35px; color: white; vertical-align: middle; text-align: center;" onclick="toggleFrameSize(this.nextElementSibling, this.firstChild)"><img id="expander-icon" style="width: 20px; height: 20px; margin-top: 7px; filter: invert(42%) sepia(193%) saturate(1352%) hue-rotate(87deg) brightness(119%);" src="images/chevron-up-solid.svg"/></div>
              <iframe style="width: 100%; height: 45px; border: none; margin-top: -35px; padding: 0px" src="services-stats.dsp?service=%value ../name%&&type=%value name%"> 
              </iframe>        
            </div>
        %endloop%
        %else%
          <div style="height: 20px; text-align: center; vertical-align: center; padding: 20px">No data yet for %value name% counter...</div>
        %endif%
        </div>
      </div>
      %endloop%
      %else%
      <center><h2>Nothing to report, nada!</h2>
        <img src="images/empty.gif"/>
        <h3>Configure some service counters <a href="configuration.dsp">here</a>
      </center>
      %endif%
      %endinvoke%
    </div>
    <div style="float: right; margin-right: 20px;">
      <a href="/invoke/wx.service.alerts.dsp/reload" class="pill-button" style="margin-top: 20px; background-color: red; color: white" onclick="return window.confirm('Configuration will be reloaded and all analysis will be voided ?');">Reset & reload</a>
    </div>
    <script>
      var coll = document.getElementsByClassName("collapsible");
      var i;
      
      for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function() {
          //this.classList.toggle("active");
          expandshrink(this.nextElementSibling);
        });
      }
      
      var iframes = document.getElementsByTagName("iframe");
                 
      for (i = 0; i < iframes.length; i++) {
        
        const iframe = iframes[i];
        
        iframe.onload = function() {
          console.log("adjusted height is " + iframe.contentWindow.document.body.scrollHeight + 'px');
          
          if (iframe.style.height != '45px') {
            iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
          }
        }
      }    
      
      function toggleFrameSize(iframe, image) {
                      
        if (iframe.style.height != "45px") {
          iframe.style.height = "45px";
          image.src = "images/chevron-down-solid.svg";
        } else {
          iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
          image.src = "images/chevron-up-solid.svg";
        }
        
        const content = iframe.closest(".content");
        content.style.maxHeight = content.scrollHeight + "px";
      }     
    </script>
</body>
</html>