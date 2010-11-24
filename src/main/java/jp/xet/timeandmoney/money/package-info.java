/**
 * 金額を表すクラスを中心としたクラス群。
 * 
 * <p>Java標準APIの範囲内で、金額は{@link java.util.Currency}と数値型で表現する。
 * 金額に対する演算をする際に、通貨単位が一致していることや、丸め処理への対応、
 * 通貨単位が持つ最低単位（日本円は1円、USドルは1セント=0.01ドルなど）を意識する必要があり、煩雑である。</p>
 * 
 * <p>このパッケージは、金額を表す{@link jp.xet.timeandmoney.money.Money}クラスを中心に、
 * その演算を行うための各種クラス群を提供する。</p>
 */
package jp.xet.timeandmoney.money;

