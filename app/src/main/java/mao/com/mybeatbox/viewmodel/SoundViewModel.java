package mao.com.mybeatbox.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import mao.com.mybeatbox.model.BeatBox;
import mao.com.mybeatbox.model.Sound;

/**
 * Created by maoqi on 2018/4/1 0001.
 * 视图模型对象
 */

public class SoundViewModel extends BaseObservable{

    private Sound mSound;
    private BeatBox mBeatBox;

    public SoundViewModel(BeatBox beatBox){
        mBeatBox=beatBox;
    }

    public Sound getSound() {
        return mSound;
    }

    public void setSound(Sound mSound) {
        this.mSound = mSound;
        notifyChange();//跟新
    }
    @Bindable //注解视图模型里可绑定的属性
    public String getTitle(){
        return mSound.getName();
    }

    public void onButtonClicked() {
        mBeatBox.play(mSound);
    }
}
