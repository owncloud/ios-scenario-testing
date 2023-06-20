#!/bin/bash

dockerid=$(docker run -tid -p $2:8080 -e OWNCLOUD_TRUSTED_DOMAINS=$1 owncloud/server:$3)
echo "Creating container..."
echo $dockerid 
#Waiting a little bit while container is created
sleep 7
echo "Let's populate"
docker exec -ti $dockerid occ group:add test
docker exec -ti $dockerid occ user:add --display-name Alice --email alice@own.com -g admin alice
docker exec -ti $dockerid occ user:add --display-name Bob --email bob@own.com -g test bob
docker exec -ti $dockerid occ user:add --display-name Charles --email charly@own.com charles
