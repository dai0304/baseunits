/**
 * 暦及び時間に関連するクラス群。
 * 
 * <p>{@link java.util.Date}や{@link java.util.Calendar}は、Java標準APIにおける基本的な日付・時刻に関する
 * 機能を提供するクラスである。しかし、単に暦日を表現したい場合であっても、本質的には時分秒精度の値を保持するもの
 * であり、例えば「同じ暦日を表現している」ことを検証する場合、注意深い実装が求められる。また、これらは
 * 可変(mutable)なクラスであり、慎重に扱わなければ他のクラスの内部表現を暴露してしまうことにも繋がる。</p>
 * 
 * <p>このパッケージでは、Java標準APIに不足している「時分秒以下の精度を持たない、単純な暦日を表すクラス」
 * ({@link jp.xet.baseunits.time.CalendarDate})をはじめ、「特定の瞬間を表す不変(immutable)クラス」
 * ({@link jp.xet.baseunits.time.TimePoint})、「期間（暦日や瞬間の範囲）を表すクラス」
 * ({@link jp.xet.baseunits.time.CalendarInterval}や{@link jp.xet.baseunits.time.TimePointInterval})、
 * 「時間量を表すクラス」({@link jp.xet.baseunits.time.Duration})等の基本クラスを提供する。</p>
 * 
 * <p>このパッケージを理解するにあたっては、
 * {@link jp.xet.baseunits.time.DayOfMonth 日}と{@link jp.xet.baseunits.time.CalendarDate 暦日}、
 * {@link jp.xet.baseunits.time.MonthOfYear 月}と{@link jp.xet.baseunits.time.CalendarMonth 暦月}、
 * {@link jp.xet.baseunits.time.WeekOfYear 週}と{@link jp.xet.baseunits.time.CalendarWeek 暦週}、
 * の違いについて、頭を整理すること。</p>
 */
package jp.xet.baseunits.time;

