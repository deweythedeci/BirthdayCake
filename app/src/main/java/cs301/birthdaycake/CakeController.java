package cs301.birthdaycake;

public class CakeController {

    private CakeView view;
    private CakeModel model;

    public CakeController(CakeView view){
        this.view = view;
        model = view.getModel();
    }

}
