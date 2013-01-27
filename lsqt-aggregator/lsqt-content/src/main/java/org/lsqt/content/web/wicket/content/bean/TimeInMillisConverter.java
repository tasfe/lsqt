package org.lsqt.content.web.wicket.content.bean;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class TimeInMillisConverter implements IConverter<Long>
{
	public static TimeInMillisConverter INSTANCE=new TimeInMillisConverter();
	
	@Override
	public Long convertToObject(String value, Locale locale)
	{
		try
		{
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", locale);
			Date dt=df.parse(value);
			Calendar c=	Calendar.getInstance(locale);
			c.setTime(dt);
			return c.getTimeInMillis();
			
		} catch (ParseException e)
		{
			e.printStackTrace();
			throw new ConversionException("'" + value + "' is not a valid Date");
		}
	}

	@Override
	public String convertToString(Long value, Locale locale)
	{
		if (value == null)
		{
			return StringUtils.EMPTY;
		}
		
		Calendar c=Calendar.getInstance(locale);
		c.setTimeInMillis(value);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return df.format(c.getTime());
	}

}
