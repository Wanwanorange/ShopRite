package android.ait.hu.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AnimationSet animations = new AnimationSet(false);

        final Animation slide = AnimationUtils.loadAnimation(this, R.anim.slide);
        final Animation slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        final ImageView titleView = (ImageView) findViewById(R.id.title);

        animations.addAnimation(slide);
        slide_up.setStartOffset(2500);
        animations.addAnimation(slide_up);

        titleView.startAnimation(animations);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

}
