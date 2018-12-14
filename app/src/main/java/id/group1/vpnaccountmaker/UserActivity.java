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

public class UserActivity extends MenuItem {
    private Preference preference;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    private FloatingActionButton fa,logout;
    public static UserActivity userActivity;
    private List<User> listUser;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        preference = new Preference(this);
        if (!preference.checkSavedCredetential()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            Toast.makeText(this, "Login Required", Toast.LENGTH_SHORT).show();
        }
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler2);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getSupportActionBar().setTitle("Data User");
        getSupportActionBar().show();

        userActivity = this;
        RefreshData();

        fa = findViewById(R.id.fab);
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageUser.class);
                startActivity(i);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListenner() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onLongClick(View v, int position) {
                final User user = listUser.get(position);
                final CharSequence[] dialogitem = {"Update User","Delete User"};
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);

                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent i = new Intent(getApplicationContext(), ManageUser.class);
                                i.putExtra("id_user",String.valueOf(user.getId_user()));
                                i.putExtra("name",user.getName());
                                i.putExtra("username",user.getUsername());
                                i.putExtra("password",user.getPassword());
                                i.putExtra("photo",user.getPhoto());
                                startActivity(i);
                                break;
                            case 1:
                                RequestBody reqIdUser =
                                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                                (user.getId_user().isEmpty())?
                                                        "" : user.getId_user());
                                RequestBody reqAction =
                                        MultipartBody.create(MediaType.parse("multipart/form-data"), "delete");

                                Call<GetUser> callDelete = mApiInterface.deleteUser(reqIdUser,reqAction);
                                callDelete.enqueue(new Callback<GetUser>() {
                                    @Override
                                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                                        if(response.message().toString().equals("Internal Server Error")){
                                            Toast.makeText(getApplicationContext(),"Error, Cek tabel master",Toast.LENGTH_SHORT).show();
                                        }
                                        RefreshData();
                                    }

                                    @Override
                                    public void onFailure(Call<GetUser> call, Throwable t) {

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
        Call<GetUser> mUserCall = mApiInterface.getUser();
        mUserCall.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                Log.d("Get User",response.body().getStatus());
                listUser = response.body().getResult();
                mAdapter = new UserAdapter(listUser,getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                Log.e("Get User",t.getMessage());
            }
        });
    }
}
