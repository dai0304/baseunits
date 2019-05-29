# BaseUnits Library

基礎的な単位にまつわるドメインモデル群を含むクラスライブラリ。現在は「時間」と「金額」を主に扱っているが、
必要に応じて要望があれば「重さ」「長さ」などのドメインも取り扱っていく。

require Java 8 or later.

## How to use?

If you're using Maven, add this to your pom.xml under the <repositories> and <dependencies> section:

### v2.16 or later

* repositories: jcenter
* groupId: `jp.xet`
* artifactId: `baseunits`

see <https://bintray.com/dai0304/maven/baseunits>

### v2.0〜2.15

* repositories
    * [Maven release repository](http://maven.xet.jp/release/)
    * [Maven snapshot repository](http://maven.xet.jp/snapshot/)
* groupId: `jp.xet`
* artifactId: `baseunits`


## Example

```java
// 日付の仕様。ある日付がその仕様を満たすかどうか、とかチェックできる
DateSpecification mlkBirthday = DateSpecification.fixed(1, 15);
// こういう風に。ここではマーチンルーサーキングの誕生日。
CalendarDate jan15_2005 = CalendarDate.from(2005, 1, 15);
assertThat(mlkBirthday.isSatisfiedBy(jan15_2005), is(true));
    
// 2005年(期間)の中で、キング牧師の誕生日を引っ張って来る（仕様を満たす日を探す）
CalendarDate mlk2005 = mlkBirthday.firstOccurrenceIn(CalendarInterval.year(2005));
assertThat(mlk2005, is(jan15_2005));
    
// 誕生日と逝去日の期間をつくって、その範囲で使用を満たす日付を順次取得するイテレータ
CalendarInterval mlkLifetime = CalendarInterval.inclusive(1929, 1, 15, 1968, 4, 4);
Iterator<CalendarDate> mlkBirthdays = mlkBirthday.iterateOver(mlkLifetime);
assertThat(mlkBirthdays.next(), is(CalendarDate.from(1929, 1, 15)));
assertThat(mlkBirthdays.next(), is(CalendarDate.from(1930, 1, 15)));
    
// っていうか、そもそもキング牧師って何日間生きたの？
assertThat(mlkLifetime.length(), is(Duration.days(14325)));
```

## License

* Copyright (C) 2011-2019 [Daisuke Miyamoto](http://d.hatena.ne.jp/daisuke-m/)
* Copyright (C) 2010-2011 TRICREO, Inc.
* Copyright (c) 2004 [Domain Language, Inc.](http://domainlanguage.com)

Distributed under the Apache License v2.0.  See the file copyright/LICENSE.txt.


