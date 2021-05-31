#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
BOLD=$(tput bold)
NORMAL=$(tput sgr0)

if [[ ! -f $1 ]] || [[ "$1" != *hosts ]]; then
    echo "Please specify as first argument the absolute path to the hosts file. Example ${BOLD}> ./init.sh ABSOLUTE_PATH_TO_HOSTS${NORMAL}"
    exit 0
fi

echo -e "\n## ${GREEN}Setting up local domains...${NC}\n"

for domain in 'st-mariadb' 'st-redis' 'st-zookeeper' 'st-kafka-1' 'st-schema-registry'
do
    if ! grep -q "$domain" $1;
    then
        line="127.0.0.1               $domain"
        echo "Addding ${line} to hosts."
        echo $line >> $1;
    else
        echo "$domain already added.";
    fi
done

echo -e "\n## ${GREEN}Compiling and generating docker images...${NC}\n"

mvn -f ../pom.xml clean package

echo -e "\n## ${GREEN}Running docker environment...${NC}\n"

docker-compose -f ./docker/docker-compose-all.yml up -d

echo -e "\n"

docker-compose -f ./docker/docker-compose-all.yml ps

echo -e "\nThe local environment is running!!. ${RED}${BOLD}Please, take into account all services needs some time to get ready!!${NC}${NORMAL}.\n"