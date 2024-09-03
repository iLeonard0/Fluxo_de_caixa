package unipar.edu.utfpr.fluxo_de_caixa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_NAME = "fluxo_de_caixa.sqlite"
private const val DATABASE_VERSION = 1

class RepositoryDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


     override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE transacoes ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TIPO TEXT NOT NULL, "
                + "$COLUMN_CATEGORIA TEXT, "
                + "$COLUMN_VALOR REAL, "
                + "$COLUMN_DATA TEXT NOT NULL)")
        db.execSQL(createTable)

         println("teste " + getTotalPorTipo("S"))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACOES")
        onCreate(db)
    }

    companion object {

        private const val TABLE_TRANSACOES = "transacoes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_CATEGORIA = "categoria"
        private const val COLUMN_VALOR = "valor"
        private const val COLUMN_DATA = "data"
    }

    fun addTransacao(transacao: Transacao) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TIPO, transacao.tipo)
            put(COLUMN_CATEGORIA, transacao.categoria)
            put(COLUMN_VALOR, transacao.valor)
            put(COLUMN_DATA, transacao.data)
        }
        db.insert(TABLE_TRANSACOES, null, values)
        db.close()
    }

    fun getAllTransacoes(): List<Transacao> {
        val transacoes = mutableListOf<Transacao>()
        val db = readableDatabase
        val cursor = db.query(TABLE_TRANSACOES, null, null, null, null, null, null)

        cursor.use {
            while (it.moveToNext()) {
                val tipo = it.getString(it.getColumnIndexOrThrow(COLUMN_TIPO))
                val categoria = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORIA))
                val valor = it.getDouble(it.getColumnIndexOrThrow(COLUMN_VALOR))
                val data = it.getString(it.getColumnIndexOrThrow(COLUMN_DATA))
                transacoes.add(Transacao(tipo, categoria, valor, data))
            }
        }

        db.close()
        return transacoes
    }

    fun getTotalPorTipo(tipo: String): Double {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TRANSACOES,
            arrayOf("SUM($COLUMN_VALOR) AS total"),
            "$COLUMN_TIPO = ?",
            arrayOf(tipo),
            null,
            null,
            null
        )

        val total = cursor.use {
            if (it.moveToFirst()) {
                it.getDouble(it.getColumnIndexOrThrow("total"))
            } else {
                0.0
            }
        }

        db.close()
        return total
    }
}
