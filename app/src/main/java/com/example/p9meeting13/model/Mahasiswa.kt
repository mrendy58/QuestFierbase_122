package com.example.p9meeting13.model

data class Mahasiswa(
    val nim:String,
    val nama:String,
    val alamat:String,
    val gender:String,
    val kelas:String,
    val angkatan:String,
    val judul_skripsi:String,
    val dosbim1:String,
    val dosbim2:String,
){
    constructor() :  this("","","","","","","","","")
}
