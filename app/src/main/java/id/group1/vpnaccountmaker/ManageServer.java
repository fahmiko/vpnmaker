package id.group1.vpnaccountmaker;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import id.group1.vpnaccountmaker.helper.VpnHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageServer extends AppCompatActivity {
    private EditText ns, location, acc;
    private Button back, save;
    private FloatingActionButton upload;
    private String nameActivity,path;
    private CircleImageView img;
    private String id;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_server);

        initComponents();
        checkUpdate();

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
                    body = MultipartBody.Part.createFormData("flag_image", file.getName(),
                            requestFile);
                }
                RequestBody reqName_server = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (ns.getText().toString().isEmpty())?"":ns.getText().toString());
                RequestBody reqLocation = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (location.getText().toString().isEmpty())?"":location.getText().toString());
                RequestBody reqAcc_remaining = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (acc.getText().toString().isEmpty())?"":acc.getText().toString());

                Call<GetServer> mServerCall;

                if(id!=null) {
                    RequestBody reqId_server = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            (id.isEmpty())?"":id);
                    RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            "update");
                    mServerCall = mApiInterface.putServer(body, reqId_server,reqName_server,
                            reqLocation, reqAcc_remaining, reqAction );
                }else{
                    RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                            "insert");
                    mServerCall = mApiInterface.postServer(body, reqName_server,
                            reqLocation, reqAcc_remaining, reqAction );
                }



                mServerCall.enqueue(new Callback<GetServer>() {
                    @Override
                    public void onResponse(Call<GetServer> call, Response<GetServer> response) {
//                      Log.d("Insert Retrofit",response.body().getMessage());
                        if (response.body().getStatus().equals("failed")) {
                            Toast.makeText(getApplicationContext(), "Proses Insert Gagal", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "UPDATE Success", Toast.LENGTH_SHORT).show();
                            MainActivity.homeActivity.RefreshData();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetServer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "UPDATE Gagal", Toast.LENGTH_SHORT).show();
                    }
                    });
                }
            });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_PICK);
                    Intent intentChoose = Intent.createChooser(
                            galleryIntent,
                            "Pilih foto untuk di-upload");
                    startActivityForResult(intentChoose, 10);
            }
        });
    }

    private void initComponents(){
        // Menghubungkan variabel edittext ke layout
        ns = findViewById(R.id.txt_nserver);
        location = findViewById(R.id.txt_location);
        acc = findViewById(R.id.txt_acc);
        img = findViewById(R.id.image_manage);


        // Menghubungkan variabel button ke layout
        save = findViewById(R.id.btn_save);
        upload = findViewById(R.id.btn_upload);
    }

    public void checkUpdate(){
        Intent intent = getIntent();
        if(intent.getStringExtra("id_server")==null){
            getSupportActionBar().setTitle("Insert Server");
            getSupportActionBar().show();
        }else{
            id = intent.getStringExtra("id_server");
            getSupportActionBar().setTitle("UPDATE ID "+intent.getStringExtra("id_server"));
            getSupportActionBar().show();
            ns.setText(intent.getStringExtra("name_server"));
            location.setText(intent.getStringExtra("location"));
            acc.setText(intent.getStringExtra("acc_remaining"));
            Glide.with(getApplicationContext()).load(ApiClient.BASE_URL+"uploads/"+intent.getStringExtra("flag_image")).into(img);
            save.setText("Update");
        }
    }

    private boolean checkValidation(){
        if(ns.getText().toString().equals("") || location.getText().toString().equals("") ||
                acc.getText().toString().equals("")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10){
            if (data==null){
                Toast.makeText(getApplicationContext(), "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            }

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath =cursor.getString(columnIndex);

                Picasso.with(getApplicationContext()).load(new File(imagePath)).fit().into(img);
//                Glide.with(mContext).load(new File(imagePath)).into(mImageView);
                cursor.close();
            }else{
                Toast.makeText(getApplicationContext(), "Foto gagal di-load", Toast.LENGTH_LONG).show();
            }
        }
    }
}
