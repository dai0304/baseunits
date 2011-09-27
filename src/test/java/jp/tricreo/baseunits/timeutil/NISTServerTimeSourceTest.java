/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
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
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.tricreo.baseunits.timeutil;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.io.IOException;

import jp.tricreo.baseunits.tests.CannedResponseServer;
import jp.tricreo.baseunits.time.TimePoint;
import jp.tricreo.baseunits.time.TimeSource;
import jp.tricreo.baseunits.time.TimeSourceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link NISTClient}のテスト。
 */
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
	 * {@link NISTClient#timeSource()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_NISTTimeSource() throws Exception {
		// This would return a source that goes to the internet       
		// TimeSource source = NISTClient.timeSource();
		NISTClient client = new NISTClient(standInNISTServer.getHostName(), standInNISTServer.getPort());
		TimeSource source = client.timeSource();
		assertThat(source.now(), is(EXPECTED_TIME_POINT));
	}
	
	/**
	 * {@link NISTClient#asTimePoint(String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_AsTimePoint() throws Exception {
		NISTClient client = new NISTClient();
		assertThat(client.asTimePoint(CANNED_RESPONSE), is(EXPECTED_TIME_POINT));
	}
	
	/**
	 * エラー発生時のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_error() throws Exception {
		NISTClient client = spy(new NISTClient());
		doThrow(new IOException()).when(client).now(anyString(), anyInt());
		
		TimeSource source = client.timeSource();
		try {
			source.now();
			fail();
		} catch (TimeSourceException e) {
			// success
		}
	}
}
