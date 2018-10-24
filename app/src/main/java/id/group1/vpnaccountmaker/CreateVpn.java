package id.group1.vpnaccountmaker;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateVpn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vpn);
        CircleImageView img = findViewById(R.id.vimg_flag);
        TextView server = findViewById(R.id.vserver_name);
        TextView location = findViewById(R.id.vlocation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().show();

        Intent i = getIntent();
        img.setImageURI(Uri.parse(i.getStringExtra("img")));
        server.setText(i.getStringExtra("server"));
        location.setText(i.getStringExtra("location"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
