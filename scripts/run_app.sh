#!/bin/bash

VERBOSE=false
BUILD=false
DOCKER_CMD="docker"

check_docker_permissions() {
    if ! $DOCKER_CMD ps >/dev/null 2>&1; then
        echo "Error: Docker permission denied."
        echo "Solution: add user to docker group."
        exit 1
    fi
}

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --verbose|-v)
      VERBOSE=true
      ;;
    --build)
      BUILD=true
      ;;
    *)
      echo "Unknown option: $1"
      exit 1
      ;;
  esac
  shift
done

check_docker_permissions

run_command() {
  if [ "$VERBOSE" = true ]; then
    "$@"
  else
    "$@" > /dev/null 2>&1
  fi
}

if [ "$BUILD" = true ]; then
  echo "Building Docker images..."
  run_command $DOCKER_CMD compose build
fi

echo "Stopping and removing existing containers..."
run_command $DOCKER_CMD compose down

echo "Starting server and database..."
run_command $DOCKER_CMD compose up -d server mysql

echo "Waiting for the database to be ready..."
run_command ./scripts/wait-for-it.sh mysql:3306 --timeout=60

echo "Waiting for the server to be ready..."
run_command ./scripts/wait-for-it.sh server:8080 --timeout=60

echo "Starting client in a new terminal..."
if [ "$VERBOSE" = true ]; then
  gnome-terminal -- bash -c "$DOCKER_CMD compose run client; exec bash"
else
  gnome-terminal -- bash -c "$DOCKER_CMD compose run client 2>&1 | grep -v '^Creating\|^Starting\|^Attaching\|^ Container\|^Running\|^Healthy\|^wait-for-it.sh'; exec bash"
fi

echo "Server and database are running. Client is running in a separate terminal."