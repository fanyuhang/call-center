@echo off
echo [INFO] compile and install.

cd %~dp0
cd ..

call mvn install:install-file -Dfile=lib/castor-1.2.jar -DgroupId=org.codehaus.castor -DartifactId=castor-xml -Dversion=1.2 -Dpackaging=jar

call mvn install:install-file -Dfile=lib/jta-1_0_1B-classes.zip -DgroupId=jta -DartifactId=jta -Dversion=1.0.1b -Dpackaging=jar

call mvn clean install 

cd bin
pause
