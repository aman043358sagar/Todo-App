package com.example.todoapp.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "titleDescription") var titleDescription: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "color") var color: Int
) : Parcelable