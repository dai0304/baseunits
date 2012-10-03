/*
 * Copyright 2011-2012 Cloudstudy, Inc..
 * Created on 2012/04/19
 * 
 * All rights reserved.
 */
package jp.xet.baseunits.wicket;

import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.formatter.Icu4jRelativeTimePointFormatter;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter.FallbackConfig;

import org.apache.wicket.WicketRuntimeException;
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
	
	private final IConverter<TimePoint> converter;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param config {@link FallbackConfig}
	 * @param timeZone {@link TimeZone}
	 * @throws WicketRuntimeException if the component has been given a null id.
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
	 * @throws WicketRuntimeException if the component has been given a null id.
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
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, IModel<TimePoint> model, RelativeTimePointFormatter formatter) {
		super(id, model);
		converter = new RelativeTimePointConverter(formatter);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param formatter {@link RelativeTimePointFormatter}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, RelativeTimePointFormatter formatter) {
		super(id);
		converter = new RelativeTimePointConverter(formatter);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == TimePoint.class) {
			return (IConverter<C>) converter;
		}
		return super.getConverter(type);
	}
}
