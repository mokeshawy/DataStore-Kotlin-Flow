package com.example.datastorekotlin_flow.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastorekotlin_flow.util.Const
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreSetting
@Inject
constructor( @ApplicationContext private val context: Context){

    object PreferenceKey{
        val name = stringPreferencesKey(Const.NAME_KEY)
    }

    private val Context.dataStore by preferencesDataStore(Const.DATA_STORE_NAME)

    // fun write data
    suspend fun writeToLocal( name : String){
        context.dataStore.edit { preference ->
            preference[PreferenceKey.name] = name
        }
    }

    // fun clear Data
    suspend fun clearData(){
        context.dataStore.edit {
            it.clear()
        }
    }

    // read data using flow.
    val readToLocal : Flow<String> = context.dataStore.data
        .catch {
            if(this is Exception){
                emit(emptyPreferences())
            }
        }.map { preference ->
            val name = preference[PreferenceKey.name] ?: ""
            name
        }
}