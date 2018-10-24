package id.group1.vpnaccountmaker.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.interfaces.DSAPrivateKey;

public class VpnHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "uts_vpn.db";
    private static final int DATABASE_VERSION = 1;

    private static final String ID = "id_server";
    private static final String NAME = "name_server";
    private static final String LOCATION = "location";
    private static final String ACC = "acc_remaining";
    private static final String PORT = "port";
    private static final String MAX = "max_login";
    private static final String ACTIVE ="active";
    private static final String IMG = "img";

    private static final String CREATE_TABLE_SERVER = "CREATE TABLE server (" +
            ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " VARCHAR(50), " +
            LOCATION + " VARCHAR(50), " +
            PORT + " VARCHAR(10), "+
            MAX + " INTEGER, "+
            ACTIVE + " INTEGER, "+
            ACC + " INTEGER, " +
            IMG + " VARCHAR(255))";

    private static final String CREATE_TABLE_USER = "CREATE TABLE users(username VARCHAR(20) PRIMARY KEY, password VARCHAR(50))";

    public VpnHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SERVER);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL("INSERT INTO users (username,password) VALUES ('admin','admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
