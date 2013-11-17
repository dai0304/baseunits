BaseUnits Library
==================

基礎的な単位にまつわるドメインモデル群を含むクラスライブラリ。現在は「時間」と「金額」を主に扱っているが、
必要に応じて要望があれば「重さ」「長さ」などのドメインも取り扱っていく。

Travis CI build status
----------------------

[![Build Status](https://travis-ci.org/dai0304/baseunits.png?branch=develop)](https://travis-ci.org/dai0304/baseunits)

Basic information
-----------------

- [Project web site](http://maven.xet.jp/site/baseunits/)
- [Maven release repository](http://maven.xet.jp/release/)
- [Maven snapshot repository](http://maven.xet.jp/snapshot/)


How to use?
-----------

If you're using Maven, add this to your pom.xml under the <repositories> and <dependencies> section:

v2.x or later

    <project>
      ...
      <repositories>
        ...
        <repsoitory>
          <id>maven.xet.jp-release</id>
          <url>http://maven.xet.jp/release/</url>
        </repository>
        ...
      </repositories>
      ...
      <dependencies>
        ...
        <dependency>
          <groupId>jp.xet</groupId>
          <artifactId>baseunits</artifactId>
          <version>LATEST</version>
        </dependency>
        ...
      </dependencies>
      ...
    </project>

v1.x

    <project>
      ...
      <repositories>
        ...
        <repsoitory>
          <id>tricreo-release</id>
          <name>TRICREO Release Repository</name>
          <url>http://maven.tricreo.jp/release/</url>
        </repository>
        ...
      </repositories>
      ...
      <dependencies>
        ...
        <dependency>
          <groupId>jp.tricreo</groupId>
          <artifactId>baseunits</artifactId>
          <version>LATEST</version>
        </dependency>
        ...
      </dependencies>
      ...
    </project>


Example
-------

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



How to develop?
---------------

1. Eclipse (3.6 Helios or later)
2. m2eclipse (1.0.0.20110607-2117 or later)
3. Checkstyle Plug-in (5.1.0 or later)
4. FindBugs Plug-in (1.3.9 or later)

TBD

License
-------

Copyright (C) 2011-2012 [Daisuke Miyamoto](http://d.hatena.ne.jp/daisuke-m/)

Copyright (C) 2010-2011 TRICREO, Inc.

Copyright (c) 2004 [Domain Language, Inc.](http://domainlanguage.com)

Distributed under the Apache License v2.0.  See the file copyright/LICENSE.txt.


