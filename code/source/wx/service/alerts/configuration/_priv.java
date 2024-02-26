package wx.service.alerts.configuration;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.GlobalVariables;
import com.wm.app.b2b.server.globalvariables.GlobalVariablesManager;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// --- <<IS-END-IMPORTS>> ---

public final class _priv

{
	// ---( internal utility methods )---

	final static _priv _instance = new _priv();

	static _priv _newInstance() { return new _priv(); }

	static _priv _cast(Object o) { return (_priv)o; }

	// ---( server methods )---




	public static final void appendToObjectList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(appendToObjectList)>> ---
		// @sigtype java 3.5
		// [i] object:0:required object
		// [i] object:1:required fromList
		// [o] object:1:required toList
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		Object object = IDataUtil.get(pipelineCursor, "object");
		Object[] fromList = IDataUtil.getObjectArray(pipelineCursor, "fromList");
		
		// process
		
		Object[] toList = null;
		
		if (fromList == null) {
		// create new array
		toList = new Object[1];
		} else {
		// create new array with old items + new 
		
		toList = new Object[fromList.length+1];
		int i = 0;
		
		for (Object o : fromList) {
		toList[i++] = o;
		}
		}
		
		toList[toList.length-1] = object;
		
		// pipeline
		
		IDataUtil.put(pipelineCursor, "toList", toList);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void channels (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(channels)>> ---
		// @sigtype java 3.5
		// [o] record:1:optional channels
		// [o] object:0:required isAvailable
		IData[] channels = null;
		
		try {
			
			IData results = Service.doInvoke("pub.alert", "channels", IDataFactory.create());
			IDataCursor rc = results.getCursor();
			channels = IDataUtil.getIDataArray(rc, "channels");
			rc.destroy();
			
		} catch(Exception e) {
			// do now't
		}
		
		IDataCursor c = pipeline.getCursor();
		
		if (channels != null) {
			
			IDataUtil.put(c, "channels", channels);
			IDataUtil.put(c, "isAvailable", true);
		} else {
			IDataUtil.put(c, "isAvailable", false);
		}
		
		c.destroy();
		// --- <<IS-END>> ---

                
	}



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



	public static final void getPersistServiceToInvoke (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getPersistServiceToInvoke)>> ---
		// @sigtype java 3.5
		// [i] field:0:required defaultService
		// [o] field:0:required persistService
		// pipeline in 
		IDataCursor cursor = pipeline.getCursor(); 
		String service = IDataUtil.getString(cursor, "defaultService"); 
		
		String alt = System.getProperty("watt.service.alerts.snapshot.service");
		
		if (alt != null)
			service = alt;
		
		IDataUtil.put(cursor, "persistService", service);
		cursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void readSetupFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(readSetupFile)>> ---
		// @sigtype java 3.5
		// [o] field:0:required content
		IDataCursor c = pipeline.getCursor();
		IDataUtil.put(c, "content", new String(readFile(PROPS_FILE)));
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



	public static final void replaceDocInConfig (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(replaceDocInConfig)>> ---
		// @sigtype java 3.5
		// [i] recref:1:required setup wx.service.alerts.configuration:Config
		// [i] recref:0:required changedFilter wx.service.alerts.configuration:Config
		// [o] recref:1:required setup wx.service.alerts.configuration:Config
		// pipeline in
		
		IDataCursor c = pipeline.getCursor();
		IData[] setup = IDataUtil.getIDataArray(c, "setup");
		IData changedFilter = IDataUtil.getIData(c, "changedFilter");
		
		// process
		
		boolean found = false;
		
		IDataCursor ccf = changedFilter.getCursor();
		String lookupType = IDataUtil.getString(ccf, "eventType");
		String lookupFilter = IDataUtil.getString(ccf, "namespace");
		ccf.destroy();
		
		int i = 0;
		for (IData s : setup) {
			
			IDataCursor sc = s.getCursor();
			String eventType = IDataUtil.getString(sc, "eventType");
			String filter = IDataUtil.getString(sc, "namespace");
			sc.destroy();
			
			if (lookupFilter.equals(filter) && lookupType.equals(eventType)) {
				found = true;
				setup[i] = changedFilter;
				break;
			}
			
			i += 1;
		}
		
		if (!found) {
			// add it to array
			
			IData[] update = new IData[setup.length+1];
			update[setup.length] = changedFilter;
			
			i = 0;
			for (IData s: setup) {
				update[i++] = s;
			}
		}
		
		// pipeline out
		
		IDataUtil.put(c, "setup", setup);
		c.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void writeSetupFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeSetupFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required content
		IDataCursor c = pipeline.getCursor();
		String content = IDataUtil.getString(c, "content");
		c.destroy();
		
		writeFile(PROPS_FILE, content.getBytes());
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static HashMap<String, Integer> _channels = new HashMap<String, Integer>();
	
	private static String _taskId;
	
	private static final String PROPS_FILE = "./packages/WxServiceAlerts/config/properties.cnf";
	
	private static String[] readFileAsStringList(String fname) throws ServiceException {
				
		InputStream is = new ByteArrayInputStream(readFile(fname));
		
		List<String> lines = new ArrayList<String>();
		String line = null;
		
		try (
			BufferedReader rdr = new BufferedReader(new InputStreamReader(is));
		)
		{
			while ((line=rdr.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			throw new ServiceException(e);
		}
			
		return lines.toArray(new String[lines.size()]);
	}
	
	private static byte[] readFile(String fname) throws ServiceException {
				
		if (fname == null)
			throw new ServiceException("provide file name please");
		
		// process
		
		byte[] data = null;
		
		try {
			data = Files.readAllBytes(Paths.get(fname));
		} catch(NoSuchFileException e) {
			
			data = new byte[0];
		} catch (IOException e) {
			
			throw new ServiceException(e);
		}
				
		return data;
	}
	
	private static void writeFile(String fname, byte[] content) throws ServiceException {
		
		if (fname == null)
			throw new ServiceException("provide file name please");
		
		// process
				
		try {
			Files.write(Paths.get(fname), content);
		} catch (NoSuchFileException e ) {
			throw new ServiceException(e);
		} catch (IOException e) {
			
			throw new ServiceException(e);
		}
	}
	// --- <<IS-END-SHARED>> ---
}

