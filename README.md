AndroidRssParser
================

RSS parser designed as a library for Android >=2.1. The design goals
are a footprint as small as possible, RSS v2.0 compliance and, of
course, ease of use.

The library is built on top of the standard SAX parser provided with
Android.

###The parser library depends on
* AndroidApacheCommonsLang
    * [Apache Commons
      Lang](http://commons.apache.org/proper/commons-lang/) adaptation
      for Android
* AndroidVersionCompatibility
    * Compatibility tools for supporting older versions of Android
      when compiling against newer.

###Features
* All fields of Item objects are guaranteed to have empty values
  instead of nulls.
* Progress notification.

License
-------
Apache Commons Lang is licensed under [Apache License
v2.0](http://www.apache.org/licenses/LICENSE-2.0)

AndroidRssParser, AndroidRssParserTest and AndroidVersionCompatibility
are all three licensed under
[LGPLv3](http://www.gnu.org/licenses/lgpl.txt)

TODOS
-----
* Configuring: the library isn't very configurable atm
* Documentation
* Extensions
* Tests

Contributing
------------
Contributions are most welcome.

If you need to contact me, do it via GitHub or at yordan [at] 4web
[dot] bg
