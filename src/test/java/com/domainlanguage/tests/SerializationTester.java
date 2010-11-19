/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.tests;

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
 * 
 * @author daisuke
 */
public class SerializationTester {
	
	/**
	 * シリアライズできるかどうか検証する。
	 * 
	 * @param serializable シリアライズ対象オブジェクト
	 * @throws Exception シリアライズに失敗した場合
	 */
	public static void assertCanBeSerialized(Object serializable) {
		if (Serializable.class.isInstance(serializable) == false) {
			fail("Object doesn't implement java.io.Serializable interface");
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
