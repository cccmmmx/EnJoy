package com.ckkj.enjoy.ui.music;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.ckkj.enjoy.R;
import com.ckkj.enjoy.adapter.MusicPlayingViewPagerAdapter;
import com.ckkj.enjoy.app.AppApplication;
import com.ckkj.enjoy.app.AppContent;
import com.ckkj.enjoy.base.BaseActivityWithoutStatus;
import com.ckkj.enjoy.base.BaseFragment;
import com.ckkj.enjoy.bean.SongUpdateInfo;
import com.ckkj.enjoy.bean.UpdateViewPagerBean;
import com.ckkj.enjoy.broadcastreceiver.ProgressReceiver;
import com.ckkj.enjoy.client.NetworkUtil;
import com.ckkj.enjoy.service.MediaPlayService;
import com.ckkj.enjoy.service.MediaServiceConnection;
import com.ckkj.enjoy.ui.music.fragment.RoundFragment;
import com.ckkj.enjoy.ui.music.model.DownLoadModel;
import com.ckkj.enjoy.utils.StatusBarSetting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by zct11 on 2017/9/27.
 */

public class PlayingActivity extends BaseActivityWithoutStatus implements View.OnClickListener {
    @BindView(R.id.iv_albumart)
    ImageView mIvAlbumart;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.iv_needle)
    ImageView mIvNeedle;
    @BindView(R.id.fl_headerView)
    FrameLayout mFlHeaderView;
    @BindView(R.id.iv_playing_fav)
    ImageView mIvPlayingFav;
    @BindView(R.id.iv_playing_down)
    ImageView mIvPlayingDown;
    @BindView(R.id.iv_playing_cmt)
    ImageView mIvPlayingCmt;
    @BindView(R.id.iv_playing_more)
    ImageView mIvPlayingMore;
    @BindView(R.id.tv_music_duration_played)
    TextView mTvMusicDurationPlayed;
    @BindView(R.id.sb_play_seek)
    SeekBar mSbPlaySeek;
    @BindView(R.id.tv_music_duration)
    TextView mTvMusicDuration;
    @BindView(R.id.iv_playing_mode)
    ImageView mIvPlayingMode;
    @BindView(R.id.iv_playing_pre)
    ImageView mIvPlayingPre;
    @BindView(R.id.iv_playing_play)
    ImageView mIvPlayingPlay;
    @BindView(R.id.iv_playing_next)
    ImageView mIvPlayingNext;
    @BindView(R.id.iv_playing_playlist)
    ImageView mIvPlayingPlaylist;
    private ActionBar mActionBar;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private int mDuration;
    private int mCurPostion;
    private String mTitle;
    private String mAuthor;
    private String mPicUrl;
    private static Context mContext;
    private static int radius = 15;
    private MusicPlayingViewPagerAdapter mPagerAdapter;
    private Intent mIntent;
    private MediaServiceConnection mConnection;
    private boolean isLocal = false;
    private ProgressReceiver mReceiver;
    private int mPosition;
    private boolean mIsPlaying;

    private AlertDialog mDialog;
    private boolean flag = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 != 0) {
                int position = msg.arg1;
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                String cur = format.format(position);
                mTvMusicDurationPlayed.setText(cur);
                mSbPlaySeek.setProgress(position);
            }
            if (msg.arg2 != 0) {
                int max = msg.arg2;
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                String total = format.format(max);
                mTvMusicDuration.setText(total);
                mSbPlaySeek.setMax(max);
            }

        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_music_play;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        StatusBarSetting.transparentStatusBar(this);
        mContext = this;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mDuration = (int) extras.get("duration");
            mCurPostion = (int) extras.get("curPostion");
            mTitle = (String) extras.get("title");
            mAuthor = (String) extras.get("author");
            mPicUrl = (String) extras.get("picUrl");
            mPosition = (int) extras.get("position");
            mIsPlaying = (boolean) extras.get("isPlaying");
        }
        setToolBar();
        setUI();
        setViewPager();
        //动态注册更新播放进度的广播
        mReceiver = new ProgressReceiver(mHandler);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lvr.progress");
        registerReceiver(mReceiver, filter);
        //注册监听更新UI的事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void setViewPager() {
        RoundFragment roundFragment = new RoundFragment();
        Bundle bundle = new Bundle();
        bundle.putString("picUrl", mPicUrl);
        roundFragment.setArguments(bundle);
        mFragmentList.add(roundFragment);
        mPagerAdapter = new MusicPlayingViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateData();

            }

            @Override
            public void onPageSelected(int position) {
                if (position - mPosition == -1) {
                    mConnection.mMediaPlayService.pre(isLocal);
                }
                if (position - mPosition == 1) {
                    mConnection.mMediaPlayService.next(isLocal);
                }
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * 更新歌曲信息
     */
    private void updateData() {
        if (mFragmentList.size() == 1) {
            if (mConnection.mMediaPlayService == null) {
                return;
            }
            List<SongUpdateInfo> list = mConnection.mMediaPlayService.getPlayingList();
            mFragmentList.clear();
            for (int i = 0; i < list.size(); i++) {
                SongUpdateInfo updateInfo = list.get(i);
                String url = updateInfo.getPicUrl();
                RoundFragment roundFragment = new RoundFragment();
                Bundle bundle = new Bundle();
                bundle.putString("picUrl", url);
                roundFragment.setArguments(bundle);
                mFragmentList.add(roundFragment);
            }
            mPagerAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(mPosition);
        }
    }

    private void setUI() {
        if (mIntent == null) {
            mIntent = new Intent(this, MediaPlayService.class);
            mConnection = new MediaServiceConnection(mIvPlayingPlay);
            bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        }
        mActionBar.setTitle(mTitle + "---" + mAuthor);
        if (!TextUtils.isEmpty(mPicUrl)) {
            new PathAsyncTask(mIvAlbumart).execute(mPicUrl);
        }
        float rotation = mIvNeedle.getRotation();
        if (mIsPlaying && rotation == -30) {
            mIvNeedle.setRotation(0);
        } else if (rotation == 0) {
            mIvNeedle.setRotation(-30);
        }
        mIvPlayingPlay.setOnClickListener(this);
        mIvPlayingNext.setOnClickListener(this);
        mIvPlayingPre.setOnClickListener(this);
        mIvPlayingMode.setOnClickListener(this);
        mIvPlayingFav.setOnClickListener(this);
        mIvPlayingDown.setOnClickListener(this);
        mSbPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mConnection.mMediaPlayService.seekTo(progress);
            }
        });

    }

    /**
     * 更新UI事件的接收
     *
     * @param info 歌曲信息
     */
    @Subscribe
    public void onUpdateUIEvent(SongUpdateInfo info) {
        System.out.println("接收到更新UI的消息");
        String title = info.getTitle();
        String author = info.getAuthor();
        String url = info.getPicUrl();

        mActionBar.setTitle(title + "---" + author);

        if (!TextUtils.isEmpty(url)) {
            new PathAsyncTask(mIvAlbumart).execute(url);
        }
        System.out.println("歌曲索引：" + info.getIndex());
        updateData();

        mViewPager.setCurrentItem(info.getIndex());


    }

    @Subscribe
    public void onUpdateViewPagerEvent(UpdateViewPagerBean bean) {
        int item = mViewPager.getCurrentItem();
        updateData();
        if (item == mFragmentList.size()) {
            return;
        }
        mViewPager.setCurrentItem(item + 1);
    }

    private void setToolBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_playing_next: {
                int item = mViewPager.getCurrentItem();
                updateData();
                if (item == mFragmentList.size()) {
                    return;
                }
                mViewPager.setCurrentItem(item + 1);
                break;
            }
            case R.id.iv_playing_play: {
                if (!NetworkUtil.isNetworkAvailable(AppApplication.getAppContext())) {
                    Toast.makeText(AppApplication.getAppContext(), "没有网络了哟，请检查网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    if (mConnection.mMediaPlayService.isPlaying()) {
                        //正在播放 转变为暂停
                        mIvPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
                        mConnection.mMediaPlayService.pause();
                    } else {
                        //正在暂停 转变为播放
                        mIvPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                        mConnection.mMediaPlayService.resume();
                    }

                }

                break;
            }
            case R.id.iv_playing_pre: {
                int item = mViewPager.getCurrentItem();
                if (item == 0) {
                    return;
                }
                mViewPager.setCurrentItem(item - 1);
                break;
            }
            case R.id.iv_playing_mode: {
                switch (mConnection.mMediaPlayService.getPlayMode()) {
                    case AppContent.PLAYING_MODE_REPEAT_ALL: {
                        //目前是循环列表 变成随机播放
                        mIvPlayingMode.setImageResource(R.drawable.play_icn_shuffle);
                        Toast.makeText(PlayingActivity.this.getApplication(), "随机播放",
                                Toast.LENGTH_SHORT).show();
                        mConnection.mMediaPlayService.setPlayMode(AppContent.PLAYING_MODE_SHUFFLE_NORMAL);

                        break;
                    }
                    case AppContent.PLAYING_MODE_REPEAT_CURRENT: {
                        //目前是单曲循环 变成循环列表
                        mIvPlayingMode.setImageResource(R.drawable.play_icn_loop);
                        Toast.makeText(PlayingActivity.this.getApplication(), "循环列表",
                                Toast.LENGTH_SHORT).show();
                        mConnection.mMediaPlayService.setPlayMode(AppContent.PLAYING_MODE_REPEAT_ALL);


                        break;
                    }
                    case AppContent.PLAYING_MODE_SHUFFLE_NORMAL: {
                        //目前是随机播放 变成单曲循环
                        mIvPlayingMode.setImageResource(R.drawable.play_icn_one);
                        Toast.makeText(PlayingActivity.this.getApplication(), "单曲循环",
                                Toast.LENGTH_SHORT).show();
                        mConnection.mMediaPlayService.setPlayMode(AppContent.PLAYING_MODE_REPEAT_CURRENT);

                        break;
                    }

                }
                break;
            }
            case R.id.iv_playing_fav: {
                if (!flag) {
                    mIvPlayingFav.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
                    flag = true;
                } else {
                    mIvPlayingFav.setImageResource(R.drawable.play_love);
                    Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                    flag = false;
                }

                break;
            }
            case R.id.iv_playing_down:
                new DownLoadModel(1).downLoad("http://pic.58pic.com/58pic/13/76/85/69x58PICm2u_1024.jpg", mTitle);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("正在为你下载");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                    }
                });
                mDialog = builder.create();
                mDialog.show();


                break;
        }

    }


    private static class PathAsyncTask extends AsyncTask<String, Void, String> {
        private ImageView mImageView;

        public PathAsyncTask(ImageView view) {
            this.mImageView = view;
        }

        private String mPath;
        private FileInputStream mIs;

        @Override
        protected String doInBackground(String... params) {
            FutureTarget<File> future = null;
            if (params[0].isEmpty()) {
                future = Glide.with(AppApplication.getAppContext())
                        .load("http://img.hb.aicdn.com/503f52bdc99b13e7a0ac6fda8f9007ab3a067eb256ab-XjWeNW_fw658")
                        .downloadOnly(100, 100);
            } else {
                future = Glide.with(AppApplication.getAppContext())
                        .load(params[0])
                        .downloadOnly(100, 100);
            }

            try {
                File cacheFile = future.get();
                mPath = cacheFile.getAbsolutePath();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return mPath;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                mIs = new FileInputStream(s);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(mIs);
            applyBlur(bitmap, mImageView);

            try {
                mIs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private static void applyBlur(Bitmap bgBitmap, ImageView view) {
        //处理得到模糊效果的图
        RenderScript renderScript = RenderScript.create(AppApplication.getAppContext());
        // Allocate memory for Renderscript to work with
        final Allocation input = Allocation.createFromBitmap(renderScript, bgBitmap);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        // Copy the output to the blurred bitmap
        output.copyTo(bgBitmap);
        renderScript.destroy();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bgBitmap);
        view.setBackground(bitmapDrawable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        unregisterReceiver(mReceiver);
    }
}
