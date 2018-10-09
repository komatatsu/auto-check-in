package app.komatatsu.autocheckin

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private val VERSION = 1
        private val NAME = "roster"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table ticket(hash text primary key, name text, number integer, kind text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 消す
        db.execSQL("drop table if exists ticket")
        // 作る
        onCreate(db)
    }

    fun saveRoster(roster: Roster) {
        val db = this.writableDatabase
        db.delete("ticket", null, null)
        for (ticket in roster.data) {
            val value = ContentValues()
            value.put("hash", ticket.hash)
            value.put("name", ticket.name)
            value.put("number", ticket.number)
            value.put("kind", ticket.kind)
            db.insert("ticket", null, value)
        }
    }

    fun countTicket(): Long {
        val db = this.readableDatabase
        return DatabaseUtils.queryNumEntries(db, "ticket")
    }

    fun search(hash: String): Ticket? {
        val db = this.readableDatabase
        val result = db.rawQuery("select * from ticket where hash = ?", arrayOf(hash))
        if (result.moveToFirst()) {
            return Ticket(result.getString(result.getColumnIndex("hash")),
                    result.getString(result.getColumnIndex("name")),
                    result.getLong(result.getColumnIndex("number")),
                    result.getString(result.getColumnIndex("kind")))
        }
        return null
    }
}
