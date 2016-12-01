package sriyaan.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    private Handler mHandler=new Handler()
    {
        public void handleMessage(android.os.Message msg)

        {

            SharedPreferences sharedPreferences=getSharedPreferences("AC", 0);
            String id=sharedPreferences.getString("id", "");

            if(id.length()>0){
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
            else{
//                startActivity(new Intent(Splash.this, Login.class));
                startActivity(new Intent(Splash.this, Signup.class));
            }

            overridePendingTransition( R.animator.pull_right_in, R.animator.push_out_left );
            finish();


        };

    };
}
