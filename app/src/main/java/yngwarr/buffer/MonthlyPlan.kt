package yngwarr.buffer

import java.util.*

// contains income & outgo data, evaluates buffer and daily limit
class MonthlyPlan(var income : Float, var outgo : Float) {
    // TODO a constructor with a specified date
    // the current month. A year is necessary for leap Februaries
    var year : Int = Calendar.getInstance().get(Calendar.YEAR)
    var month : Int = Calendar.getInstance().get(Calendar.MONTH)
    val daysInMonth : Int
        get() = GregorianCalendar(year, month, 1).getActualMaximum(Calendar.DAY_OF_MONTH)
    // monthly earnings and expenditure
    //var income : Float = 0f
    //var outgo: Float = 0f
    // a part of budget to be buffered
    var bufferPart : Float = 1f / 3f

    val buffer : Float
        get() = (income - outgo) * bufferPart
    val pool : Float
        get() = (income - outgo) - buffer
    val dailyLimit : Float
        get() = pool / daysInMonth
}
