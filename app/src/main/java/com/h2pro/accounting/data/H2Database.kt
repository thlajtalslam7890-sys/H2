package com.h2pro.accounting.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class H2Database(context: Context) : SQLiteOpenHelper(context, "h2pro.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE financial_year (id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER NOT NULL, start_date TEXT, end_date TEXT, active INTEGER NOT NULL DEFAULT 1)")
        db.execSQL("CREATE TABLE company (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, phone TEXT, address TEXT, logo_uri TEXT)")
        db.execSQL("CREATE TABLE accounts (id INTEGER PRIMARY KEY AUTOINCREMENT, code TEXT NOT NULL UNIQUE, name TEXT NOT NULL, parent_id INTEGER, type TEXT NOT NULL, level INTEGER NOT NULL DEFAULT 1, active INTEGER NOT NULL DEFAULT 1)")
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, user_no TEXT NOT NULL UNIQUE, name TEXT, password_hash TEXT NOT NULL, active INTEGER NOT NULL DEFAULT 1)")
        db.execSQL("CREATE TABLE areas (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE, active INTEGER NOT NULL DEFAULT 1)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) { /* reserved for future migrations */ }
    }
}
