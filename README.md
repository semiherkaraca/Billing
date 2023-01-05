# Billing Project 

First of all, we must run the commands in the docker-compose.yml file under the docs folder.

You can use this command for this:  docker-compose up

then you can use the following command to get the project up and running (For this, you need to be in the folder where the jar file is):

java -DINVOICE_LIMIT=200 -jar billingapplication-0.0.1-SNAPSHOT.jar

Here the limit value is given as 200, you can give what you want.

You can view the urls you can use from the address below after you have the project up and running.

http://localhost:8080/swagger-ui.html#/
