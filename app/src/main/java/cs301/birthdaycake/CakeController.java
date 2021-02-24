package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
                                        SeekBar.OnSeekBarChangeListener, View.OnTouchListener{

    private CakeView cakeView;
    private CakeModel model;

    /*public float x;
    public float y;*/

    public CakeController(CakeView cakeView){
        this.cakeView = cakeView;
        model = cakeView.getModel();
        this.cakeView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        model.litCandles = false;
        cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        model.showCandles = isChecked;
        cakeView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        model.amountCandles = progress;
        cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
    public void onStopTrackingTouch(SeekBar seekBar) {}

    public boolean onTouch(View view, MotionEvent motionEvent){
        model.x = motionEvent.getX();
        model.y = motionEvent.getY();

        this.cakeView.invalidate();
        return false;
    }

}
