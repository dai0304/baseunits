/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

/**
 * 現在時刻を返す責務を表すインターフェイス。
 * 
 * @author daisuke
 */
public interface TimeSource {
	
	/**
	 * 現在時刻を返す。
	 * 
	 * @return 現在時刻
	 * @throws TimeSourceException 現在時刻の取得に失敗した場合
	 */
	TimePoint now();
}
