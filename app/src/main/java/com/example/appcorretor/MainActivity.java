package com.example.appcorretor;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.appcorretor.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {

    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.buttonAdd2);
        btn2 = (Button) findViewById(R.id.buttonVisualizar2);

        btn1.setOnClickListener(veTelaAdd);
        btn2.setOnClickListener(veTelaView);
    }

    View.OnClickListener veTelaAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adcCasaBtn();
        }
    };

    View.OnClickListener veTelaView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            veCasaBtn();
        }
    };

    private void adcCasaBtn(){
        Intent intent = new Intent(this, AdcCasa.class);
        startActivity(intent);
    }

    private void veCasaBtn(){
        Intent intent = new Intent(this, VeCasa.class);
        startActivity(intent);
    }
}