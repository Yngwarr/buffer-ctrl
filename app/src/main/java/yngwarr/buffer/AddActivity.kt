package yngwarr.buffer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class AddActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        if (intent.getBooleanExtra("yngwarr.buffer.EXTRA_SUB", false)) {
            val value_text = findViewById<EditText>(R.id.add_value)
            value_text.setText("-", TextView.BufferType.EDITABLE)
        }
    }

    fun submit(view : View) {
        val result = Intent("yngwarr.buffer.SUBMIT_ADDITION")
        val val_field = findViewById<EditText>(R.id.add_value)
        val msg_field = findViewById<EditText>(R.id.add_comment)
        result.putExtra("yngwarr.buffer.EXTRA_VAL", val_field.text.toString())
        result.putExtra("yngwarr.buffer.EXTRA_MSG", msg_field.text.toString())
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}
