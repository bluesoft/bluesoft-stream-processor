#!/bin/bash

mvn release:clean
mvn -B release:prepare
mvn release:perform