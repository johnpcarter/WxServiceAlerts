package wx.service.alerts.db;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
// --- <<IS-END-IMPORTS>> ---

public final class _priv

{
	// ---( internal utility methods )---

	final static _priv _instance = new _priv();

	static _priv _newInstance() { return new _priv(); }

	static _priv _cast(Object o) { return (_priv)o; }

	// ---( server methods )---




	public static final void dateTimeRangeForDayDate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(dateTimeRangeForDayDate)>> ---
		// @sigtype java 3.5
		// [i] object:0:optional date
		// [i] field:0:optional dateRange {"DAY","WEEK","MONTH","YEAR"}
		// [o] object:0:required start
		// [o] object:0:required end
		// [o] field:0:optional label
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		Date date = (Date) IDataUtil.get(pipelineCursor, "date");
		String dateRange = IDataUtil.getString(pipelineCursor, "dateRange");
		
		pipelineCursor.destroy();
		
		// process 
		
		Date start = null;
		Date end = null;
		String label = null;
		
		if (date == null)
			date = new Date();
		
		if (dateRange == null || dateRange.equalsIgnoreCase("DAY")) {
		
			start = getStartOfDay(date);
			end = getEndOfDay(date);
			
			DayOfWeek day = (dateToLocalDateTime(date)).getDayOfWeek();
		    label = day.getDisplayName(TextStyle.FULL, Locale.getDefault());
			
		} else if (dateRange.equalsIgnoreCase("WEEK")) {
			
			Date[] dates = getWeekInterval(date);
			start = dates[0];
			end = dates[1];
			
		    label = "Weekly";
			
		} else if (dateRange.equalsIgnoreCase("MONTH")) {
			
			Date[] dates = getMonthInterval(date);
			start = dates[0];
			end = dates[1];
			
			Month month = (dateToLocalDateTime(date)).getMonth();
		    label = month.getDisplayName(TextStyle.FULL, Locale.getDefault());
		    
		} else if (dateRange.equalsIgnoreCase("YEAR")) {
			
			Date[] dates = getYearInterval(date);
			start = dates[0];
			end = dates[1];
			
			label = "" + (dateToLocalDateTime(date)).getYear();
		}
		
		// pipeline out
		
		IDataUtil.put(pipelineCursor, "start", start);
		IDataUtil.put(pipelineCursor, "end", end);
		IDataUtil.put(pipelineCursor, "label", label);
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void dateToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(dateToString)>> ---
		// @sigtype java 3.5
		// [i] object:0:optional date
		// [i] field:0:required pattern
		// [o] field:0:required stringDate
		// pipeline in
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		Object date = IDataUtil.get(pipelineCursor, "date");
		String pattern = IDataUtil.getString(pipelineCursor, "pattern");
		
		// process
		
		if (date == null) {
			date = new Date();
		}
					
		DateFormat fmt = new SimpleDateFormat(pattern);
		String stringDate = fmt.format(date);
		
		// pipeline out
		
		IDataUtil.put(pipelineCursor, "stringDate", stringDate);
		
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void doubleToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(doubleToString)>> ---
		// @sigtype java 3.5
		// [i] object:0:required number
		// [i] field:0:optional precision
		// [o] field:0:required string
		IDataCursor c = pipeline.getCursor();
		Double d = (Double) IDataUtil.get(c,  "number");
		String precision = IDataUtil.getString(c, "precision");
		
		// process
		
		if (precision == null)
			precision = "2";
		
		IDataUtil.put(c, "string", String.format("%,."+precision+"f", d));
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getMeteringSettings (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getMeteringSettings)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional defaultName
		// [o] field:0:optional meteringFile
		// [o] field:0:required entity
		// [o] field:0:required product
		// [o] field:0:required runtimeId
		// pipeline in 
		
		IDataCursor cursor = pipeline.getCursor(); 
		String defaultName = IDataUtil.getString(cursor, "defaultName");
		
		// process
		
