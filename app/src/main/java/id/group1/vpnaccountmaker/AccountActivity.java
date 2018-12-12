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

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends MenuItem {
    private Preference preference;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    private FloatingActionButton fa,logout;
    public static AccountActivity accActivity;
    private List<Acc> listAcc;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        /*
        preference = new Preference(this);
        if (!preference.checkSavedCredetential()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            Toast.makeText(this, "Login Required", Toast.LENGTH_SHORT).show();
        }
        */
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler3);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getSupportActionBar().setTitle("Data Account");
        getSupportActionBar().show();

        accActivity = this;
        RefreshData();

        fa = findViewById(R.id.fab);
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageAcc.class);
                startActivity(i);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListenner() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onLongClick(View v, int position) {
                final Acc acc = listAcc.get(position);
                final CharSequence[] dialogitem = {"Update Account","Delete Account"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);

                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent i = new Intent(getApplicationContext(), ManageUser.class);
                                i.putExtra("id_acc",String.valueOf(acc.getId()));
                                i.putExtra("user",acc.getUser());
                                i.putExtra("server",acc.getServer());
                                i.putExtra("active",acc.getActive());
                                startActivity(i);
                                break;
                            case 1:
                                RequestBody reqIdAcc =
                                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                                (acc.getId().isEmpty())?
                                                        "" : acc.getId());
                                RequestBody reqAction =
                                        MultipartBody.create(MediaType.parse("multipart/form-data"), "delete");

                                Call<GetAcc> callDelete = mApiInterface.deleteAcc(reqIdAcc,reqAction);
                                callDelete.enqueue(new Callback<GetAcc>() {
                                    @Override
                                    public void onResponse(Call<GetAcc> call, Response<GetAcc> response) {
                                        RefreshData();
                                    }

                                    @Override
                                    public void onFailure(Call<GetAcc> call, Throwable t) {

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
        Call<GetAcc> mAccCall = mApiInterface.getAcc();
        mAccCall.enqueue(new Callback<GetAcc>() {
            @Override
            public void onResponse(Call<GetAcc> call, Response<GetAcc> response) {
                Log.d("Get Acc",response.body().getStatus());
                listAcc = response.body().getResult();
                mAdapter = new AccAdapter(listAcc,getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetAcc> call, Throwable t) {
                Log.e("Get Acc",t.getMessage());
            }
        });
    }
}
