package yngwarr.buffer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
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
        dropdownMonth.setSelection(7)

        createListMock()
    }

    private fun createListMock() {
        val longList = findViewById(R.id.long_list) as ListView
        val data = arrayListOf(
                // TODO create custom layout w/ date and warnings
                hashMapOf("val" to "[22 Aug] -100", "msg" to "Snack"),
                hashMapOf("val" to "[22 Aug] +500", "msg" to "HW for Joanne"),
                hashMapOf("val" to "[22 Aug] -230", "msg" to "Dinner"),
                hashMapOf("val" to "[20 Aug] -230", "msg" to "Dinner"),
                hashMapOf("val" to "[19 Aug] -230", "msg" to "Dinner"),
                hashMapOf("val" to "[19 Aug] -1100", "msg" to "Steam"),
                hashMapOf("val" to "[17 Aug] -230", "msg" to "Dinner"),
                hashMapOf("val" to "[16 Aug] -100", "msg" to "TableTop Event"),
                hashMapOf("val" to "[16 Aug] -780", "msg" to "Food"),
                hashMapOf("val" to "[15 Aug] -230", "msg" to "Dinner"),
                hashMapOf("val" to "[14 Aug] -320", "msg" to "Dinner"),
                hashMapOf("val" to "[13 Aug] -350", "msg" to "Lunch"),
                hashMapOf("val" to "[12 Aug] -155", "msg" to "Dinner"),
                hashMapOf("val" to "[10 Aug] -312", "msg" to "Dinner"),
                hashMapOf("val" to "[9 Aug] -1300", "msg" to "Present"),
                hashMapOf("val" to "[7 Aug] -230", "msg" to "Dinner"),
                hashMapOf("val" to "[6 Aug] -126", "msg" to "Snack"),
                hashMapOf("val" to "[5 Aug] -230", "msg" to "Dinner")
        )
        longList.adapter = SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
                arrayOf("val", "msg"), intArrayOf(android.R.id.text1, android.R.id.text2))
    }
}
