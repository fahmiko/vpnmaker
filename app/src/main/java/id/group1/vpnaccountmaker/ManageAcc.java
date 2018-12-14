package id.group1.vpnaccountmaker;


import android.app.DatePickerDialog;
import android.content.Intent;
import id.group1.vpnaccountmaker.adapter.*;
import id.group1.vpnaccountmaker.helper.*;
import id.group1.vpnaccountmaker.model.*;
import id.group1.vpnaccountmaker.rest.*;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAcc extends AppCompatActivity {
    private EditText user, server, active;
    private Button back, save;
    private FloatingActionButton upload;
    private String nameActivity,path;
    private CircleImageView img;
    private String id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_acc);

        initComponents();
        checkUpdate();

        final EditText cal = findViewById(R.id.macc_active);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                MultipartBody.Part body = null;
                if (!imagePath.isEmpty()){
                    // Buat file dari image yang dipilih
                    File file = new File(imagePath);

                    // Buat RequestBody instance dari file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

                    // MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("photo", file.getName(),
                            requestFile);
                }
                RequestBody reqUser = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (user.getText().toString().isEmpty())?"":user.getText().toString());
                RequestBody reqServer = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (server.getText().toString().isEmpty())?"":server.getText().toString());
                RequestBody reqActive = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (active.getText().toString().isEmpty())?"":active.getText().toString());

                Call<GetAcc> mAccCall;
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                mAccCall = mApiInterface.postAcc(body, reqServer,
                        reqUser, reqActive, reqAction);

                mAccCall.enqueue(new Callback<GetAcc>() {
                    @Override
                    public void onResponse(Call<GetAcc> call, Response<GetAcc> response) {
//                      Log.d("Insert Retrofit",response.body().getMessage());
                        if (response.body().getMessage().equals("server")) {
                            Toast.makeText(getApplicationContext(), "Server/Id user tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "UPDATE Success", Toast.LENGTH_SHORT).show();
                            AccountActivity.accActivity.RefreshData();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAcc> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "UPDATE Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initComponents(){
        // Menghubungkan variabel edittext ke layout
        user = findViewById(R.id.macc_user);
        server = findViewById(R.id.macc_server);
        active = findViewById(R.id.macc_active);
        // Menghubungkan variabel button ke layout
        save = findViewById(R.id.btn_save);
    }

    public void showDialog(){
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                active.setText(simpleDateFormat.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void checkUpdate(){
        Intent intent = getIntent();
        if(intent.getStringExtra("id_acc")==null){
            getSupportActionBar().setTitle("Insert Acc");
        }else{
            id = intent.getStringExtra("id_acc");
            getSupportActionBar().setTitle("Update id "+intent.getStringExtra("id_acc"));
            user.setText(intent.getStringExtra("user"));
            server.setText(intent.getStringExtra("server"));
            active.setText(intent.getStringExtra("active"));
            save.setText("Update");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(getApplicationContext(),AccountActivity.class);
        startActivity(back);
        return true;
    }

}
