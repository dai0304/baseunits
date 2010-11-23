/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.util;

//import java.util.StringTokenizer;
//
//import junit.framework.TestCase;
//
//import org.junit.Test;
//
//public class TimeAndMoneyDomainClassFilter implements ClassFilter {
//	
//	@Override
//	public boolean accepts(Class<?> klass) {
//		try {
//			if (isTestCase(klass)) {
//				return false;
//			}
//			if (isInnerClass(klass) && isTimeAndMoney(klass.getDeclaringClass()) == false) {
//				return false;
//			}
//			if (isTimeAndMoney(klass)) {
//				return true;
//			}
//			return false;
//		} catch (NoClassDefFoundError error) {
//			return false;
//		}
//	}
//	
//	private boolean isInnerClass(Class<?> klass) {
//		return klass.getName().indexOf('$') > -1;
//	}
//	
//	private boolean isTestCase(Class<?> klass) {
//		if (klass.getAnnotation(Test.class) != null) {
//			return true;
//		}
//		Class<?> superclass = klass.getSuperclass();
//		if (superclass == null) {
//			return false;
//		}
//		if (superclass == Object.class) {
//			return false;
//		}
//		if (superclass == TestCase.class) {
//			return true;
//		}
//		return isTestCase(superclass);
//	}
//	
//	private boolean isTimeAndMoney(Class<?> klass) {
//		if (klass == null) {
//			return false;
//		}
//		StringTokenizer parts = new StringTokenizer(klass.getName(), ".");
//		boolean result = false;
//		while (parts.hasMoreTokens()) {
//			String next = parts.nextToken();
//			if (next.equals("timeandmoney")) {
//				result = true;
//			}
//			if (next.equals("tests") || next.equals("util") || next.equals("adt")) {
//				result = false;
//			}
//		}
//		return result;
//	}
//}
