package id.group1.vpnaccountmaker;

import id.group1.vpnaccountmaker.adapter.*;
import id.group1.vpnaccountmaker.helper.*;
import id.group1.vpnaccountmaker.model.*;
import id.group1.vpnaccountmaker.rest.*;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends MenuItem {
    private Preference preference;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    private FloatingActionButton fa,logout;
    public static MainActivity homeActivity;
    private List<Server> listServer;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        preference = new Preference(this);
        if (!preference.checkSavedCredetential()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            Toast.makeText(this, "Login Required", Toast.LENGTH_SHORT).show();
        }
        */
        Log.d("Msg","WELCOME");
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getSupportActionBar().setTitle("Admin");
        getSupportActionBar().show();

        homeActivity = this;
        RefreshData();

        fa = findViewById(R.id.fab);
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageServer.class);
                startActivity(i);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListenner() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onLongClick(View v, int position) {
                final Server server = listServer.get(position);
                final CharSequence[] dialogitem = {"Update Server","Delete Server"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent i = new Intent(getApplicationContext(), ManageServer.class);
                                i.putExtra("id_server",String.valueOf(server.getIdServer()));
                                i.putExtra("name_server",server.getName_server());
                                i.putExtra("location",server.getLocation());
                                i.putExtra("acc_remaining",server.getAcc_remaining());
                                i.putExtra("flag_image",server.getFlag_image());
                                startActivity(i);
                                break;
                                case 1:
                                    RequestBody reqIdServer =
                                            MultipartBody.create(MediaType.parse("multipart/form-data"),
                                                    (server.getIdServer().isEmpty())?
                                                            "" : server.getIdServer());
                                    RequestBody reqAction =
                                            MultipartBody.create(MediaType.parse("multipart/form-data"), "delete");

                                    Call<GetServer> callDelete = mApiInterface.deleteServer(reqIdServer,reqAction);
                                    callDelete.enqueue(new Callback<GetServer>() {
                                        @Override
                                        public void onResponse(Call<GetServer> call, Response<GetServer> response) {
                                            if(response.message().toString().equals("Internal Server Error")){
                                                Toast.makeText(getApplicationContext(),"Error, Cek tabel master",Toast.LENGTH_SHORT).show();
                                            }
                                                RefreshData();

                                        }

                                        @Override
                                        public void onFailure(Call<GetServer> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(),"Error, Cek tabel master",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                        }
                    }
                }).create().show();
            }
        }));
    }

    public void RefreshData(){
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetServer> mServerCall = mApiInterface.getServer();
        mServerCall.enqueue(new Callback<GetServer>() {
            @Override
            public void onResponse(Call<GetServer> call, Response<GetServer> response) {
                Log.d("Get Server",response.body().getStatus());
                listServer = response.body().getResult();
                mAdapter = new ServerAdapter(listServer,getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetServer> call, Throwable t) {
                Log.e("Get Server",t.getMessage());
            }
        });
    }
}
