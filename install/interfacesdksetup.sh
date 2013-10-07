#!/usr/bin/env bash

# This script clones the proper github repositories to run the interface sdk and
# other major modules (such as the homescreen) 

# if the --with-repos option is specified, the git repos will be downloaded and
# the most recent version of the interface sdk will be downloaded based on the
# most recent tagged version in the git repository (it will not be built from
# the repo to avoid having to install maven and such).  Otherwise the interface
# sdk specified in the script will be downloaded

# set the default options
repos=false

for arg in "$@"
do
  case $arg in
    --no-repos) repos=false;;
    --with-repos) repos=true;;
    --help | -h) cat << EOF
usage: ./interfacesdksetup [--with-repos | --no-repos] [--help | -h]

The following options are useful for developers not wanting to have to track
down all the repositories.  It does not affect the runtime of the interface sdk

--with-repos : downloads the source git repositories
--no-repos   : does not download the source respositories

Please report bugs to the github page at 
https://github.com/ColoradoSchoolOfMines/interface_sdk.git
EOF
exit 1;;
esac
done


exhibit_dir=`pwd`/exhibit
exhibit_source_dir=`pwd`/exhibit/source
interfaceSDKdirname="interfaceSDK"

interfacesdk_repo="https://github.com/ColoradoSchoolOfMines/interface_sdk.git" 
module_repositories=(
"https://github.com/ColoradoSchoolOfMines/home_screen_module"
"https://github.com/TheCrownedFox/fractalModule.git"
"https://github.com/TheCrownedFox/CLIModuleLauncher.git"
"https://github.com/TheCrownedFox/brickBreaker.git"
"https://github.com/ColoradoSchoolOfMines/Pong.git"
"https://github.com/ColoradoSchoolOfMines/simple_module.git")

# create directory structure
mkdir -p "$exhibit_dir/development/modules"

if [[ "$repos" == "true" ]];
then
  mkdir -p "$exhibit_source_dir/modules"
  # clone the source repos
  # these are not actually needed for running as later we will be referencing another location
  cd "$exhibit_source_dir" 
  echo "Cloning interface sdk"
  git clone "$interfacesdk_repo" "$interfaceSDKdirname"
  cd "$exhibit_source_dir/modules" 
  for module in ${module_repositories[@]}
  do
    echo "Cloning $module"
    git clone $module
  done
fi

# create a basic setup in development.  production is not populated as it should be tested in development first and then copied over
# first get the most recent jar file
# also here we reference the maven repository so that we dont have to worry about locally building the jars.
version="0.1.2"
if [[ "$repos" == "true" ]];
then
  cd "$exhibit_source_dir/$interfaceSDKdirname"
  version="`git describe --abbrev=0 | sed 's/v//'`"
fi
wget -P "$exhibit_dir/development" "https://s3-us-west-2.amazonaws.com/acmx.mines.edu/release/edu/mines/acmX/exhibit/interfacesdk/$version/interfacesdk-$version-jar-with-dependencies.jar" 

# now get a general configuration file
cd "$exhibit_dir/development"
wget "https://raw.github.com/ColoradoSchoolOfMines/interface_sdk/development/src/main/resources/example_module_manager_manifest.xml" -O "manifest.xml"
wget "https://raw.github.com/ColoradoSchoolOfMines/interface_sdk/master/src/main/resources/openni_config.xml" -O "openni_config.xml"

cd "$exhibit_dir/development/modules"
wget "https://s3-us-west-2.amazonaws.com/acmx.mines.edu/release/edu/mines/acmX/exhibit/modules/home_screen/0.0.1/home_screen-0.0.1.jar"
wget "https://s3-us-west-2.amazonaws.com/acmx.mines.edu/release/com/austindiviness/cltest/CLIModuleLauncher/1.0/CLIModuleLauncher-1.0.jar"

# create an identical folder for production
cp -r "${exhibit_dir}/development" "${exhibit_dir}/production"
