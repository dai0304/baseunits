/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.domainlanguage.intervals.Interval;
import com.domainlanguage.intervals.IntervalTest;
import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.Duration;
import com.domainlanguage.time.HourOfDay;
import com.domainlanguage.time.MinuteOfHour;
import com.domainlanguage.time.TimeOfDay;
import com.domainlanguage.time.TimeRate;
import com.domainlanguage.time.TimeUnitTest;

public class PersistentMappingVerification {
	
	private static final String SET = "set";
	
	private static final String GET = "get";
	
	private static final String GET_PRIMITIVE_PERSISTENCE_MAPPING_TYPE = "getPrimitivePersistenceMappingType";
	
	private static final String FOR_PERSISTENT_MAPPING = "ForPersistentMapping_";
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private static final Map<String, Object> TEST_TYPE_MAPPING;
	
	private static final Set<String> SHOULD_IGNORE_FIELDS;
	static {
		TEST_TYPE_MAPPING = new HashMap<String, Object>();
		TEST_TYPE_MAPPING.put(BigDecimal.class.getName(), BigDecimal.valueOf(1));
		TEST_TYPE_MAPPING.put(Boolean.TYPE.getName(), Boolean.TRUE);
		TEST_TYPE_MAPPING.put(CalendarDate.class.getName(), CalendarDate.date(2005, 12, 29));
		TEST_TYPE_MAPPING.put(Comparable.class.getName(), Integer.valueOf(2));
		TEST_TYPE_MAPPING.put(Currency.class.getName(), Currency.getInstance("EUR"));
		TEST_TYPE_MAPPING.put(Duration.class.getName(), Duration.days(11));
		TEST_TYPE_MAPPING.put(HourOfDay.class.getName(), HourOfDay.of(11));
		TEST_TYPE_MAPPING.put(Integer.TYPE.getName(), Integer.valueOf(3));
		TEST_TYPE_MAPPING.put("org.sisioh.timeandmoney.intervals.IntervalLimit",
				IntervalTest.exampleLimitForPersistentMappingTesting());
		TEST_TYPE_MAPPING.put(Long.TYPE.getName(), Long.valueOf(4));
		TEST_TYPE_MAPPING.put(List.class.getName(), new ArrayList<Object>());
		TEST_TYPE_MAPPING.put(Map.class.getName(), new HashMap<Object, Object>());
		TEST_TYPE_MAPPING.put(MinuteOfHour.class.getName(), MinuteOfHour.of(22));
		TEST_TYPE_MAPPING.put(Set.class.getName(), new HashSet<Object>());
		TEST_TYPE_MAPPING.put(String.class.getName(), "sample value");
		TEST_TYPE_MAPPING.put(TimeOfDay.class.getName(), TimeOfDay.hourAndMinute(7, 44));
		TEST_TYPE_MAPPING.put(TimeRate.class.getName(), new TimeRate(BigDecimal.valueOf(5), Duration.days(6)));
		TEST_TYPE_MAPPING.put("org.sisioh.timeandmoney.time.TimeUnit",
				TimeUnitTest.exampleForPersistentMappingTesting());
		TEST_TYPE_MAPPING.put("org.sisioh.timeandmoney.time.TimeUnit$Type",
				TimeUnitTest.exampleTypeForPersistentMappingTesting());
		
		SHOULD_IGNORE_FIELDS = new HashSet<String>();
		SHOULD_IGNORE_FIELDS.add(Interval.class.getName());
		SHOULD_IGNORE_FIELDS.add("org.sisioh.timeandmoney.intervals.IntervalLimit");
	}
	

	public static PersistentMappingVerification on(Class<?> klass) throws ClassNotFoundException {
		return new PersistentMappingVerification(klass);
	}
	

	private Class<?> toVerify;
	
	private Object instance;
	
	private List<String> problems;
	

	private PersistentMappingVerification(Class<?> klass) {
		initialize(klass);
	}
	
	public String formatFailure() {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (String nextProblem : problems) {
			if (!first) {
				buffer.append(LINE_SEPARATOR);
			}
			first = false;
			buffer.append(nextProblem);
		}
		return buffer.toString();
	}
	
	public boolean isPersistableRequirementsSatisfied() {
		return problems.isEmpty();
	}
	
	private void addToProblems(String reason) {
		problems.add(reason);
	}
	
	private String capitalize(String string) {
		char chars[] = string.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}
	
	private void checkClass(Class<?> klass) {
		if (klass.isInterface()) {
			return;
		}
		if (klass.isEnum()) {
			return;
		}
		if (isAbstract(klass)) {
			return;
		}
		if (isFinal(klass)) {
			addToProblems(klass.toString() + " must not be final");
		}
		checkConstructor(klass);
	}
	
	private void checkConstructor(Class<?> klass) {
		Constructor<?> constructor = null;
		try {
			constructor = klass.getDeclaredConstructor();
			constructor.setAccessible(true);
			instance = constructor.newInstance();
		} catch (NoSuchMethodException ex) {
			addToProblems(klass.toString() + " has no default constructor");
		} catch (IllegalArgumentException ex) {
			addToProblems(constructor.toString() + " had an illegal argument exception");
		} catch (InstantiationException ex) {
			addToProblems(constructor.toString() + " had an instantion exception");
		} catch (IllegalAccessException ex) {
			addToProblems(constructor.toString() + " had an illegal access exception");
		} catch (InvocationTargetException ex) {
			addToProblems(constructor.toString() + " had an invocation exception");
		}
	}
	
	private void checkEverything() {
		checkClass(toVerify);
		Class<?> current = toVerify;
		while (current != null && current != Object.class) {
			checkFields(current);
			current = current.getSuperclass();
		}
	}
	
