#!/bin/bash

# Use Maven to compile the project (includes all dependencies)
echo "Compiling with Maven..."
mvn clean compile

# Execute the compiled Java application with Main class
echo "Executing Java application..."
mvn exec:java -Dexec.mainClass="Main"
