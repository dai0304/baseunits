/*
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
package jp.xet.timeandmoney.tests;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;

/**
 * シリアライズのテストを行う、ヘルパークラス。
 */
public class SerializationTester {
	
	/**
	 * シリアライズできるかどうか検証する。
	 * 
	 * @param serializable シリアライズ対象オブジェクト
	 * @throws AssertionError シリアライズに失敗した場合
	 */
	public static void assertCanBeSerialized(Object serializable) {
		if (Serializable.class.isInstance(serializable) == false) {
			fail("Object doesn't implement java.io.Serializable interface: " + serializable.getClass());
		}
		
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		ByteArrayInputStream byteArrayIn = null;
		try {
			out = new ObjectOutputStream(byteArrayOut);
			out.writeObject(serializable);
			
			byteArrayIn = new ByteArrayInputStream(byteArrayOut.toByteArray());
			in = new ObjectInputStream(byteArrayIn);
			Object deserialized = in.readObject();
			if (serializable.equals(deserialized) == false) {
				fail("Reconstituted object is expected to be equal to serialized");
			}
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ClassNotFoundException e) {
			fail(e.getMessage());
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
	}
	
}
