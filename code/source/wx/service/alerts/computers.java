package wx.service.alerts;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.jc.compute.Rule;
import com.jc.compute.Computer.Source;
import com.jc.compute.computers.Average;
import com.jc.compute.computers.Difference;
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
		Object[] rules = (Object[]) IDataUtil.get(c, "rules");
		
		// create new Average rule java Object to use with a computer
		
		Average av = new Average(Source.valueOf(source));
		
		if (rules != null) {
			for (Object rule : rules) {
				av.addRule((Rule<Double>) rule);
			}
		}
		
		// pipeline ou
		
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
		Object[] rules = (Object[]) IDataUtil.get(c, "rules");
		
		// process
		
		Difference dt = new Difference(Source.valueOf(source));
		
		if (rules != null) {
			for (Object rule : rules) {
				dt.addRule((Rule<Long>) rule);
			}
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
		
		Object[] rules = (Object[]) IDataUtil.get(c, "rules");
		
		// process
		
		Total tc = new Total(Source.valueOf(source));
		
		if (rules != null) {
			for (Object rule : rules) {
				tc.addRule((Rule<Long>) rule);
			}
		}
		
		// pipeline out
		
		IDataUtil.put(c, "computeTotal", tc);
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static final String[] EXCLUDE = {"wm.", "pub.", "com."};
	private static final String[] INCLUDE = null;
		
		
	// --- <<IS-END-SHARED>> ---
}

