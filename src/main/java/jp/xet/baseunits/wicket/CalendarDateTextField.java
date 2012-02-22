/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/18
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.xet.baseunits.wicket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jp.xet.baseunits.time.CalendarDate;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

/**
 * A TextField that is mapped to a {@link CalendarDate} object.
 * 
 * If no Date pattern is explicitly specified, the default <code>DateFormat.SHORT</code> pattern for
 * the current locale will be used.
 * 
 * @since 2.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class CalendarDateTextField extends TextField<CalendarDate> implements ITextFormatProvider
{
	
	private static final String DEFAULT_PATTERN = "MM/dd/yyyy";
	
	
	/**
	 * Try to get datePattern from user session locale. If it is not possible, it will return
	 * {@link #DEFAULT_PATTERN}
	 * 
	 * @return CalendarDate pattern
	 */
	private static String defaultdatePattern() {
		// It is possible to retrieve from session?
		Locale locale = Session.get().getLocale();
		if (locale != null) {
			DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
			if (format instanceof SimpleDateFormat) {
				return ((SimpleDateFormat) format).toPattern();
			}
		}
		return DEFAULT_PATTERN;
	}
	
	
	/**
	 * The CalendarDate pattern of the text field
	 */
	private String datePattern;
	
	/**
	 * The converter for the TextField
	 */
	private final IConverter<CalendarDate> converter;
	
	
	/**
	 * Creates a new CalendarDateTextField, without a specified pattern. This is the same as calling
	 * <code>new TextField(id, CalendarDate.class)</code>
	 * 
	 * @param id The id of the text field
	 * @see org.apache.wicket.markup.html.form.TextField
	 */
	public CalendarDateTextField(final String id) {
		this(id, null, defaultdatePattern());
	}
	
	/**
	 * Creates a new CalendarDateTextField, without a specified pattern. This is the same as calling
	 * <code>new TextField(id, object, CalendarDate.class)</code>
	 * 
	 * @param id The id of the text field
	 * @param model The model
	 * @see org.apache.wicket.markup.html.form.TextField
	 */
	public CalendarDateTextField(final String id, final IModel<CalendarDate> model) {
		this(id, model, defaultdatePattern());
	}
	
	/**
	 * Creates a new CalendarDateTextField bound with a specific <code>SimpleDateFormat</code> pattern.
	 * 
	 * @param id The id of the text field
	 * @param model The model
	 * @param datePattern A <code>SimpleDateFormat</code> pattern
	 * @see org.apache.wicket.markup.html.form.TextField
	 */
	public CalendarDateTextField(final String id, final IModel<CalendarDate> model, final String datePattern) {
		super(id, model, CalendarDate.class);
		this.datePattern = datePattern;
		converter = new CalendarDateConverter(datePattern);
	}
	
	/**
	 * Creates a new CalendarDateTextField bound with a specific <code>SimpleDateFormat</code> pattern.
	 * 
	 * @param id The id of the text field
	 * @param datePattern A <code>SimpleDateFormat</code> pattern
	 * 
	 * @see org.apache.wicket.markup.html.form.TextField
	 */
	public CalendarDateTextField(final String id, final String datePattern) {
		this(id, null, datePattern);
	}
	
	/**
	 * Returns the default converter if created without pattern; otherwise it returns a
	 * pattern-specific converter.
	 * 
	 * @param type The type for which the convertor should work
	 * @return A pattern-specific converter
	 * @see org.apache.wicket.markup.html.form.TextField
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(final Class<C> type) {
		if (CalendarDate.class.isAssignableFrom(type)) {
			return (IConverter<C>) converter;
		} else {
			return super.getConverter(type);
		}
	}
	
	/**
	 * Returns the Date pattern.
	 * 
	 * @see org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider#getTextFormat()
	 */
	@Override
	public String getTextFormat() {
		return datePattern;
	}
}
