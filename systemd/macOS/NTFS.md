# NTFS

## 挂载法

```bash
MacBook-Pro % sudo -i
MacBook-Pro: root# diskutil info /Volumes/DAOCHI/
   Device Identifier:         `disk4s1`
   Device Node:               /dev/disk4s1
   Whole:                     No
   Part of Whole:             disk4
MacBook-Pro: root# umount /dev/disk4s1
MacBook-Pro: root# mkdir /User/shizc/NTFS
MacBook-Pro: root# mount_ntfs -o rw,auto,nobrowse,noowners,noatime /dev/disk4s1 /Users/shizc/NTFS
```

