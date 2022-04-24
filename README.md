Versión de Java 17

For building, run:  ./gradlew build

Execute the task googleJavaFormat to format all *.java files in the project:
./gradlew goJF

Execute the task verifyGoogleJavaFormat to verify that all *.java files are formatted properly
./gradlew verGJF

For the linter, gradlew checkstyleMain

For generating the report, jacocoTestReport