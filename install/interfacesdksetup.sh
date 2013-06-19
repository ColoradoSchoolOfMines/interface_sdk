#!/usr/bin/env bash

# This script clones the proper github repositories to run the interface sdk and other major modules (such as the homescreen) 

exhibit_dir=`pwd`/exhibit
exhibit_source_dir=`pwd`/exhibit/source
interfaceSDKdirname="interfaceSDK"

interfacesdk_repo="https://github.com/ColoradoSchoolOfMines/interface_sdk.git" 
module_repositories=( "https://github.com/ColoradoSchoolOfMines/home_screen_module" "https://github.com/TheCrownedFox/fractalModule.git"  "https://github.com/TheCrownedFox/CLIModuleLauncher.git" "https://github.com/TheCrownedFox/brickBreaker.git" )

# create directory structure
mkdir -p "$exhibit_dir/development/modules"
mkdir -p "$exhibit_dir/production/modules"
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

# create a basic setup in development.  productioin is not populated as it should be tested in development first and then copied over
# first get the most recent jar file
# also here we reference the maven repository so that we dont have to worry about locally building the jars.
cd "$exhibit_source_dir/$interfaceSDKdirname"
wget -P "$exhibit_dir/development" "https://s3-us-west-2.amazonaws.com/acmx.mines.edu/release/edu/mines/acmX/exhibit/interfacesdk/`git describe --abbrev=0 | sed 's/v//'`/interfacesdk-`git describe --abbrev=0 | sed 's/v//'`-jar-with-dependencies.jar" 

# now get a general configuration file
cd "$exhibit_dir/development"
wget "https://raw.github.com/ColoradoSchoolOfMines/interface_sdk/development/src/main/resources/example_module_manager_manifest.xml" -O "manifest.xml"
wget "https://raw.github.com/ColoradoSchoolOfMines/interface_sdk/master/src/main/resources/openni_config.xml" -O "openni_config.xml"

cd "$exhibit_dir/development/modules"
wget "https://s3-us-west-2.amazonaws.com/acmx.mines.edu/release/edu/mines/acmX/exhibit/modules/home_screen/0.0.1/home_screen-0.0.1.jar"
