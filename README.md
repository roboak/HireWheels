# Steps to Start the Application

1. Import the Project in the IntelliJ IDE
2. Configure the database connection properties in the application.properties file
3. Run the Project

# Note 1

When the project is being executed for the first time, make sure that the code calling the initService in the HireWheelsApplication.java file is uncommented.
This will call the initService which will pre-populate some of the tables in the database.

# Note 2

If you have successfully run the project at least once on your IDE, comment out the initService method call in the HireWheelsApplication.java file. Otherwise, the code will try to re-enter the same data in the same table which may lead to having duplicate entries in the tables and in some cases might lead to several errors due to unique constraint violation.

# Pre-Populated tables and their values

The following link contains information about the tables which will be pre-populated by the initService

https://docs.google.com/document/d/15dMhSPcWLzT29eQyOsNLL9IjEOhhiQBzsrDiBlNE254/edit?usp=sharing

# API Documentation

https://documenter.getpostman.com/view/5716890/T1Dv9FUC?version=latest

