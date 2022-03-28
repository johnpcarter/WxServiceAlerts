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
<body style="overflow-y: scroll; padding: 0px" topmargin="0" leftmargin="0" marginwidth="0" marginheight="0" onload="onLoad()">
    <div class="tdmasthead" id="top" height="50px">
      <div style="float:right; padding: 10px">
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
      <form id="form" action=".">
        <input type="hidden" name="selectedTab" value="%value selectedTab%">
        <div class="tab">
          <button id="latestButton" class="tablinks" onclick="setTab('latest')">Recent</button>
          <button id="historyButton" class="tablinks" onclick="setTab('history')">History</button>
          </div>
      
        <div id="latest" class="tabcontent">
        
          %ifvar selectedTab -notempty%
            %ifvar selectedTab equals('latest')%
              %include analytics-latest-frag.dsp%
              %endif%
          %else%
            %include analytics-latest-frag.dsp%
          %endif%
          </div>
      
        <div id="history" class="tabcontent">
          %ifvar selectedTab equals('history')%
            %include analytics-history-frag.dsp%
          %endif%
        </div>
    </form>
    <div style="float: right; margin-right: 20px;">
    <a href="/invoke/wx.service.alerts.dsp/reload" class="pill-button" style="margin-top: 20px; background-color: red; color: white" onclick="return window.confirm('Configuration will be reloaded and all analysis will be voided ?');">Reset & reload</a>
    </div>
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
        //console.log("adjusted height is " + iframe.contentWindow.document.body.scrollHeight + 'px');
      
        if (iframe.style.height != '45px') {
          iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
        }
      }
    }
  
    function setTab(label) {
    
      form = document.getElementById("form");
      form.selectedTab.value = label;
    }
    
    function showTab(label) {
      // Declare all variables
      var i, tabcontent, tablinks;
  
      // Get all elements with class="tabcontent" and hide them
      tabcontent = document.getElementsByClassName("tabcontent");
      for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
      }
  
    // Get all elements with class="tablinks" and remove the class "active"
      tablinks = document.getElementsByClassName("tablinks");
      for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
      }
  
    // Show the current tab, and add an "active" class to the button that opened the tab
      document.getElementById(label).style.display = "block";
      document.getElementById(label+"Button").className += " active";
    }
  
    function onLoad() {
      %ifvar selectedTab -notempty%
        showTab('%value selectedTab%');
      %else%
        showTab('latest');
      %endif%      
    }  
  </script>
</body>
</html>