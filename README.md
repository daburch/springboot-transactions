Spring boot RESTful API for adding transactions to a database

usage: 
* docker build -t springboot-transactions-api
* docker run -p 8080:8080 --link mysql:mysql -d springboot-transactions-api 