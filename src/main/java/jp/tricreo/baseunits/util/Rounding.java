/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 200X Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.tricreo.baseunits.util;

import java.math.BigDecimal;

/**
 * 丸めモードを表す列挙型。
 * 
 * <p>演算の結果、数字を丸める際に適用するモードをあらわす。例えば、切り捨て,切り上げ,四捨五入など。</p>
 * 
 * @since 1.0
 * @deprecated 次期メジャーバージョンアップ（v2.0）以降、この機能はサポートされません。
 * 			use {@link java.math.RoundingMode}
 */
@Deprecated
public enum Rounding {
	
	/**
	 * 切り上げモード UP。
	 * 
	 * <p>{@code 0}から遠ざかる方向に向けた丸めモードを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_UP
	 * @since 1.0
	 */
	UP(BigDecimal.ROUND_UP),
	
	/**
	 * 切り捨てモード DOWN。
	 * 
	 * <p>{@code 0}に近づく方向に向けた丸めモードを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_DOWN
	 * @since 1.0
	 */
	DOWN(BigDecimal.ROUND_DOWN),
	
	/**
	 * 切り上げモード CEILING。
	 * 
	 * <p>正の無限大に向けて切り上げを行う丸めモードを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_CEILING
	 * @since 1.0
	 */
	CEILING(BigDecimal.ROUND_CEILING),
	
	/**
	 * 切り捨てモード FLOOR。
	 * 
	 * <p>負の無限大に向けて切り上げを行う丸めモードを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_FLOOR
	 * @since 1.0
	 */
	FLOOR(BigDecimal.ROUND_FLOOR),
	
	/**
	 * 丸めモード HALF_UP。
	 * 
	 * <p>両隣のうち、近い方に向けて切り上げを行う丸めモードを表す。両者の距離が同一である場合は、
	 * {@link #UP}モードを適用する。</p>
	 * 
	 * @see BigDecimal#ROUND_HALF_UP
	 * @since 1.0
	 */
	HALF_UP(BigDecimal.ROUND_HALF_UP),
	
	/**
	 * 丸めモード HALF_DOWN。
	 * 
	 * <p>両隣のうち、近い方に向けて切り上げを行う丸めモードを表す。両者の距離が同一である場合は、
	 * {@link #DOWN}モードを適用する。</p>
	 * 
	 * @see BigDecimal#ROUND_HALF_DOWN
	 * @since 1.0
	 */
	HALF_DOWN(BigDecimal.ROUND_HALF_DOWN),
	
	/**
	 * 丸めモード HALF_EVEN。
	 * 
	 * <p>両隣のうち、近い方に向けて切り上げを行う丸めモードを表す。両者の距離が同一である場合は、
	 * 偶数側に丸める。</p>
	 * 
	 * @see BigDecimal#ROUND_HALF_DOWN
	 * @since 1.0
	 */
	HALF_EVEN(BigDecimal.ROUND_HALF_EVEN),
	
	/**
	 * 丸めが不要であることを明示的に表すモード。
	 * 
	 * <p>このモードを明示的に指定したが割り切れない場合は、そのメソッドは {@link ArithmeticException}を
	 * スローする。</p>
	 * 
	 * @see BigDecimal#ROUND_UNNECESSARY
	 * @since 1.0
	 */
	UNNECESSARY(BigDecimal.ROUND_UNNECESSARY);
	
	/**
	 * {@link BigDecimal}クラスにおける{@code int}型による定義値。
	 * 
	 * @since 1.0
	 */
	public final int value;
	
	
	Rounding(int value) {
		this.value = value;
	}
}
