#!/bin/sh
#

SOURCE="$0"
while [[ -h "$SOURCE"  ]]; do # resolve $SOURCE until the file is no longer a symlink
    scriptDir="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
    SOURCE="$(readlink "$SOURCE")"
    [[ ${SOURCE} != /*  ]] && SOURCE="$scriptDir/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
scriptDir="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
basedir=${scriptDir%/*}
echo "baseDir:"${basedir}

version=`cat ${basedir}/gradle.properties | grep "plugin_version=" | grep -Eo '[0-9][0-9.]+'`
echo "version:"${version}

cd ${basedir}/icodeless-idea
../gradlew clean buildPlugin --stacktrace



if [[ ! -d "$basedir/plugins" ]];then
mkdir ${basedir}/plugins
fi
mv ${basedir}/icodeless-idea/build/distributions/*.zip ${basedir}/plugins/icodeless.${version}.zip