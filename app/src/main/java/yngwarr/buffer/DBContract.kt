package yngwarr.buffer

import android.provider.BaseColumns

class DBContract {
    companion object {
        val DB_NAME = "BufferBudget.db"
        // increment it when changing the structure
        val DB_VERSION = 1
        class PlanEntry : BaseColumns {
            companion object {
                val TABLE_NAME = "plans"
                val COL_MONTH = "month"
                val COL_YEAR = "year"
                val COL_INCOME = "income"
                val COL_OUTGO = "outgo"
            }
        }
        class SpendingEntry : BaseColumns {
            companion object {
                val TABLE_NAME = "spendings"
                val COL_ID = "_id"
                val COL_DAY = "day"
                val COL_MONTH = "month"
                val COL_YEAR = "year"
                val COL_VALUE = "value"
                val COL_COMMENT = "comment"
            }
        }
    }
}