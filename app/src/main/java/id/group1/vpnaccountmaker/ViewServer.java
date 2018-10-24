package id.group1.vpnaccountmaker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.group1.vpnaccountmaker.helper.Preference;
import id.group1.vpnaccountmaker.helper.VpnHelper;

public class ViewServer extends AppCompatActivity {
    private VpnHelper dbHelper;
    private Button create;
    private TextView titleServer, server, location, port, max, acc, active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_server);
        initComponents();
    }

    private void initComponents(){
        // Mendapatkan intent dari Main Activity
       Intent intent = getIntent();
       // Set Variabel dengan Layout
       titleServer = findViewById(R.id.label_location);
       server = findViewById(R.id.dt_server);
       location = findViewById(R.id.dt_location);
       port = findViewById(R.id.dt_protocol);
       max = findViewById(R.id.dt_login);
       active = findViewById(R.id.dt_active);
       acc = findViewById(R.id.dt_limit);
       create = findViewById(R.id.button_create);

       // Set action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();

       // Instansiasi dan mendapatkan data dari SQLite Database
        dbHelper = new VpnHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int id_server = Integer.parseInt(intent.getStringExtra("id"));
        Cursor cursor = db.rawQuery("SELECT * FROM server WHERE id_server="+id_server,null);
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            // Set Variabel dengan Intent
            titleServer.setText("Server : "+cursor.getString(2));
            server.setText(cursor.getString(1));
            location.setText(cursor.getString(2));
            port.setText(cursor.getString(3));
            max.setText(cursor.getString(4)+"/Account");
            active.setText(cursor.getString(5)+" Days");
            acc.setText(cursor.getString(6)+" Account/Days");
        }
     create.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Preference pr = new Preference(getApplicationContext());
             if(pr.checkSavedCredetential()){
                 Snackbar.make(v,"Account Berhasil Dibuat",Snackbar.LENGTH_SHORT).show();
             }else {
                 Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                 startActivity(i);
             }
         }
     });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
