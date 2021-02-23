package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.os.Bundle;

import java.util.concurrent.CancellationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CakeView cakeView = findViewById(R.id.cakeview);
        CakeController cakeCtrl = new CakeController(cakeView);
    }

    public void goodbye(View button){
        Log.i("button", "Goodbye");
    }
}
