/**
 * 時間及び金額にまつわるドメインモデル群。
 * 
 * <dl>
 *   <dt>基礎的なインフラを整えるパッケージ</dt>
 *   <dd>util, intervals</dd>
 *   
 *   <dt>時間にまつわるドメインモデルを提供するパッケージ</dt>
 *   <dd>time, timeutil</dd>
 *   
 *   <dt>金額にまつわるドメインモデルを提供するパッケージ</dt>
 *   <dd>money</dt>
 * </dl>
 * 
 * <h4>開発メモ</h4>
 * 
 * <ul>
 *   <li>asは特化、つまりダウンキャスト。情報を失わない。</li>
 *   <li>toは凡化、つまりアップキャスト。情報を失う。</li>
 * </ul>
 */
package jp.xet.baseunits;

