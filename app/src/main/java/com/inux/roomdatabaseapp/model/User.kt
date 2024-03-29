package com.inux.roomdatabaseapp.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    @Embedded
    val endereco: Endereco
) : Parcelable

@Parcelize
data class Endereco(
    val endereco: String,
    val numero: Int,
    val bairro: String,
    val complemento: String
) : Parcelable