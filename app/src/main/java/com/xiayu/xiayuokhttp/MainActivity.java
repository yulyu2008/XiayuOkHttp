package com.xiayu.xiayuokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xiayu.xiayuokhttp.bean.MovieEntity;
import com.xiayu.xiayuokhttp.library.okhttp.callback.XiayuCallBack;
import com.xiayu.xiayuokhttp.net.MovieUtils;

import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getData(View v) {
        MovieUtils.getMovie(0, 10, new XiayuCallBack<MovieEntity>(MovieEntity.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("onError");
            }

            @Override
            public void onResponse(MovieEntity response, int id) {
                System.out.println("onResponse");
                List<MovieEntity.SubjectsEntity> subjects = response.subjects;
                if (subjects!=null){
                    String title = "";
                    for (MovieEntity.SubjectsEntity subject : subjects) {
                        title = title + subject.title+" , ";
                    }
                    Toast.makeText(MainActivity.this, title, Toast.LENGTH_LONG);
                    System.out.println("title="+title);
                }

            }
        });
    }
}