		String meteringFile = System.getProperty("watt.service.alerts.metering.file");
		String entity = System.getProperty("watt.service.alerts.metering.entity");
		String product = System.getProperty("watt.service.alerts.metering.product");
		String runtimeId = System.getProperty("watt.service.alerts.metering.runtimeId");
		
		// pipeline out
		
		if (product != null)
			IDataUtil.put(cursor, "product", product);
		else
			IDataUtil.put(cursor, "product", "PIE");
		
		if (entity != null)
			IDataUtil.put(cursor, "entity", entity);
		else
			IDataUtil.put(cursor, "entity", "not set - please provide extended setting watt.service.alerts.metering.entity");
		
		if (runtimeId != null)
			IDataUtil.put(cursor, "runtimeId", runtimeId);
		else
			IDataUtil.put(cursor, "runtimeId", "not set - please provide extended setting watt.service.alerts.metering.runtimeId");
		if (meteringFile != null)
			IDataUtil.put(cursor, "meteringFile", meteringFile);
		else
			IDataUtil.put(cursor, "meteringFile", defaultName);
		
		cursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void readFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(readFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fname
		// [i] field:0:required loadAs {"bytes","string","stream"}
		// [i] field:0:optional ignoreError {"false","true"}
		// [o] field:0:required name
		// [o] object:0:optional bytes
		// [o] field:0:optional string
		// [o] object:0:optional stream
		IDataCursor c = pipeline.getCursor();
		String fname = IDataUtil.getString(c, "fname");
		String loadAs = IDataUtil.getString(c, "loadAs");
		String ignoreError = IDataUtil.getString(c, "ignoreError");
		
		if (fname == null)
			throw new ServiceException("provide file name please");
		
		// process
		
		byte[] data = null;
		InputStream in = null;
		
		try {
			if (loadAs != null && loadAs.equalsIgnoreCase("stream")) {
				in = new FileInputStream(new File(fname));
			} else {
				data = Files.readAllBytes(Paths.get(fname));
			}
		} catch (NoSuchFileException e ) {
			if (ignoreError == null || !ignoreError.equalsIgnoreCase("true"))
				throw new ServiceException(e);
		} catch (IOException e) {
			
			throw new ServiceException(e);
		}
		
		// pipeline out
		
		IDataUtil.put(c, "name", new File(fname).getName());
		
		if (data != null) {
			
			if (loadAs != null && loadAs.equalsIgnoreCase("string"))
				IDataUtil.put(c, "string", new String(data));
			else
				IDataUtil.put(c, "bytes", data);
			c.destroy();
		} else {
			IDataUtil.put(c, "stream", in);
		}
		// --- <<IS-END>> ---

                
	}



	public static final void writeFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fileName
		// [i] record:0:required data
		// [i] - field:0:optional string
		// [i] - object:0:optional bytes
		// [i] - object:0:optional stream
		// [i] object:0:optional append
		// [i] object:0:optional appendNewLine
		// [o] field:0:required fileNameOnly
		// pipeline in
		
				IDataCursor pipelineCursor = pipeline.getCursor();
				String fileName = IDataUtil.getString(pipelineCursor, "fileName");
				IData data = IDataUtil.getIData( pipelineCursor, "data");
				IDataCursor dataCursor = data.getCursor();
				String	string = IDataUtil.getString( dataCursor, "string");
				byte[] bytes = (byte[]) IDataUtil.get(dataCursor, "bytes");
				InputStream in = (InputStream) IDataUtil.get(dataCursor, "stream");
				dataCursor.destroy();
				
				boolean append = false;
				boolean appendNewLine = false;
				
				try {
				append = (boolean) IDataUtil.get(pipelineCursor, "append");
				} catch(Exception e) {
					// ignore
				}
				try {
					appendNewLine = (boolean) IDataUtil.get(pipelineCursor, "appendNewLine");
					} catch(Exception e) {
						// ignore
					}
				// process
								
