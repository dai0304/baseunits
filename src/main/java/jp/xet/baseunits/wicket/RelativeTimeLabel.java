/*
 * Copyright 2011-2012 Cloudstudy, Inc..
 * Created on 2012/04/19
 * 
 * All rights reserved.
 */
package jp.xet.baseunits.wicket;

import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.formatter.Icu4jRelativeTimePointFormatter;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter.FallbackConfig;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

/**
 * TODO for daisuke
 * 
 * @since 2.2
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class RelativeTimeLabel extends GenericLabel<TimePoint> {
	
	private final RelativeTimePointFormatter formatter;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param config {@link FallbackConfig}
	 * @param timeZone {@link TimeZone}
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, FallbackConfig config, TimeZone timeZone) {
		this(id, new Icu4jRelativeTimePointFormatter(config, timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param config {@link FallbackConfig}
	 * @param timeZone {@link TimeZone}
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, IModel<TimePoint> model, FallbackConfig config, TimeZone timeZone) {
		this(id, model, new Icu4jRelativeTimePointFormatter(config, timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param formatter {@link RelativeTimePointFormatter}
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, IModel<TimePoint> model, RelativeTimePointFormatter formatter) {
		super(id, model);
		this.formatter = formatter;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param formatter {@link RelativeTimePointFormatter}
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, RelativeTimePointFormatter formatter) {
		super(id);
		this.formatter = formatter;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == TimePoint.class) {
			return (IConverter<C>) new TimePointConverter(formatter);
		}
		return super.getConverter(type);
	}
	
	
	private static class TimePointConverter implements IConverter<TimePoint> {
		
		private final RelativeTimePointFormatter formatter;
		
		
		private TimePointConverter(RelativeTimePointFormatter formatter) {
			this.formatter = formatter;
		}
		
		@Override
		public TimePoint convertToObject(String value, Locale locale) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public String convertToString(TimePoint value, Locale locale) {
			return formatter.format(value, locale);
		}
	}
}