	private void checkField(Field theField) {
		String name = capitalize(theField.getName());
		Class<?> type = getTypeFor(theField);
		Object toTest = getTestValueFor(type);
		checkSetter(theField, name, toTest);
		Object actual = checkGetter(theField, name);
		if (instance != null && !sameClassOrBothNull(toTest, actual)) {
			addToProblems(theField.toString() + " getter/setter result do not match, expected [" + toTest
					+ "], but got [" + actual + "]");
			return;
		}
	}
	
	private void checkFields(Class<?> klass) {
		if (shouldIgnoreFields(klass)) {
			return;
		}
		Field[] fields = klass.getDeclaredFields();
		for (Field each : fields) {
			if ((each.getModifiers() & Modifier.STATIC) > 0) {
				continue;
			}
			checkField(each);
		}
	}
	
	private Object checkGetter(Field theField, String name) {
		Method getter = null;
		Object actual = null;
		try {
			getter = getGetter(theField, name, GET + FOR_PERSISTENT_MAPPING + name);
			if (!isMethodPrivate(getter)) {
				addToProblems(getter.toString() + " not declared private");
			}
			if (instance != null) {
				getter.setAccessible(true);
				actual = getter.invoke(instance);
			}
		} catch (NoSuchMethodException ex) {
			addToProblems(ex.getMessage() + "does not exist");
		} catch (IllegalArgumentException ex) {
			addToProblems(getter.toString() + " had an illegal argument exception");
		} catch (IllegalAccessException ex) {
			addToProblems(getter.toString() + " had an illegal access exception");
		} catch (InvocationTargetException ex) {
			addToProblems(getter.toString() + " had an invocation target exception");
		}
		return actual;
	}
	
	private void checkSetter(Field theField, String name, Object toTest) {
		Method setter = null;
		try {
			setter = getSetter(theField, SET + FOR_PERSISTENT_MAPPING + name);
			if (!isMethodPrivate(setter)) {
				addToProblems(setter.toString() + " not declared private");
			}
			if (instance != null) {
				setter.setAccessible(true);
				setter.invoke(instance, new Object[] {
					toTest
				});
			}
		} catch (NoSuchMethodException ex) {
			addToProblems(ex.getMessage() + "does not exist");
		} catch (IllegalArgumentException ex) {
			addToProblems(setter.toString() + " had an illegal argument exception");
		} catch (IllegalAccessException ex) {
			addToProblems(setter.toString() + " had an illegal access exception");
		} catch (InvocationTargetException ex) {
			addToProblems(setter.toString() + " had an invocation target exception");
		}
	}
	
	private Method getGetter(Field theField, String name, String getter) throws NoSuchMethodException {
		try {
			return theField.getDeclaringClass().getDeclaredMethod(getter);
		} catch (NoSuchMethodException unknownGetter) {
			return theField.getDeclaringClass().getDeclaredMethod("is" + FOR_PERSISTENT_MAPPING + name);
		}
	}
	
	private Method getSetter(Field theField, String setter) throws NoSuchMethodException {
		Class<?>[] setterTypes = new Class[1];
		setterTypes[0] = getTypeFor(theField);
		return theField.getDeclaringClass().getDeclaredMethod(setter, setterTypes);
	}
	
	private Object getTestValueFor(Class<?> type) {
		Object result = TEST_TYPE_MAPPING.get(type.getName());
		if (result == null) {
			addToProblems("Add sample value for " + type.toString() + " to TEST_TYPE_MAPPING");
		}
		return result;
	}
	
	private Class<?> getTypeFor(Field theField) {
		Class<?> type = theField.getType();
		if (type.isPrimitive()) {
			return type;
		}
		try {
			Method method = type.getDeclaredMethod(GET_PRIMITIVE_PERSISTENCE_MAPPING_TYPE);
			method.setAccessible(true);
			return (Class<?>) method.invoke(null);
		} catch (IllegalArgumentException ex) {
			return type;
		} catch (IllegalAccessException ex) {
			return type;
		} catch (InvocationTargetException ex) {
			return type;
		} catch (SecurityException ex) {
			return type;
		} catch (NoSuchMethodException ex) {
			return type;
		}
	}
	
	private void initialize(Class<?> klass) {
		toVerify = klass;
		problems = new ArrayList<String>();
		try {
			if (isPrimitivePersistenceMappingType(klass)) {
				return;
			}
			checkEverything();
		} catch (RuntimeException ex) {
			addToProblems(ex.toString());
		}
	}
	
	private boolean isAbstract(Class<?> klass) {
		return (klass.getModifiers() & Modifier.ABSTRACT) > 0;
	}
	
	private boolean isFinal(Class<?> klass) {
		return (klass.getModifiers() & Modifier.FINAL) > 0;
	}
	
	private boolean isMethodPrivate(Method toCheck) {
		return (toCheck.getModifiers() & Modifier.PRIVATE) > 0;
	}
	
	private boolean isPrimitivePersistenceMappingType(Class<?> klass) {
		try {
			return klass.getDeclaredMethod(GET_PRIMITIVE_PERSISTENCE_MAPPING_TYPE) != null;
		} catch (SecurityException ex) {
			return false;
		} catch (NoSuchMethodException ex) {
			return false;
		}
	}
	
	private boolean sameClassOrBothNull(Object one, Object another) {
		if (one == another) {
			return true;
		}
		if (one == null) {
			return false;
		}
		if (another == null) {
			return false;
		}
		return one.getClass().isInstance(another);
	}
	
	private boolean shouldIgnoreFields(Class<?> klass) {
		return SHOULD_IGNORE_FIELDS.contains(klass.getName());
	}
}
