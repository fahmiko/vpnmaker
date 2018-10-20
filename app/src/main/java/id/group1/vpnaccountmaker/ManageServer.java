package id.group1.vpnaccountmaker;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import id.group1.vpnaccountmaker.helper.VpnHelper;

public class ManageServer extends AppCompatActivity {
    private VpnHelper dbHelper;
    private EditText ns, location, acc, max, active;
    private RadioGroup protocol;
    private Button back, save;
    private FloatingActionButton upload;
    private String nameActivity,path;
    private CircleImageView img;
    private static final int PICK_IMAGE=100;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_server);

        // Instansiasi Kelas Database Helper
        dbHelper = new VpnHelper(this);

        // Menghubungkan variabel edittext ke layout
        ns = findViewById(R.id.txt_nserver);
        location = findViewById(R.id.txt_location);
        acc = findViewById(R.id.txt_acc);
        max = findViewById(R.id.txt_maxlogin);
        active = findViewById(R.id.txt_active);
        protocol = findViewById(R.id.radioPort);
        img = findViewById(R.id.image_manage);


        // Menghubungkan variabel button ke layout
        back = findViewById(R.id.btn_back);
        save = findViewById(R.id.btn_save);
        upload = findViewById(R.id.btn_upload);

        // Mendapatkan Intent dari activity
        final Intent intent = getIntent();
        // Jika Berhasil mendapatkan intent
        if (intent.getStringExtra("id") != null){
            this.id = Integer.parseInt(intent.getStringExtra("id"));
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM server WHERE id_server = "+id,null);

            if(cursor.getCount() > 0){
                cursor.moveToPosition(0);
                ns.setText(cursor.getString(1));
                location.setText(cursor.getString(2));
                max.setText(String.valueOf(cursor.getString(4)));
                active.setText(String.valueOf(cursor.getString(5)));
                acc.setText(String.valueOf(cursor.getString(6)));
                img.setImageURI(Uri.parse(cursor.getString(7)));
                this.path = cursor.getString(7);
            }

            nameActivity = "Update";
            getSupportActionBar().setTitle(nameActivity);
            save.setText(nameActivity);
        }else{
            this.path = null;
            nameActivity = "Insert";
            getSupportActionBar().setTitle(nameActivity);
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRadio = protocol.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(idRadio);
                if (!checkValidation()){
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }else{
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if(nameActivity.equals("Update")){
                        db.execSQL("UPDATE server SET name_server='"+ns.getText().toString()+"', " +
                                "location='"+location.getText().toString()+"', " +
                                "port='"+radioButton.getText().toString()+"', " +
                                "max_login="+Integer.parseInt(max.getText().toString())+", " +
                                "active="+Integer.parseInt(active.getText().toString())+", " +
                                "acc_remaining="+Integer.parseInt(acc.getText().toString())+", " +
                                "img='"+path+"' "+
                                "WHERE id_server="+id);
                        Toast.makeText(getApplicationContext(),"Data berhasil diupdate",Toast.LENGTH_SHORT).show();
                        MainActivity.homeActivity.RefreshData();
                        finish();
                    }else{
                        db.execSQL("INSERT INTO server (name_server,location,port,max_login,active,acc_remaining,img) VALUES('"+
                                ns.getText().toString()+"','"+
                                location.getText().toString()+"','"+
                                radioButton.getText().toString()+"',"+
                                Integer.parseInt(max.getText().toString())+","+
                                Integer.parseInt(active.getText().toString())+", "+
                                Integer.parseInt(acc.getText().toString())+", '"+
                                path+"')");
                        Toast.makeText(getApplicationContext(),"Data berhasil Ditambahkan",Toast.LENGTH_SHORT).show();
                        MainActivity.homeActivity.RefreshData();
                        finish();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(i, PICK_IMAGE);
            }
        });
    }

    private boolean checkValidation(){
        if(ns.getText().toString().equals("") || location.getText().toString().equals("") ||
                acc.getText().toString().equals("") || (protocol.getCheckedRadioButtonId()) == 0 ||
                max.getText().toString().equals("") || active.getText().equals("") ||
                this.path.equals(null)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            Uri uri = data.getData();
            String x = getPath(uri);
            this.path = x;
            img.setImageURI(Uri.parse(x));
        }
    }

    private String getPath(Uri uri){
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

}
