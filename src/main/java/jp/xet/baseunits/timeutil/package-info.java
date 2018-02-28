/*
 * Copyright 2010-2018 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 時間を取り扱うためのユーティリティクラス群。
 * 
 * <p>Java標準APIにおいて、現在時刻を取り扱うには、{@link java.util.Date}や{@link java.util.Calendar}、
 * {@link java.lang.System#currentTimeMillis()}、{@link java.lang.System#nanoTime()}等を利用する。
 * しかし、これらのクラスは必ずシステム時計を参照し、それ以外の値を返すことができない。従って、現在時刻に依存した
 * コードしか書けないので、その単体テストを書くことが困難な場合がある。さらに、NTPプロトコルを利用して、
 * 外部サーバの時計を参照することもできない。</p>
 * 
 * <p>このパッケージは、{@link jp.xet.baseunits.timeutil.Clock}という中心的なクラスを提供する。
 * 現在時刻を必ずこのクラスから取得することによって、テスト時に任意の時刻を返すように挙動を修正してテストを行うことができる。
 * また、NTPプロトコルのクライアントを提供し、外部サーバの時計を参照することができる。</p>
 */
package jp.xet.baseunits.timeutil;

