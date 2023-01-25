package wx.service.alerts;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.jc.compute.ComputersForNamespace.EventType;
import com.jc.compute.Rule;
import com.jc.compute.rules.MaxValueExceededRule;
import com.jc.compute.rules.PercentageExceededRule;
// --- <<IS-END-IMPORTS>> ---

public final class rules

{
	// ---( internal utility methods )---

	final static rules _instance = new rules();

	static rules _newInstance() { return new rules(); }

	static rules _cast(Object o) { return (rules)o; }

	// ---( server methods )---




	public static final void maxValueExceeded (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(maxValueExceeded)>> ---
		// @sigtype java 3.5
		// [i] field:0:required eventType
		// [i] field:0:required alertType
		// [i] field:0:required serviceToCall
		// [i] field:0:optional minOccurrences {"1"}
		// [i] field:0:optional sticky {"false","true"}
		// [i] field:0:required value
		// [i] field:0:required level {"info","warning","error"}
		// [i] field:0:optional sendEmail {"false","true"}
		// [o] object:0:required rule
		// pipeline in
		
		IDataCursor c = pipeline.getCursor();
		String eventType = IDataUtil.getString(c, "eventType");
		String alertType = IDataUtil.getString(c, "alertType");
		String serviceToCall = IDataUtil.getString(c, "serviceToCall");
		String minOccurrences = IDataUtil.getString(c, "minOccurrences");
		String sticky = IDataUtil.getString(c, "sticky");
		String value = IDataUtil.getString(c, "value");
		String level = IDataUtil.getString(c, "level");
		String sendMail = IDataUtil.getString(c, "sendEmail");
		
		// process
		
		// set defaults
		int minO = 0;
		boolean stickyB = false;
		double valueD = -1;
		int levelInt = 0;
		boolean sendMailB = false;
		
		try { minO = Integer.parseInt(minOccurrences); } catch(Exception e) {}
		try { stickyB = Boolean.parseBoolean(sticky); } catch(Exception e) {}
		try { levelInt = Integer.parseInt(level); } catch(Exception e) {}
		try { sendMailB = Boolean.parseBoolean(sendMail); } catch(Exception e) {}
		
		try { valueD = Double.parseDouble(value); } catch(Exception e) {
			throw new ServiceException("please provide a valid value for threshhold: " + value);
		}
		
		Rule<Number> r = new MaxValueExceededRule(EventType.valueOf(eventType), alertType, serviceToCall, minO, valueD, stickyB, levelInt, sendMailB);
		
		// pipeline out
		
		IDataUtil.put(c, "rule", r);
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void percentageExceeded (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(percentageExceeded)>> ---
		// @sigtype java 3.5
		// [i] field:0:required eventType
		// [i] field:0:required alertType
		// [i] field:0:required serviceToCall
		// [i] field:0:optional minOccurrences {"1"}
		// [i] field:0:optional sticky {"false","true"}
		// [i] field:0:required value
		// [i] field:0:required level {"info","warning","error"}
		// [i] field:0:optional sendEmail {"false","true"}
		// [o] object:0:required rule
		// pipeline in
		
		IDataCursor c = pipeline.getCursor();
		String eventType = IDataUtil.getString(c, "eventType");
		String alertType = IDataUtil.getString(c, "alertType");
		String serviceToCall = IDataUtil.getString(c, "serviceToCall");
		String minOccurrences = IDataUtil.getString(c, "minOccurrences");
		String sticky = IDataUtil.getString(c, "sticky");
		String value = IDataUtil.getString(c, "value");
		String level = IDataUtil.getString(c, "level");
		String sendMail = IDataUtil.getString(c, "sendEmail");
		
		// process
		
		if (alertType == null) {
			if (eventType.startsWith("Exception")) {
				alertType = "ServiceException";
			} else {
				alertType = "ServiceInstances";
			}
		}
		
		int minO = 0;
		boolean stickyB = false;
		double valueD = -1;
		int levelInt = 0;
		boolean sendMailB = false;
		
		try { minO = Integer.parseInt(minOccurrences); } catch(Exception e) {}
		try { stickyB = Boolean.parseBoolean(sticky); } catch(Exception e) {}
		try { levelInt = Integer.parseInt(level); } catch(Exception e) {}
		try { sendMailB = Boolean.parseBoolean(sendMail); } catch(Exception e) {}
		
		try { valueD = Double.parseDouble(value); } catch(Exception e) {
			throw new ServiceException("please provide a valid value for threshhold: " + value);
		}
		
		Rule<Number> r = new PercentageExceededRule(EventType.valueOf(eventType), alertType, serviceToCall, minO, valueD, stickyB, levelInt, sendMailB);
		
		// pipeline out
		
		IDataUtil.put(c, "rule", r);
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}
}

