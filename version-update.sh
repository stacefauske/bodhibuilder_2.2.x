#!/bin/bash
#

# used to update the version number throughout the necessary files in bodhibuilder
#   usr/share/bodhibuilder-gtk/bodhibuilder-gtk.py:APP_VERSION = "2.1.0"
#   etc/bodhibuilder/bodhibuilder.version:BODHIBUILDERVERSION="2.1.0"
#   DEBIAN/control:Version: 2.1.0
#   

currversion=`cat ./etc/bodhibuilder/bodhibuilder.version | cut -d\" -f2`

if [ ! "${1}" ] ; then # just echo out the version number
  echo ""
  echo " Current bodhibuilder version is :  ${currversion}"
  echo ""
else # change version number throughout files
  sed -i "s/APP_VERSION = \".*\"$/APP_VERSION = \"${1}\"/" ./usr/share/bodhibuilder-gtk/bodhibuilder-gtk.py
  sed -i "s/BODHIBUILDERVERSION=\".*\"$/BODHIBUILDERVERSION=\"${1}\"/" ./etc/bodhibuilder/bodhibuilder.version
  sed -i "s/Version:.*$/Version: ${1}/" ./DEBIAN/control
fi

exit 0
