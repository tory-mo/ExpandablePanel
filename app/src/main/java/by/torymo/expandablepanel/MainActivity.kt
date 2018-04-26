package by.torymo.expandablepanel


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view1 = LayoutInflater.from(this).inflate(R.layout.panel1, null, false) as TextView
        view1.text = "25.04.18 в 17:00 ШТОРМ Новогрудок (Беларусь) 25-го апреля в 17:05, Гроза на станции, Северо-северо-западное направление, ср. скорость 1 м\\/с, макс. скорость 4 м\\/с(порыв), наблюдаемая погода - слабая гроза с осадками в срок наблюдения.";
        val view2 = LayoutInflater.from(this).inflate(R.layout.panel2, null, false) as TextView
        view2.text = "Теплее всего: +23,3С - в 1995 г.\nХолоднее всего: -4,8С - в 1988 г.\nБольше всего осдков: 24 в 1963 г."

        panel1.setContent(view1)
        panel2.setContent(view2)

    }
}
