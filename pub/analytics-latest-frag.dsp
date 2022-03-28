%invoke wx.service.alerts.record:getServiceAnalytics%
  %ifvar results -notempty%
  %loop results%
  <div style="width: 100%; padding-top: 15px">
	<div class="collapsible" onClick="toggleStatsIframe('%value name%', 'ifp-%value name%', 'services-stats.dsp?service=%value name%')">%value name%</div>
	<div id="ifp-%value name%" class="visible-content">
 	%ifvar types -notempty%
	
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
  <script>
  	function toggleStatsIframe(fname, parent, url) {
		  
		  var ifrm = document.getElementById(parent).getElementsByTagName("iframe")[0];
		  
		  if (ifrm == null) {
			ifrm = document.createElement('iframe');
			ifrm.setAttribute('id', fname); // assign an id
			ifrm.style.width="100%";
			ifrm.style.height="45px";
			ifrm.style.marginTop="20px";
			ifrm.style.padding="0px";
			ifrm.onload= function() {
				console.log("iframe content loaded");
				toggleFrameSize(ifrm);
			};
			  
			var el = document.getElementById(parent);	
			el.appendChild(ifrm);
			  
			ifrm.setAttribute('src', url);    
		  } else {
			ifrm.remove();
		  }
	  }
	  
	function toggleFrameSize(iframe, image) {
	
		//iframe = document.getElementById(iframe).parentNode();
		
		if (iframe.style.height != "45px") {
			iframe.style.height = "45px";
			if (image != null)
		    image.src = "images/chevron-down-solid.svg";
	  	} else {
			if (iframe.contentWindow != null)
				iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
			
			if (image != null)
				image.src = "images/chevron-up-solid.svg";
	  	}
	
		const content = iframe.closest(".visible-content");
		content.style.maxHeight = content.scrollHeight + "px";        
	}
  </script>