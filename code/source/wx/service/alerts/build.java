package wx.service.alerts;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.HashMap;
// --- <<IS-END-IMPORTS>> ---

public final class build

{
	// ---( internal utility methods )---

	final static build _instance = new build();

	static build _newInstance() { return new build(); }

	static build _cast(Object o) { return (build)o; }

	// ---( server methods )---




	public static final void getChannelId (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getChannelId)>> ---
		// @sigtype java 3.5
		// [i] field:0:required type
		// [o] object:0:required channelId
		// pipeline in
		
		IDataCursor c = pipeline.getCursor();
		String type = IDataUtil.getString(c, "type");
		
		// pipeline out
		
		IDataUtil.put(c, "channelId", _channels.get(type));
		c.destroy(); 
			
		// --- <<IS-END>> ---

                
	}



	public static final void recordChannelId (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(recordChannelId)>> ---
		// @sigtype java 3.5
		// [i] field:0:required type
		// [i] object:0:required channelId
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		String type = IDataUtil.getString(pipelineCursor, "type");
		int id = (Integer) IDataUtil.get(pipelineCursor, "channelId");
		pipelineCursor.destroy();
		
		// process
		
		_channels.put(type, id);
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static HashMap<String, Integer> _channels = new HashMap<String, Integer>();
	// --- <<IS-END-SHARED>> ---
}

