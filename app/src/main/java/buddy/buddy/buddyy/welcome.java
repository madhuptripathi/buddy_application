package buddy.buddy.buddyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class welcome extends AppCompatActivity {

    Animation anim;
    ImageView im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        im = (ImageView) findViewById(R.id.imageView3);

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(welcome.this, MainActivity.class));
            }
        }, 2000);


    }
}



       /* new Timer().schedule(new TimerTask(){
            public void run() {



            }
        }, 4000);
        anim= AnimationUtils.loadAnimation(welcome.this,R.anim.zoom);
        im.setAnimation(anim);





        onAnimationEnd(anim);




    }

    @Override
    public void onAnimationStart(Animation animation) {


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animation.startNow();
        //Toast.makeText(this, "above finish", Toast.LENGTH_SHORT).show();

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(welcome.this, MainActivity.class));
            }
        }, 1000);
        anim.reset();



    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }*/
