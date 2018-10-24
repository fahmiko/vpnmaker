package id.group1.vpnaccountmaker;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.group1.vpnaccountmaker.helper.Preference;
import id.group1.vpnaccountmaker.helper.VpnHelper;

public class LoginActivity extends AppCompatActivity {
    private VpnHelper dhHelper;
    private EditText username,password;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dhHelper = new VpnHelper(this);
        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        login = findViewById(R.id.btn_login);

        TextView t = findViewById(R.id.btn_register);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dhHelper.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM users WHERE username='"+username.getText().toString()+"'",null);
                if (username.getText().toString().length()<6 || password.getText().toString().length()<6){
                    Toast.makeText(getApplicationContext(),"Failed Registration(min character 6)",Toast.LENGTH_SHORT).show();
                }else if (username.getText().toString().equals("") || password.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"Failed Registration(empty data)",Toast.LENGTH_SHORT).show();
                }else if(c.getCount()>0) {
                    Toast.makeText(getApplicationContext(),"Username Already Exist",Toast.LENGTH_SHORT).show();
                }else {
                    db = dhHelper.getWritableDatabase();
                    db.execSQL("INSERT INTO users (username,password) VALUES('"+username.getText().toString()+"','"+password.getText().toString()+"')");
                    Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference preferencer = new Preference(getApplicationContext());
                if (preferencer.login(username.getText().toString(),password.getText().toString())){
                    Snackbar.make(v,"Login Success",Snackbar.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Login Failed Check Username Password",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
