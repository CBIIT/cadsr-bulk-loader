@ECHO OFF

set CLASSPATH=.

for %%x in (*.jar) do call :appendCP %%x

for %%x in (lib/*.jar) do call :appendCP lib/%%x

java -cp %CLASSPATH% gov.nih.nci.ncicb.cadsr.bulkloader.ui.CommandLineProcessor

GOTO :end

:appendCP
SET CLASSPATH=%CLASSPATH%;%1
GOTO :end

:end