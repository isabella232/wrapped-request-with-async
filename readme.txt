
Example of AsyncContext on Jetty with wrapped request and Rewrite rules.

This project uses maven.
This checkout is a valid ${jetty.base} directory too.

To build:

  $ mvn clean install

  This should have created a webapps/asyncdemo.war file for you

To prepare your jetty.base:

  $ java -jar /path/to/jetty-home-9.4.6.v20170531/start.jar --create-files

To run using installed jetty.home:

  $ cd /path/to/wrapped-request-with-async
  $ java -jar /path/to/jetty-home-9.4.6.v20170531/start.jar

To test:

  * Open http://localhost:8080/asyncdemo/ in your web browser
  * Look at your log output too


