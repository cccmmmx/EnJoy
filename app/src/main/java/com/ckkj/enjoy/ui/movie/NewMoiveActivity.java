package com.ckkj.enjoy.ui.movie;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ckkj.enjoy.R;
import com.ckkj.enjoy.adapter.MovieAdapter;
import com.ckkj.enjoy.adapter.NewMovieAdapter;
import com.ckkj.enjoy.adapter.UpdateMovieAdapter;
import com.ckkj.enjoy.anims.LandingAnimator;
import com.ckkj.enjoy.app.AppContent;
import com.ckkj.enjoy.base.BaseActivity;
import com.ckkj.enjoy.bean.Movie;
import com.ckkj.enjoy.bean.MovieDetils;
import com.ckkj.enjoy.bean.NewMovie;
import com.ckkj.enjoy.bean.OtherMovie;
import com.ckkj.enjoy.ui.home.presenter.Imp.OtherMoviePresenterImp;
import com.ckkj.enjoy.ui.home.view.OtherMovieView;
import com.ckkj.enjoy.ui.movie.presenter.MoviePresenterImpl;
import com.ckkj.enjoy.ui.movie.view.MovieView;
import com.ckkj.enjoy.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 显示电影列表页面
 */
public class NewMoiveActivity extends BaseActivity implements UpdateMovieAdapter.onItemClickListenr {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    private UpdateMovieAdapter adapter;
    private List<OtherMovie.SubjectsBean> movies = new ArrayList<OtherMovie.SubjectsBean>();
    private OtherMovie otherMovie;


    /**
     * 实例化P
     */
    @Override
    public void initPresenter() {
    }

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("美国上映大片");
        Intent intent=getIntent();
        otherMovie=(OtherMovie) intent.getSerializableExtra("OtherMovie");
        movies.addAll(otherMovie.getSubjects());
        initData(movies);
        /*回退键触发事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData(final List<OtherMovie.SubjectsBean> movies) {
        /*初始化适配器*/
        adapter = new UpdateMovieAdapter( this,movies);
        /*设置GirdLayoutManager布局，两列，垂直方向*/
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        /*设置布局到RecycleView*/
        recycle.setLayoutManager(manager);
        /*设置RecycleView刷新*/
        //recycle.setOnRefreshListener(this);
        /*设置RecycleView点击事件*/
         adapter.setOnItemClickListenr(this);
        /*设置RecycleView每一项动画效果*/
        recycle.setItemAnimator(new LandingAnimator());
        /*设置适配器*/
        recycle.setAdapter(adapter);
    }


    /**
     * 获取布局ID
     * @return
     */
    @Override
    public int getLayoutID() {
        return R.layout.activity_newmoive;
    }




    @Override
    public void onItemClick(int position, ImageView view) {
        Intent intent = new Intent(this, MovieDetilsActivity.class);
        intent.putExtra("movie", movies.get(position).getSubject().getId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext, view, AppContent.TRANSITION_IMAGE_MOVIE);
            mContext.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }
    }

}
