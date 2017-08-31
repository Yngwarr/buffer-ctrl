package yngwarr.buffer

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.select

class MainActivity : AppCompatActivity() {

    // request code to earn the result of EditActivity
    private val REQ_EDIT = 0
    private val plan = MonthlyPlan(6000f, 1500f)

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
        intent.putExtra("yngwarr.buffer.EXTRA_INCOME", plan.income)
        intent.putExtra("yngwarr.buffer.EXTRA_OUTGO", plan.outgo)
        startActivityForResult(intent, REQ_EDIT)
    }

    // creates a visual interpretation to be shown
    private fun createChart() : CombinedChart {
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
        // TODO add appropriate legend
        return chart
    }

    private fun updateChart() {
        // TODO recalculate a percentage & redraw the chart
        Log.d("MUSHROOM", "Income: ${plan.income}, Outcome: ${plan.outgo}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // TODO add button functionality
        // TODO change the button icon
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // TODO take the plan data from a DB
        database.use{
            // TODO do it async
            val e = DBContract.Companion.PlanEntry
            val plans = select(e.TABLE_NAME, "*")
                    .orderBy(e.COL_DATE, SqlOrderDirection.DESC)
                    .exec {
                        this // это сраный курсор. Живи с этим знанием как хочешь. -_-
                        // TODO parse data, check if null, send to Edit if so
                        // TODO take plans, form a list, let user switch it
                    }
        }

        val dropdownDate = findViewById(R.id.dropdown_date) as Spinner
        val adapter = ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
                listOf("May 2017", "June 2017", "July 2017", "Aug 2017"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdownDate.adapter = adapter

        // creating a visual representation
        val chart = createChart()
        val comboData = CombinedData()
        // TODO change to some real data
        val data = listOf(0f, 0f, .1f, .15f, .16f, .2f, .2f, .2f, .3f, .8f, .8f)
        comboData.setData(generateBarsData(plan.daysInMonth, data.size))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_EDIT -> {
                if (resultCode != Activity.RESULT_OK) return
                if (data == null) return
                val income = data.getFloatExtra("yngwarr.buffer.EXTRA_INCOME", -1f)
                val outgo = data.getFloatExtra("yngwarr.buffer.EXTRA_OUTGO", -1f)
                if (income >= 0) plan.income = income
                if (outgo >= 0) plan.outgo = outgo
                updateChart()
            }
        }
    }
}
