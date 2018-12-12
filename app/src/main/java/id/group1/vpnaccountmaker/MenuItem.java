package id.group1.vpnaccountmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

public class MenuItem extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Intent mIntent;

        switch (item.getItemId()){
            case R.id.menuDtServer:
                mIntent = new Intent(this,MainActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.menuDtUser:
                mIntent = new Intent(this,UserActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.menuDtAcc:
                mIntent = new Intent(this,AccountActivity.class);
                startActivity(mIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
