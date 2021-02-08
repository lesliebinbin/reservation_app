Vagrant.configure("2") do |config|
  config.vagrant.plugins = [
    "docker",
    "vagrant-docker-compose",
  ]
  config.vm.network "private_network", ip: "172.30.1.5"

  config.vm.box = "bento/ubuntu-18.04"

  config.vm.provision :docker

  config.vm.provision :docker_compose,
    compose_version: "1.22.0"

  config.vm.provision "shell",
    path: "bootstrap.sh"

  config.vm.provision "shell",
     run: "always",
     inline: <<-SCRIPT
        mkdir -p /etc/docker
        echo '{"debug": true, "hosts": ["tcp://0.0.0.0:2376"]}' > /etc/docker/daemon.json
        service docker stop
        dockerd
     SCRIPT

  # config.vm.provision "shell",
  #  keep_color: true,
  #  privileged: false,
  #  run: "always",
  #  inline: <<-SCRIPT
  #    cd /vagrant
  #    docker-compose up --detach
  #  SCRIPT
end
