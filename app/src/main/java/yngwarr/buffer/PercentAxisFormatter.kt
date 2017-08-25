package yngwarr.buffer

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.DecimalFormat

class PercentAxisFormatter : IAxisValueFormatter {
    private val mFormat : DecimalFormat = DecimalFormat("0")
    override fun getFormattedValue(value: Float, axis: AxisBase?): String
            = "${mFormat.format(value * 100)}%"
}