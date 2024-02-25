#!/bin/sh

IMAGE=$1

# set template image ID
ID=9008

# create a new VM
sudo /usr/sbin/qm create $ID --memory 2048 --cores 2 --net0 virtio,bridge=vmbr0

# create cloud-init image
sudo /usr/sbin/qm set $ID --ide0 vmstore:cloudinit

# import the downloaded disk to vmstore storage
sudo /usr/sbin/qm importdisk $ID $IMAGE vmstore -format qcow2

# finally attach the new disk to the VM as scsi drive
sudo /usr/sbin/qm set $ID --scsihw virtio-scsi-pci --scsi0 vmstore:vm-$ID-disk-0

# Resize the disk from to 48G.
sudo /usr/sbin/qm resize $ID scsi0 48G

# Set boot from disk
sudo /usr/sbin/qm set $ID --boot c --bootdisk scsi0

# Set video output to serial console
sudo /usr/sbin/qm set $ID --serial0 socket --vga serial0

# Make it a template, so you don't have to repeat these steps
sudo /usr/sbin/qm template $ID
