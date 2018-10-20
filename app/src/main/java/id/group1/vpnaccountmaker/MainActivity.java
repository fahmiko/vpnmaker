package id.group1.vpnaccountmaker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.group1.vpnaccountmaker.adapter.ServerAdapter;
import id.group1.vpnaccountmaker.helper.ClickListenner;
import id.group1.vpnaccountmaker.helper.RecyclerTouchListener;
import id.group1.vpnaccountmaker.helper.VpnHelper;
import id.group1.vpnaccountmaker.model.ServerModel;

public class MainActivity extends AppCompatActivity {
    private VpnHelper dbHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fa;
    List<ServerModel> server = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListenner() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onLongClick(View v, int position) {
                ServerModel sm = server.get(position);
                Toast.makeText(getApplicationContext(),"Lokasi"+ sm.getLocation(),Toast.LENGTH_LONG).show();
            }
        }));

//        fa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                db.execSQL("INSERT INTO server (name_server,location,acc_remaining) VALUES('singapore','singapore',50)");
//                Snackbar.make(v, "Insert Succesfull", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    public void initComponent(){
        dbHelper = new VpnHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        fa = findViewById(R.id.fab);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        server.addAll(getAll());
        mAdapter = new ServerAdapter(getAll());
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<ServerModel> getAll(){
        List<ServerModel> serverList = new ArrayList<>();
        String selectQuery = "SELECT * FROM server";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                ServerModel server = new ServerModel(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getInt(3));
                serverList.add(server);
            } while (cursor.moveToNext());
        }
        return serverList;
    }
}
