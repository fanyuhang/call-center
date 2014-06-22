@echo off
echo [INFO] Use maven jetty-plugin run the project.

cd %~dp0
cd ..

call mvn org.apache.cxf:cxf-codegen-plugin:wsdl2java

cd bin
pause