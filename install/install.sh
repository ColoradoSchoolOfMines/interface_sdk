#!/usr/bin/env bash


################################
# Development                  #
################################
# General Software Development dependencies
sudo apt-get install -y vim git build-essential

# Andrew DeMaria'a personal vim configs
if [[ ! -d "$HOME/VimGit" ]]
then
    git clone --recursive https://github.com/muff1nman/Vim.git $HOME/VimGit
    if [[ -a "$HOME/.vimrc" ]]
    then
        mv $HOME/.vimrc $HOME/.vimrc.bak
    fi
    if [[ -d "$HOME/.vim" ]]
    then
        mv $HOME/.vim $HOME/.vim.bak
    fi
    ln -s $HOME/VimGit/vim $HOME/.vim
    ln -s $HOME/VimGit/vimrc $HOME/.vimrc
fi


################################
# install java                 #
################################
# TODO check for an exisiting version of java
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get -y update
sudo echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get install -y oracle-java7-installer

# java also needs 32 compatiblity layers for 64 bit machines
if [[ "`uname -i`" == "x86_64" ]];
then
    sudo apt-get install -y ia32-libs
fi

################################
# OpenNI and Nite              #
################################
sudo apt-get install -y libusb-1.0-0-dev freeglut3-dev g++

# setup link sources
if [[ "`uname -i`" == "x86_64" ]];
then
    openni_source="http://www.openni.org/wp-content/uploads/2012/12/OpenNI-Bin-Dev-Linux-x64-v1.5.4.0.tar.zip"
    nite_source="http://www.openni.org/wp-content/uploads/2012/12/NITE-Bin-Linux-x64-v1.5.2.21.tar.zip"
else
    openni_source="http://www.openni.org/wp-content/uploads/2012/12/OpenNI-Bin-Dev-Linux-x86-v1.5.4.0.tar.zip"
    nite_source="http://www.openni.org/wp-content/uploads/2012/12/NITE-Bin-Linux-x86-v1.5.2.21.tar.zip"
fi
kinect_sensor_source="https://github.com/avin2/SensorKinect/zipball/unstable"

# Check for a plugged in kinect ~ not sure if it actually makes a difference yet
echo "Is your kinect plugged in?"
# if yes do not continue
# TODO

cd ~
mkdir kinect
cd kinect

## download and extract
#wget $openni_source
#wget $nite_source
#wget $kinect_sensor_source -O kinect_sensor.zip

## extract the weird nested zipped and tarred folders
#unzip OpenNI-Bin-Dev-Linux*.tar.zip
#unzip NITE-Bin-Linux*.tar.zip
#unzip kinect_sensor.zip
#tar -xjvf NITE-Bin-Linux*.tar.bz2
#tar -xjvf OpenNI-Bin-Dev-Linux*.tar.bz2
#rm *.bz2
#rm *.zip

#mv avin2-SensorKinect* kinect_sensor
#mv NITE-Bin-Dev-Linux* nite
#mv OpenNI-Bin-Dev-Linux* openni

## install openni
#cd ~/kinect/openni/
#chmod a+x install.sh
#sudo ./install.sh

## install kinect sensor
#cd ~/kinect/kinect_sensor/Platform/Linux/CreateRedist/
#chmod a+x RedistMaker
#sudo ./RedistMaker
#cd ../Redist/Sensor-Bin-Linux*
#sudo chmod a+x install.sh
#sudo ./install.sh

## install nite
#cd ~/kinect/nite/
#chmod a+x install.sh
#sudo ./install.sh


# install processing
# TODO nested directories
#processing_source="http://processing.googlecode.com/files/processing-1.5.1-linux.tgz"
#cd ~
#mkdir processing-1.5.1
#cd processing-1.5.1
#wget $processing_source
#tar -xzvf *.tgz
#rm *.tgz

# upgrade the included java
cd processing-1.5.1
rm -rf java
ln -s /usr/bin/java java

simpleni_source='https://simple-openni.googlecode.com/files/SimpleOpenNI-0.27.zip'
cd ~/sketchbook
mkdir libraries
cd libraries
wget $simpleni_source
unzip *.zip


