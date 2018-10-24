package id.group1.vpnaccountmaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.group1.vpnaccountmaker.adapter.ServerAdapter;
import id.group1.vpnaccountmaker.helper.ClickListenner;
import id.group1.vpnaccountmaker.helper.Preference;
import id.group1.vpnaccountmaker.helper.RecyclerTouchListener;
import id.group1.vpnaccountmaker.helper.VpnHelper;
import id.group1.vpnaccountmaker.model.ServerModel;

public class MainActivity extends AppCompatActivity {
    private VpnHelper dbHelper;
    private Preference preference;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fa,logout;
    public static MainActivity homeActivity;
    private List<ServerModel> myServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preference = new Preference(this);
        if(!preference.checkSavedCredetential()) {
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
            Toast.makeText(this,"Login Required",Toast.LENGTH_SHORT).show();
        }
        homeActivity = this;
        dbHelper = new VpnHelper(this);

        mRecyclerView = findViewById(R.id.recycler1);
        fa = findViewById(R.id.fab);
        logout = findViewById(R.id.btn_logout);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RefreshData();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListenner() {
            @Override
            public void onClick(View v, int position) {
                final ServerModel server = myServer.get(position);
                Intent i = new Intent(getApplicationContext(), ViewServer.class);
                i.putExtra("id", ""+server.getId());
                startActivity(i);
            }

            @Override
            public void onLongClick(final View v, int position) {
                final ServerModel server = myServer.get(position);
                final CharSequence[] dialogitem = {"View Server","Update Server","Delete Server"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent i = new Intent(getApplicationContext(), ViewServer.class);
                                i.putExtra("id", ""+server.getId());
                                startActivity(i);
                                break;
                            case 1:
                                Intent update = new Intent(getApplicationContext(),ManageServer.class);
                                update.putExtra("id",""+server.getId());
                                startActivity(update);
                                break;
                            case 2:
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                int id = server.getId();
                                db.execSQL("DELETE FROM server WHERE id_server="+id+"");
                                Snackbar.make(v,"Server "+server.getName_server()+" Telah Terhapus",Snackbar.LENGTH_LONG).show();
                                RefreshData();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        }));

        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insert = new Intent(getApplicationContext(),ManageServer.class);
                startActivity(insert);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.logout();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    public void RefreshData(){
        this.myServer = new ArrayList<>();
        myServer.addAll(getAll());
        mRecyclerView.setAdapter(new ServerAdapter(myServer));
    }

    public List<ServerModel> getAll(){
        List<ServerModel> serverList = new ArrayList<>();
        String selectQuery = "SELECT * FROM server";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                ServerModel server = new ServerModel(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getInt(6),cursor.getString(3),cursor.getString(7));
                serverList.add(server);
            } while (cursor.moveToNext());
        }
        return serverList;
    }
}
