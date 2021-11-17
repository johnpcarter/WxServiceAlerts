package wx.service.alerts;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.jc.compute.Rule;
import com.jc.compute.computers.Average;
import com.jc.compute.computers.Delta;
import com.jc.compute.computers.Total;
// --- <<IS-END-IMPORTS>> ---

public final class computers

{
	// ---( internal utility methods )---

	final static computers _instance = new computers();

	static computers _newInstance() { return new computers(); }

	static computers _cast(Object o) { return (computers)o; }

	// ---( server methods )---




	public static final void average (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(average)>> ---
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required uom
		// [i] object:1:required rules
		// [o] object:0:required computeAverage
		// pipeline in
		
		IDataCursor c = pipeline.getCursor();
		String source = IDataUtil.getString(c, "source");
		String uom = IDataUtil.getString(c, "uom");
		@SuppressWarnings("unchecked")		
		Rule<Double>[] rules = (Rule<Double>[]) IDataUtil.get(c, "rules");
		
		// process
		
		Average av = new Average(source, uom, 0);
		
		if (rules != null) {
			for (Rule<Double> rule : rules) {
				av.addRule(rule);
			}
		}
		
		// pipeline out
		
		IDataUtil.put(c, "computeAverage", av);
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void delta (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(delta)>> ---
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required uom
		// [i] object:1:required rules
		// [o] object:0:required computeDelta
		// pipeline in
		
		IDataCursor c = pipeline.getCursor();
		String source = IDataUtil.getString(c, "source");
		String uom = IDataUtil.getString(c, "uom");
		@SuppressWarnings("unchecked")
		Rule<Double> rule = (Rule<Double>) IDataUtil.get(c, "rule");
		
		// process
		
		Delta dt = new Delta(source, uom, 0);
		
		if (rule != null) {
			dt.addRule(rule);
		}
		
		// pipeline out
		
		IDataUtil.put(c, "computeDelta", dt);
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void total (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(total)>> ---
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required uom
		// [i] object:1:required rules
		// [o] object:0:required computeTotal
		// pipeline in 
		
		IDataCursor c = pipeline.getCursor();
		String source = IDataUtil.getString(c, "source");
		String uom = IDataUtil.getString(c, "uom");
		
		Rule<Double>[] rules = (Rule<Double>[]) IDataUtil.get(c, "rules");
		
		// process
		
		Total tc = new Total(source, uom, 0);
		
		if (rules != null) {
			for (Rule<Double> rule : rules) {
				tc.addRule(rule);
			}
		}
		
		// pipeline out
		
		IDataUtil.put(c, "computeTotal", tc);
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}
}

