package mao.com.mybeatbox.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

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

    private static final int MAX_SOUND=5;//同时播放音频的个数

    private SoundPool mSoundPool;


    public BeatBox(Context context){
        mAssetManager=context.getAssets();
        mSoundPool=new SoundPool(MAX_SOUND, AudioManager.STREAM_MUSIC,0);
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
            try {
                 String assetPath=SOUND_FOLDER+"/"+soundName;
                 Sound sound=new Sound(assetPath);
                 load(sound);
                 mSound.add(sound);
            } catch (IOException e) {
                Log.e("毛麒添", "Could not load sound " + soundName, e);
                e.printStackTrace();
            }

        }
    }

    //载入全部音频文件
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(assetFileDescriptor, 1);
        sound.setSoundId(soundId);
    }

    //播放音频
    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){
            return;
        }
        mSoundPool.play(soundId,1.0f,1.0f,1,0,1.0f);
    }

    public ArrayList<Sound> getSound() {
        return mSound;
    }

    public void release(){
        mSoundPool.release();
    }
}
