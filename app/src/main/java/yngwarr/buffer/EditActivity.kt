package yngwarr.buffer

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView

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

        result.putExtra("yngwarr.buffer.EXTRA_INCOME", income)
        result.putExtra("yngwarr.buffer.EXTRA_OUTGO", outgo)
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}
