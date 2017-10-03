package com.ckkj.enjoy.ui.movie.model;

import com.ckkj.enjoy.bean.Movie;
import com.ckkj.enjoy.bean.MovieDetils;
import com.ckkj.enjoy.bean.NewMovie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ting on 2017/9/17.
 */

public interface MovieImp {
    Observable<List<Movie.SubjectsBean>> getHotMovie(int count,int start);
    Observable<MovieDetils> getMovieById(String id);
    Observable<List<NewMovie.SubjectsBean>> getnewMovie(int count,int start);

}
