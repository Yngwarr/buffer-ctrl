package yngwarr.buffer

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.db.*
import java.util.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val income_text = findViewById(R.id.edit_income) as EditText
        val outgo_text = findViewById(R.id.edit_outgo) as EditText
        val income = intent.getFloatExtra("yngwarr.buffer.EXTRA_INCOME", -1f)
        val outgo = intent.getFloatExtra("yngwarr.buffer.EXTRA_OUTGO", -1f)

        income_text.setText(if (income < 0) "" else income.toInt().toString(),
                TextView.BufferType.EDITABLE)
        outgo_text.setText(if (outgo < 0) "" else outgo.toInt().toString(),
                TextView.BufferType.EDITABLE)
    }

    fun submitData(view : View) {
        val result = Intent("yngwarr.buffer.SUBMIT_EDITION")
        val income_text = findViewById(R.id.edit_income) as EditText
        val outgo_text = findViewById(R.id.edit_outgo) as EditText
        val income = income_text.text.toString().toFloat()
        val outgo = outgo_text.text.toString().toFloat()

        // sanity check
        if (income <= outgo) {
            outgo_text.error = resources.getString(R.string.error_outgo)
            return
        }

        database.use {
            val e = DBContract.Companion.PlanEntry
            val curr_month = Calendar.getInstance().get(Calendar.MONTH) + 1
            val curr_year = Calendar.getInstance().get(Calendar.YEAR)
            select(e.TABLE_NAME, "COUNT(*)")
                    .whereArgs(" {col_month} = {month} and {col_year} = {year}",
                            "col_month" to e.COL_MONTH,
                            "col_year" to e.COL_YEAR,
                            "month" to curr_month,
                            "year" to curr_year)
                    .exec {
                        val count = parseSingle(IntParser)
                        if (count == 0) {
                            // TODO set custom date
                            insert(e.TABLE_NAME,
                                    e.COL_INCOME to income,
                                    e.COL_OUTGO to outgo,
                                    e.COL_MONTH to curr_month,
                                    e.COL_YEAR to curr_year)
                        } else {
                            update(e.TABLE_NAME, e.COL_INCOME to income, e.COL_OUTGO to outgo)
                                    .whereArgs(" {cm} = {m} and {cy} = {y}",
                                            "cm" to e.COL_MONTH,
                                            "m" to curr_month,
                                            "cy" to e.COL_YEAR,
                                            "y" to curr_year).exec()
                        }
                    }
        }

        result.putExtra("yngwarr.buffer.EXTRA_INCOME", income)
        result.putExtra("yngwarr.buffer.EXTRA_OUTGO", outgo)
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}
