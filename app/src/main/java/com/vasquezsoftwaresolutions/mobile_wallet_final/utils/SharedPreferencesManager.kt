package com.vasquezsoftwaresolutions.mobile_wallet_final.utils

import android.content.Context
import android.content.SharedPreferences
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.model.UserModel

/**
 *@autor Pablo
 *@create 27-06-2024 14:31
 *@project mobile_wallet_final
 *@Version 1.0
 */
class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun saveUser(user: UserModel) {
        sharedPreferences.edit().apply {
            putString("first_name", user.firstName)
            putString("last_name", user.lastName)
            putString("email", user.email)
            putString("password", user.password)
            putInt("roleId", user.roleId)
            putInt("points", user.points)
            apply()
        }
    }

    fun saveAccountData(money: String, id: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong("account_id", id)
        editor.putString("money", money)
        editor.apply()
    }

    fun getAccountId(): Long {
        return sharedPreferences.getLong("account_id", -1)
    }

    fun getSaldo(): String? {
        return sharedPreferences.getString("money", "0")
    }

    fun getUser(): UserModel? {
        // Recuperar los datos del usuario de las preferencias compartidas
        val firstName = sharedPreferences.getString("first_name", null)
        val lastName = sharedPreferences.getString("last_name", null)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        val roleId = sharedPreferences.getInt("roleId", -1)
        val points = sharedPreferences.getInt("points", -1)

        return if (firstName != null && lastName != null && email != null && password != null
            && roleId != -1 && points != -1
        ) {
            UserModel(firstName, lastName, email, password, roleId, points)
        } else {
            null
        }
    }

    fun clearSession() {
        // Limpiar todas las preferencias compartidas
        sharedPreferences.edit().clear().apply()
    }

}