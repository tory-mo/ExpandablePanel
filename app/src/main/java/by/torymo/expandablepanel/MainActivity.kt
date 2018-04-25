package by.torymo.expandablepanel


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view1 = LayoutInflater.from(this).inflate(R.layout.panel1, null, false)
        val view2 = LayoutInflater.from(this).inflate(R.layout.panel2, null, false)
        panel1.setContent(view1)
        panel2.setContent(view2)
    }
}
