# IF2211 - Strategi Algoritma
> Tugas Kecil 1 Strategi Algoritma 2025

# EKKO IQ Puzzler Pro Puzzle-Solver
![alt text](https://github.com/koinen/Tucil1_13523083/blob/main/doc/ekko.jpg?raw=true)
> -- "Yo it's like I'm the boy, who like....shatters time or something."

## Description
Aplikasi ini adalah pencari solusi untuk permainan puzzle IQ Puzzler Pro, menggunakan algoritma brute force, dilengkapi dengan GUI.

## Prerequisites
Pastikan untuk menggunakan program requirement dengan versi yang sesuai. 
- JavaFX SDK 23.0.2 (https://gluonhq.com/products/javafx/)
- Java JDK 23.0.1 (https://jdk.java.net/archive/)

## Running the Program
### Compilation and Execution
1. Sesuaikan pathnya dan jalankan script berikut untuk menetapkan path menuju library JavaFX SDK. <br />
>> Windows
```cmd
set PATH_TO_FX=path\to\javafx-sdk-23.0.2\lib
```
>> macOS/ Linux
```bash
export PATH_TO_FX=path/to/javafx-sdk-23.0.2/lib
```

2. Kompilasikan file-file source code ke file .class di folder bin dengan menjalankan script berikut.
>> Windows
```cmd
javac -d bin --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.swing src/*.java
```
>> macOS/ Linux
```bash
javac -d bin --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.swing src/*.java
```

3. Terakhir, jalankan program dengan menjalankan script berikut.
>> Windows
```cmd
java -cp bin --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.swing Main
```
>> macOS/ Linux
```bash
java -cp bin --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.swing Main
```

4. Untuk menjalankan program versi CLI, jalankan dengan menggunakan argumen CLI.
>> Windows
```cmd
java -cp bin Main CLI
```
>> macOS/ Linux
```bash
java -cp bin Main CLI
```

### How to Use
1. Untuk menggunakan program, klik tombol 'Select Input Config File' untuk memilih file .txt yang berisi konfigurasi permainan.
2. Setelah beberapa saat, akan muncul pop-up yang berisikan informasi solusi permainan.
3. Apabila ingin menyimpan hasil solusi, dapat disimpan dengan memasukkan nama file penyimpanan dan klik tombol 'Save as Image' atau 'Save as Text' sesuai keinginan (Hasil penyimpanan akan disimpan di folder result). Jika tidak, tutup pop-up.
4. Selamat mencoba!

## Author
David Bakti Lodianto - 13523083
