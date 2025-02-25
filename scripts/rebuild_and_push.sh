#!/bin/bash

if [ "$#" -lt 3 ]; then
  echo "Usage: $0 <dockerhub_username> <dockerhub_repository> <tag>"
  echo "Example: $0 myusername myrepo v1.0.0"
  exit 1
fi

DOCKER_HUB_USERNAME="$1"
DOCKER_HUB_REPOSITORY="$2"
TAG="$3"

run_command() {
  echo "➜ $*"
  "$@"
  if [ $? -ne 0 ]; then
    echo "Error: Command failed."
    exit 1
  fi
}

echo "Building Docker images..."
run_command docker compose build

SERVICES=("server" "client")

for SERVICE in "${SERVICES[@]}"; do
  IMAGE_NAME="${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}-${SERVICE}:${TAG}"

  echo "Tagging ${SERVICE} image as ${IMAGE_NAME}..."
  run_command docker tag "${SERVICE}" "${IMAGE_NAME}"

  echo "Pushing ${IMAGE_NAME} to Docker Hub..."
  run_command docker push "${IMAGE_NAME}"
done

echo "All images have been rebuilt and pushed to Docker Hub."