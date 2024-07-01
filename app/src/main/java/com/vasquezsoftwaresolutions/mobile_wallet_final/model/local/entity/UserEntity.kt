package com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 *Entidad de la base de datos que representa un usuario local.
 *@autor Pablo
 *@create 30-06-2024 19:09
 *@project mobile_wallet_final
 *@Version 1.0
 */
@Entity(tableName = "table_users")
data class UserEntity(
    @PrimaryKey
    val id: Long,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val password: String,
    val points: Int,
    val roleid: Int,
    val createdAt: String,
    val updatedAt: String?
)
