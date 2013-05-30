#!/bin/bash

mvn clean
mvn package -Dmaven.test.skip=true
mvn exec:java -Dexec.mainClass=edu.mines.acmX.exhibit.module_manager.ModuleManager
