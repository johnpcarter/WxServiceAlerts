package wx.service.alerts;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServerAPI;
import com.jc.compute.AllComputers;
import com.jc.compute.Computer;
import com.jc.compute.Rule;
import com.jc.compute.ServiceHistory;
import com.jc.compute.Snapshot;
import com.jc.compute.Computer.Source;
import com.jc.compute.ComputersForNamespace.EventType;
import com.jc.service.ServiceInterceptor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
// --- <<IS-END-IMPORTS>> ---

public final class record

{
	// ---( internal utility methods )---

	final static record _instance = new record();

	static record _newInstance() { return new record(); }

	static record _cast(Object o) { return (record)o; }

	// ---( server methods )---




	public static final void alerts (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(alerts)>> ---
		// @sigtype java 3.5
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "rules", AllComputers.instance.firedRules());
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void clearRules (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(clearRules)>> ---
		// @sigtype java 3.5
		AllComputers.instance.clear();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getServiceAnalytics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServiceAnalytics)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional filter
		// [i] field:0:optional service
		// [i] field:0:optional source
		// [i] field:0:required yscale
		// [o] record:1:required results
		// [o] - field:0:required name
		// [o] - record:1:required types
		// [o] -- field:0:required name
		// [o] -- field:0:required uom
		// [o] -- field:0:required source
		// [o] -- record:1:required eventTypes
		// [o] --- field:0:required label
		// [o] --- field:0:required color
		// [o] -- record:1:required intervals
		// [o] --- field:0:required time
		// [o] --- record:1:required values
		// [o] ---- field:0:required label
		// [o] ---- object:0:required violationLevel
		// [o] ---- object:0:required didFire
		// [o] ---- object:0:required value
		// [o] ---- object:0:required scaledValue
		// [o] ---- field:0:required color
		// pipeline in 
		
		IDataCursor c = pipeline.getCursor();
		String filter = IDataUtil.getString(c, "filter");
		String service = IDataUtil.getString(c, "service");
		String source = IDataUtil.getString(c, "source");
		
		// process
		
		long yscale = 200; 
		
		if (filter != null && filter.length() == 0) {
			filter = null;
		}
		
		Source src = source != null ? Source.valueOf(source) : null;
		
		if (service != null) {
			IDataUtil.put(c, "results", AllComputers.instance.serviceStatisticsFor(service, src, yscale));
		} else {
			IDataUtil.put(c, "results", AllComputers.instance.servicesFor(src, filter));
		}
		
