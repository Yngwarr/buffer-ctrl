package yngwarr.buffer

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.DecimalFormat

class DateAxisFormatter : IAxisValueFormatter {
    private val mFormat : DecimalFormat = DecimalFormat("0")
    // TODO get month somewhere
    private var mMonth = "Aug"
    var month : String
        get() = mMonth
        set(value) { mMonth = value }

    override fun getFormattedValue(value: Float, axis: AxisBase?): String
            = "$mMonth ${mFormat.format(value)}"
}
