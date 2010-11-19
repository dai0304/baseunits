/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.util;

//import static org.junit.Assert.fail;
//
//import org.junit.Test;
//
//public class VerifyPersistentMappingTest {
//	
//	boolean hasFailures;
//	
//
//	/**
//	 * テスト実行。
//	 * 
//	 * @throws Exception 例外が発生した場合
//	 */
//	@Test
//	public void test() throws Exception {
//		hasFailures = false;
//		ClassGenerator generator = new ClassGenerator(new TimeAndMoneyDomainClassFilter()) {
//			
//			@Override
//			protected void next(Class<?> klass) throws Exception {
//				PersistentMappingVerification verification;
//				verification = PersistentMappingVerification.on(klass);
//				if (verification.isPersistableRequirementsSatisfied() == false) {
//					hasFailures = true;
//					System.err.println(formatFailure(verification));
//				}
//			}
//		};
//		generator.go();
//		if (hasFailures) {
//			fail("Failed Test. See System.err for details");
//		}
//	}
//	
//	String formatFailure(PersistentMappingVerification verification) {
//		return verification.formatFailure();
//	}
//}
