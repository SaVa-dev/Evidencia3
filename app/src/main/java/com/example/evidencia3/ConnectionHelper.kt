package com.example.evidencia3

import android.annotation.SuppressLint
import android.os.StrictMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class ConnectionHelper {
    companion object {
        val endPoint: String     = "database-1.ctam4gw68b95.us-east-1.rds.amazonaws.com"
        val port: String         = "3306"
        val databaseName: String = "AndroidStudio"
        val DB_USER: String      = "admin"
        val DB_PASSWORD: String  = "Rrez*0214"
        val DB_URL = "jdbc:mysql://$endPoint:$port/$databaseName"

        /*
        fun query(connection: Connection?, sql: String): List<Map<String, Any>> {
            val resultList = mutableListOf<Map<String, Any>>()
            try {
                connection?.let {
                    val statement = it.createStatement()
                    val resultSet: ResultSet = statement.executeQuery(sql)
                    val metaData = resultSet.metaData
                    val columnCount = metaData.columnCount

                    while (resultSet.next()) {
                        val row = mutableMapOf<String, Any>()
                        for (i in 1..columnCount) {
                            row[metaData.getColumnName(i)] = resultSet.getObject(i)
                        }
                        resultList.add(row)
                    }

                    resultSet.close()
                    statement.close()
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return resultList
        }
        * */
    }

    @SuppressLint("NewApi")
    fun connect(): Connection? {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        )
        return try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }
}