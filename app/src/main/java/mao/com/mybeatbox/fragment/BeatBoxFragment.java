package mao.com.mybeatbox.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import mao.com.mybeatbox.R;
import mao.com.mybeatbox.databinding.FragmentBeatBoxBinding;
import mao.com.mybeatbox.databinding.ListItemSoundBinding;
import mao.com.mybeatbox.model.BeatBox;
import mao.com.mybeatbox.model.Sound;
import mao.com.mybeatbox.viewmodel.SoundViewModel;

/**
 * Created by maoqitian on 2018/3/31 0031.
 *
 */

public class BeatBoxFragment extends Fragment{

    private BeatBox mBeatBox;

    private SeekBar mSeekBar;        // seekbar 引用
    private TextView mPlayTitle;    // 显示播放速率的 TextView
    private float mProgress;        // 保存 progress
    private Sound mSound;            // 保存 sound 变量

    public static BeatBoxFragment newInstance() {

        Bundle args = new Bundle();

        BeatBoxFragment fragment = new BeatBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);该方法可保留设备旋转和对象保存fragment。已保留的fragment不会随activity一起被销毁
        // 相反，它会一直保留，并在需要时原封 不动地转给新的activity。
        // 对于已保留的fragment实例，其全部实例变量（如mBeatBox）的值也会保持不变，
        // 因此可放 心继续使用。旋转屏幕音频也可以据需播放
        setRetainInstance(true);
        mBeatBox=new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding=DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box,container,false);
        //设置RecycleView 展示方式
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSoundList()));
        mSeekBar=getActivity().findViewById(R.id.seek_bar);
        mPlayTitle=getActivity().findViewById(R.id.play_control_title);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                   mProgress=0.5F;
                }else {
                    mProgress=progress*0.015F+0.5F;
                }
                mPlayTitle.setText("Rate"+mProgress+"x");
                mBeatBox.setmRate(mProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //SeekBar 触碰中..
                mSound=mBeatBox.getSound();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               //SeekBar 停止触碰..
                mBeatBox.play(mSound);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();//释放资源
    }

    private class SoundHolder extends RecyclerView.ViewHolder{

        private ListItemSoundBinding listItemSoundBinding;

        public SoundHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            listItemSoundBinding=binding;
            //关联使用视图模型
            listItemSoundBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind(Sound sound){
            listItemSoundBinding.getViewModel().setSound(sound);
            listItemSoundBinding.executePendingBindings();//迫使布局立即刷新
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{

        private ArrayList<Sound> mSounds;

        public SoundAdapter(ArrayList<Sound> sounds){
            mSounds=sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ListItemSoundBinding binding=
                    DataBindingUtil.inflate(LayoutInflater.from(getActivity()),R.layout.list_item_sound,parent,false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
