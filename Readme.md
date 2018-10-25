#### VPN Account Maker - Group 1

------------

- Alfianti Widyastuti (02)
- Fahmiko Purnama Putra (11)


------------
#### SDK Requirment
Grandle Build v26
Minimum Android API 21, Android Version 5.0.0 Lolipop(beta)



------------


#### Apa Itu VPN ?
Virtual Private Network (VPN) adalah sebuah teknologi komunikasi yang memungkinkan untuk dapat terkoneksi ke jaringan public dan menggunakannya untuk dapat bergabung dengan jaringan local. Dengan cara tersebut maka akan didapatkan hak dan pengaturan yang sama seperti halnya berada didalam LAN itu sendiri, walaupun sebenarnya menggunakan jaringan milik public.

------------
#### List Class
- Create Vpn.java 
class ini berfungsi untuk membuat data baru 
- Login activity 
class ini berfungsi untuk dapat mengakses aplikasi, yang kemudian masuk ke class Main activity untuk mengelola data edit, upload, dan delete, ataupun membuat akun vpn
- Manage server 
Form edit dan insert untuk data pada form admin (Main activity) 
- View Server 
Melihat detail pada server
- RecyclerViewAdapter.java
Digunakan untuk menampung data list dan menampilkan ke layout
- ServerModel.java
Digunakan untuk membuat kelas model dengan atribut yang sama dengan kolom pada sql
- Preference,java
Digunakan untuk instansiasi fungsi sharedpreference
- VpnHelper.java
Digunakan untuk instansiasi database dan tabel SQLite



------------

#### Fitur Aplkasi
- List Server
- Create Account
- Insert Server
- Update Server
- Login Automatically
- Logout

------------

#### Reference
- [https://developer.android.com/training/data-storage/sqlite](https://developer.android.com/training/data-storage/sqlite "https://developer.android.com/training/data-storage/sqlite")
- [https://developer.android.com/reference/java/sql/Blob](https://developer.android.com/reference/java/sql/Blob "https://developer.android.com/reference/java/sql/Blob")
- [https://developer.android.com/guide/topics/ui/layout/recyclerview#java](https://developer.android.com/guide/topics/ui/layout/recyclerview#java "https://developer.android.com/guide/topics/ui/layout/recyclerview#java")
- [https://stackoverflow.com/questions/51805505/failed-to-load-appcompat-actionbar-with-unknown-error-in-android-studio-3-1-4](https://stackoverflow.com/questions/51805505/failed-to-load-appcompat-actionbar-with-unknown-error-in-android-studio-3-1-4 "https://stackoverflow.com/questions/51805505/failed-to-load-appcompat-actionbar-with-unknown-error-in-android-studio-3-1-4")
- [https://stackoverflow.com/questions/24855476/android-studio-sqlite-insert-image-from-database](https://stackoverflow.com/questions/24855476/android-studio-sqlite-insert-image-from-database "https://stackoverflow.com/questions/24855476/android-studio-sqlite-insert-image-from-database")




------------

#### Tampilan Aplikasi
![Login](https://raw.githubusercontent.com/fahmiko/vpnmaker/master/image/login.png)

![Home](https://raw.githubusercontent.com/fahmiko/vpnmaker/master/image/home_server.png)

![Manage](https://raw.githubusercontent.com/fahmiko/vpnmaker/master/image/insert_server.png)

![View](https://raw.githubusercontent.com/fahmiko/vpnmaker/master/image/view_server.png)


[1]: https://developer.android.com/training/data-storage/sqlite "SQLite data storage"
[SQL DATA]: https://developer.android.com/training/data-storage/sqlite "SQL DATA"
