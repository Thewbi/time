# time
A webapp that lets you keep track of working hours you have spend on tasks

## Usage
The webapp allows you to create projects, that contain task. Tasks contain durations. You use durations to track the time that you spent working on tasks in projects. A weekly report can be created from the projects page. That report is a page in the webapp that simply lists all the durations that overlap with the current workweek (Monday - Sunday).

## maven build
The packaging has to be war, as the application is a traditional web app and therefore has to have a WEB-INF folder containing the JSP-views. The packaging jar does not create a WEB-INF folder.

## Creating a docker image
The docker image contains a tomcat and deploys the war file into the tomcat.

```
mvn install
mvn dockerfile:build
```

## URL
[http://localhost:8080/time-0.0.1-SNAPSHOT/projects](http://localhost:8080/time-0.0.1-SNAPSHOT/projects)

## AWS Elastic Beanstalk Deployment
Create a tomcat environment and upload the war file. 
After the environment has started up, the /projects URL should serve the project JSP page.