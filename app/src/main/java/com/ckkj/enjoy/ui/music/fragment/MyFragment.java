package com.ckkj.enjoy.ui.music.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.ckkj.enjoy.R;
import com.ckkj.enjoy.adapter.MusicMyAdapter;
import com.ckkj.enjoy.base.BaseFragment;
import com.ckkj.enjoy.bean.MusicMyItem;
import com.ckkj.enjoy.bean.Song;
import com.ckkj.enjoy.bean.SongDetailInfo;
import com.ckkj.enjoy.message.Download;
import com.ckkj.enjoy.ui.music.DownloadManagerActivity;
import com.ckkj.enjoy.ui.music.LastPlayMusicActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Ting on 2017/9/25.
 */

public class MyFragment extends BaseFragment implements MusicMyAdapter.onItemClickListenr,OnRefreshListener{


    @BindView(R.id.irv_music_my)
    IRecyclerView irvMusicMy;
    @BindView(R.id.iv_broadcast)
    ImageView ivBroadcast;
    private List<MusicMyItem> mList;
    private Context mcontext;
    private MusicMyAdapter mMusicMyAdapter;
    private int count=0;

    private ArrayList<SongDetailInfo.SonginfoBean> list=new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_music_my;
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mcontext = getActivity();
        initData();
        setRecycle();

    }

    private void setRecycle() {
        mMusicMyAdapter = new MusicMyAdapter(mcontext, mList);
        irvMusicMy.setLayoutManager(new LinearLayoutManager(mcontext));
        // TODO: 2017/4/26  先默认不能刷新
        irvMusicMy.setOnRefreshListener(this);
        mMusicMyAdapter.setOnItemClickListener(this);
        irvMusicMy.setItemAnimator(new DefaultItemAnimator());
        irvMusicMy.setIAdapter(mMusicMyAdapter);
        irvMusicMy.addItemDecoration(new DividerItemDecoration(mcontext, DividerItemDecoration.VERTICAL));

    }

    private void initData() {
        mList = new ArrayList<>();
        MusicMyItem item1 = new MusicMyItem();
        item1.setTitle("本地音乐");
        item1.setCount(0);
        item1.setImageRes(getResources().getDrawable(R.drawable.music_local));
        mList.add(item1);
        MusicMyItem item2 = new MusicMyItem();
        item2.setTitle("最近播放");
        item2.setCount(0);
        item2.setImageRes(getResources().getDrawable(R.drawable.music_recent));
        mList.add(item2);
        MusicMyItem item3 = new MusicMyItem();
        item3.setTitle("下载管理");
        item3.setCount(0);
        item3.setImageRes(getResources().getDrawable(R.drawable.music_download));
        mList.add(item3);
        MusicMyItem item4 = new MusicMyItem();
        item4.setTitle("我的歌手");
        item4.setCount(0);
        item4.setImageRes(getResources().getDrawable(R.drawable.music_singer));
        mList.add(item4);
        MusicMyItem item5 = new MusicMyItem();
        item5.setTitle("我喜欢的音乐");
        item5.setCount(0);
        item5.setImageRes(getResources().getDrawable(R.drawable.music_list_default));
        mList.add(item5);
    }


    @Override
    public void onItemClick(int position) {
         switch (position){
             case 0:
                 /*本地音乐*/
                 break;
             case 1:
                 /*最近播放*/
                Intent intent= new Intent(mcontext,LastPlayMusicActivity.class);
                 intent.putExtra("song", (Serializable) list);
                 startActivity(intent);
                 break;
             case 2:
                 startActivity(new Intent(mcontext, DownloadManagerActivity.class));
                 /*下载管理*/
                 break;
             case 3:
                 /*我的歌手*/
                 break;
         }
    }

    @Override
    public void onMiddleClick(View v, int position) {
        switch (v.getId()){
            case R.id.iv_expand_img:

                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getLastMusic(Song info){
        if(list.size()==0){
            list.add(info.getSongDetailInfo().getSonginfo());
        }else {
            for(int i=0;i<list.size();i++){

                if(info.getSongDetailInfo().getSonginfo().getSong_id()==list.get(i).getSong_id()){
                    list.remove(info.getSongDetailInfo().getSonginfo());
                }
            }
            list.add(info.getSongDetailInfo().getSonginfo());
        }


        Log.d("MyFragment", "list.size():" + list.size());
        for(int i=0;i<mList.size();i++){
            if(i==1){
                mList.get(i).setCount(list.size());
            }
        }
        mMusicMyAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        if(list.size()!=0){
            mMusicMyAdapter.notifyDataSetChanged();

        }
        irvMusicMy.setRefreshing(false);

    }


}
