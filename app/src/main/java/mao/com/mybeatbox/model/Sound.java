package mao.com.mybeatbox.model;

/**
 * Created by maoqitian on 2018/4/1 0001.
 * 声音资源管理类
 */

public class Sound {

    private String mAssetPath;//路径
    private String mName;//名称

    private Integer mSoundId;//每个音频的ID

    public Sound(String assetPath){

        mAssetPath=assetPath;
        String[] components  = mAssetPath.split("/");
        String filename=components[components.length-1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer mSoundId) {
        this.mSoundId = mSoundId;
    }
}
