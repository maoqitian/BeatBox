package mao.com.mybeatbox.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by maoqitian on 2018/4/1 0001.
 * 播放声音文件 对象
 */

public class BeatBox {

    private static final String SOUND_FOLDER="sample_sounds";
    private AssetManager mAssetManager;

    private ArrayList<Sound> mSound=new ArrayList<>();

    public BeatBox(Context context){
        mAssetManager=context.getAssets();
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssetManager.list(SOUND_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for (String soundName:soundNames) {
            String assetPath=SOUND_FOLDER+"/"+soundName;
            Sound sound=new Sound(assetPath);
            mSound.add(sound);
        }
    }

    public ArrayList<Sound> getSound() {
        return mSound;
    }
}
