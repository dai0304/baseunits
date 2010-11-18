/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.timeutil;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.domainlanguage.tests.CannedResponseServer;
import com.domainlanguage.time.TimePoint;
import com.domainlanguage.time.TimeSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NISTServerTimeSourceTest {
	
	private static final TimePoint EXPECTED_TIME_POINT = TimePoint.from(1124679473000l);
	
	private static final String CANNED_RESPONSE = "\n53604 05-08-22 02:57:53 50 0 0 725.6 UTC(NIST) * \n";
	
	private CannedResponseServer standInNISTServer;
	

	/**
	 * テストを初期化する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Before
	public void setUp() throws Exception {
		standInNISTServer = new CannedResponseServer(CANNED_RESPONSE);
		standInNISTServer.start();
	}
	
	/**
	 * テストの情報を破棄する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@After
	public void tearDown() throws Exception {
		standInNISTServer.stop();
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_NISTTimeSource() throws Exception {
		//This would return a source that goes to the internet       
		//TimeSource source = NISTClient.timeSource();
		TimeSource source = NISTClient.timeSource(standInNISTServer.getHostName(), standInNISTServer.getPort());
		assertThat(source.now(), is(EXPECTED_TIME_POINT));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_AsTimePoint() throws Exception {
		assertThat(NISTClient.asTimePoint(CANNED_RESPONSE), is(EXPECTED_TIME_POINT));
	}
	
}
