package id.group1.vpnaccountmaker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ManageAcc extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_acc);
        final EditText cal = findViewById(R.id.macc_active);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.setText(Calendar.YEAR);
                cal.setText(Calendar.MONTH);
                cal.setText(Calendar.DAY_OF_MONTH);
            }
        };

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(this,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH)).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        cal.setText(Calendar.YEAR);
        cal.setText(Calendar.MONTH);
        cal.setText(Calendar.DAY_OF_MONTH);
    }
}
