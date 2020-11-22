#!/bin/bash

# Import .env
source .env

docker-compose -f docker/docker-compose.yml up --build -d
