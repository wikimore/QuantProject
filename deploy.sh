#!/bin/bash

clear

git init

git add QuantProject-all-1.0.jar

git add config.properties

git commit -m "Here is the Jar"

git checkout -b test-deployment

git checkout test-deployment

git remote add origin https://github.com/LittleHouses/QuantProject.git

git push origin test-deployment