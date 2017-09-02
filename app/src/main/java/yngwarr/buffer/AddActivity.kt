package yngwarr.buffer

import android.app.Activity
import android.os.Bundle
import android.view.View

class AddActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    fun submit(view : View) {
        finish()
    }
}
