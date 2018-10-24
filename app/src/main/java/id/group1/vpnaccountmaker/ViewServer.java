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
    String img, sv, sl;
    private TextView server, location, port, max, acc, active;
    private int id_server, rAcc;

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
        this.id_server = Integer.parseInt(intent.getStringExtra("id"));
        Cursor cursor = db.rawQuery("SELECT * FROM server WHERE id_server="+id_server,null);
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            // Set Variabel dengan Intent
            getSupportActionBar().setTitle("VPN Server "+cursor.getString(2));
            server.setText(cursor.getString(1));
            location.setText(cursor.getString(2));
            port.setText(cursor.getString(3));
            max.setText(cursor.getString(4)+"/Account");
            active.setText(cursor.getString(5)+" Days");
            acc.setText(cursor.getString(6)+" Account/Days");
            rAcc = cursor.getInt(6);
            img = cursor.getString(7);
            sl = cursor.getString(2);
            sv = cursor.getString(1);
        }

     create.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (rAcc <= 0){
                 Toast.makeText(getApplicationContext(),"Account VPN is full",Toast.LENGTH_SHORT).show();
             }else {
                 rAcc -= 1;
                 SQLiteDatabase db = dbHelper.getWritableDatabase();
                 db.execSQL("UPDATE server SET acc_remaining="+rAcc+" WHERE id_server="+id_server);
                 Toast.makeText(getApplicationContext(),"Account VPN Successfull Created",Toast.LENGTH_SHORT).show();
                 MainActivity.homeActivity.RefreshData();
                 Intent i = new Intent(getApplicationContext(), CreateVpn.class);
                 i.putExtra("img",img);
                 i.putExtra("server",sv);
                 i.putExtra("location",sl);
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
