package id.group1.vpnaccountmaker.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import javax.xml.parsers.FactoryConfigurationError;

import id.group1.vpnaccountmaker.MainActivity;
import id.group1.vpnaccountmaker.ManageServer;

public class Preference {
//    private VpnHelper dbHelper;
    private Context context;

    public Preference(Context context) {
        this.context = context;
//        this.dbHelper = new VpnHelper(context);
    }

    public boolean login(String username, String password){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username='"+username+"' AND password='"+password+"'",null);
        if(username.equals("admin") && password.equals("admin")){
            saveCredentials(username,password);
            return true;
        }else {
            return false;
        }
    }

    public void logout(){
        SharedPreferences sf = context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
    }

    public void saveCredentials(String username, String password){
        SharedPreferences sf = context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();

        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
    }

    public boolean checkSavedCredetential(){
        SharedPreferences sf = context.getSharedPreferences("login",Context.MODE_PRIVATE);

        String username = sf.getString("username","");
        String password = sf.getString("password","");

        if (username.equals("") || password.equals("")){
            return false;
        }else {
            return true;
        }
    }
}
