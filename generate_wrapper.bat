@echo off
REM generate_wrapper.bat - convenience script to generate/use Gradle wrapper and build/run the project (Windows)
REM Usage:
REM   generate_wrapper.bat         -> build then bootRun (default)
REM   generate_wrapper.bat build   -> run only build
REM   generate_wrapper.bat run     -> run only bootRun (assumes build already done)
REM   generate_wrapper.bat test    -> run tests

setlocal enabledelayedexpansion

:: determine requested action
set ACTION=%1%
if "%ACTION%"=="" set ACTION=buildAndRun

echo Action: %ACTION%

:: helper to run command and exit on failure
def :runAndCheck
  %*
  if ERRORLEVEL 1 (
    echo.
    echo Command failed with exit code %ERRORLEVEL%.
    exit /b %ERRORLEVEL%
  )
  goto :eof

:: If wrapper exists, use it
if exist "%~dp0gradlew.bat" (
  echo Found Gradle wrapper: gradlew.bat
  set WRAPPER="%~dp0gradlew.bat"
) else (
  echo Gradle wrapper not found. Checking for local Gradle installation...
  gradle -v >nul 2>&1
  if ERRORLEVEL 1 (
    echo Gradle is not installed and wrapper is missing.
    echo You have two options:
    echo  1) Install Gradle and re-run this script, OR
    echo  2) Run 'gradle wrapper' on a machine with Gradle to generate the wrapper files, then commit them.
    echo
    echo Example (if Gradle installed):
    echo    gradle wrapper
    echo Then run this script again.
    exit /b 1
  ) else (
    echo Gradle detected. Generating Gradle wrapper...
    gradle wrapper
    if ERRORLEVEL 1 (
      echo Failed to generate Gradle wrapper.
      exit /b 1
    )
    if exist "%~dp0gradlew.bat" (
      echo Wrapper generated.
      set WRAPPER="%~dp0gradlew.bat"
    ) else (
      echo Wrapper generation did not produce gradlew.bat in the project root.
      echo You can still run with your local Gradle:
      set WRAPPER=gradle
    )
  )
)

:: Normalize WRAPPER (remove quotes for checks)
set WRAPPER_NOQ=%WRAPPER:~1,-1%
if "%WRAPPER_NOQ%"=="%WRAPPER%" set WRAPPER_NOQ=%WRAPPER%

echo Using: %WRAPPER_NOQ%

:: Execute requested action
if /I "%ACTION%"=="build" (
  call %WRAPPER_NOQ% build
  if ERRORLEVEL 1 exit /b %ERRORLEVEL%
  echo Build completed.
  exit /b 0
)

if /I "%ACTION%"=="run" (
  call %WRAPPER_NOQ% bootRun
  exit /b %ERRORLEVEL%
)

if /I "%ACTION%"=="test" (
  call %WRAPPER_NOQ% test
  exit /b %ERRORLEVEL%
)

:: Default: build then run
echo Running build then bootRun...
call %WRAPPER_NOQ% build
if ERRORLEVEL 1 exit /b %ERRORLEVEL%
call %WRAPPER_NOQ% bootRun
exit /b %ERRORLEVEL%
