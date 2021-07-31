docker pull openjdk:11 && mvnw clean package && docker build -t payroll-application . && docker run -d -p 8080:8080 payroll-application

