#!/bin/bash

if [ "$1" != "--no-build" ]; then
  echo "Building Docker images..."
  docker-compose build
fi

echo "Stopping and removing existing containers..."
docker-compose down

echo "Starting server and database..."
docker-compose up -d server mysql

if ! docker-compose ps | grep mysql | grep Up; then
  echo "Waiting for the database to be ready..."
  ./wait-for-it.sh mysql:3306 --timeout=60 -- echo "Database is ready!"
fi

if ! docker-compose ps | grep server | grep Up; then
  echo "Waiting for the server to be ready..."
  ./wait-for-it.sh server:8080 --timeout=60 -- echo "Server is ready!"
fi

echo "Starting client in a new terminal..."
gnome-terminal -- bash -c "docker-compose run client; exec bash"

echo "Server and database are running. Client is running in a separate terminal."