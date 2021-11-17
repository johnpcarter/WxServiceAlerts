package wx.service.alerts;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServerAPI;
import com.jc.compute.ComputeMap;
import com.jc.compute.Computer;
import com.jc.compute.Rule;
import com.jc.compute.ServiceHistory;
import com.jc.compute.Snapshot;
import com.jc.compute.ComputeMap.ComputerSnapshot;
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




	public static final void getAnalytics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getAnalytics)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional eventType
		// [i] field:0:optional filter
		// [i] field:0:optional computeType
		// [i] field:0:optional service
		// [i] field:0:required summaryOnly {"true","false"}
		// [i] field:0:optional yScale
		// [o] record:1:required results
		// [o] - record:1:required computers
		// [o] -- field:0:required id
		// [o] -- field:0:required types
		// [o] -- record:1:required rules
		// [o] --- field:0:required description
		// [o] --- object:0:required didFire
		// [o] -- record:1:required history
		// [o] --- field:0:required id
		// [o] --- record:1:required values
		// [o] ---- field:0:required time
		// [o] ---- object:0:required didFire
		// [o] ---- object:0:required value
		// [o] ---- object:0:required scaledValue
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String eventType = IDataUtil.getString(pipelineCursor, "eventType");
		String filter = IDataUtil.getString(pipelineCursor, "filter");
		String source = IDataUtil.getString(pipelineCursor, "computeSource");
		String type = IDataUtil.getString(pipelineCursor, "computeType");
		String service = IDataUtil.getString(pipelineCursor, "service");
		String summaryOnlyStr = IDataUtil.getString(pipelineCursor, "summaryOnly");
		String yscaleStr = IDataUtil.getString(pipelineCursor, "yScale");
		
		System.out.println("analytics - " + eventType + " / " + filter + " / " + source + " / " + type + " / " + service);
		
		// process
		
		long yscale = 200;
		boolean summaryOnly = eventType != null ? false : true;
		
		try { yscale = Long.parseLong(yscaleStr); } catch(Exception e) {}
		try { if (summaryOnlyStr != null) summaryOnly = Boolean.parseBoolean(summaryOnlyStr); } catch (Exception e) {}
		
		System.out.println("sum '" + summaryOnlyStr + "' /" + summaryOnly);
		
		ArrayList<IData> namespaces = new ArrayList<IData>();
		
		for (String id : _default.keySet()) {
			
			if (eventType == null || id.equals(eventType+filter)) {
				populateAnalytics(id, source, type, service, yscale, namespaces, summaryOnly);
			}
		}
		
		// pipeline out
		
		IDataUtil.put(pipelineCursor, "results", namespaces.toArray(new IData[namespaces.size()]));
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getServiceAnalytics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServiceAnalytics)>> ---
		// @sigtype java 3.5
		// pipeline in 
		
		IDataCursor c = pipeline.getCursor();
		
		// process
		
		long yscale = 200;
		
		ArrayList<IData> namespaces = new ArrayList<IData>();
				
		for (String id : _default.keySet()) {
					
			Map<String, ServiceHistory> h = _default.get(id).getServiceHistory();
			
			IData ns = IDataFactory.create();
			IDataCursor nsc = ns.getCursor();
			
			nsc.destroy();
			namespaces.add(ns);
		}
				
		// pipeline out
				
		IDataUtil.put(pipelineCursor, "results", namespaces.toArray(new IData[namespaces.size()]));
		pipelineCursor.destroy();
			
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
		//String time = IDataUtil.getString(pipelineCursor, "time");
		String result = IDataUtil.getString(pipelineCursor, "result");
		
		pipelineCursor.destroy();
		
		long time = result == "Ended" ? new Date().getTime() : -1;
		
		System.out.println("<<<<<<<<<<<<<<<<<<<< recording end event 'duration' for " + service);
		
		applyComputers("Audit Event", id, service, "duration", time);
		
		System.out.println("<<<<<<<<<<<<<<<<<<<< recording end event 'count' for " + service);
		
		applyComputers("Audit Event", null, service, time > 0 ? "count" : "failed", 1);
			
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
		
		applyComputers("Audit Event", id, service, "duration", time);
			
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
		
		applyComputers("Exception Event", null, service, "count", 1);
			
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
		// [i] object:0:required computer
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String eventType = IDataUtil.getString(pipelineCursor, "eventType");
		String filter = IDataUtil.getString(pipelineCursor, "filter");
		String timeInterval = IDataUtil.getString(pipelineCursor,"timeInterval");
		String maxSlots = IDataUtil.getString(pipelineCursor, "maxSlots");
		String countZerosS = IDataUtil.getString(pipelineCursor, "countZeros");
		Computer<Double> c = (Computer<Double>) IDataUtil.get(pipelineCursor, "computer");
		pipelineCursor.destroy();
		
		// process
		
		boolean countZeros = false;
		
		try { countZeros = Boolean.parseBoolean(countZerosS); } catch (Exception e) {}
		
		if (filter.endsWith(".*")) {
			filter = filter.substring(0, filter.length()-2);
		} else if (filter.endsWith("*")) {
			filter = filter.substring(0, filter.length()-1);
		}
		
		ComputeMap ts = _default.get(eventType + filter);
		
		if (ts == null) {
			
			ts = new ComputeMap(filter, Long.parseLong(timeInterval), eventType, Integer.parseInt(maxSlots), countZeros, c);
			_default.put(eventType + filter, ts);
		} else {
			ts.addComputer(c);
		}
		
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
				sc.destroy();
				
				String flt = eventType + filter;
				
				if (filter.endsWith(".*")) {
					flt = eventType + filter.substring(0, filter.length()-2);
				} else if (filter.endsWith("*")) {
					flt = eventType + filter.substring(0, filter.length()-1);
				}
				
				System.out.println("filter is " + flt + " / " + id);
				
				if (flt != null && _default.get(flt) != null) {
					
					System.out.println("removing computer");
					
					removeSubscriber(id, flt);
				}
			}
		} while (c.next());
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static void populateAnalytics(String id, String source, String type, String service, long yscale, ArrayList<IData> namespaces, boolean summaryOnly) {
	
		List<ComputerSnapshot> cs =  _default.get(id).getComputers();
		
		IData out = IDataFactory.create();
		IDataCursor cursor = out.getCursor();
		IDataUtil.put(cursor, "id", _default.get(id).getId());
		IDataUtil.put(cursor, "type", _default.get(id).eventType());
		
		ArrayList<IData> computers = new ArrayList<IData>();
		
		for (ComputerSnapshot c : cs) {
			
			if ((type == null || type.equals(c.type())) && (source == null || source.equals(c.source()))) {
				IData computer = IDataFactory.create();
				IDataCursor ccursor = computer.getCursor();
		
				if (summaryOnly) {
					Set<String> services = c.getRecordedServices();
					IDataUtil.put(ccursor, "type", c.type());
					IDataUtil.put(ccursor, "source", c.source());
					IDataUtil.put(ccursor, "services", services.toArray(new String[services.size()]));
				
				} else {
					IDataUtil.put(ccursor, "source", c.source());
					IDataUtil.put(ccursor, "type", c.type());
					IDataUtil.put(ccursor, "uom", c.uom());
					IDataUtil.put(ccursor, "rules", convertRulesToIDataList(c.getRules()));
					IDataUtil.put(ccursor, "history", convertHistoryToIDataList(service, c.getHistory(), yscale));
				}
			
				ccursor.destroy();
			
				computers.add(computer);
			}
		}
		
		IDataUtil.put(cursor, "computers", computers.toArray(new IData[computers.size()]));
		cursor.destroy();
		namespaces.add(out);
	}
	
	private static IData[] convertRulesToIDataList(List<Rule<Double>> rules) {
		
		ArrayList<IData> list = new ArrayList<IData>();
		
		for (Rule<Double> r : rules) {
			IData out = IDataFactory.create();
			IDataCursor c = out.getCursor();
			
			IDataUtil.put(c, "description", r.description());
			IDataUtil.put(c, "didFire", r.didFire());
			c.destroy();
			
			list.add(out);
		}
		
		return list.toArray(new IData[list.size()]);
	}
	
	private static IData[] convertHistoryToIDataList(String service, Map<String, Stack<Snapshot<Double>>> history, long yscale) {
		
		ArrayList<IData> list = new ArrayList<IData>();
		double max = 0;
		
		if (yscale > 0) {
			max = getMaxValue(history);
			
			if (max > 0) {
				max = max / yscale;
			}
		}
		
		for (String id : history.keySet()) {
			
			Stack<Snapshot<Double>> s = history.get(id);
		
			if (service == null || id.equals(service)) {
				ArrayList<IData> values = new ArrayList<IData>();
				
				for (Snapshot<Double> p : s) {
					IData v = IDataFactory.create();
					IDataCursor vc = v.getCursor();
					IDataUtil.put(vc, "time", formatTime(p.time));
					IDataUtil.put(vc, "didFire", p.didFireARule);
					IDataUtil.put(vc, "violationLevel", p.violationLevel);
					IDataUtil.put(vc, "value", (long) Math.rint(p.value));
	
					if (max > 0 && p.value != 0) {
						IDataUtil.put(vc, "scaledValue", p.value / max);
					} else {
						IDataUtil.put(vc, "scaledValue", p.value);
					}
				
					vc.destroy();
				
					values.add(v);
				}
			
				IData out = IDataFactory.create();
				IDataCursor c = out.getCursor();
		
				IDataUtil.put(c, "id", id);
				IDataUtil.put(c,  "values", values.toArray(new IData[values.size()]));
			
				c.destroy();
			
				list.add(out);
			}
		}
		
		return list.toArray(new IData[list.size()]);
	}
	
	private static double getMaxValue(Map<String, Stack<Snapshot<Double>>> history) {
				
		double max = 0;
		ArrayList<IData> list = new ArrayList<IData>();
			
		for (String id : history.keySet()) {
				
			Stack<Snapshot<Double>> s = history.get(id);			
			for (Snapshot<Double> p : s) {
				if (p.value > max) {
					max = p.value;
				}
			}
		}
		
		return max;
	}
	
	private static String formatTime(Date date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		return sdf.format(date);
	}
	
	private static void applyComputers(String type, String id, String service, String source, double i) {
						
		for (String c : _default.keySet()) {
			
			System.out.println("***************** Checking " + _default.get(c).getId());
			
			if (service.startsWith(_default.get(c).getId()) && _default.get(c).eventType().equals(type)) {
						
				System.out.println("***************** recording value " + i + " for " + source + " and " + service);
	
				(_default).get(c).add(id, service, source, i);
			}
		}
	}
	
	private static void removeSubscriber(String id, String namespace) throws ServiceException {
		
		for (String n : _default.keySet()) {
			
			System.out.println("checking " + n);
			
			if (namespace.startsWith(n)) {
						
				System.out.println("removing " + namespace);
				
				ComputeMap c = (_default).remove(namespace);
				c.stop();
				
				deleteSubscriber(c.eventType(), id);
			}
		}
	}
	
	private static void deleteSubscriber(String eventType, String id) throws ServiceException {
		
		// input
		IData pipeline = IDataFactory.create();
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "EventType", eventType);
		IDataUtil.put(c, "gID", id);
	
		try {
			Service.doInvoke( "pub.event", "deleteSubscriber", pipeline);
			
			if (!IDataUtil.getString(c, "Result").equals("true")) {
				throw new ServiceException("failed to delete subscriber " + id + " for type " + eventType);
			}
	
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		c.destroy();
	}
	
	private static final long FIVE_MINS_MS = 300000;
	
	private static Map<String, ComputeMap> _default = new HashMap<String, ComputeMap>();
	
	
		
	// --- <<IS-END-SHARED>> ---
}

