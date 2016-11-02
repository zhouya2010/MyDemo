package app.example.com.mydemo.dagger;

import javax.inject.Inject;

/**
 * Created by dell on 2016/11/1.
 */

public class Lily extends Flower {

    @Inject
    Lily() {}

    @Override
    public String whisper() {
        return "纯洁";
    }
}
