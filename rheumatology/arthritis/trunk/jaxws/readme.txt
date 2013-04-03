This is a maven project for generating JAXWS binding classes (jsr-181).
-----------------------------------------------------------------------
The purpose is to give producer- and consumer projects that use Java and Maven a head-start for setting up the build system to generate JAX-WS binding classes for the this service domain.

The maven build uses the cxf wsdl2java tool. The generated binding classes will be fine to use with whatever JAXWS2 webstack you are using in runtime. This is guaranteed by the JSR-181, which defines exactly how the classes should be generated from wsdl and service schemas. There are several problems with the corresponding tool supplied by the JDK (the metro wsimport tool).

How to build the jaxws scheduling project:

1) Install the maven build automation tool (http://maven.apache.org)
2) Open a command prompt in the directory of this file
3) Run the following command: 
   mvn clean package

You will now have the generated java classes compiled and packaged into the following jar file: target/rheumatology-arthritis-schemas-1.0-SNAPSHOT.jar. The jar file is ready to be used in your project as a jar dependency.
