sudo apt-get update
if ! [ -x `command -v docker-compose` ]; then
 echo 'docker-compose is not yet installed, now install.'
 sudo apt-get install docker-compose -y
fi
