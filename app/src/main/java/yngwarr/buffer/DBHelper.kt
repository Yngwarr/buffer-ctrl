package yngwarr.buffer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

class DBHelper(ctx : Context)
    : ManagedSQLiteOpenHelper(ctx, DBContract.DB_NAME, null, DBContract.DB_VERSION) {
    companion object {
        private var instance : DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) : DBHelper {
            if (instance == null) {
                // TODO read something about contexts
                instance = DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val planEntry = DBContract.Companion.PlanEntry
        val spendEntry = DBContract.Companion.SpendingEntry
        Log.d("БАЗА", "СОЗДАНА")
        db.createTable(planEntry.TABLE_NAME, true,
                planEntry.COL_YEAR to INTEGER + PRIMARY_KEY,
                planEntry.COL_MONTH to INTEGER + PRIMARY_KEY,
                planEntry.COL_INCOME to REAL,
                planEntry.COL_OUTGO to REAL)
        db.createTable(spendEntry.TABLE_NAME, true,
                spendEntry.COL_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                spendEntry.COL_YEAR to INTEGER,
                spendEntry.COL_MONTH to INTEGER,
                spendEntry.COL_DAY to INTEGER,
                spendEntry.COL_VALUE to REAL,
                spendEntry.COL_COMMENT to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO find the better way to handle it
        db.dropTable(DBContract.Companion.PlanEntry.TABLE_NAME, true)
        db.dropTable(DBContract.Companion.SpendingEntry.TABLE_NAME, true)
    }
}

// Access property for Context
val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)

