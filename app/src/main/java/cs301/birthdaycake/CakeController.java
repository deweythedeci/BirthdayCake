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

    public CakeController(CakeView cakeView){
        this.cakeView = cakeView;
        this.cakeView.setOnTouchListener(this);
        model = cakeView.getModel();
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        //sets a balloon to be drawn at the location of the touch
        model.showBalloon = true;
        model.balloonX = motionEvent.getX();
        model.balloonY = motionEvent.getY();

        //redraws the entire CakeView
        this.cakeView.invalidate();

        return false;
    }

}
