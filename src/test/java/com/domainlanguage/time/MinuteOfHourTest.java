/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.domainlanguage.time.HourOfDay;
import com.domainlanguage.time.MinuteOfHour;

import org.junit.Test;

/**
 * {@link MinuteOfHour}のテストクラス。
 * 
 * @author daisuke
 */
public class MinuteOfHourTest {
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Simple() throws Exception {
		assertThat(MinuteOfHour.of(11).value(), is(11));
		assertThat(MinuteOfHour.of(23), is(MinuteOfHour.of(23)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_IllegalLessThanZero() throws Exception {
		try {
			MinuteOfHour.of(-1);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail("Illegal Argument Not Caught");
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_GreaterThan() throws Exception {
		try {
			HourOfDay.of(60);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail("Illegal Argument Not Caught");
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_LaterAfterEarlier() throws Exception {
		MinuteOfHour later = MinuteOfHour.of(45);
		MinuteOfHour earlier = MinuteOfHour.of(15);
		assertThat(later.isAfter(earlier), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_EarlierAfterLater() throws Exception {
		MinuteOfHour earlier = MinuteOfHour.of(15);
		MinuteOfHour later = MinuteOfHour.of(45);
		assertThat(earlier.isAfter(later), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_EqualAfterEqual() throws Exception {
		MinuteOfHour anMinute = MinuteOfHour.of(45);
		MinuteOfHour anotherMinute = MinuteOfHour.of(45);
		assertThat(anMinute.isAfter(anotherMinute), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_LaterBeforeEarlier() throws Exception {
		MinuteOfHour later = MinuteOfHour.of(45);
		MinuteOfHour earlier = MinuteOfHour.of(15);
		assertThat(later.isBefore(earlier), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_EarlierBeforeLater() throws Exception {
		MinuteOfHour earlier = MinuteOfHour.of(15);
		MinuteOfHour later = MinuteOfHour.of(45);
		assertThat(earlier.isBefore(later), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_EqualBeforeEqual() throws Exception {
		MinuteOfHour anMinute = MinuteOfHour.of(15);
		MinuteOfHour anotherMinute = MinuteOfHour.of(15);
		assertThat(anMinute.isBefore(anotherMinute), is(false));
	}
}