		// pipeline out
				
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void registerComputer (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(registerComputer)>> ---
		// @sigtype java 3.5
		// [i] field:0:required eventType
		// [i] field:0:required filter
		// [i] field:0:required timeInterval
		// [i] field:0:required maxSlots
		// [i] field:0:optional countZeros {"false","true"}
		// [i] field:0:required transactionDuration
		// [i] field:0:required traceType {"all","allx","top","topx"}
		// [i] object:0:required computer
		// [i] field:0:optional persistService
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String eventType = IDataUtil.getString(pipelineCursor, "eventType");
		String filter = IDataUtil.getString(pipelineCursor, "filter");
		String timeIntervalStr = IDataUtil.getString(pipelineCursor,"timeInterval");
		String maxSlotsStr = IDataUtil.getString(pipelineCursor, "maxSlots");
		String countZerosS = IDataUtil.getString(pipelineCursor, "countZeros");
		String transactionDurationStr = IDataUtil.getString(pipelineCursor,"transactionDuration");
		String traceType = IDataUtil.getString(pipelineCursor, "traceType");
		String persistService = IDataUtil.getString(pipelineCursor, "persistService");
		
		@SuppressWarnings("unchecked")
		Computer<Number> c = (Computer<Number>) IDataUtil.get(pipelineCursor, "computer");
		pipelineCursor.destroy();
		
		// process
		
		boolean countZeros = false;
		boolean topLevelOnly = false;
		String[] excludeList = null;
		String[] includeList = null;
		long timeInterval = 0;
		long transactionDuration = 3000;
		int maxSlots = 100;
		
		try { maxSlots = Integer.parseInt(maxSlotsStr); } catch (Exception e) {}
		try { countZeros = Boolean.parseBoolean(countZerosS); } catch (Exception e) {}
		try { timeInterval = Long.parseLong(timeIntervalStr); } catch (Exception e) {
			throw new ServiceException("Provide a valid non zero time interval");
		}
		try { transactionDuration = Long.parseLong(transactionDurationStr); } catch (Exception e) {}
		
		if (filter.endsWith(".*")) {
			filter = filter.substring(0, filter.length()-2);
		} else if (filter.endsWith("*")) {
			filter = filter.substring(0, filter.length()-1);
		}
		
		if (traceType != null) {
			switch (traceType) {
			case "allx":
				excludeList = EXCLUDE_WM_SERVICES;
				includeList = INCLUDE_WM_SERVICES;
				break;
			case "top":
				topLevelOnly = true;
				break;
			case "topx":
				topLevelOnly = true;
				excludeList = EXCLUDE_WM_SERVICES;
				includeList = INCLUDE_WM_SERVICES;
				break;
			default:
				break;
			}
		}
		
		AllComputers.instance.add(timeInterval, EventType.valueOf(eventType), topLevelOnly, maxSlots, countZeros, filter, c, excludeList, includeList, transactionDuration, persistService);
		
		// pipeline out
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void serviceTotals (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(serviceTotals)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional filter
		// [o] record:0:required results
		// [o] - record:1:required rows
		// [o] -- field:0:required name
		// [o] -- field:0:required averageDuration
		// [o] -- field:0:required totalCount
		// [o] -- field:0:required totalErrors
		// [o] -- field:0:required totalTransactions
		// [o] - field:0:required trackedServices
		// [o] - field:0:required totalCount
		// [o] - field:0:required totalErrors
		// [o] - field:0:required totalTransactions
		// [o] - object:0:required fromDate
		// [o] - field:0:required from
		// [o] - object:0:required toDate
		// [o] - field:0:required to
		IDataCursor c = pipeline.getCursor();
		String filter = IDataUtil.getString(c, "filter");
		
		// process
		
		IDataUtil.put(c, "results",  AllComputers.instance.totalsFor(filter));
		c.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void startInvokeInterceptor (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(startInvokeInterceptor)>> ---
		// @sigtype java 3.5
		ServiceInterceptor.register(); 
			
		// --- <<IS-END>> ---

                
	}



	public static final void stopInvokeInterceptor (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(stopInvokeInterceptor)>> ---
		// @sigtype java 3.5
		ServiceInterceptor.unregister();
		AllComputers.instance.clear();
			
		// --- <<IS-END>> ---

                
	}



	public static final void unregisterComputers (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(unregisterComputers)>> ---
		// @sigtype java 3.5
		// [i] field:0:required EventType
		// [i] record:0:required Subscribers
		// pipeline in
		
		IDataCursor c = pipeline.getCursor();
		String eventType = IDataUtil.getString(c, "EventType");
		IData subscribers = IDataUtil.getIData(c, "Subscribers");
		c.destroy();
		
		// process
		
		c = subscribers.getCursor();
		c.first();
		do {
			String id = c.getKey();
			IData subscriber = (IData) c.getValue();
						
			if (subscriber != null) {
			
				System.out.println("unregistering listener for " + id + " and " + eventType);
		
				IDataCursor sc = subscriber.getCursor();
				String filter = IDataUtil.getString(sc, "Filter");
				String service = IDataUtil.getString(sc, "Service");
		
				sc.destroy();
				
				if (service.equals("wx.service.alerts.events:manageAuditEvents") || service.equals("wx.service.alerts.events:manageExceptionEvents"))
					deleteSubscriber(eventType, id);
			}
		} while (c.next());
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static final String[] EXCLUDE_WM_SERVICES = {
			"wm.", 
			"pub.", 
			"com."
			};
	
	private static final String[] INCLUDE_WM_SERVICES = {
			"wm.tn:receive",
			"wm.tn.doc.xml:routeXml",
			"wm.EDIINT:receive",
			"wm.prt.dispatch:handlePublishedInput",
			"wm.prt.dispatch:handleSubprocessStart",
			"wm.prt.dispatch:handleCallActivityStart",
			"wm.prt.dispatch:handleEDAEvent",
			"wm.prt.dispatch:handleRequestReply",
			"wm.prt.dispatch:invokeCallActivityStart",
			"wm.prt.dispatch:invokeSubprocessStart",
			"pub.prt.tn:handleBizDoc",
			"pub.sap.client:invokeTransaction",
			"pub.sap.client:sendIDoc",
			"pub.sap.bapi:commit",
			"pub.sap.transport.ALE:OutboundProcess",
			"wm.ach.tn.trp:receive",
			"wm.ach.trp:receive",
			"wm.ach.trp:receiveStream",
			"wm.ip.hl7.tn.service:receive",
			"pub.estd.hipaa:receive",
			"pub.estd.rosettaNet:receive",
			"/ws/msh/receive",
			"wm.ip.rn:receive",
			"wm.ip.ebxml.MSH:receive",
			"pub.estd.chem:receive",
			"wm.b2b.io.core:submit",
			"wm.b2b.cxml:receiveCXML",
			"wm.channels.services:gateway",
			"wm.cloudstreams.listener.event:invokeService",
			"wm.oftp.gateway.Gw:fetchAndUpdateStatus",
			"wm.oftp.tn:receiveOftp",
			"wm.oftp.tn:deliverOftp",
			"wm.x400.tn:receiveX400",
			"wm.x400.gateway.Gw:sendAndFetch"
			};
	
	private static void deleteSubscriber(String eventType, String id) throws ServiceException {
		
		// input
		
		IData pipeline = IDataFactory.create();
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "EventType", eventType);
		IDataUtil.put(c, "gID", id);
	
		try {
			Service.doInvoke("pub.event", "deleteSubscriber", pipeline);
			
			if (!IDataUtil.getString(c, "Result").equals("true")) {
				throw new ServiceException("failed to delete subscriber " + id + " for type " + eventType);
			}
	
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		c.destroy();
	}	
		
	// --- <<IS-END-SHARED>> ---
}

