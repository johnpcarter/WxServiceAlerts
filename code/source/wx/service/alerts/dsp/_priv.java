package wx.service.alerts.dsp;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class _priv

{
	// ---( internal utility methods )---

	final static _priv _instance = new _priv();

	static _priv _newInstance() { return new _priv(); }

	static _priv _cast(Object o) { return (_priv)o; }

	// ---( server methods )---




	public static final void _removeFromList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(_removeFromList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required index
		// [i] recref:1:required inList wm_1.simplerules.alerts.dsp:dspComputer
		// [o] recref:1:required outList wm_1.simplerules.alerts.dsp:dspComputer
		// pipeline in
		IDataCursor c = pipeline.getCursor();
		String index = IDataUtil.getString(c, "index");
		IData[] inList = IDataUtil.getIDataArray(c, "inList");
		
		// process
		
		int i = -1;
		IData[] outList = inList;
		
		try { i = Integer.parseInt(index) - 1; } catch (Exception e) {}
		
		if (inList != null && i >=0 && i < inList.length) {
			
			if (inList.length == 1) {
				outList = new IData[0];
			} else {
				outList = new IData[inList.length-1];
				int z = 0;
				for (int q = 0; q < inList.length; q++) {
					
					if (q != i) {
						outList[z++] = inList[q];
					}
				}
			}
		}
		
		// pipeline out
		
		IDataUtil.put(c, "outList", outList);
		c.destroy();
		// --- <<IS-END>> ---

                
	}
}

