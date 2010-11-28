Basicunits Library
==================


Basic information
-----------------

- [Project web site](http://earth.tricreo.jp/maven2-site/basicunits/latest/)
- [Maven release repository](http://earth.tricreo.jp/maven2/)
- [Maven snapshot repository](http://earth.tricreo.jp/maven2-snapshot/)


How to use?
-----------

If you're using Maven, add this to your pom.xml under the <repositories> and <dependencies> section:

    <project>
      ...
      <repositories>
        ...
        <repsoitory>
          <id>tricreo-release</id>
          <name>TRICREO Release Repository</name>
          <url>http://earth.tricreo.jp/maven2/</url>
        </repository>
        ...
      </repositories>
      ...
      <dependencies>
        ...
        <dependency>
          <groupId>jp.tricreo</groupId>
          <artifactId>basicunits</artifactId>
          <version>LATEST</version>
        </dependency>
        ...
      </dependencies>
      ...
    </project>

How to develop?
---------------

1. Eclipse (3.6 Helios or later)
2. m2eclipse (0.10.2 or later)
3. Checkstyle Plug-in (5.1.0 or later)
4. FindBugs Plug-in (1.3.9 or later)

TBD

License
-------

Copyright (C) 2010 [TRICREO, Inc.](http://tricreo.jp/).

Distributed under the Apache License v2.0. See the file copyright/LICENSE.txt.