				try
				{		
					if (!new File(fileName).getParentFile().exists());
						new File(fileName).getParentFile().mkdirs();
					
					if (bytes != null)
					{
						in = new ByteArrayInputStream(bytes);
					}
					else if (string != null)
					{
						in = new ByteArrayInputStream(string.getBytes());
					} 
					
					File targetFile = new File(fileName);
				    OutputStream outStream = new FileOutputStream(targetFile, append);
				 
				    byte[] buffer = new byte[8 * 1024];
				    int bytesRead;		       
				    
				    while ((bytesRead = in.read(buffer)) != -1) {
				    	
				    	outStream.write(buffer, 0, bytesRead);
				    }
				    
				    if (appendNewLine) {
				    	outStream.write(System.lineSeparator().getBytes());
				    }
				    in.close();
				    outStream.close();
				    
				} catch(IOException e) {
					throw new ServiceException(e);
				}
				
				
				// pipeline out
				
				IDataUtil.put(pipelineCursor, "fileNameOnly", new File(fileName).getName());
				pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	public static Date[] getWeekInterval(Date date) {
		
		ZoneId defaultZoneId = ZoneId.systemDefault();
	
		final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.US).getFirstDayOfWeek();
		final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
		
		LocalDate localDate = Instant.ofEpochMilli(date.getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		
		Date[] dates = new Date[2];
	
		dates[0] = Date.from(localDate.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)).atStartOfDay(defaultZoneId).toInstant());
		dates[1] = Date.from(localDate.with(TemporalAdjusters.nextOrSame(lastDayOfWeek)).atStartOfDay(defaultZoneId).toInstant());
		
		return dates;
	}
	
	public static Date[] getMonthInterval(Date data) {
	
		Date[] dates = new Date[2];
	
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
	
		start.setTime(data);
		start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
	
		end.setTime(data);
		end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
	
	//System.out.println("start "+ start.getTime());
	//System.out.println("end   "+ end.getTime());
	
		dates[0] = start.getTime();
		dates[1] = end.getTime();
	
		return dates;
	}
	
	public static Date[] getYearInterval(Date date) {
	
		ZoneId defaultZoneId = ZoneId.systemDefault();
		
		LocalDate localDate = Instant.ofEpochMilli(date.getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		
		Date[] dates = new Date[2];
		
		dates[0] = Date.from(localDate.with(localDate.with(java.time.temporal.TemporalAdjusters.firstDayOfYear())).atStartOfDay(defaultZoneId).toInstant());
		dates[1] = Date.from(localDate.with(localDate.with(java.time.temporal.TemporalAdjusters.lastDayOfYear())).atTime(23, 59).toInstant(ZoneOffset.MAX));
		
		return dates;
	}
	
	public static Date getEndOfDay(Date date) 
	{
		  LocalDateTime localDateTime = dateToLocalDateTime(date);
		  LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		  return localDateTimeToDate(endOfDay);
		}
	
		public static Date getStartOfDay(Date date) 
		{
		  LocalDateTime localDateTime = dateToLocalDateTime(date);
		  LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		  //LocalDateTime startOfDay = localDateTime.atStartOfDay();
		  return localDateTimeToDate(startOfDay);
		}
		
		private static Date localDateTimeToDate(LocalDateTime startOfDay) 
		{
			return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		}
	
		private static LocalDateTime dateToLocalDateTime(Date date) 
		{
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
		}
	
		public static class DateTimeUtils {
		    private static final long ONE_HOUR_IN_MS = 3600000;
		    private static final long ONE_MIN_IN_MS = 60000;
		    private static final long ONE_SEC_IN_MS = 1000;
	
		    public static Date sumTimeToDate(Date date, int hours, int mins, int secs) {
		        long hoursToAddInMs = hours * ONE_HOUR_IN_MS;
		        long minsToAddInMs = mins * ONE_MIN_IN_MS;
		        long secsToAddInMs = secs * ONE_SEC_IN_MS;
		        return new Date(date.getTime() + hoursToAddInMs + minsToAddInMs + secsToAddInMs);
		    }
		}
	// --- <<IS-END-SHARED>> ---
}

