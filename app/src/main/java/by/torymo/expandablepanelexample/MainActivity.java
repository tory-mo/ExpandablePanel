package by.torymo.expandablepanelexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import by.torymo.expandablepanel.ExpandablePanelLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view1 = (TextView) LayoutInflater.from(this).inflate(R.layout.panel1, null);
        view1.setText("25.04.18 в 17:00 ШТОРМ Новогрудок (Беларусь) 25-го апреля в 17:05, Гроза на станции, Северо-северо-западное направление, ср. скорость 1 м\\/с, макс. скорость 4 м\\/с(порыв), наблюдаемая погода - слабая гроза с осадками в срок наблюдения.");
        TextView view2 = (TextView) LayoutInflater.from(this).inflate(R.layout.panel2, null);
        view2.setText("Теплее всего: +23,3С - в 1995 г.\nХолоднее всего: -4,8С - в 1988 г.\nБольше всего осдков: 24 в 1963 г.");

        ExpandablePanelLayout panel1 = findViewById(R.id.panel1);
        ExpandablePanelLayout panel2 = findViewById(R.id.panel2);

        panel1.setContent(view1);
        panel2.setContent(view2);
    }
}
