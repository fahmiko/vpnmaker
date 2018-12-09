package id.group1.vpnaccountmaker;

import id.group1.vpnaccountmaker.adapter.*;
import id.group1.vpnaccountmaker.helper.*;
import id.group1.vpnaccountmaker.model.*;
import id.group1.vpnaccountmaker.rest.*;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Preference preference;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    private FloatingActionButton fa,logout;
    public static MainActivity homeActivity;
    private List<Server> myServer;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Logout");
        getSupportActionBar().show();

        homeActivity = this;
        RefreshData();

    }

    public void RefreshData(){
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetServer> mServerCall = mApiInterface.getServer();
        mServerCall.enqueue(new Callback<GetServer>() {
            @Override
            public void onResponse(Call<GetServer> call, Response<GetServer> response) {
                Log.d("Get Server",response.body().getStatus());
                List<Server> listServer = response.body().getResult();
                mAdapter = new ServerAdapter(listServer);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetServer> call, Throwable t) {
                Log.e("Get Server",t.getMessage());
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        preference.logout();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        return true;
    }
}
