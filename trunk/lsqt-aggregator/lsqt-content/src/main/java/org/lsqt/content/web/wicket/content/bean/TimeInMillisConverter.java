package org.lsqt.content.web.wicket.content.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.DateTime;

public class TimeInMillisConverter implements IConverter<Double>
{
	public static TimeInMillisConverter INSTANCE=new TimeInMillisConverter();
	
	@Override
	public Double convertToObject(String value, Locale locale)
	{
		try
		{
			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", locale);
			Date dt=df.parse(value);
			Calendar c=	Calendar.getInstance(locale);
			c.setTime(dt);
			return Double.valueOf(c.getTimeInMillis());
			
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new ConversionException("'" + value + "' is not a valid Date");
		}
	}

	@Override
	public String convertToString(Double value, Locale locale)
	{
		if (value == null)
		{
			return StringUtils.EMPTY;
		}
		
		Calendar c=Calendar.getInstance(locale);
		c.setTimeInMillis(value.longValue());
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(c.getTime());
	}

	public static void main(String args[])
	{
		DateTime dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		System.out.println(dateTime.plusDays(45).plusMonths(1).dayOfWeek().withMaximumValue().toString("yyyy-MM-dd hh:mm:ss S"));
	}
}
