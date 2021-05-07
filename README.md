# Revature Bank App

- [Database Design](#database-design)
- [User Flow Chart](#user-flow-chart)
- [How To Use](#how-to-use)
<!---
<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>
--->

<!---
## Project Evaluation Parameters
1) Your project will be evaluated based on coverage of technology rather than the user stories.
2) How well you present, naming conventions, separation of code into various classes, how well have you applied OOP and design patterns.
3) So you could focus less on user stories and more on coverage of things/technologies which you learnt during the training.
--->

## Database Design

![](images/DBChart.png)

## User Flow Chart

![](images/FunctionalFlowChart.png)

## How To Use
In order to use this application on your local machine, you must have JDK/JRE and utalize as a maven project. You will need to create a config.properties file that contains properties DB_USER : your database username("postgre" in my case), DB_URL: your database jdbc url(localhost url in my case), and DB_PASSWORD : what you defined your database password as. Then change the CONFIG_FILE_PATH to the location of you config.properties.

Then you must intialize your database using the sql file found in the resources folder as a schema called bank.

Once everything is changed to your specific paths, you can then use the application in the console by running the BankUI.
