#! /bin/sh -e

. /usr/share/debconf/confmodule

if CODENAME="$(lsb_release -cs)"; then
  # TODO cjwatson 2006-04-07: wrong for Debian, I think
  db_set mirror/suite "$CODENAME"
  db_set mirror/codename "$CODENAME"
fi

rm -f /target/etc/apt/sources.list
rm -f /target/etc/apt/sources.list.d/dvd.list
PATH="/usr/lib/ubiquity/apt-setup:/usr/lib/ubiquity/choose-mirror:$PATH" \
  OVERRIDE_BASE_INSTALLABLE=1 OVERRIDE_LEAVE_CD_MOUNTED=1 \
  /usr/lib/ubiquity/apt-setup/apt-setup --log-output /target

# UEFI support for VM's:
if [ -e /target/boot/efi ] ; then
  rm -f /target/boot/efi/startup.nsh*
  touch /target/boot/efi/startup.nsh
  ARCH=`archdetect | cut -d/ -f1`
  if [ "${ARCH}" = "amd64" ] ; then
    echo "fs0:\EFI\ubuntu\grubx64.efi" >> /target/boot/efi/startup.nsh
  else
    echo "fs0:\EFI\ubuntu\grubia32.efi" >> /target/boot/efi/startup.nsh
  fi
fi
#~ #   ^ replaced ^ by ^ section ^ below ^

#~ yes | rm -f /target/boot/efi/startup.nsh*
#~ mkdir -p /target/boot/efi
#~ touch /target/boot/efi/startup.nsh
#~ ARCH=`archdetect | cut -d/ -f1`
#~ if [ "${ARCH}" = "amd64" ] ; then
  #~ echo "fs0:\EFI\ubuntu\grubx64.efi" >> /target/boot/efi/startup.nsh
#~ else
  #~ echo "fs0:\EFI\ubuntu\grubia32.efi" >> /target/boot/efi/startup.nsh
#~ fi


rm -f /target/etc/gdm/custom.conf
rm -f /target/etc/mdm/mdm.conf
