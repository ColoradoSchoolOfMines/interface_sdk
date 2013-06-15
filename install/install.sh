#!/usr/bin/env bash


################################
# Development                  #
################################
# General Software Development dependencies
sudo apt-get install -y git build-essential

################################
# install java                 #
################################
echo 'Would you like to install java 7 from oracle? (y/n): '
read -n 1 prompt
echo ""
if [[ "$prompt" == "y" || "$prompt" == "Y" ]];
then
  echo "Installing java 7 from oracle"
  sudo add-apt-repository -y ppa:webupd8team/java
  sudo apt-get -y update
  sudo echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
  sudo apt-get install -y oracle-java7-installer
else
  echo "Skipping oracle-java7..."
fi

# also do a system architecture sanity check here
arch=`uname -i`

# java also needs 32 compatiblity layers for 64 bit machines
if [[ "$arch" == "x86_64" ]];
then
  echo "Detected 64 bit system"
  echo "For processing 1.5.1 we will need the 32 bit comptability layers"
  echo "Can I install this for you? (y/n)"
  read -n 1 prompt
  if [[ "$prompt" == "y" || "$prompt" == "Y" ]];
  then
    echo "installing ia32-libs..."
    sudo apt-get install -y ia32-libs
  else
    echo "Skipping ia32-libs..."
  fi
elif [[ "$arch" == "x86" ]];
then
  echo "Detected 32 bit system"
  echo "Continuing..."
else
  echo "Could not recognize the system or the system may not be supported"
  echo "Are you sure you want to continue? (y/n)"
  read -n 1 prompt
  if [[ "$prompt" == "y" || "$prompt" == "Y" ]];
  then
    echo "Continuing..."
  else
    exit 1
  fi
fi

#################################
## OpenNI and Nite              #
#################################
echo "I am about to begin the installation of openni, nite and the kinect libraries"
echo "Continue? (y/n)"
read -n 1 prompt
if [[ "$prompt" == "y" || "$prompt" == "Y" ]];
then
  echo "Continuing..."

  echo "First, the dependencies"
  sudo apt-get install -y libusb-1.0-0-dev freeglut3-dev g++

  # setup link sources
  if [[ "$arch" == "x86_64" ]];
  then
    openni_source="http://www.openni.org/wp-content/uploads/2012/12/OpenNI-Bin-Dev-Linux-x64-v1.5.4.0.tar.zip"
    nite_source="http://www.openni.org/wp-content/uploads/2012/12/NITE-Bin-Linux-x64-v1.5.2.21.tar.zip"
  else
    openni_source="http://www.openni.org/wp-content/uploads/2012/12/OpenNI-Bin-Dev-Linux-x86-v1.5.4.0.tar.zip"
    nite_source="http://www.openni.org/wp-content/uploads/2012/12/NITE-Bin-Linux-x86-v1.5.2.21.tar.zip"
  fi
  kinect_sensor_source="https://github.com/avin2/SensorKinect/zipball/unstable"


  # Check for a plugged in kinect ~ not sure if it actually makes a difference yet
  prompt="N"
  until [[ "$prompt" == "Y" || "$prompt" == "Y" ]];
  do
    "Please disconnect your kinect before proceeding"
    "Have you disconnected your kinect?"
    read -n 1 prompt
  done
  echo "Great!.. continuing"

  cd ~
  mkdir kinect
  cd kinect

  # download and extract
  echo "Downloading sources"
  wget $openni_source
  wget $nite_source
  wget $kinect_sensor_source -O kinect_sensor.zip

  # extract the weird nested zipped and tarred folders
  echo "Extracting sources"
  unzip OpenNI-Bin-Dev-Linux*.tar.zip
  unzip NITE-Bin-Linux*.tar.zip
  unzip kinect_sensor.zip
  tar -xjvf NITE-Bin-Linux*.tar.bz2
  tar -xjvf OpenNI-Bin-Dev-Linux*.tar.bz2

  echo "Cleaning up sources downloaded"
  rm *.bz2
  rm *.zip

  echo "Moving some folders around for nicer names"
  mv avin2-SensorKinect* kinect_sensor
  mv NITE-Bin-Dev-Linux* nite
  mv OpenNI-Bin-Dev-Linux* openni

  # install openni
  echo "Installing openni"
  cd ~/kinect/openni/
  chmod a+x install.sh
  sudo ./install.sh

  # install kinect sensor
  echo "Installing kinect sensor"
  cd ~/kinect/kinect_sensor/Platform/Linux/CreateRedist/
  chmod a+x RedistMaker
  sudo ./RedistMaker
  cd ../Redist/Sensor-Bin-Linux*
  sudo chmod a+x install.sh
  sudo ./install.sh

  # install nite
  echo "Installing nite"
  cd ~/kinect/nite/
  chmod a+x install.sh
  sudo ./install.sh

else
  echo "Skipping the installation of openni"
  echo "Note the kinect will be unavailable unless you have installed openni/nite/kinect separately"
fi

# install processing
# TODO nested directories
echo "I would like to install processing 1.5.1"
echo "Continue? (y/n):"
read -n 1 prompt
if [[ "$prompt" == "y" || "$prompt" == "Y" ]];
then
  processing_source="http://processing.googlecode.com/files/processing-1.5.1-linux.tgz"
  cd ~
  mkdir processing-1.5.1
  cd processing-1.5.1
  echo "Getting source"
  wget $processing_source
  tar -xzvf *.tgz
  rm *.tgz
  # TODO
  echo "You may need to source the processing bin if you want to run processing standalone"

  # upgrade the included java
  echo "Upgrading the included java for processing to reference the installed java"
  cd processing-1.5.1
  rm -rf java
  ln -s /usr/bin/java java

  # Not needed for the interface sdk
  echo "Would you like to download the OPTIONAL simple openni processing library? (y/n)"
  echo "Note this is not required for the interface sdk and is a separate processing library for standalone sketches"

  read -n 1 prompt
  if [[ "$prompt" == "y" || "$prompt" == "Y" ]];
  then
    simpleni_source='https://simple-openni.googlecode.com/files/SimpleOpenNI-0.27.zip'
    cd ~/sketchbook
    mkdir libraries
    cd libraries
    wget $simpleni_source
    unzip *.zip
  else
    echo "Skipping simple openni..."
  fi
else
  echo "Skipping processing..."
fi

echo "Finished installation script!"
echo "Please report any unexpected behavior on the github"
