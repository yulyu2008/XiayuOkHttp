package com.xiayu.xiayuokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xiayu.xiayuokhttp.bean.MovieEntity;
import com.xiayu.xiayuokhttp.net.MovieUtils;
import com.xiayu.xiayuokhttp.net.XiayuCallBack;

import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
    }

    public void getData(View v) {
        MovieUtils.getMovie(10, 10, new XiayuCallBack<MovieEntity>(MovieEntity.class, this, false) {


            @Override
            public void myError(Call call, Exception e, int id) {
                System.out.println("onError");
            }

            @Override
            public void onResponse(MovieEntity response, int id) {
                System.out.println("onResponse");
                List<MovieEntity.SubjectsEntity> subjects = response.subjects;
                if (subjects != null) {
                    String title = "";
                    for (MovieEntity.SubjectsEntity subject : subjects) {
                        title = title + subject.title + " , ";
                    }
                    tv.setText(title);
                    System.out.println("title=" + title);
                }

            }
        });
    }

    public void getDataWithProgress(View v) {
        MovieUtils.getMovie(0, 10, new XiayuCallBack<MovieEntity>(MovieEntity.class, this, true) {


            @Override
            public void myError(Call call, Exception e, int id) {
                System.out.println("onError");
            }

            @Override
            public void onResponse(MovieEntity response, int id) {
                System.out.println("onResponse");
                List<MovieEntity.SubjectsEntity> subjects = response.subjects;
                if (subjects != null) {
                    String title = "";
                    for (MovieEntity.SubjectsEntity subject : subjects) {
                        title = title + subject.title + " , ";
                    }
                    tv.setText(title);
                    System.out.println("title=" + title);
                }

            }
        });
    }
}
