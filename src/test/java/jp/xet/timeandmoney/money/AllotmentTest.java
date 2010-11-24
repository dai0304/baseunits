package jp.xet.timeandmoney.money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Allotment}のテストクラス。
 */
public class AllotmentTest {
	
	/**
	 * 等価性検証。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Equals() throws Exception {
		Allotment<String> abc123dollars = new Allotment<String>("ABC", Money.dollars(1.23));
		assertThat(abc123dollars.equals(new Allotment<String>("ABC", Money.dollars(1.23))), is(true));
		assertThat(abc123dollars.equals(new Allotment<String>("ABC", Money.euros(1.23))), is(false));
		assertThat(abc123dollars.equals(new Allotment<String>("XYZ", Money.dollars(1.23))), is(false));
		assertThat(abc123dollars.equals(new Allotment<String>("ABC", Money.dollars(1.24))), is(false));
	}
	
	/**
	 * 正負転換検証。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Negated() throws Exception {
		Allotment<String> abc123dollars = new Allotment<String>("ABC", Money.dollars(1.23));
		assertThat(abc123dollars.negated(), is(new Allotment<String>("ABC", Money.dollars(-1.23))));
	}
}
