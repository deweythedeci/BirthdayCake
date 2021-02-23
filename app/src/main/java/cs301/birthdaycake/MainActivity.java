package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import java.util.concurrent.CancellationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets up CakeController to modify the cake
        CakeView cakeView = findViewById(R.id.cakeview);
        CakeController cakeCtrl = new CakeController(cakeView);

        //links UI elements to the CakeController
        Button blowOutButton = findViewById(R.id.blowOutButton);
        blowOutButton.setOnClickListener(cakeCtrl);

        CompoundButton showCandlesSwitch = findViewById(R.id.candlesSwitch);
        showCandlesSwitch.setOnCheckedChangeListener(cakeCtrl);

        SeekBar candlesSeekBar = findViewById(R.id.candlesSeekBar);
        candlesSeekBar.setOnSeekBarChangeListener(cakeCtrl);
    }

    public void goodbye(View button){
        Log.i("button", "Goodbye");
    }
}
