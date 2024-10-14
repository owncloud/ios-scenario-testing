#!/bin/sh
USERNAME=root
PASSWORD=owncloud
export OC_PASS=a
SCRIPT="cd /opt/owncloud; su -s /bin/sh www-data -c \"php occ user:delete $3\""
ssh -p $2 $USERNAME@$1 $SCRIPT