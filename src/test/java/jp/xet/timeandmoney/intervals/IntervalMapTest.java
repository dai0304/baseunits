/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.intervals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link IntervalMap}のテストクラス。
 */
public class IntervalMapTest {
	
	/**
	 * {@link IntervalMap}に対する参照メソッドのテスト。
	 * 
	 * <ul>
	 *   <li>{@link IntervalMap#containsKey(Comparable)}</li>
	 *   <li>{@link IntervalMap#get(Comparable)}</li>
	 * </ul>
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Lookup() throws Exception {
		IntervalMap<Integer, String> map = new LinearIntervalMap<Integer, String>();
		map.put(Interval.closed(1, 3), "one-three");
		map.put(Interval.closed(5, 9), "five-nine");
		map.put(Interval.open(9, 12), "ten-eleven");
		
		assertThat(map.containsKey(0), is(false));
		assertThat(map.containsKey(1), is(true));
		assertThat(map.containsKey(2), is(true));
		assertThat(map.containsKey(3), is(true));
		assertThat(map.containsKey(4), is(false));
		assertThat(map.containsKey(5), is(true));
		assertThat(map.containsKey(9), is(true));
		assertThat(map.containsKey(11), is(true));
		assertThat(map.containsKey(12), is(false));
		assertThat(map.containsKey(13), is(false));
		assertThat(map.containsKey(null), is(false));
		
		assertThat(map.get(0), is(nullValue()));
		assertThat(map.get(1), is("one-three"));
		assertThat(map.get(2), is("one-three"));
		assertThat(map.get(3), is("one-three"));
		assertThat(map.get(4), is(nullValue()));
		assertThat(map.get(5), is("five-nine"));
		assertThat(map.get(9), is("five-nine"));
		assertThat(map.get(10), is("ten-eleven"));
		assertThat(map.get(11), is("ten-eleven"));
		assertThat(map.get(12), is(nullValue()));
		assertThat(map.get(13), is(nullValue()));
		assertThat(map.get(null), is(nullValue()));
	}
	
	/**
	 * {@link IntervalMap#remove(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Remove() throws Exception {
		IntervalMap<Integer, String> map = new LinearIntervalMap<Integer, String>();
		map.put(Interval.closed(1, 10), "one-ten");
		map.remove(Interval.closed(3, 5));
		assertThat(map.get(2), is("one-ten"));
		assertThat(map.get(3), is(nullValue()));
		assertThat(map.get(4), is(nullValue()));
		assertThat(map.get(5), is(nullValue()));
		assertThat(map.get(6), is("one-ten"));
	}
	
	/**
	 * {@link IntervalMap#put(Interval, Object)}で割り当て区間が重複した場合、後勝ちになることを確認するテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_ConstructionOverwriteOverlap() throws Exception {
		IntervalMap<Integer, String> map = new LinearIntervalMap<Integer, String>();
		map.put(Interval.closed(1, 3), "one-three");
		map.put(Interval.closed(5, 9), "five-nine");
		map.put(Interval.open(9, 12), "ten-eleven");
		assertThat(map.get(10), is("ten-eleven"));
		assertThat(map.get(11), is("ten-eleven"));
		assertThat(map.get(12), is(nullValue()));
		
		Interval<Integer> eleven_thirteen = Interval.closed(11, 13);
		assertThat(map.containsIntersectingKey(eleven_thirteen), is(true));
		map.put(eleven_thirteen, "eleven-thirteen");
		assertThat(map.get(10), is("ten-eleven"));
		assertThat(map.get(11), is("eleven-thirteen"));
		assertThat(map.get(12), is("eleven-thirteen"));
	}
	
	/**
	 * {@link IntervalMap#put(Interval, Object)}で割り当て区間が重複した場合、後勝ちになることを確認するテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_ConstructionOverwriteMiddle() throws Exception {
		IntervalMap<Integer, String> map = new LinearIntervalMap<Integer, String>();
		map.put(Interval.closed(1, 3), "one-three");
		map.put(Interval.closed(5, 9), "five-nine");
		map.put(Interval.open(9, 12), "ten-eleven");
		assertThat(map.get(6), is("five-nine"));
		assertThat(map.get(7), is("five-nine"));
		assertThat(map.get(8), is("five-nine"));
		assertThat(map.get(9), is("five-nine"));
		
		Interval<Integer> seven_eight = Interval.closed(7, 8);
		assertThat(map.containsIntersectingKey(seven_eight), is(true));
		map.put(seven_eight, "seven-eight");
		assertThat(map.get(6), is("five-nine"));
		assertThat(map.get(7), is("seven-eight"));
		assertThat(map.get(8), is("seven-eight"));
		assertThat(map.get(9), is("five-nine"));
	}
	
	/**
	 * {@link IntervalMap#put(Interval, Object)}で割り当て区間が重複した場合、後勝ちになることを確認するテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_ConstructionOverwriteMultiple() throws Exception {
		IntervalMap<Integer, String> map = new LinearIntervalMap<Integer, String>();
		map.put(Interval.closed(1, 2), "one-two");
		map.put(Interval.closed(3, 4), "three-four");
		map.put(Interval.closed(5, 6), "five-six");
		map.put(Interval.closed(8, 9), "eight-nine");
		map.put(Interval.closed(3, 8), "three-eight");
		assertThat(map.get(2), is("one-two"));
		assertThat(map.get(3), is("three-eight"));
		assertThat(map.get(4), is("three-eight"));
		assertThat(map.get(5), is("three-eight"));
		assertThat(map.get(6), is("three-eight"));
		assertThat(map.get(7), is("three-eight"));
		assertThat(map.get(8), is("three-eight"));
		assertThat(map.get(9), is("eight-nine"));
	}
	
}
