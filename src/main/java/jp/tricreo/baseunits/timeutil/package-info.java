/**
 * 時間を取り扱うためのユーティリティクラス群。
 * 
 * <p>Java標準APIにおいて、現在時刻を取り扱うには、{@link java.util.Date}や{@link java.util.Calendar}、
 * {@link java.lang.System#currentTimeMillis()}、{@link java.lang.System#nanoTime()}等を利用する。
 * しかし、これらのクラスは必ずシステム時計を参照し、それ以外の値を返すことができない。従って、現在時刻に依存した
 * コードしか書けないので、その単体テストを書くことが困難な場合がある。さらに、NTPプロトコルを利用して、
 * 外部サーバの時計を参照することもできない。</p>
 * 
 * <p>このパッケージは、{@link jp.tricreo.baseunits.timeutil.Clock}という中心的なクラスを提供する。
 * 現在時刻を必ずこのクラスから取得することによって、テスト時に任意の時刻を返すように挙動を修正してテストを行うことができる。
 * また、NTPプロトコルのクライアントを提供し、外部サーバの時計を参照することができる。</p>
 */
package jp.tricreo.baseunits.timeutil;

