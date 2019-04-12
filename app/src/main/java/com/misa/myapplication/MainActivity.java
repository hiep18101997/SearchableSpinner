package com.misa.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private SearchableSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=findViewById(R.id.spinner);
        String[] data = getResources().getStringArray(R.array.unit_array);
        spinner.setData(data);
        spinner.setDefaultText("Chọn đơn vị tiền");
        spinner.setInvalidTextColor(getResources().getColor(R.color.colorAccent));
        spinner.setSelectionListener(new SearchableSpinner.OnSelectionListener() {
            @Override
            public void onSelect(int spinnerId, int position, String value) {

            }
        });

    }
}
