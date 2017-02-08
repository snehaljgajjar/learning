#!/bin/bash

param=""
for var in "$@"
do
    if [ -z "$param" ]; then
        param="'$var'"
    else
        param="$param, '$var'"
    fi
done
echo "./gradlew run -PappArgs=\"[$param]\""
./gradlew run -PappArgs="[$param]"
