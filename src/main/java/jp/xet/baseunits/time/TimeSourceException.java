/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2009 TRICREO, Inc. (http://tricreo.jp/)
 * Created on 2009/12/08
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
 */
package jp.xet.baseunits.time;

/**
 * 現在の{@link TimePoint}の取得に失敗したことをあらわす例外。
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public class TimeSourceException extends RuntimeException {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param message 例外メッセージ
	 * @param e 起因例外
	 * @since 1.0
	 */
	public TimeSourceException(String message, Exception e) {
		super(message, e);
	}
}
