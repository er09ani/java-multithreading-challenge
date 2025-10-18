@echo off
echo === Running Performance Tests with Maximum CPU Utilization ===
echo Available CPU cores: %NUMBER_OF_PROCESSORS%

echo.
echo === Running tests with parallel execution ===
gradlew :shared-counter:test --parallel --max-workers=%NUMBER_OF_PROCESSORS% --info

echo.
echo === Running tests with JVM optimization ===
gradlew :shared-counter:test -Dorg.gradle.parallel=true -Dorg.gradle.workers.max=%NUMBER_OF_PROCESSORS% --info

echo.
echo === Running tests with custom JVM args ===
gradlew :shared-counter:test -Dorg.gradle.jvmargs="-Xmx4g -XX:+UseG1GC -XX:+UseStringDeduplication" --info

echo.
echo === Performance test completed ===
pause
