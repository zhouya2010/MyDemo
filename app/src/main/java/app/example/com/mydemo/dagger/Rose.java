package app.example.com.mydemo.dagger;

import javax.inject.Inject;

/**
 * Created by dell on 2016/11/1.
 */

public class Rose extends Flower {

    @Inject
    public Rose() {}

    @Override
    public String whisper() {
        return "热恋";
    }
}
