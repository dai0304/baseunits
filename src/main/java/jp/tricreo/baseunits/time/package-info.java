/**
 * 時間を表すクラスを中心としたクラス群。
 * 
 * <p>{@link java.util.Date}や{@link java.util.Calendar}は、Java標準APIにおける基本的な日付・時刻に関する
 * 機能を提供するクラスである。しかし、単に日付を表現したい場合であっても、本質的には時分秒精度の値を保持するもの
 * であり、例えば「同じ日付を表現している」ことを検証する場合、注意深い実装が求められる。また、これらは
 * 可変(mutable)なクラスであり、慎重に扱わなければ他のクラスの内部表現を暴露してしまうことにも繋がる。</p>
 * 
 * <p>このパッケージでは、Java標準APIに不足している「時分秒以下の精度を持たない、単純な日付を表すクラス」
 * ({@link jp.tricreo.baseunits.time.CalendarDate})をはじめ、「特定の瞬間を表す不変(immutable)クラス」
 * ({@link jp.tricreo.baseunits.time.TimePoint})、「期間（時間の範囲）を表すクラス」
 * ({@link jp.tricreo.baseunits.time.CalendarInterval}や{@link jp.tricreo.baseunits.time.TimeInterval})、
 * 「時間量を表すクラス」({@link jp.tricreo.baseunits.time.Duration})等の基本クラスを提供する。</p>
 */
package jp.tricreo.baseunits.time;

