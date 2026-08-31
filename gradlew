#!/usr/bin/env sh
set -e
APP_HOME=$(cd "$(dirname "$0")"; pwd)
exec java -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@"
