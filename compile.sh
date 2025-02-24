#!/bin/bash

# Create output directory

mkdir -p out

# Compile all java files
javac -d out $(find src/main/java -name "*.java")
