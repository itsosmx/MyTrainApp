package mytrain.osmx.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TimeTable : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
    supportActionBar?.title = "الخدمات"

    }

    fun main(args: Array<String>) {
        // string to be split to lines
        var str: String = "Kotlin Tutorial.\nLearn Kotlin Programming with Ease.\rLearn Kotlin Basics."

        // splitting string using lines() function
        var lines = str.lines()

        // printing lines
        lines.forEach { println(it) }
    }
}