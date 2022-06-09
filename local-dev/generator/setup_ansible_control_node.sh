#!/bin/bash

# Update all packages that have available updates.
sudo apt-get update -y

# Install Python 3 and pip.
sudo apt-get install -y python3-pip

# Upgrade pip3.
sudo pip3 install --upgrade pip

sudo pip3 install ansible==5.8.0