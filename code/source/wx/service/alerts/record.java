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
		// [i] field:0:optional service
		// [i] field:0:optional type
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
		String service = IDataUtil.getString(c, "service");
		String type = IDataUtil.getString(c, "type");
		
		// process
		
		long yscale = 200;
		
		if (service != null || type != null) {
			IDataUtil.put(c, "results", AllComputers.instance.report(service, type, yscale));
		} else {
			IDataUtil.put(c, "results", AllComputers.instance.summary());
		}	
		
		// pipeline out
				
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void recordAuditEndEvent (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(recordAuditEndEvent)>> ---
		// @sigtype java 3.5
		// [i] field:0:required TID
		// [i] field:0:required service
		// [i] field:0:required time
		// [i] field:0:required result
		IDataCursor pipelineCursor = pipeline.getCursor();
		String id = IDataUtil.getString(pipelineCursor, "TID");
		String service = IDataUtil.getString(pipelineCursor, "service");
		String result = IDataUtil.getString(pipelineCursor, "result");
		
		pipelineCursor.destroy();
		
		long time = result == "Ended" ? new Date().getTime() : -1;
		
		System.out.println("<<<<<<<<<<<<<<<<<<<< recording end event 'duration' for " + service);
		
		AllComputers.instance.record("Audit Event", id, service, "duration", time, false);
		
		if (time != -1) {
						
			System.out.println("<<<<<<<<<<<<<<<<<<<< recording end event 'count' for " + service);
			AllComputers.instance.record("Audit Event", null, service, "count", 1, false);
		
		} else {
			//System.out.println("<<<<<<<<<<<<<<<<<<<< recording error event 'count' for " + service);
			//AllComputers.instance.record("Exception Event", null, service, "count", 1, false);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void recordAuditStartEvent (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(recordAuditStartEvent)>> ---
		// @sigtype java 3.5
		// [i] field:0:required TID
		// [i] field:0:required service
		// [i] field:0:required time
		IDataCursor pipelineCursor = pipeline.getCursor();
		String id = IDataUtil.getString(pipelineCursor, "TID");
		String service = IDataUtil.getString(pipelineCursor, "service");
		//String time = IDataUtil.getString(pipelineCursor, "time");
		pipelineCursor.destroy();
		
		long time = new Date().getTime();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>> recording start event 'duration' for " + service);
		
		AllComputers.instance.record("Audit Event", id, service, "duration", time, true);
		// --- <<IS-END>> ---

                
	}



	public static final void recordExceptionEvent (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(recordExceptionEvent)>> ---
		// @sigtype java 3.5
		// [i] field:0:required service
		// [i] field:0:optional error
		IDataCursor pipelineCursor = pipeline.getCursor();
		String service = IDataUtil.getString(pipelineCursor, "service");
		String error = IDataUtil.getString(pipelineCursor, "error");
		
		pipelineCursor.destroy();
		
		AllComputers.instance.record("Exception Event", null, service, "count", 1, false);
		// --- <<IS-END>> ---

                
	}



	public static final void registerComputer (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(registerComputer)>> ---
		// @sigtype java 3.5
		// [i] field:0:required id
		// [i] field:0:required eventType
		// [i] field:0:required filter
		// [i] field:0:required timeInterval
		// [i] field:0:required maxSlots
		// [i] field:0:optional countZeros {"false","true"}
		// [i] object:0:required computer
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String eventType = IDataUtil.getString(pipelineCursor, "eventType");
		String filter = IDataUtil.getString(pipelineCursor, "filter");
		String timeIntervalStr = IDataUtil.getString(pipelineCursor,"timeInterval");
		String maxSlotsStr = IDataUtil.getString(pipelineCursor, "maxSlots");
		String countZerosS = IDataUtil.getString(pipelineCursor, "countZeros");
		@SuppressWarnings("unchecked")
		Computer<Double> c = (Computer<Double>) IDataUtil.get(pipelineCursor, "computer");
		pipelineCursor.destroy();
		
		// process
		
		boolean countZeros = false;
		long timeInterval = 0;
		int maxSlots = 100;
		
		try { maxSlots = Integer.parseInt(maxSlotsStr); } catch (Exception e) {}
		try { countZeros = Boolean.parseBoolean(countZerosS); } catch (Exception e) {}
		try { timeInterval = Long.parseLong(timeIntervalStr); } catch (Exception e) {
			throw new ServiceException("Provide a valid non zero time interval");
		}
		
		if (filter.endsWith(".*")) {
			filter = filter.substring(0, filter.length()-2);
		} else if (filter.endsWith("*")) {
			filter = filter.substring(0, filter.length()-1);
		}
		
		AllComputers.instance.add(timeInterval, maxSlots, countZeros, filter, c);
		
		// pipeline out
		
		pipelineCursor.destroy();
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

