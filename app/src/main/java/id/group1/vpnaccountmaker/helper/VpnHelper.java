package id.group1.vpnaccountmaker.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private static final String CREATE_TABLE_SERVER = "CREATE TABLE server (" +
            ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " varchar(50), " +
            LOCATION + " varchar(50), " +
            PORT + " varchar(10), "+
            MAX + " INTEGER, "+
            ACTIVE + " INTEGER, "+
            ACC + " INTEGER)";

    public VpnHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SERVER);
        db.execSQL("INSERT INTO server values (1,'sgdo.freevpn.net','Singapore','TCP',2,10,50)");
        db.execSQL("INSERT INTO server values (2,'id.freevpn.net','Indonesia','UDP',2,10,25)");
        db.execSQL("INSERT INTO server values (3,'jp.freevpn.net','Japan','TCP',2,7,10)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
