package com.example.p9meeting13.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.p9meeting13.ui.viewmodel.FormErrorState
import com.example.p9meeting13.ui.viewmodel.MahasiswaEvent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import com.example.p9meeting13.ui.viewmodel.FormState
import com.example.p9meeting13.ui.viewmodel.InsertUiState

@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: InsertUiState,
    onClick:() -> Unit,
    homeUiState: FormState
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        FormMahasiswa(
            mahasiswaEvent = uiState.insertUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = homeUiState !is FormState.Loading
        ) {
            if (homeUiState is FormState.Loading){
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp)
                )
                Text("Loading...")
            } else{
                Text("Add")
            }
        }
    }
}

@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(), //berisi data class yang memiliki beberapa member member
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("Laki - laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(top = 20.dp))
    {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama, //mengambil data dari mahasiswaEvent
            onValueChange = { //mengubah status tampilan
                onValueChange(mahasiswaEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null, //digunakan untuk validasi, dan errorstate diambil dari parameter
            placeholder = { Text("Masukkan Nama") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.nama ?: "", //digunakan untuk memunculkan pesan errornya
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim, //mengambil data dari mahasiswaEvent
            onValueChange = { //mengubah status tampilan
                onValueChange(mahasiswaEvent.copy(nim = it))
            },
            label = { Text("Nim") },
            isError = errorState.nim != null, //digunakan untuk validasi, dan errorstate diambil dari parameter
            placeholder = { Text("Masukkan Nim") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nim ?: "", //digunakan untuk memunculkan pesan errornya
            color = Color.Red
        )

        Text(text = "Jenis Kelamin")
        Row(modifier = Modifier.fillMaxWidth())
        {
            jenisKelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                )
                {
                    RadioButton(
                        selected = mahasiswaEvent.gender == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(gender = jk))
                        },
                    )
                    Text(text = jk)
                }
            }
        }
        Text(
            text = errorState.gender ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan Alamat") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        Text(text = "Kelas")
        Row(modifier = Modifier.fillMaxWidth())
        {
            kelas.forEach { kelas ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                )
                {
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kelas,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas = kelas))
                        },
                    )
                    Text(text = kelas)
                }
            }
        }
        Text(
            text = errorState.kelas ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(angkatan = it))
            },
            label = { Text("Angkatan") },
            isError = errorState.angkatan != null,
            placeholder = { Text("Masukkan Angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
    }
}