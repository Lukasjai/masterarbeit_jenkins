#!/bin/bash

set -e

ENV=$1

if [ -z "$ENV" ]; then
  echo "No environment set. use: ./deploy.sh [dev|test|prod]"
  exit 1
fi

if [ -z "$APP_NAME" ]; then
  echo "APP_NAME environment variable not set!"
  exit 1
fi

echo "Set Heroku-Stack to 'container' "
heroku stack:set container --app "$APP_NAME"

echo " Deploying to Heroku environment: $ENV (App: $APP_NAME)"

# Tag and Push of Docker-Images
docker tag Lukasjai/masterarbeit_jenkis:latest registry.heroku.com/$APP_NAME/web
docker push registry.heroku.com/$APP_NAME/web

# trigger Heroku-Release
heroku container:release web --app $APP_NAME

echo "Deployment successful for $ENV  → $APP_NAME"