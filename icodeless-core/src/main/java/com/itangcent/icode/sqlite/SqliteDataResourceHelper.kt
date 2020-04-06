package com.itangcent.icode.sqlite

import com.itangcent.icode.log.Logger
import com.itangcent.icode.log.traceError
import com.itangcent.icode.spi.ServiceLoader
import com.itangcent.icode.utils.FileUtils
import org.sqlite.SQLiteConfig
import org.sqlite.SQLiteDataSource
import org.sqlite.javax.SQLiteConnectionPoolDataSource
import java.io.File
import java.sql.ResultSet
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList

class SqliteDataResourceHelper {

    private val sdCache: ConcurrentHashMap<String, SQLiteDataSource> = ConcurrentHashMap()

    private val logger: Logger? = ServiceLoader.getService(Logger::class.java)

    private fun getSD(fileName: String): SQLiteDataSource {
        FileUtils.forceMkdirParent(File(fileName))
        return sdCache.computeIfAbsent(fileName) {
            val sqLiteConfig = SQLiteConfig()
            sqLiteConfig.setSynchronous(SQLiteConfig.SynchronousMode.OFF)
            sqLiteConfig.setCacheSize(1024 * 8)
            sqLiteConfig.setTempStore(SQLiteConfig.TempStore.MEMORY)
            sqLiteConfig.setPageSize(1024 * 8)
//            sqLiteConfig.busyTimeout = 60000
            val sd = SQLiteConnectionPoolDataSource(sqLiteConfig)
            sd.url = "jdbc:sqlite:$fileName"
            return@computeIfAbsent sd
        }
    }

    companion object {
        fun checkTableExisted(sqlLiteDataSource: SQLiteDataSource, table: String): Boolean {
            val sql = "select count(*) as count from sqlite_master where type = 'table' and name = '$table'"
            return try {
                sqlLiteDataSource.execute(sql) { result -> result.getInt("count") == 1 } ?: false
            } catch (e: Exception) {
                false
            }
        }
    }

    fun getSimpleBeanDAO(fileName: String, cacheName: String): SimpleBeanDAO {
        return SimpleBeanDAO(getSD(fileName), cacheName)
    }

    inner class SimpleBeanDAO(private val sqlLiteDataSource: SQLiteDataSource, private var cacheName: String) {
        init {
            if (!checkTableExisted(sqlLiteDataSource, cacheName)) {
                val sql = "CREATE TABLE $cacheName\n" +
                        "(" +
                        "  ID INTEGER NOT NULL" +
                        "  PRIMARY KEY AUTOINCREMENT," +
                        "  HASH  INTEGER NOT NULL," +
                        "  NAME  TEXT NOT NULL," +
                        "  TINY  TEXT NOT NULL," +
                        "  VALUE TEXT NOT NULL" +
                        ");" +
                        "\n" +
                        "CREATE INDEX ${cacheName}_MD5_INDEX ON $cacheName(HASH);"
                sqlLiteDataSource.execute(sql) {}
            }
        }

        fun listNames(): Array<ByteArray> {
            return try {
                return sqlLiteDataSource
                    .execute(
                        "SELECT NAME FROM $cacheName"
                    ) { resultSet ->
                        val ret: ArrayList<ByteArray> = ArrayList()
                        resultSet.collect { ret.add(it.getString("NAME").decodeBase64()) }
                        return@execute ret.toTypedArray()
                    } ?: emptyArray()
            } catch (e: Exception) {
                emptyArray()
            }
        }

        fun listNameAndTiny(): Array<NameAndTiny> {
            return try {
                return sqlLiteDataSource
                    .execute(
                        "SELECT NAME,TINY FROM $cacheName"
                    ) { resultSet ->
                        val ret: ArrayList<NameAndTiny> = ArrayList()
                        resultSet.collect {
                            ret.add(
                                NameAndTiny(
                                    it.getString("NAME").decodeBase64(),
                                    it.getString("TINY").decodeBase64()
                                )
                            )
                        }
                        return@execute ret.toTypedArray()
                    } ?: emptyArray()
            } catch (e: Exception) {
                emptyArray()
            }
        }

        fun get(name: ByteArray): ByteArray? {
            return try {
                return sqlLiteDataSource
                    .execute<String?>(
                        "SELECT NAME,VALUE FROM $cacheName WHERE HASH = '${name.contentHashCode()}'" +
                                " AND NAME = '${name.encodeBase64()}' LIMIT 1"
                    ) { resultSet -> resultSet.getString("VALUE") }
                    ?.decodeBase64()
            } catch (e: Exception) {
                null
            }
        }

        fun getTiny(name: ByteArray): ByteArray? {
            return try {
                return sqlLiteDataSource
                    .execute<String?>(
                        "SELECT NAME,TINY FROM $cacheName WHERE HASH = '${name.contentHashCode()}'" +
                                " AND NAME = '${name.encodeBase64()}' LIMIT 1"
                    ) { resultSet -> resultSet.getString("TINY") }
                    ?.decodeBase64()
            } catch (e: Exception) {
                null
            }
        }

        fun set(name: ByteArray, value: ByteArray) {
            val base64Name = name.encodeBase64()
            val hash = name.contentHashCode()
            try {
                sqlLiteDataSource.execute("DELETE FROM $cacheName WHERE HASH = $hash AND NAME = '$base64Name'") {}
                sqlLiteDataSource.execute("INSERT INTO $cacheName (HASH,NAME,VALUE) values ('$hash','$base64Name','${value.encodeBase64()}')") {}
            } catch (e: Exception) {
                logger?.traceError(e)
            }
        }

        fun set(name: ByteArray, tiny: ByteArray, value: ByteArray) {
            val base64Name = name.encodeBase64()
            val hash = name.contentHashCode()
            try {
                sqlLiteDataSource.execute("DELETE FROM $cacheName WHERE HASH = $hash AND NAME = '$base64Name'") {}
                sqlLiteDataSource.execute("INSERT INTO $cacheName (HASH,NAME,TINY,VALUE) values ('$hash','$base64Name','${tiny.encodeBase64()}','${value.encodeBase64()}')") {}
            } catch (e: Exception) {
                logger?.traceError(e)
            }
        }

        fun delete(name: ByteArray) {
            val base64Name = name.encodeBase64()
            val hash = name.contentHashCode()
            try {
                sqlLiteDataSource.execute("DELETE FROM $cacheName WHERE HASH = $hash AND NAME = '$base64Name'") {}
            } catch (e: Exception) {
                logger?.traceError(e)
            }
        }
    }

    class NameAndTiny {

        constructor(name: ByteArray?, tiny: ByteArray?) {
            this.name = name
            this.tiny = tiny
        }

        constructor()

        var name: ByteArray? = null

        var tiny: ByteArray? = null
    }
}

fun <T> SQLiteDataSource.execute(sql: String, result: (ResultSet) -> T): T? {
    val statement = this.connection.createStatement()
    statement.use {
        statement.execute(sql)
        statement.resultSet?.use { resultSet ->
            if (resultSet.isClosed) return null
            return result(resultSet)
        }
    }
    return null
}

fun ResultSet.collect(handle: (ResultSet) -> Unit) {
    if (!this.wasNull()) {
        handle(this)
    }
    while (this.next()) {
        handle(this)
    }
}

fun ByteArray.encodeBase64(): String? {
    return Base64.getEncoder().encodeToString(this)
}

fun String.decodeBase64(): ByteArray = Base64.getDecoder().decode(this)