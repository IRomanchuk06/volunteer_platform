#!/bin/bash

VERBOSE=false

if [[ "$1" == "--verbose" || "$1" == "-v" ]]; then
  VERBOSE=true
fi

run_command() {
  if [ "$VERBOSE" = true ]; then
    "$@"
  else
    "$@" > /dev/null 2>&1
  fi
}

if [[ "$1" != "--no-build" && "$1" != "--verbose" && "$1" != "-v" ]]; then
  echo "Building Docker images..."
  run_command sudo docker compose build
fi

echo "Stopping and removing existing containers..."
run_command sudo docker compose down

echo "Starting server and database..."
run_command sudo docker compose up -d server mysql

echo "Waiting for the database to be ready..."
run_command ./scripts/wait-for-it.sh mysql:3306 --timeout=60

echo "Waiting for the server to be ready..."
run_command ./scripts/wait-for-it.sh server:8080 --timeout=60

echo "Starting client in a new terminal..."
if [ "$VERBOSE" = true ]; then
  gnome-terminal -- bash -c "sudo docker compose run client; exec bash"
else
  gnome-terminal -- bash -c "sudo docker compose run client 2>&1 | grep -v '^Creating\|^Starting\|^Attaching\|^ Container\|^Running\|^Healthy\|^wait-for-it.sh'; exec bash"
fi

echo "Server and database are running. Client is running in a separate terminal."