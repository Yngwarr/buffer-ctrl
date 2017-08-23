package yngwarr.buffer

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class MainActivity : AppCompatActivity() {

    fun generateBufChartData(daysInMonth : Int) : BarData {
        // generate percentage for all the months
        val fDOM = daysInMonth.toFloat()
        val data = List(daysInMonth) {
            val safe = it / fDOM
            BarEntry(it.toFloat(), floatArrayOf(safe, 1f - safe))
        }
        // contains data and style data
        val barSet = BarDataSet(data, "Buffer Usage")
        barSet.stackLabels = arrayOf("Acceptable Zone", "Cautious Zone")
        barSet.colors = listOf(Color.rgb(16, 255, 16), Color.rgb(255, 16, 16))
        //barSet.axisDependency = YAxis.AxisDependency.LEFT
        // TODO add some more style
        val barData = BarData(barSet)
        barData.barWidth = 1f
        return barData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // TODO add button
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // chart data
        // TODO fill chart data
        val chart = findViewById(R.id.chart) as BarChart
        chart.data = generateBufChartData(30)
        chart.setFitBars(true)
        chart.invalidate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
