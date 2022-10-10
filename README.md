# FanCodeProject
used TestNG Testing Framework with Page Object Model Design and Maven as a build tool
src/main/java/responsepayload - contains the response Pojo of ToDOs, Users, Address, Geo location 
src/main/java/config - contains the Configuration like api endpoint 
src/test/java - contains the testng test classes of Todos and Users
userstest is depends on todostest class to get the completed task list
testng.xml - Is the testng xml used to run the test classes
You can run the test classes directly or by using testng.xml file
Assumption:
Latitude negative value is compared close to 100 e.g -37 is less than -40
