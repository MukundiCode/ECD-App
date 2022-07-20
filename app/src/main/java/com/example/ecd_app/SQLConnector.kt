package com.example.ecd_app

import android.util.Log
import java.sql.*
import java.util.Properties

class SQLConnector {
    internal var conn: Connection? = null
    internal var username = "uctgroup2022" // provide the username
    internal var password = "ecd@BBP__!" // provide the corresponding password
    var resultset: ResultSet? = null
    var stmt: Statement? = null

    /**
     * This method makes a connection to MySQL Server
     * In this example, MySQL Server is running in the local host (so 127.0.0.1)
     * at the standard port 3306
     */
    fun getConnection() {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "ecd-portal-js.mysql.database.azure.com" +
                        ":" + "3306" + "/" +
                        "wordpress" + "?autoReconnect=true&useSSL=false",
                connectionProps)
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
    }

    /** Should return the iteratore for the query and do nothing else */
    fun executeMySQLQuery(query: String): ResultSet? {
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery(query)

            if (stmt!!.execute(query)) {
                resultset = stmt!!.resultSet
                return resultset
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {

        }
        return resultset
    }

    fun closeConnection(){
        // release resources
        if (resultset != null) {
            try {
                resultset!!.close()
            } catch (sqlEx: SQLException) {
            }

            resultset = null
        }
        if (stmt != null) {
            try {
                stmt!!.close()
            } catch (sqlEx: SQLException) {
            }
            stmt = null
        }
        if (conn != null) {
            try {
                conn!!.close()
            } catch (sqlEx: SQLException) {
            }
            conn = null
        }
    }
}