## Download debian cloud image
```
wget https://cloud.debian.org/images/cloud/bookworm/latest/debian-12-generic-amd64.qcow2
```

## Create VM Template
```
sudo /usr/sbin/qm create 9008 --name "debian12" --memory 2048 --cores 2 --net0 virtio,bridge=vmbr0
sudo /usr/sbin/qm set 9008 --ide0 vmstore:cloudinit
sudo /usr/sbin/qm importdisk 9008 debian-12-generic-amd64.qcow2 vmstore -format qcow2
sudo /usr/sbin/qm set 9008 --scsihw virtio-scsi-pci --scsi0 vmstore:vm-9008-disk-0
sudo /usr/sbin/qm resize 9008 scsi0 48G
sudo /usr/sbin/qm set 9008 --boot c --bootdisk scsi0
sudo /usr/sbin/qm set 9008 --serial0 socket --vga serial0
sudo /usr/sbin/qm template 9008
```
