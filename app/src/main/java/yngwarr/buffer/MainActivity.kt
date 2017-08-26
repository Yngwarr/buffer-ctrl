package yngwarr.buffer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class MainActivity : AppCompatActivity() {

    private fun generateBarsData(daysInMonth : Int, visibleDays : Int = daysInMonth) : BarData {
        // generate percentage for all the months
        val fDOM = daysInMonth.toFloat()
        val data = List(visibleDays) {
            val safe = it / fDOM
            BarEntry(it.toFloat(), floatArrayOf(safe, 1f - safe))
        }
        // contains data and style data
        // TODO localization
        val barSet = BarDataSet(data, "Buffer Available")
        barSet.stackLabels = arrayOf("Acceptable Zone", "Cautious Zone")
        barSet.colors = listOf(Color.rgb(16, 255, 16), Color.rgb(255, 16, 16))
        barSet.setDrawValues(false)
        //barSet.axisDependency = YAxis.AxisDependency.LEFT
        // TODO add some more style
        val barData = BarData(barSet)
        barData.barWidth = 1f
        return barData
    }

    private fun generateLineData(vals : List<Float>) : LineData {
        val lineData = LineData()
        val data = List(vals.size) {
            Entry(it.toFloat() /*+ .5f*/, vals[it])
        }

        val lineSet = LineDataSet(data, "Buffer Usage")
        lineSet.color = Color.BLACK
        lineSet.lineWidth = 2.5f
        lineSet.circleRadius = 5f
        lineSet.mode = LineDataSet.Mode.LINEAR
        lineSet.valueTextSize = 10f
        lineSet.valueTextColor = Color.BLACK
        lineSet.setCircleColor(Color.BLACK)
        lineSet.setDrawValues(false)
        lineSet.axisDependency = YAxis.AxisDependency.LEFT

        lineData.addDataSet(lineSet)
        return lineData
    }

    private fun openEditScreen() {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // TODO add button functionality
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // chart data
        val chart = findViewById(R.id.chart) as CombinedChart
        chart.description.isEnabled = false
        chart.setBackgroundColor(Color.WHITE)
        chart.setDrawGridBackground(false)
        chart.setDrawBarShadow(false)
        chart.isHighlightFullBarEnabled = false
        chart.xAxis.valueFormatter = DateAxisFormatter()
        chart.xAxis.granularity = 1f
        chart.axisLeft.valueFormatter = PercentAxisFormatter()
        chart.axisLeft.granularity = .01f
        chart.axisRight.setDrawLabels(false)

        chart.drawOrder = arrayOf(CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE)

        // TODO Legend

        val comboData = CombinedData()
        // TODO change to some real data
        val data = listOf(0f, 0f, .1f, .15f, .16f, .2f, .2f, .2f, .3f, .8f, .8f)
        comboData.setData(generateBarsData(31, data.size))
        comboData.setData(generateLineData(data))
        chart.data = comboData
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

        return when (id) {
            R.id.action_settings -> true
            R.id.action_edit -> {
                openEditScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
