package jp.xet.timeandmoney.time;

import java.util.Calendar;

/**
 * 曜日を表す列挙型。
 * 
 * @author daisuke
 */
public enum DayOfWeek {
	
	/** 日曜日 */
	SUNDAY(Calendar.SUNDAY),

	/** 月曜日 */
	MONDAY(Calendar.MONDAY),

	/** 火曜日 */
	TUESDAY(Calendar.TUESDAY),

	/** 水曜日 */
	WEDNESDAY(Calendar.WEDNESDAY),

	/** 木曜日 */
	THURSDAY(Calendar.THURSDAY),

	/** 金曜日 */
	FRIDAY(Calendar.FRIDAY),

	/** 土曜日 */
	SATURDAY(Calendar.SATURDAY);
	
	/**
	 * {@link Calendar}に定義されている定数値から、インスタンスを取得する。
	 * 
	 * @param value {@link Calendar}に定義されている定数値
	 * @return {@link DayOfWeek}. 見つからなかった場合は {@code null}
	 */
	public static DayOfWeek valueOf(int value) {
		for (DayOfWeek dayOfWeek : values()) {
			if (dayOfWeek.value == value) {
				return dayOfWeek;
			}
		}
		return null;
	}
	

	/** {@link Calendar}に定義されている定数値 */
	public final int value;
	

	DayOfWeek(int value) {
		this.value = value;
	}
	
}
