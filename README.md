Transaction tracker API using spring boot

usage:
* docker build -t transaction-tracker-api .
* docker run -p 8080:8080 --link transaction-tracker-database-mysql:mysql -d -rm transaction-tracker-api
