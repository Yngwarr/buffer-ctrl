package yngwarr.buffer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.text.DateFormatSymbols

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val dropdownMonth = findViewById(R.id.dropdown_alist_month) as Spinner
        val dropdownYear = findViewById(R.id.dropdown_alist_year) as Spinner
        // TODO take from DB
        val years = listOf("2017")
        val months = DateFormatSymbols().months
        val adapterYear = ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item, years)
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val adapterMonth = ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item, months)
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdownMonth.adapter = adapterMonth
        dropdownYear.adapter = adapterYear
    }
}
