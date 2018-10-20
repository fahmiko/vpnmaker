package id.group1.vpnaccountmaker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import id.group1.vpnaccountmaker.helper.VpnHelper;

public class ManageServer extends AppCompatActivity {
    private VpnHelper dbHelper;
    private EditText ns, location, acc, max, active;
    private RadioGroup protocol;
    private Button back, save;
    private String nameActivity;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_server);

        // Instansiasi Kelas Database Helper
        dbHelper = new VpnHelper(this);

        // Menghubungkan variabel edittext ke layout
        ns = findViewById(R.id.txt_nserver);
        location = findViewById(R.id.txt_location);
        acc = findViewById(R.id.txt_acc);
        max = findViewById(R.id.txt_maxlogin);
        active = findViewById(R.id.txt_active);
        protocol = findViewById(R.id.radioPort);


        // Menghubungkan variabel button ke layout
        back = findViewById(R.id.btn_back);
        save = findViewById(R.id.btn_save);

        // Mendapatkan Intent dari activity
        Intent intent = getIntent();
        // Jika Berhasil mendapatkan intent
        if (intent.getStringExtra("id") != null){
            this.id = Integer.parseInt(intent.getStringExtra("id"));
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM server WHERE id_server = "+id,null);

            if(cursor.getCount() > 0){
                cursor.moveToPosition(0);
                ns.setText(cursor.getString(1));
                location.setText(cursor.getString(2));
                max.setText(String.valueOf(cursor.getString(4)));
                active.setText(String.valueOf(cursor.getString(5)));
                acc.setText(String.valueOf(cursor.getString(6)));
            }

            nameActivity = "Update";
            getSupportActionBar().setTitle(nameActivity);
            save.setText(nameActivity);
        }else{
            nameActivity = "Insert";
            getSupportActionBar().setTitle(nameActivity);
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRadio = protocol.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(idRadio);
                if (!checkValidation()){
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }else{
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if(nameActivity.equals("Update")){
                        db.execSQL("UPDATE server SET name_server='"+ns.getText().toString()+"', " +
                                "location='"+location.getText().toString()+"', " +
                                "port='"+radioButton.getText().toString()+"', " +
                                "max_login="+Integer.parseInt(max.getText().toString())+", " +
                                "active="+Integer.parseInt(active.getText().toString())+", " +
                                "acc_remaining="+Integer.parseInt(acc.getText().toString())+" " +
                                "WHERE id_server="+id);
                        Toast.makeText(getApplicationContext(),"Data berhasil diupdate",Toast.LENGTH_SHORT).show();
                        MainActivity.homeActivity.RefreshData();
                        finish();
                    }else{
                        db.execSQL("INSERT INTO server (name_server,location,port,max_login,active,acc_remaining) VALUES('"+
                                ns.getText().toString()+"','"+
                                location.getText().toString()+"','"+
                                radioButton.getText().toString()+"',"+
                                Integer.parseInt(max.getText().toString())+","+
                                Integer.parseInt(active.getText().toString())+", "+
                                Integer.parseInt(acc.getText().toString())+")");
                        Toast.makeText(getApplicationContext(),"Data berhasil Ditambahkan",Toast.LENGTH_SHORT).show();
                        MainActivity.homeActivity.RefreshData();
                        finish();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean checkValidation(){
        if(ns.getText().toString().equals("") || location.getText().toString().equals("") ||
                acc.getText().toString().equals("") || (protocol.getCheckedRadioButtonId()) == 0 ||
                max.getText().toString().equals("") || active.getText().equals("")){
            return false;
        }else{
            return true;
        }
    }
}
