package com.example.p9meeting13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.p9meeting13.model.Mahasiswa
import com.example.p9meeting13.repository.RepositoryMhs
import kotlinx.coroutines.launch

class InsertViewModel (
    private val repoMhs: RepositoryMhs
) : ViewModel(){
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent
        )

    }

    fun validateFields(): Boolean{
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            gender = if (event.gender.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",
            judul_skripsi = if (event.judul_skripsi.isNotEmpty()) null else "Judul Skripsi tidak boleh kosong",
            dosbim1 = if (event.dosbim1.isNotEmpty()) null else "Dosen Pembimbing 1 tidak boleh kosong",
            dosbim2 = if (event.dosbim2.isNotEmpty()) null else "Dosen Pembimbing 2 tidak boleh kosong",
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun insertMhs(){
        if (validateFields()){
            viewModelScope.launch {
                uiState = FormState.Loading
                try{
                    repoMhs.insertMhs(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data Berhasil disimpan")
                }
                catch (e: Exception){
                    uiState = FormState.Error("Data gagal disimpan")
                }
            }
        } else{
            uiState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm(){
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    fun resetSnackBarMessage(){
        uiState = FormState.Idle
    }
}

sealed class FormState{
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String): FormState()
    data class Error(val message: String): FormState()
}

data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val gender: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
    val judul_skripsi: String? = null,
    val dosbim1: String? = null,
    val dosbim2: String? = null
){
    fun isValid():Boolean{
        return nim == null && nama == null && gender == null &&
                alamat == null && kelas == null && angkatan == null && judul_skripsi == null
                && dosbim1 == null && dosbim2 == null
    }
}
// data class untuk menyimpan data input form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val gender: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val judul_skripsi: String = "",
    val dosbim1 : String = "",
    val dosbim2 : String = "",
)

//menyimpan input form ke dalam entity
fun MahasiswaEvent.toMhsModel(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    gender = gender,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan,
    judul_skripsi = judul_skripsi,
    dosbim1 = dosbim1,
    dosbim2 = dosbim2
)
