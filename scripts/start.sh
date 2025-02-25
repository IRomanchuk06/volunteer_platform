#!/bin/bash

if [ "$1" != "--no-build" ]; then
  echo "Building Docker images..."
  docker-compose build
fi

echo "Stopping and removing existing containers..."
docker-compose down > /dev/null 2>&1

echo "Starting server and database..."
docker-compose up -d server mysql > /dev/null 2>&1

echo "Waiting for the database to be ready..."
./scripts/wait-for-it.sh mysql:3306 --timeout=60 > /dev/null 2>&1

echo "Waiting for the server to be ready..."
./scripts/wait-for-it.sh server:8080 --timeout=60 > /dev/null 2>&1

echo "Starting client in a new terminal..."
gnome-terminal -- bash -c "docker-compose run client 2>&1 | grep -v '^Creating\|^Starting\|^Attaching\|^ Container\|^Running\|^Healthy\|^wait-for-it.sh'; exec bash"

echo "Server and database are running. Client is running in a separate terminal."