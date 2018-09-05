SET ProgFiles86Root=%ProgramFiles(x86)%
IF NOT "%ProgFiles86Root%"=="" GOTO amd64
:amd64
:: Set Variables 
set ANT_HOME=%~dp0%Resources\ant
set PATH=%PATH%;%ANT_HOME%\bin;%~dp0%Resources\allure-cli\bin
set JDK="%ProgramFiles%\Java\jdk*"
for /d %%i in (%JDK%) do set JAVA_HOME=%%i
:: Display Variables and Launch Ant
set JAVA_HOME
echo %ANT_HOME%
echo %PATH%
Call ant -f Build_SouthWest_PreProd.xml -lib %ANT_HOME%\lib
pause