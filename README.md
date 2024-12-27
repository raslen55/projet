DevOps Project: CI/CD Pipeline
Project Overview
This project demonstrates a fully automated CI/CD pipeline using the following DevOps tools:

Jenkins: Automates the build, test, and deployment processes.
SonarQube: Performs static code analysis for code quality and security vulnerabilities.
Nexus Repository: Hosts project dependencies and artifacts.
Maven: Builds and manages dependencies for Java-based projects.
Docker: Containerizes the application for consistent and scalable deployments.
Mockito: Provides unit testing for verifying code functionality.
The pipeline is designed to automate the development lifecycle by integrating the tools and ensuring code quality, artifact management, and containerized delivery.

Pipeline Workflow
Stages
Checkout GIT:

Pull the source code from a Git repository (GitHub or GitLab).
MVN Clean:

Use Maven to clean up previously compiled files and prepare for a fresh build.
MVN Compile:

Use Maven to compile the code and resolve dependencies.
SonarQube Analysis:

Analyze the code with SonarQube for code quality and security checks.
Test with Mockito:

Use Mockito to execute unit tests and verify code functionality.
Deploy:

Deploy built artifacts to the Nexus Repository for future use.
Build Docker Image:

Containerize the application using Docker and tag it.
Push Docker Image to Local Registry:

Push the Docker image to a Docker registry (e.g., Docker Hub or Nexus).
Run Docker Compose:

Run the Docker image using docker-compose to deploy the application.
Prerequisites
Installed Tools:

Jenkins
Docker and Docker Compose
SonarQube
Nexus Repository Manager
Maven
Java Development Kit (JDK 8+)
Mockito (as part of your test dependencies)
Docker Images:

SonarQube: sonarqube:latest
Nexus Repository: sonatype/nexus3:latest
Application Image: Customized for your project.
Configuration:

Ensure Jenkins is configured with the following plugins:
Maven Integration
SonarQube Scanner
Docker Pipeline Plugin
Integrate Jenkins with SonarQube and Nexus.
Setup Instructions
1. Clone the Repository
bash
Copier le code
git clone https://github.com/your-repository/project.git
cd project
2. Build the Project
Run the following command to build the project and generate a .jar file:

bash
Copier le code
mvn clean install
3. Run SonarQube
Run SonarQube locally:

bash
Copier le code
docker run -d --name sonarqube -p 9000:9000 sonarqube
Access the SonarQube dashboard at http://localhost:9000.

4. Run Nexus Repository
Run Nexus Repository:

bash
Copier le code
docker run -d --name nexus -p 8081:8081 sonatype/nexus3
Access the Nexus dashboard at http://localhost:8081.

5. Run Jenkins
Start Jenkins with Docker:

bash
Copier le code
docker run -d --name jenkins -p 8080:8080 -p 50000:50000 jenkins/jenkins:lts
Access Jenkins at http://localhost:8080.

6. Run the Application
Run the application using Docker Compose:

bash
Copier le code
docker-compose up -d


Pipeline Configuration
Jenkinsfile
Below is our Jenkinsfile used in this project:


Copier le code
pipeline {
    agent any
    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling code from private repo'
                git branch: 'main',
                    url: 'https://github.com/raslen55/projet.git'
                    
            }
        }
        stage('MVN clean') {
            steps {
                echo 'Cleaning project'
                sh 'mvn clean'
            }
        }
        stage('MVN compile') {
            steps {
                echo 'Compiling project'
                sh 'mvn compile'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    sh '''
                        mvn sonar:sonar \
                            -Dsonar.login=$SONAR_TOKEN
                    '''
                }
                echo 'SonarQube analysis has been completed.'
            }
        }
        stage('Test with Mockito') {
            steps {
                echo 'Running unit tests with Mockito'
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying project'
                sh 'mvn deploy -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image'
                sh 'docker build -t raslen166/tp-foyer-bloc:latest .'
                
            }
        }
        stage('Push Docker Image to Local Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh '''
                        echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                        docker push raslen166/tp-foyer-bloc:latest
                    '''
                }
            }
        }

        
        stage('Run Docker Compose') {
            steps {
                echo 'Running Docker Compose'
                sh 'docker-compose up -d'
            }
        }
    }
}




Docker Compose File
Here’s our docker-compose.yml file:

yaml
Copier le code
version: "3.8"

services:
  mysqldb:
    image: mysql:5.7
    container_name: mysqldb
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: foyer_db
    ports:
      - "3306:3306"
    networks:
      - network
    volumes:
      - data-volume-bloc:/var/lib/mysql

  app-foyer:
    depends_on:
      - mysqldb
    image: raslen166/tp-foyer-bloc:latest
    container_name: app-foyer
    restart: on-failure
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysqldb:3306/foyer_db?createDatabaseIfNotExist=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
      SPRING_DATASOURCE_DRIVER_CLASS_NAME: com.mysql.cj.jdbc.Driver
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT: org.hibernate.dialect.MySQLDialect
    ports:
      - "8082:8082"
    networks:
      - network
    stdin_open: true
    tty: true

volumes:
  data-volume-bloc:

networks:
  network:
    driver: bridgeEndpoints

Endpoints ; 
Jenkins: http://192.168.137.200:8080
SonarQube: http://192.168.137.200:9000
Nexus Repository: http://192.168.137.200:8081
Application: http://192.168.137.200:8082
