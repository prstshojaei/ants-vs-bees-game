# JUnit Testing Guide

## Overview

JUnit tests in this project are executed using the **JUnit Platform Console Standalone JAR** instead of Maven.

---

## Test Setup

The following library is used to run the tests:

```
lib/junit-platform-console-standalone-6.0.3.jar
```

All test files are located in:

```
src/tests/
```

---

## Step 1: Compile the Project

Before running any tests, compile all source and test files:

```bash
javac -cp "lib/junit-platform-console-standalone-6.0.3.jar" -d out src/core/*.java src/ants/*.java src/tests/*.java
```
alt version for pointing at javafx library
```bash
javac -cp "lib/junit-platform-console-standalone-6.0.3.jar;C:\Users\Public\javafx-sdk-25.0.1\lib\*" -d out src/core/*.java src/ants/*.java src/tests/*.java
```

alt version for pointing at javafx library - Parastoo
```bash
javac -cp "lib/junit-platform-console-standalone-6.0.3.jar;D:\Downloads\apps\openjfx-25.0.2_windows-x64_bin-sdk\javafx-sdk-25.0.2\lib\*" -d out src/core/*.java src/ants/*.java src/tests/*.java
```
---

## Step 2: Run Individual Tests

### AntColonyTest

```bash
java -jar lib/junit-platform-console-standalone-6.0.3.jar execute --class-path out --select-class tests.AntColonyTest
```

### BeeTest

```bash
java -jar lib/junit-platform-console-standalone-6.0.3.jar execute --class-path out --select-class tests.BeeTest
java -cp "out;C:\Users\Public\javafx-sdk-25.0.1\lib\*;lib/junit-platform-console-standalone-6.0.3.jar" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
```
```bash
javac -cp "lib/junit-platform-console-standalone-6.0.3.jar;C:\Users\Public\javafx-sdk-25.0.1\lib\*" -d out src/core/*.java src/ants/*.java src/tests/*.java
```

alt version for pointing at javafx library - Parastoo
```bash
javac -cp "lib/junit-platform-console-standalone-6.0.3.jar;D:\Downloads\apps\openjfx-25.0.2_windows-x64_bin-sdk\javafx-sdk-25.0.2\lib\*" -d out src/core/*.java src/ants/*.java src/tests/*.java
```
---

## Run All Tests

Paddy
```bash
java -cp "out;C:/Users/Public/javafx-sdk-25.0.1/lib/*;lib/junit-platform-console-standalone-6.0.3.jar" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
```

Run All Tests - Parastoo

```bash
java -cp "out;<YOUR_JAVAFX_PATH>/lib/*;lib/junit-platform-console-standalone-6.0.3.jar" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
```

## Run All Tests - Parastoo

```bash
java -cp "out;D:\Downloads\apps\openjfx-25.0.2_windows-x64_bin-sdk\javafx-sdk-25.0.2\lib\*;lib\junit-platform-console-standalone-6.0.3.jar" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
```
```bash paddy
java -cp "out;C:/Users/Public/javafx-sdk-25.0.1/lib/*;lib\junit-platform-console-standalone-6.0.3.jar" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
```
---

