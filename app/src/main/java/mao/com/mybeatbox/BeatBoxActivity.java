package mao.com.mybeatbox;

import android.support.v4.app.Fragment;

import mao.com.mybeatbox.fragment.BeatBoxFragment;

public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return  BeatBoxFragment.newInstance();
    }
}
