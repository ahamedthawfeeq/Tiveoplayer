package com.thawfeeqstudios.sahamedthawfeeq.tiveoplayer;

import android.animation.Animator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity{
    private ImageView ff,ss,pp;
    private String rsp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ff = (ImageView)findViewById(R.id.ff);
        ss = (ImageView)findViewById(R.id.ss);
        pp = (ImageView)findViewById(R.id.pp);
        final String pp=System.getenv("SECONDARY_STORAGE");
        File fileList[] = new File("/storage/").listFiles();
        for (File file :fileList ){
            if(!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath())&& file.isDirectory() && file.canRead()){
                rsp = file.getAbsolutePath();
            }
        }
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 23){
                    new MaterialFilePicker()
                            .withActivity(MainActivity.this)
                            .withPath(rsp)
                            .withRootPath(rsp)
                            .withRequestCode(1000)
                            .withHiddenFiles(true) // Show hidden files and folders
                            .start();
                }
                else {
                    new MaterialFilePicker()
                            .withActivity(MainActivity.this)
                            .withPath(pp)
                            .withRootPath(pp)
                            .withRequestCode(1000)
                            .withHiddenFiles(true) // Show hidden files and folders
                            .start();
                }

            }
        });
        ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1000)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            final String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            int c = (pp.getLeft() + pp.getRight())/2;
            int d = (pp.getTop() + pp.getBottom())/2;
            int s = 0;
            int f =(int)Math.hypot(pp.getWidth(),pp.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(pp,c,d,s,f);
            pp.setVisibility(View.VISIBLE);
            anim.start();
            pp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("abc",filePath);
                    startActivity(intent);
                }
            });

        }
    }
}
