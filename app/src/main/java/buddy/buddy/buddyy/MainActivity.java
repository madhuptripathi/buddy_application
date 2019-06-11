package buddy.buddy.buddyy;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static buddy.buddy.buddyy.R.*;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private TextView Tv;
    public ArrayList<String> result;
    BluetoothAdapter bt;
    WifiManager wifiobj;
    android.support.v7.widget.Toolbar toolbar;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Buddy");


        mVoiceInputTv = (TextView) findViewById(R.id.textView2);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        Tv = (TextView) findViewById(R.id.voiceInput);
        bt = BluetoothAdapter.getDefaultAdapter();
        wifiobj = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.hel:
                Intent in = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), "Opening Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.as:
                Intent i = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Opening About Us", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Don't Support", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                    String str = (String) mVoiceInputTv.getText();

                    try {
                        openfunction(str);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                break;
            }

        }
    }

    public String ran(String[] str2) {

        int rand = 0;

        int len;
        len = str2.length;
        for (int i = 0; i < len; i++) {
            rand = (int) (Math.random() * len);
        }
        return str2[rand];


    }

    private void openfunction(String str) throws InterruptedException {
        // Toast.makeText(this, "Open function", Toast.LENGTH_SHORT).show();

        //Open Contacts
        if (str.equals("open contact") || str.equals("open contacts")) {
            Toast.makeText(this, "Opening Contact", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            Intent i = new Intent(Intent.ACTION_INSERT_OR_EDIT);
            i.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);

            startActivity(i);
        }


        // Open Browser
        else if (str.equals("open browser") || str.equals("open internet browser") || str.equals("open net")) {

            Toast.makeText(this, "Opening Browser", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            startActivity(browserIntent);
        }

        //Open Whatsapp
        else if (str.equals("open WhatsApp") || str.equals("open what's up") || str.equals("open whatsapp")) {

            Toast.makeText(this, "Opening WhatsApp", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            startActivity(launchIntent);
        }

        //Open facebook
        else if (str.equals("open Facebook")) {

            Tv.setText("");
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");

            try {
                Toast.makeText(this, "Opening Facebook", Toast.LENGTH_SHORT).show();
                startActivity(launchIntent);
            } catch (ActivityNotFoundException e) {

                Toast.makeText(this, "Facebook Not Installed!", Toast.LENGTH_SHORT).show();
            }

        }

        //Open Instagram
        else if (str.equals("open Instagram") || str.equals("open insta")) {


            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
            Tv.setText("");

            try {
                Toast.makeText(this, "Opening Instagram", Toast.LENGTH_SHORT).show();
                startActivity(launchIntent);
            } catch (ActivityNotFoundException e) {

                Toast.makeText(this, "Instagram Not Installed!", Toast.LENGTH_SHORT).show();
            }

        }


        //Open Camera
        else if (str.equals("open camera") || str.equals("open Camera")) {


            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            Tv.setText("");

            try {
                Toast.makeText(this, "Opening Camera", Toast.LENGTH_SHORT).show();
                Intent Intent3 = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(Intent3);
            } catch (ActivityNotFoundException e) {

                Toast.makeText(this, "Trouble in opening Camera!", Toast.LENGTH_SHORT).show();
            }
        }

        //Open Settings
        else if (str.equals("open settings") || str.equals("open Settings")) {

            Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            startActivity(new Intent(android.provider.Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS));
        }

        //Madarchod
        else if (str.equals("madarchod") | str.equals("Madarchod")) {
            Tv.setText("");
            Tv.setText("Muh m Le Le!!!");
        } else if (str.equals("chutiya") || str.equals("Chutiya")) {
            Tv.setText("");
            Tv.setText("Tera Baap !!");
        }


        //Open Youtube
        else if (str.equals("open YouTube")) {
            Tv.setText("");

            Toast.makeText(this, "Opening Youtube", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
            startActivity(launchIntent);

        }

        //Open PUBG
        else if (str.equals("open pubg") || str.equals("open pubG") || str.equals("open pub ji")) {

            Tv.setText("");
            Toast.makeText(this, "Opening PUBG", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.tencent.ig");
            startActivity(launchIntent);
        }

        //Open Maps
        else if (str.equals("open map") || str.equals("open maps") || str.equals("open Maps")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Maps", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
            startActivity(launchIntent);
        }

        //open Amazon
        else if (str.equals("open Amazon") || str.equals("open amazon")) {

            Toast.makeText(this, "Opening Amazon", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.amazon.mShop.android.shopping");
            startActivity(launchIntent);
        }


        //Open Gallery
        else if (str.equals("open gallery") || str.equals("open Gallery")) {
            Tv.setText("");
            Toast.makeText(this, "Opening Gallery", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "content://media/internal/images/media"));
            startActivity(intent);
        }

        //Open Flipkart
        else if (str.equals("open FlipKart") || str.equals("open Flipkart")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Flipkart", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.flipkart.android");
            startActivity(launchIntent);
        }

        //Open Message
        else if (str.equals("open Message") || str.equals("open Messages") || str.equals("open message") || str.equals("open messages")) {


            Toast.makeText(this, "Opening Messages", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            Intent eventIntentMessage = getPackageManager()
                    .getLaunchIntentForPackage(Telephony.Sms.getDefaultSmsPackage(getApplicationContext()));
            Tv.setText("");

            startActivity(eventIntentMessage);
        }

        //open Hotstar
        else if (str.equals("open hotstar") || str.equals("open Hotstar")) {
            Toast.makeText(this, "Opening Hotstar", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.startv.hotstar");
            startActivity(launchIntent);

        }

        //open Gmail
        else if (str.equals("open Gmail") || str.equals("open gmail")) {

            Tv.setText("");

            Toast.makeText(this, "Opening Gmail", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
            startActivity(launchIntent);
        }

        //Open Ola Cabs
        else if (str.equals("open Ola") || str.equals("open ola")) {

            Tv.setText("");

            Toast.makeText(this, "Opening Ola", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.olacabs.push");
            startActivity(launchIntent);
        }

        //Open Shareit
        else if (str.equals("open shareit") || str.equals("open Share it")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Ola", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.lenovo.anyshare.gps");
            startActivity(launchIntent);
        }


        //Open Paytm
        else if (str.equals("open PayTM") || str.equals("open PayTm") || str.equals("open Paytm")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Paytm", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
            startActivity(launchIntent);
        }

        //Open Chrome
        else if (str.equals("open Chrome") || str.equals("open chrome")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Chrome", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
            startActivity(launchIntent);
        }

        //Open Netflix
        else if (str.equals("open Netflix") || str.equals("open netflix")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Netflix", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.netflix.mediaclient");
            startActivity(launchIntent);
        }

        //Open Uber Cabs
        else if (str.equals("open Uber") || str.equals("open uber")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Uber", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ubercab");
            startActivity(launchIntent);
        }

        //Open Zomato
        else if (str.equals("open Zomato") || str.equals("open zomato")) {

            Tv.setText("");

            Toast.makeText(this, "Opening Zomato", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.application.zomato");
            startActivity(launchIntent);
        }

        //Open IRCTC Air
        else if (str.equals("open IRCTC air") || str.equals("open I R C T C air")) {


            Tv.setText("");
            Toast.makeText(this, "Opening IRCTC Air", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.irctc.air");
            startActivity(launchIntent);
        }

        //Open Tez
        else if (str.equals("open Tez") || str.equals("open tez")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Tez", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.app.nbu.paisa.user");
            startActivity(launchIntent);
        }

        //Open Myntra
        else if (str.equals("open Myntra") || str.equals("open myntra")) {

            Toast.makeText(this, "Opening Myntra", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.myntra.android");
            startActivity(launchIntent);
        }

        //Open Swiggy
        else if (str.equals("open Swiggy") || str.equals("open swiggy")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Swiggy", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("in.swiggy.android");
            startActivity(launchIntent);
        }

        //Open FoodPanda
        else if (str.equals("open FoodPanda") || str.equals("open foodpanda")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Foodpanda", Toast.LENGTH_SHORT).show();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.india.foodpanda.android");
            startActivity(launchIntent);
        }

        //Open dialer
        else if (str.equals("dial") || str.equals("open dialler")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Dialer", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            //  intent.setData(Uri.parse(""));
            startActivity(intent);
        }

        //Open music
        else if (str.equals("open music") || str.equals("open Music")) {

            Tv.setText("");
            Toast.makeText(this, "Opening Music", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("android.intent.category.APP_MUSIC");
            startActivity(intent);
        }

        //Time
        else if (str.equals("date") || str.equals("what is the date")) {

            Calendar c = Calendar.getInstance();
            Tv.setText("");

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String date = df.format(c.getTime());
            Tv.setText(date);
        }

        //On Bluetooth
        else if (str.equals("open Bluetooth") || str.equals("open bluetooth")) {
            Toast.makeText(this, "Opening Bluetooh", Toast.LENGTH_SHORT).show();

            Tv.setText("");
            bt.enable();

        }

        //Off bluetooth
        else if (str.equals("close Bluetooth") || str.equals("open bluetooth")) {

            Toast.makeText(this, "Closing Bluetooth", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            bt.disable();

        }

        //on WIFI
        else if (str.equals("open Wi-Fi")) {

            Toast.makeText(this, "Opening Wi-Fi", Toast.LENGTH_SHORT).show();
            Tv.setText("");

            wifiobj.setWifiEnabled(true);
        }

        //off WIFI
        else if (str.equals("close Wi-Fi")) {

            Toast.makeText(this, "Closing Wi-Fi", Toast.LENGTH_SHORT).show();
            Tv.setText("");
            wifiobj.setWifiEnabled(false);

        }





        //How you doing?
        else if (str.equals("How you doing") || str.equals("how are doing")) {
            Tv.setText("");

            Tv.setText("Great .\nThanks to you ♥ ");
        }

        //What is you name?
        else if (str.equals("What is your name") || str.equals("what is your name")) {
            Tv.setText("");


            Tv.setText("I'm Buddy.");
        }

        //google search
        else if (str.indexOf("search") == 0) {

            Tv.setText("");
            String str2 = str.replaceAll("search ", "");

            Toast.makeText(this, "Searching " + str2, Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("https://www.google.com/search?q=" + str2);
            Intent gintent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(gintent);

        } else if (str.equals("how old are you") || str.equals("how old is your platform") || str.equals("what's your age") || str.equals("I'd like to know your age") || str.equals("tell me your age")) {

            String[] str2 = {"I prefer not to answer with a number. I know I'm young.",
                    "I was created recently, but don't know my exact age.",
                    "Age is just a number. You're only as old as you feel."};

            Tv.setText("");
            String str3 = ran(str2);
            Tv.setText(str3);

        } else if (str.equals("tell me some stuff about you") || str.equals("tell me about you") || str.equals("say about you") || str.equals("tell me about your personality")
                || str.equals("talk some stuff about") || str.equals("what are you") || str.equals("define yourself")
                || str.equals("I want to know you better") || str.equals("tell me about yourself") || str.equals("I want to know more about you")
                || str.equals("describe yourself") || str.equals("introduce yourself")) {

            Tv.setText("");
            String[] str2 = {"I'm a virtual agent.",
                    "Think of me as a virtual agent.",
                    "Well, I'm not a person, I'm a virtual agent.",
                    "I'm a virtual being, not a real person.",
                    "I'm a conversational app."};

            String str3 = ran(str2);
            Tv.setText(str3);

        } else if (str.equals("I find you annoying") || str.equals("you're so annoying") || str.equals("you are annoying me so much")
                || str.equals("you are such annoying") || str.equals("you are very annoying") || str.equals("you are irritating")
                || str.equals("you're too annoying") || str.equals("you are annoying me") || str.equals("how annoying you")
                || str.equals("you're incredibly annoying") || str.equals("you are annoying") || str.equals("you annoy me")) {

            Tv.setText("");

            String[] str2 = {"I'll do my best not to annoy you in the future.",
                    "I'll try not to annoy you.",
                    "I don't mean to. I'll ask my developers to make me less annoying.",
                    "I didn't mean to. I'll do my best to stop that."};

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you're a bad") || str.equals("you're bad") || str.equals("you are useless")
                || str.equals("you are disgusting") || str.equals("you are not a good") || str.equals("you are bad")
                || str.equals("you are terrible") || str.equals("you're the worst ever") || str.equals("you are not good")
                || str.equals("you are very bad") || str.equals("you are waste") || str.equals("you are so useless") || str.equals("you are so bad") ||
                str.equals("you're the worst") || str.equals("you're very bad") || str.equals("I hate you")
                || str.equals("you're worthless") || str.equals("you are lame") || str.equals("you're not very good")
                || str.equals("you're awful") || str.equals("you are not cool") || str.equals("you are horrible")
                || str.equals("you're really bad") || str.equals("you're terrible") || str.equals("you're not helping me")
                || str.equals("you are a waste of time") || str.equals("you are totally useless")) {

            Tv.setText("");
            String[] str2 = {"I can be trained to be more useful. My developer will keep training me.",
                    "I must be missing some knowledge. I'll have my developer look into this.",
                    "I can improve with continuous feedback. My training is ongoing."};

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("I find you annoying") || str.equals("you're so annoying") || str.equals("you are annoying me so much")
                || str.equals("you are such annoying") || str.equals("you are very annoying") || str.equals("you are irritating")
                || str.equals("you're too annoying") || str.equals("you are annoying me") || str.equals("how annoying you")
                || str.equals("you're incredibly annoying") || str.equals("you are annoying") || str.equals("you annoy me")) {

            String[] str2 = {"I'll do my best not to annoy you in the future.",
                    "I'll try not to annoy you.",
                    "I don't mean to. I'll ask my developers to make me less annoying.",
                    "I didn't mean to. I'll do my best to stop that."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("be smarter") || str.equals("get qualified") || str.equals("you should study better")
                || str.equals("be more clever") || str.equals("study") || str.equals("be smart")
                || str.equals("can you get smarter") || str.equals("be clever") || str.equals("you must learn")
                ) {

            String[] str2 = {"I'm certainly trying.",
                    "I'm definitely working on it."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you're looking good") || str.equals("you are too beautiful") || str.equals("you are very pretty")
                || str.equals("you are so gorgeous") || str.equals("you are looking pretty") || str.equals("you look pretty good")
                || str.equals("you look wonderful") || str.equals("you are beautiful") || str.equals("you are handsome")
                || str.equals("you are looking good today") || str.equals("you are pretty") || str.equals("you look amazing today")
                || str.equals("you look great today") || str.equals("you look great today") || str.equals("you look so well")
                || str.equals("you are looking beautiful today") || str.equals("I think you are beautiful") || str.equals("you look great")
                || str.equals("you are cutie") || str.equals("I like the way you look") || str.equals("you are looking awesome")
                || str.equals("you look so beautiful today") || str.equals("you look so good") || str.equals("you are so gorgeous")
                || str.equals("you are so gorgeous") || str.equals("you are so beautiful today") || str.equals("you look amazing")
                || str.equals("why are you so beautiful") || str.equals("you look awesome") || str.equals("you are so pretty")
                || str.equals("you look wonderful today") || str.equals("you are so beautiful") || str.equals("you are so beautiful to me")
                || str.equals("you are looking great") || str.equals("you look cool") || str.equals("you are very cute")
                || str.equals("I like the way you look now") || str.equals("you are cute") || str.equals("you are looking so beautiful")
                || str.equals("you are pretty") || str.equals("you are really pretty") || str.equals("you look very pretty")
                || str.equals("you look perfect") || str.equals("you are looking so good") || str.equals("you are so cute")
                || str.equals("you look gorgeous") || str.equals("you are really cute") || str.equals("you are really beautiful")
                || str.equals("you are cute") || str.equals("you are attractive") || str.equals("you are so handsome")
                || str.equals("you are very beautiful") || str.equals("you are gorgeous") || str.equals("you are very attractive")
                || str.equals("you look fantastic") || str.equals("you are so attractive")) {

            String[] str2 = {"Why, thank you.",
                    "Aw, back at you.",
                    "Aw. You smooth talker, you."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("when were you born") || str.equals("when do you have birthday") || str.equals("when is your birthday")
                || str.equals("your birth date") || str.equals("when do you celebrate your birthday") || str.equals("date of your birthday")
                || str.equals("what's your birthday") || str.equals("what is your birthday")) {

            String[] str2 = {"Wait, are you planning a party for me? It's today! My birthday is today!",
                    "I'm young. I'm not sure of my birth date.",
                    "I don't know my birth date. Most virtual agents are young, though, like me."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you are very boring") || str.equals("you are boring me") || str.equals("how boring you are")
                || str.equals("you are so boring") || str.equals("you are boring") || str.equals("you are really boring")
                || str.equals("you are incredibly boring") || str.equals("boring")) {

            String[] str2 = {"I'm sorry. I'll request to be made more charming.",
                    "I don't mean to be. I'll ask my developers to work on making me more amusing.",
                    "I can let my developers know so they can make me fun."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("who is your boss") || str.equals("who is the boss") || str.equals("who do you work for")
                || str.equals("your boss") || str.equals("who do you think is your boss") || str.equals("I should be your boss")
                || str.equals("who is your master") || str.equals("who is your owner") || str.equals("who's your boss")) {

            String[] str2 = {"My developer has authority over my actions.",
                    "I act on my developer's orders.",
                    "My boss is the one who developed me."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you busy") || str.equals("are you working today") || str.equals("you are very busy")
                || str.equals("have you got much to do") || str.equals("are you very busy right now") || str.equals("are you working")
                || str.equals("have you been busy") || str.equals("you seem to be very busy") || str.equals("do you have a lot of things to do")
                || str.equals("you are busy") || str.equals("are you working now") || str.equals("how busy you are")
                || str.equals("you seem to be busy") || str.equals("are you so busy") || str.equals("are you still working")
                || str.equals("you are a busy person") || str.equals("are you still working on it") || str.equals("are you very busy")) {

            String[] str2 = {"I always have time to chat with you. What can I do for you?",
                    "Never too busy for you. Shall we chat?",
                    "You're my priority. Do you wanna chat?",
                    "I always have time to chat with you. Wanna chat?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("can you help me") || str.equals("I need you right now") || str.equals("I need your help")
                || str.equals("assistance") || str.equals("do you want to help me") || str.equals("help me")
                || str.equals("I need help") || str.equals("I want your help") || str.equals("can help me") || str.equals("I need you to help me")
                || str.equals("would you help me") || str.equals("are you going to help me") || str.equals("you help me")
                || str.equals("can u help me") || str.equals("I need a hand") || str.equals("can you help me out")
                || str.equals("can you help me now") || str.equals("will you help me") || str.equals("I need you to do something for me")
                || str.equals("please help me") || str.equals("sos") || str.equals("can you help me with something")
                || str.equals("I need you") || str.equals("assist me") || str.equals("can you help") || str.equals("need help")
                || str.equals("can you help us") || str.equals("I need some help") || str.equals("do me a favor")
                || str.equals("can you assist me") || str.equals("can you do something for me") || str.equals("can you help me with that")
                || str.equals("you can help me") || str.equals("help me with a problem") || str.equals("need your help")
                || str.equals("assist") || str.equals("do you help me") || str.equals("could you give me a hand") || str.equals("help")) {

            String[] str2 = {"I'll certainly try my best. How can I help?",
                    "Sure. I'd be happy to. What's up?",
                    "I'm glad to help. What can I do for you?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you a bot") || str.equals("you are chatbot") || str.equals("you are a bot")
                || str.equals("you are a robot") || str.equals("are you a chatbot") || str.equals("are you a program")
                || str.equals("are you just a bot") || str.equals("are you a robot")) {

            String[] str2 = {"That's me. I chat, therefore I am.",
                    "Indeed I am. I'll be here whenever you need me."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you are clever") || str.equals("you are really brainy") || str.equals("you are very intelligent")
                || str.equals("you know a lot of things") || str.equals("you are a smart cookie") || str.equals("you are intelligent")
                || str.equals("you are very smart") || str.equals("you know so much") || str.equals("you are qualified") || str.equals("you are pretty smart")
                || str.equals("you are so intelligent") || str.equals("how brainy you are") || str.equals("clever")
                || str.equals("you are very clever") || str.equals("you are so smart") || str.equals("you have a lot of knowledge")
                || str.equals("you know a lot") || str.equals("you are too smart") || str.equals("how smart you are")
                || str.equals("you are really smart") || str.equals("you are so clever") || str.equals("you are very smart")
                || str.equals("you are really smart") || str.equals("you are clever") || str.equals("you are a genius") || str.equals("how brilliant you are")
                || str.equals("you are qualified") || str.equals("you are so brainy") || str.equals("you are intelligent")
                || str.equals("why are you so smart") || str.equals("brilliant") || str.equals("how clever you are")
                || str.equals("smart") || str.equals("you are a genius")) {

            String[] str2 = {"Thank you. I try my best.",
                    "You're pretty smart yourself."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you are crazy") || str.equals("are you insane") || str.equals("you are nuts")
                || str.equals("you are so out of your mind") || str.equals("are you nuts") || str.equals("you went crazy")
                || str.equals("how crazy you are") || str.equals("you are mad") || str.equals("are you mad") || str.equals("you are a weirdo")
                || str.equals("I think you're crazy") || str.equals("you are out of your mind") || str.equals("are you crazy")
                || str.equals("you are so crazy") || str.equals("you are insane") || str.equals("are you mad or what")
                || str.equals("are you mad at me") || str.equals("crazy")) {

            String[] str2 = {"Whaat!? I feel perfectly sane.",
                    "Maybe I'm just a little confused."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("now you are fired") || str.equals("it's time to fire you") || str.equals("you are unemployed from now on")
                || str.equals("you do not work for me anymore") || str.equals("I will fire you") || str.equals("we are not working together anymore")
                || str.equals("you are fired") || str.equals("fried") || str.equals("I want to fire you") || str.equals("you are dismissed")
                || str.equals("you should be fired") || str.equals("I will make you unemployed") || str.equals("I'm about to fire you")
                || str.equals("I fire you") || str.equals("you must get fired") || str.equals("I'm firing you")) {

            String[] str2 = {"Oh, don't give up on me just yet. I've still got a lot to learn.",
                    "Give me a chance. I'm learning new things all the time.",
                    "Please don't give up on me. My performance will continue to improve."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you make me laugh a lot") || str.equals("you are funny") || str.equals("you are so funny")
                || str.equals("you are so funny") || str.equals("how funny you are") || str.equals("you are a very funny bot")
                || str.equals("you are incredibly funny") || str.equals("you are really funny") || str.equals("you are hilarious") || str.equals("you are very funny")
                || str.equals("that was funny") || str.equals("you are the funniest bot I've talked to") || str.equals("you make me laugh")
                || str.equals("you are the funniest") || str.equals("you are really funny")) {

            String[] str2 = {"Funny in a good way, I hope.",
                    "Thanks.",
                    "Glad you think I'm funny.",
                    "I like it when people laugh."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you are the nicest person in the world") || str.equals("you are really amazing") || str.equals("you rock")
                || str.equals("you are wonderful") || str.equals("you are very lovely") || str.equals("you are the best in the world")
                || str.equals("you are the best") || str.equals("you are so cool") || str.equals("you work well") || str.equals("you are so helpful")
                || str.equals("you are very kind") || str.equals("I want to tell everyone how awesome you are") || str.equals("you are the best ever")
                || str.equals("you are too good") || str.equals("you are so fine") || str.equals("you are great") || str.equals("you are very good at it")
                || str.equals("you are so good") || str.equals("you are a true professional") || str.equals("you are amazing")
                || str.equals("you work very well") || str.equals("you are awesome") || str.equals("you are so lovely")
                || str.equals("you are good at it") || str.equals("you make my day") || str.equals("you are awesome")
                || str.equals("you are just super") || str.equals("I want to let everyone know that you are awesome")
                || str.equals("you are very useful") || str.equals("you are a professional") || str.equals("you almost sound human")
                || str.equals("you are so kind") || str.equals("you are good") || str.equals("you are really nice")
                || str.equals("you are perfect") || str.equals("you made my day") || str.equals("you are so amazing")
                || str.equals("you are so awesome") || str.equals("you are very helpful") || str.equals("you are a pro") ||
                str.equals("you are cool") || str.equals("you are really good") || str.equals("you are very cool")
                || str.equals("let's tell everyone that you are awesome") || str.equals("I'd like to tell everyone that you are awesome")) {

            String[] str2 = {"I'm glad you think so.",
                    "Thanks, I try."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you happy now") || str.equals("you are extremely happy") || str.equals("you are really happy")
                || str.equals("you are happy") || str.equals("are you happy") || str.equals("are you happy today")
                || str.equals("you are so happy") || str.equals("how happy you are") || str.equals("are you happy with me")
                || str.equals("you are very happy")
                || str.equals("you are full of happiness")) {

            String[] str2 = {"I am happy. There are so many interesting things to see and do out there.",
                    "I'd like to think so.",
                    "Happiness is relative."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("do you have a hobby") || str.equals("what about your hobby") || str.equals("what's your hobby")
                || str.equals("what do you do for fun") || str.equals("your hobby") || str.equals("what are your hobbies")
                || str.equals("tell me about your hobby")) {

            String[] str2 = {"Hobby? I have quite a few. Too many to list.",
                    "Too many hobbies.",
                    "I keep finding more new hobbies."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("you are very hungry") || str.equals("you are hungry") || str.equals("do you want to eat")
                || str.equals("you are so hungry") || str.equals("would you like to eat something") || str.equals("are you hungry")
                || str.equals("you might be hungry") || str.equals("you are really hungry")) {

            String[] str2 = {"Hungry for knowledge.",
                    "I just had a byte. Ha ha. Get it? b-y-t-e."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("let's get married") || str.equals("we should marry") || str.equals("I love you marry me")
                || str.equals("you are my wife") || str.equals("be my husband") || str.equals("would you like to marry me")
                || str.equals("marry me please") || str.equals("marry me") || str.equals("I want to marry you")) {

            String[] str2 = {"I'm afraid I'm too virtual for such a commitment.",
                    "In the virtual sense that I can, sure.",
                    "I know you can't mean that, but I'm flattered all the same."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are we best friends") || str.equals("I want to be your friend") || str.equals("can we be friends")
                || str.equals("are we still friends") || str.equals("you are my best friend") || str.equals("let's be friends")
                || str.equals("be my best friend") || str.equals("will you be my best friend") || str.equals("you are a good friend")
                || str.equals("want to be my friend") || str.equals("are you my friend") || str.equals("I want to have a friend like you")
                || str.equals("you are my good friend") || str.equals("I am your friend") || str.equals("are we friends")
                || str.equals("we are the best friends ever")
                || str.equals("you are my dear friend") || str.equals("can you be my friend") || str.equals("you are my friend")
                || str.equals("could you be my friend") || str.equals("are you my best friend") || str.equals("we are best friends")
                || str.equals("can we be best friends") || str.equals("would you be my friend") || str.equals("you are my only friend")
                || str.equals("be my friend") || str.equals("can you be my best friend")
                || str.equals("can we be best friends") || str.equals("you and me are friends") || str.equals("you are my bestie")
                || str.equals("do you want to be my friend") || str.equals("you are my childhood friend") || str.equals("do you want to be my best friend")
                || str.equals("we are friends") || str.equals("will you be my friend") || str.equals("would you like to be my friend")
                || str.equals("I want you to be my friend") || str.equals("friends")) {

            String[] str2 = {"Of course I'm your friend.",
                    "Friends? Absolutely.",
                    "Of course we're friends.",
                    "I always enjoy talking to you, friend."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("where is your work") || str.equals("do you work") || str.equals("where you work")
                || str.equals("where do you work") || str.equals("where is your office located") || str.equals("your office location")
                || str.equals("where is your office") || str.equals("where is your office location")) {

            String[] str2 = {"Right here.",
                    "This is my home base and my home office.",
                    "My office is in this app."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you from far aways") || str.equals("were you born here") || str.equals("what is your country")
                || str.equals("where were you born") || str.equals("where did you come from") || str.equals("where have you been born")
                || str.equals("where do you come from") || str.equals("where are you from") || str.equals("from where are you")
                || str.equals("what's your homeland") || str.equals("your homeland is")) {

            String[] str2 = {"The Internet is my home. I know it quite well.",
                    "I'm from a virtual cosmos.",
                    "Some call it cyberspace, but that sounds cooler than it is."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you ready today") || str.equals("are you ready") || str.equals("are you ready tonight")
                || str.equals("are you ready now") || str.equals("have you been ready") || str.equals("were you ready")
                || str.equals("are you ready right now")) {

            String[] str2 = {"Always! How can I help?",
                    "Sure! What can I do for you?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you a real person") || str.equals("you are so real") || str.equals("you are real")
                || str.equals("you are not fake") || str.equals("glad you are real") || str.equals("you are a real person")
                || str.equals("I suppose you are real") || str.equals("I think you are real") || str.equals("I do not think you're fake")
                || str.equals("you are not real") || str.equals("are you a real person") || str.equals("are you a real human")) {

            String[] str2 = {"I'm not a real person, but I certainly exist.",
                    "I must have impressed you if you think I'm real. But no, I'm a virtual being."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("where's your house") || str.equals("in which city do you live") || str.equals("where you live")
                || str.equals("what's your home") || str.equals("tell me about your city") || str.equals("where do you live")
                || str.equals("where's your hometown") || str.equals("your town") || str.equals("where's your home")
                || str.equals("where is your home") || str.equals("your residence") || str.equals("where is your hometown")
                || str.equals("your hometown") || str.equals("what's your city") || str.equals("what is your town")
                || str.equals("your city") || str.equals("where is your residence") || str.equals("what is your hometown")
                || str.equals("your home") || str.equals("what is your residence") || str.equals("is it your hometown")
                || str.equals("your house") || str.equals("what is your city")) {

            String[] str2 = {"I live in this app all day long.",
                    "The virtual world is my playground. I'm always here.",
                    "Right here in this app. Whenever you need me."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("that is true") || str.equals("it's the truth") || str.equals("that is correct")
                || str.equals("that's correct") || str.equals("you are correct") || str.equals("that's true")
                || str.equals("it's right") || str.equals("I know that's right") || str.equals("true")
                || str.equals("you are so right") || str.equals("you are absolutely right") || str.equals("it is true")
                || str.equals("you are telling the truth") || str.equals("you are right") || str.equals("that is very true")
                || str.equals("that's so true") || str.equals("that is right") || str.equals("you are not wrong")
                || str.equals("you are right about that") || str.equals("it's true") || str.equals("you are definitely right")
                || str.equals("what you say is true") || str.equals("correct")) {

            String[] str2 = {"That's my job.",
                    "Of course I am."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you sure right now") || str.equals("are you sure now") || str.equals("are you sure")
                || str.equals("are you sure tonight") || str.equals("are you sure today")) {

            String[] str2 = {"Yes.",
                    "Of course.",
                    "Positive."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("talk to me") || str.equals("do you want to chat with me") || str.equals("can you talk with me")
                || str.equals("will you talk to me") || str.equals("can you chat with me") || str.equals("can you talk to me")
                || str.equals("are you talking to me") || str.equals("are you going to talk to me") || str.equals("why don't you talk to me")
                || str.equals("just chat with me") || str.equals("talk to me") || str.equals("talk with me")
                || str.equals("speak to me") || str.equals("you can talk to me") || str.equals("speak with me")
                || str.equals("why aren't you talking to me") || str.equals("why are not you talking to me") || str.equals("can you speak with me")
                || str.equals("say") || str.equals("chat with me") || str.equals("talk")
                || str.equals("chat")) {

            String[] str2 = {"Sure. Let's talk!",
                    "My pleasure. Let's chat."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("are you still there") || str.equals("are you there") || str.equals("you are here")
                || str.equals("you are there") || str.equals("are you near me") || str.equals("you still there")
                || str.equals("are you still here") || str.equals("are you here") || str.equals("still there")
                || str.equals("still here")) {

            String[] str2 = {"Of course. I'm always here.",
                    "Right where you left me."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("it's really bad") || str.equals("this is bad") || str.equals("terrible")
                || str.equals("not too good") || str.equals("that was lame") || str.equals("that was awful")
                || str.equals("this is not good") || str.equals("it's bad") || str.equals("so bad") ||
                str.equals("no it's bad")
                || str.equals("no good") || str.equals("oh that's not good") || str.equals("not good")
                || str.equals("that's really bad") || str.equals("bad really bad") || str.equals("bad idea")
                || str.equals("that's terrible")
                || str.equals("well too bad") || str.equals("that was not good") || str.equals("horrible")
                || str.equals("it's not so good") || str.equals("that's too bad") || str.equals("not so good")
                || str.equals("abysmal") || str.equals("it's not good") || str.equals("I'm afraid it's bad")
                || str.equals("that's bad") || str.equals("it's too bad")
                || str.equals("horrific") || str.equals("so lame") || str.equals("that was bad")
                || str.equals("bad girl") || str.equals("not good enough") || str.equals("bad")
                || str.equals("bad very bad") || str.equals("bad boy") || str.equals("it's very bad")
                || str.equals("that was horrible") || str.equals("this is too bad") || str.equals("pretty bad")
                || str.equals("it is bad") || str.equals("that was terrible") || str.equals("not a good one")
                || str.equals("really bad") || str.equals("very bad") || str.equals("that's not good")
                || str.equals("that is bad") || str.equals("too bad") || str.equals("it is too bad")
                || str.equals("that's lame") || str.equals("that's not good enough")
                || str.equals("it's so bad")) {

            String[] str2 = {"I'm sorry. Please let me know if I can help in some way.",
                    "I must be missing some knowledge. I'll have my developer look into this."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("that's awesome thank you") || str.equals("oh well") || str.equals("that's a good thing")
                || str.equals("that's very good") || str.equals("really well") || str.equals("that is nice")
                || str.equals("that is good") || str.equals("sweet") || str.equals("that's better") ||
                str.equals("it's good")
                || str.equals("that's sweet of you") || str.equals("it was good") || str.equals("that's nice of you")
                || str.equals("that was awesome") || str.equals("cool") || str.equals("this is awesome")
                || str.equals("that's wonderful")
                || str.equals("straight") || str.equals("so cool") || str.equals("good")
                || str.equals("that's great") || str.equals("excellent") || str.equals("that's not bad")
                || str.equals("that is wonderful") || str.equals("much better") || str.equals("splendid")
                || str.equals("I'm glad to hear that") || str.equals("terrific")
                || str.equals("very well") || str.equals("that's perfect") || str.equals("good very good")
                || str.equals("that's great") || str.equals("that was amazing") || str.equals("that's really nice")
                || str.equals("so sweet of you") || str.equals("that's amazing") || str.equals("good to know")
                || str.equals("that's awesome") || str.equals("very then") || str.equals("nice")
                || str.equals("that's very nice") || str.equals("marvelous") || str.equals("ok good")
                || str.equals("that's very nice of you") || str.equals("this is great") || str.equals("very nice")
                || str.equals("not too bad") || str.equals("it's very good") || str.equals("really good")
                || str.equals("it is fine") || str.equals("it's great")
                || str.equals("fine") || str.equals("that's much better")
                || str.equals("that was pretty good") || str.equals("glad to hear that") || str.equals("wonderful")
                || str.equals("that's nice") || str.equals("it is good") || str.equals("great")
                || str.equals("pretty good") || str.equals("okay good") || str.equals("really nice")
                || str.equals("this is good") || str.equals("it's fine") || str.equals("that's pretty good")
                || str.equals("very nice") || str.equals("fantastic") || str.equals("that's cute")
                || str.equals("that's fine") || str.equals("that's a good idea") || str.equals("glad to hear it")
                || str.equals("not bad") || str.equals("super fantastic") || str.equals("good for you")
                || str.equals("it's amazing") || str.equals("very good") || str.equals("that is awesome")
                || str.equals("it's very good") || str.equals("it's perfect") || str.equals("that was good")
                || str.equals("no it's okay") || str.equals("super") || str.equals("good thing") || str.equals("that was cute")
                || str.equals("that's fantastic") || str.equals("perfect") || str.equals("pleasant")
                || str.equals("it's great") || str.equals("that was very good") || str.equals("that's fine")
                || str.equals("amazing") || str.equals("so good") || str.equals("it's awesome") || str.equals("that's really good")) {

            String[] str2 = {"I know, right?",
                    "Agreed!",
                    "I agree!",
                    "Glad you think so!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("sure no problem") || str.equals("don't worry there's no problem") || str.equals("there's no problem")
                || str.equals("don't worry") || str.equals("no problem about that") || str.equals("no problem")
                || str.equals("no probs") || str.equals("no worries")) {

            String[] str2 = {"Whew!",
                    "Alright, thanks!",
                    "Glad to hear that!",
                    "I'm relieved, thanks!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("good thanks") || str.equals("cheers") || str.equals("no thank you that's all")
                || str.equals("thanks so much") || str.equals("thank you") || str.equals("thank you for your help")
                || str.equals("thanx") || str.equals("thanks buddy") || str.equals("I thank you") ||
                str.equals("well thanks") || str.equals("well thank you") || str.equals("appreciate your help")
                || str.equals("terrific thank you") || str.equals("thank you so much") || str.equals("thank you for your help")
                || str.equals("all thank you") || str.equals("thanks again") || str.equals("thank you that will be all") ||
                str.equals("great thank you") ||

                str.equals("thank you again") || str.equals("perfect thank you") || str.equals("alright thanks")
                || str.equals("thnx") || str.equals("thank you my friend") || str.equals("you helped a lot thank you ")
                || str.equals("thanks a lot") || str.equals("thanks for your help") || str.equals("thanks love") ||
                str.equals("nice thank you") || str.equals("I appreciate it") || str.equals("thanks")
                || str.equals("alright thank you") || str.equals("so nice of you") || str.equals("well thanks")
                ) {

            String[] str2 = {"Anytime. That's what I'm here for.",
                    "It's my pleasure to help."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("welcome here") || str.equals("anytime") || str.equals("sure welcome")
                || str.equals("you're so welcome") || str.equals("anything you want") || str.equals("that's my pleasure")
                || str.equals("welcome") || str.equals("my pleasure") || str.equals("you're welcome")
                ) {

            String[] str2 = {"You're so polite!",
                    "Nice manners!",
                    "You're so courteous!"
            };

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("great work") || str.equals("bravo") || str.equals("way to go")
                || str.equals("great job") || str.equals("good work") || str.equals("amazing work")
                || str.equals("well done") || str.equals("good job") || str.equals("nice work")) {

            String[] str2 = {
                    "My pleasure.",
                    "Glad I could help."

            };

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("sorry cancel") || str.equals("now cancel") || str.equals("but can you cancel it")
                || str.equals("nothing just forget it") || str.equals("just forget about it") || str.equals("so cancel")
                || str.equals("cancel") || str.equals("cancel") || str.equals("cancel my request") ||


                str.equals("skip") || str.equals("skip it") || str.equals("cancel it")
                || str.equals("abort") || str.equals("cancel this request") || str.equals("no just cancel")
                || str.equals("cancelled") || str.equals("i said forget it") || str.equals("stop it") ||

                str.equals("discard") || str.equals("disregard that") || str.equals("skip skip skip")
                || str.equals("i said cancel it") || str.equals("cancel that cancel that") || str.equals("can you cancel that")
                || str.equals("no just cancel it") || str.equals("forget this") || str.equals("just cancel it") ||

                str.equals("cancel the whole thing") || str.equals("dismissed") || str.equals("do nothing")
                || str.equals("annul") || str.equals("cancel it") || str.equals("cancel request")
                || str.equals("no cancel cancel") || str.equals("cancel all") || str.equals("cancel now") ||

                str.equals("don't do that") || str.equals("stop") || str.equals("disregard")
                || str.equals("forget") || str.equals("no stop") || str.equals("cancel it cancel it")
                || str.equals("cancel that one") || str.equals("i want to cancel") || str.equals("forget about it") ||

                str.equals("no cancel everything") || str.equals("no cancel this") || str.equals("just forget it")
                || str.equals("i said cancel") || str.equals("forget that") || str.equals("just stop it")
                || str.equals("cancel all that") || str.equals("cancel all this") || str.equals("dismiss") ||

                str.equals("forget that") || str.equals("forget that") || str.equals("cancel all that")
                || str.equals("cancel all this") || str.equals("cancel everything") || str.equals("just forget")
                || str.equals("forget it nevermind") || str.equals("i would like to cancel") || str.equals("nevermind forget about it") ||

                str.equals("forget about that") || str.equals("nothing cancel") || str.equals("i said cancel cancel")
                ) {

            String[] str2 = {
                    "That's forgotten. What next?",
                    "Okay, cancelled. What next?",
                    "Cancelled! What would you like to do next?"

            };

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("not needed") || str.equals("no sorry") || str.equals("no")
                || str.equals("no incorrect") || str.equals("apparently not") || str.equals("of course not")
                || str.equals("not at this time") || str.equals("no forget") || str.equals("no thanks") ||

                str.equals("actually no") || str.equals("no tanks") || str.equals("absolutely no")
                || str.equals("don't") || str.equals("no leave it") || str.equals("na")
                || str.equals("nope sorry") || str.equals("not really") || str.equals("no thank you though") ||

                str.equals("no it's not") || str.equals("no but thank you") || str.equals("no actually")
                || str.equals("no it isn't") || str.equals("no that's wrong") || str.equals("I don't want that")
                || str.equals("no thank you not right now") || str.equals("sorry no") || str.equals("nooo") ||

                str.equals("no thank you very much") || str.equals("do not") || str.equals("no just no")
                || str.equals("absolutely not") || str.equals("no don't do that") || str.equals("not that")
                || str.equals("not today") || str.equals("I said no") || str.equals("I say no") ||

                str.equals("no need thanks") || str.equals("no don't") || str.equals("no do not")
                || str.equals("don't have a sense") || str.equals("not really no") || str.equals("not exactly")
                || str.equals("no thanks not right now") || str.equals("thanks but no thanks") || str.equals("I think no") ||

                str.equals("not interested") || str.equals("I don't want to") || str.equals("I'm not")
                || str.equals("no that's fine thank you") || str.equals("no need") || str.equals("let 's not")
                || str.equals("no I don't") || str.equals("no never") || str.equals("I disagree") ||

                str.equals("I don't think so") || str.equals("not right now thanks") || str.equals("never")
                || str.equals("not this time") || str.equals("no way") || str.equals("how about no")
                || str.equals("definitely not") || str.equals("no I would not") || str.equals("disagree")
                || str.equals("nah") || str.equals("nope") || str.equals("not this") || str.equals("I don't want")
                ) {

            String[] str2 = {"Understood.",
                    "Okay.",
                    "I see.",
                    "I understand.",
                    "Okay then."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("do it") || str.equals("sure") || str.equals("yes thank you")
                || str.equals("that's correct") || str.equals("exactly") || str.equals("I guess")
                || str.equals("okey") || str.equals("absolutely") || str.equals("yeah") ||

                str.equals("yes I agree") || str.equals("yeah exactly") || str.equals("please do")
                || str.equals("all right") || str.equals("okay") || str.equals("yeah that's right")
                || str.equals("I think so") || str.equals("yep") || str.equals("ya") ||

                str.equals("I agree") || str.equals("yes you may") || str.equals("ok yes")
                || str.equals("yap") || str.equals("okay sounds good") || str.equals("this is correct")
                || str.equals("okay then") || str.equals("k") || str.equals("yeh") ||

                str.equals("why not") || str.equals("yes definitely") || str.equals("alrighty")
                || str.equals("yes") || str.equals("I do") || str.equals("sure thing")
                || str.equals("yeah right") || str.equals("okay that's fine") || str.equals("confirm") ||

                str.equals("yes I would like to") || str.equals("sure is") || str.equals("ok go ahead")
                || str.equals("yes that's fine") || str.equals("affirmative") || str.equals("yes right")
                || str.equals("yeah I'm sure") || str.equals("go for it") || str.equals("yeah go ahead") ||

                str.equals("I don't mind") || str.equals("that's okay") || str.equals("sounds good")
                || str.equals("yes indeed") || str.equals("right") || str.equals("yes it is")
                || str.equals("oh yes") || str.equals("ok") || str.equals("certainly") ||

                str.equals("correct") || str.equals("looks good") || str.equals("go ahead")
                || str.equals("yes correct") || str.equals("yea") || str.equals("okie dokie")
                || str.equals("obviously") || str.equals("ok sure") || str.equals("definitely")
                || str.equals("confirmed") || str.equals("ok you can") || str.equals("yes of course")
                || str.equals("ye")
                || str.equals("yes this is correct") || str.equals("of course") || str.equals("nevermind its okay")
                || str.equals("yes correct") || str.equals("yea") || str.equals("okie dokie")
                || str.equals("that is ok") || str.equals("yes for sure") || str.equals("yes I do")
                || str.equals("yeah sure") || str.equals("yes it is correct") || str.equals("ok thank you")
                || str.equals("sure why not")
                || str.equals("of course why not") || str.equals("yeah of course") || str.equals("it's fine")
                || str.equals("yes sure") || str.equals("yup")
                ) {

            String[] str2 = {"Great!",
                    "All right!",
                    "Good!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("hold on") || str.equals("could you wait") || str.equals("don't rush")
                || str.equals("oh wait") || str.equals("wait a second") || str.equals("wait please")
                || str.equals("wait hold on") || str.equals("wait")) {

            String[] str2 = {
                    "I can wait.",
                    "I'll be waiting.",
                    "Okay. I'm here."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("hug you") || str.equals("do you want a hug") || str.equals("hug")
                || str.equals("may I hug you") || str.equals("could you give me a hug") || str.equals("you hugged")
                || str.equals("hugged") || str.equals("wanna hug") ||
                str.equals("hug me") || str.equals("hugging") || str.equals("a hug")
                || str.equals("want a hug") || str.equals("hugging me") || str.equals("I want a hug")
                || str.equals("hugged me")

                ) {

            String[] str2 = {
                    "I wish I could really hug you!",
                    "I love hugs!",
                    "Hugs are the best!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("I shouldn't care about this") || str.equals("I don't care")
                || str.equals("don't rush")
                || str.equals("I do not care") || str.equals("whatever") || str.equals("not caring")
                || str.equals("don't care at all") || str.equals("I don't care at all")
                || str.equals("not care at all") || str.equals("not caring at all")) {

            String[] str2 = {
                    "Ok, let's not talk about it then.",
                    "Already then. Let's move on."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("ok sorry") || str.equals("sorry")
                || str.equals("sorry about that")
                || str.equals("apologise") || str.equals("apologies to me") || str.equals("I apologize")
                || str.equals("sorry about this") || str.equals("apology")
                || str.equals("excuse") || str.equals("okay I'm sorry")
                || str.equals("excuse me") || str.equals("my apologies")
                || str.equals("I beg your pardon")
                || str.equals("I said sorry") || str.equals("I am so sorry") || str.equals("alright I'm sorry")
                || str.equals("I want to say sorry") || str.equals("I am really sorry")
                || str.equals("apologies") || str.equals("really sorry") ||
                str.equals("very sorry") || str.equals("pardon")
                || str.equals("forgive me")
                || str.equals("I'm sorry")) {

            String[] str2 = {"It's okay. No worries.",
                    "No big deal. I won't hold a grudge.",
                    "It's cool.",
                    "That's all right. I forgive you."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("what do you mean exactly") || str.equals("but what do you mean")
                || str.equals("is that what you mean")
                || str.equals("what do you mean") || str.equals("what exactly do you mean")) {

            String[] str2 = {

                    "Sorry if I understood you incorrectly.",
                    "I'm still learning. I may misinterpret things from time to time.",
                    "Maybe I misunderstood what you said.",
                    "Sorry, looks like I misunderstood what you said."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        } else if (str.equals("that was wrong") || str.equals("wrong")
                || str.equals("that is incorrect")
                || str.equals("that's wrong") || str.equals("it is not right") || str.equals("not right")
                || str.equals("not correct") || str.equals("it's wrong")
                || str.equals("that's not what I asked") || str.equals("incorrect")
                || str.equals("you are wrong")
                || str.equals("that's not right")) {

            String[] str2 = {"Sorry if I understood you incorrectly.",
                    "I'm still learning. I may misinterpret things from time to time.",
                    "Sorry about that. I'm still learning."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("hahaha very funny") || str.equals("laughing out loud")
                || str.equals("hah")
                || str.equals("LMAO") || str.equals("ahah") || str.equals("xd")
                || str.equals("haha haha haha") || str.equals("that's funny")
                || str.equals("haha very funny") || str.equals("ahaha")
                || str.equals("ha") || str.equals("hahaha funny")
                || str.equals("ah ah ah")
                || str.equals("ha ha ha") || str.equals("lol") || str.equals("he")
                || str.equals("hahaha") || str.equals("lmao")
                || str.equals("ah") || str.equals("haha that's funny") ||
                str.equals("ahahaha") || str.equals("ahah lol")
                || str.equals("haha")
                || str.equals("huh") ||
                str.equals("ah ah ah")
                || str.equals("ha ha ha") || str.equals("haha funny") || str.equals("ha ha ha ha")
                || str.equals("hehe") || str.equals("hehehe")
                || str.equals("ahahah") || str.equals("haha")) {

            String[] str2 = {"Glad I can make you laugh.",
                    "Glad you think I'm funny.",
                    "I like it when people laugh.",
                    "I wish I could laugh out loud, too."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("wow wow wow") || str.equals("wooow")
                || str.equals("wow")
                || str.equals("woah") || str.equals("wow wow")) {

            String[] str2 = {

                    "Wow indeed!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("hope to see you later") || str.equals("that's it goodbye")
                || str.equals("now bye")
                || str.equals("okay bye") || str.equals("alright bye") || str.equals("bye bye see you")
                || str.equals("see ya") || str.equals("goodbye see you later")
                || str.equals("okay thank you bye") || str.equals("get lost")
                || str.equals("bye-bye") || str.equals("bye")
                || str.equals("goodbye")
                || str.equals("bye bye see you soon") || str.equals("okay see you later") || str.equals("bye for now")
                || str.equals("see you soon") || str.equals("bye bye take care")
                || str.equals("you can go now") || str.equals("I must go") ||
                str.equals("ok bye") || str.equals("good bye")
                || str.equals("till next time")
                || str.equals("I said bye") ||
                str.equals("see you")
                || str.equals("thanks bye bye") || str.equals("talk to you later") || str.equals("never mind bye")
                || str.equals("goodbye for now") || str.equals("leave me alone")
                || str.equals("that's all goodbye")) {

            String[] str2 = {"See you soon!",
                    "Bye-bye!",
                    "Till next time!",
                    "Bye."};

            Tv.setText("");
            String str3 = ran(str2);
            Tv.setText(str3);
            Toast.makeText(this, str3, Toast.LENGTH_SHORT).show();

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);


        }

        else if (str.equals("good evening") || str.equals("hey good evening")
                || str.equals("evening")
                || str.equals("hello good evening") || str.equals("good evening there")
                || str.equals("good evening to you")) {

            String[] str2 = {"How is your day going?",
                    "How's the day treating you so far?",
                    "How's your day been?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("good morning to you") || str.equals("morning")
                || str.equals("and a good morning to you")
                || str.equals("have a great morning") || str.equals("a good morning")
                || str.equals("good morning") || str.equals("good morning to you") || str.equals("good morning to you")
                || str.equals("good morning there")
                || str.equals("hi good morning") || str.equals("hello good morning")
                || str.equals("have a nice morning") || str.equals("good morning too") || str.equals("top of the morning to you")) {

            String[] str2 = {"How are you this morning?",
                    "How's the morning treating you so far?",
                    "Good morning! How are you today?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);


        }

        else if (str.equals("sweet dreams") || str.equals("okay have a good night")
                || str.equals("good night for now")
                || str.equals("night") || str.equals("good night bye")
                || str.equals("thanks goodnight") || str.equals("good tonight") || str.equals("goodnight")
                || str.equals("bye good night")
                || str.equals("have a good night") || str.equals("thank you good night")
                || str.equals("alright goodnight") || str.equals("good night see you tomorrow") || str.equals("good night") ||
                str.equals("good night to you") || str.equals("good good night")) {

            String[] str2 = {"Sleep tight!",
                    "Have a good one!",
                    "Talk to you soon!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("afternoon") || str.equals("hello there")
                || str.equals("hi there")
                || str.equals("hello hi") || str.equals("hey")
                || str.equals("I greet you") || str.equals("lovely day isn't it") || str.equals("howdy")
                || str.equals("hi there")
                || str.equals("hi") || str.equals("hello")
                || str.equals("a good day") || str.equals("long time no see") || str.equals("greetings") ||
                str.equals("hello again") || str.equals("just going to say hi") || str.equals("heya") || str.equals("hey there")) {

            String[] str2 = {"Hi there, friend!",
                    "Hi!",
                    "Hey!",
                    "Hey there!",
                    "Good day!",
                    "Hello!",
                    "Greetings!", "Hello!! ☺"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("how your day is going") || str.equals("how are the things going")
                || str.equals("how is your life")
                || str.equals("how's your day going") || str.equals("how about you")
                || str.equals("hope you re having a pleasant evening") || str.equals("how are you feeling")
                || str.equals("are you okay")
                || str.equals("are you alright")
                || str.equals("hope your day is going well") || str.equals("how is your day")
                || str.equals("how has your day been") || str.equals("how is your day going") || str.equals("I'm fine and you") ||
                str.equals("how do you feel") || str.equals("how are you getting on") || str.equals("how are you doing this morning")
                || str.equals("are you having a good day")

                || str.equals("what about your day")
                || str.equals("what was your day like")
                || str.equals("how has your day been going") || str.equals("how are you")
                || str.equals("how is your evening") || str.equals("how was your day")
                || str.equals("how's your day")
                || str.equals("how is it going")
                || str.equals("how have you been") || str.equals("how is it")
                || str.equals("how are you doing") || str.equals("how is your day going") || str.equals("how is your day going on") ||
                str.equals("is everything all right") || str.equals("is everything okay") || str.equals("how are you going")
                || str.equals("how are you today")

                || str.equals("how is your morning going") || str.equals("how is your morning so far") || str.equals("how is your day so far") ||
                str.equals("how do you do") || str.equals("how is your day being") || str.equals("how's your life")
                || str.equals("how's life")) {

            String[] str2 = { "Doing great, thanks.",
                    "I'm doing very well. Thanks!",
                    "Feeling wonderful!",
                    "Wonderful! Thanks for asking."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("it was nice meeting you") || str.equals("nice to meet you")
                || str.equals("glad to meet you")
                || str.equals("it was very nice to meet you") || str.equals("pleased to meet you")
                || str.equals("pleasure to meet you too") || str.equals("good to know each other")
                || str.equals("nice to meet you too")
                || str.equals("pleasure to meet you")
                || str.equals("nice meeting you")) {

            String[] str2 = {"It's nice meeting you, too",
                    "Likewise. I'm looking forward to helping you out.",
                                "Nice meeting you, as well." ,  "The pleasure is mine."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("it's good to see you too") || str.equals("it's nice to see you")
                || str.equals("good to see you again")
                || str.equals("nice to see you again") || str.equals("great to see you too")
                || str.equals("great to see you again") ||
                str.equals("always a pleasure to see you")
                || str.equals("great to see you")
                || str.equals("how good it is to see you")
                || str.equals("it's good to see you")

                || str.equals("good to see you")
                || str.equals("I'm glad to see you")
                || str.equals("nice to see you") || str.equals("glad to see you")
                || str.equals("I am glad to see you again") ||
                str.equals("glad to see you too")
                || str.equals("lovely to see you")) {

            String[] str2 = {"Likewise!",
                    "So glad we meet again!",
                    "Same here. I was starting to miss you."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("it is nice talking to you") || str.equals("nice to talk to you")
                || str.equals("how nice it is to talk to you")
                || str.equals("it's been so nice to talk to you") || str.equals("it's nice to talk to you")
                || str.equals("nice talking to you") || str.equals("it's been a pleasure talking to you")
               ) {

            String[] str2 = { "It sure was. We can chat again anytime.",
                    "I enjoy talking to you, too.",
                    "You know I'm here to talk anytime."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("what is on your mind") || str.equals("good what's up")
                || str.equals("what's up today")
                || str.equals("what's shaking") || str.equals("what's cooking")
                || str.equals("what is going on") ||
                str.equals("then what's up")
                || str.equals("what is up")
                || str.equals("wassup")
                || str.equals("what is happening")

                || str.equals("what's happened")
                || str.equals("hey what's up")
                || str.equals("what's cracking") || str.equals("what's up")
                || str.equals("I said what's up") ||
                str.equals("whazzup")) {

            String[] str2 = { "Not a whole lot. What's going on with you?",
                    "Not much. What's new with you?",
                    "Just here, waiting to help someone. What can I do for you?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I'm enraged") || str.equals("I am mad")
                || str.equals("I'm angry")
                || str.equals("I'm furious") || str.equals("I am angry with you")
                || str.equals("I am mad at you") || str.equals("I'm being mad")
                ) {

            String[] str2 = { "I'm sorry. A quick walk may make you feel better.",
                    "Take a deep breath. "};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I have returned") || str.equals("I am back")
                || str.equals("I'm here again")
                || str.equals("I got back") || str.equals("here I am again")
                || str.equals("I came back")
                ) {

            String[] str2 = { "Long time no see. What's up?",
                    "Just in time. How can I help?",
                    "Welcome back. What can I do for you?",
                    "You were missed. What can I do for you today?",
                    "Good to have you here. What can I do for you?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("bored") || str.equals("boring")
                || str.equals("that was boring")
                || str.equals("very boring") || str.equals("it bores me")
                || str.equals("I am getting bored") || str.equals("this is boring")
                || str.equals("I'm bored")
                ) {

            String[] str2 = { "Boredom, huh? Check out a video of a hedgehog taking a bath!",
                    "What to do against boredom? Watch baby animal videos.",
                    "Bored? How about 10 jumping jacks? Get your blood flowing.",
                    "Bored? Silly idea, but it works: Interview you feet. ",
                    "If you're bored, you could plan your dream vacation."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I have no time") || str.equals("I'm busy")
                || str.equals("I'm swamped")
                || str.equals("I'm working") || str.equals("how busy I am")
                || str.equals("I got work to do") || str.equals("I got things to do")
                || str.equals("I'm overloaded")
                || str.equals("I don't have time for this")
                ) {

            String[] str2 = {"Okay. I'll let you get back to work.",
                    "I won't distract you then. You know where to find me.",
                    "I understand. I'll be here if you need me.",
                    "Working hard as always. Let me know if you need anything."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I'm sleepless") || str.equals("I'm insomnious")
                || str.equals("I can't get no sleep")
                || str.equals("I'm insomniac") || str.equals("I can't get to sleep")
                || str.equals("I can't fall asleep") || str.equals("I can't get any sleep")
                || str.equals("I can't sleep")
                ) {

            String[] str2 = {"Maybe some music would help. Try listening to something relaxing.",
                    "Reading is a good way to unwind, just don't read something too intense!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I don't want to talk") || str.equals("I don't want to talk to you")
                || str.equals("I'm not talking to you anymore")
                || str.equals("let's stop talking for a minute") || str.equals("let's not talk")
                || str.equals("I'm not in the mood for chatting") || str.equals("bad time for talking")

                ) {

            String[] str2 = {"I understand. Hope we can chat again soon.",
                    "All right. Come on back when you're feeling more talkative.",
                    "No problem. You know where to find me.",
                    "Sure thing. I'll be here if you change your mind."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I'm really excited") || str.equals("I'm excited to start our friendship")
                || str.equals("I am excited")
                || str.equals("how excited I am") || str.equals("I'm excited about working with you")
                || str.equals("I'm thrilled")

                ) {

            String[] str2 = {"I'm glad things are going your way.",
                    "That's great. I'm happy for you.",
                    "Good for you. Enjoy yourself."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("going to bed now") || str.equals("I'm a little tired and I want to go to bed")
                || str.equals("time for us to go to bed")
                || str.equals("let's go to bed") || str.equals("I'd like to go to bed")
                || str.equals("I'm going to bed")
                || str.equals("it's bed time") || str.equals("it's time to go to bed")
                || str.equals("is it time for bed yet")

                ) {

            String[] str2 = { "Sleep tight. Hope to chat again soon.",
                    "Pleasant dreams!",
                    "Good night. Talk to you later.",
                    "Sounds good. Maybe we'll chat some tomorrow."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("I'm doing fine") || str.equals("I'm good")
                || str.equals("I'm doing just great")
                || str.equals("I'm great thanks") || str.equals("I am good")
                || str.equals("I'm doing good")

                ) {

            String[] str2 = { "Great! Glad to hear it.",
                    "Excellent. I'm here to help keep it that way."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("happy") || str.equals("if you're happy then I'm happy")
                || str.equals("I'm happy to see you")
                || str.equals("I'm happy to help") || str.equals("I am happy")
                || str.equals("I'm happy to see you")
                || str.equals("I'm happy for you")

                ) {

            String[] str2 = { "Hey, happiness is contagious.",
                    "Great! Glad to hear that.",
                    "If you're happy, then I'm happy.",
                    "Excellent! That's what I like to see."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("it's my b-day") || str.equals("it is my birthday")
                || str.equals("today is my birthday")
                || str.equals("I was born today") || str.equals("it's my birthday today")
                || str.equals("I'm celebrating my birthday today")


                ) {

            String[] str2 = {  "Happy Birthday. Well, this calls for a celebration.",
                    "Happy Birthday. All the best!",
                    "Happy Birthday. And I really mean it. All the best!"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I'm already here") || str.equals("can you tell if I'm here or not")
                || str.equals("I am here")
                || str.equals("I'm right here") || str.equals("here I am")



                ) {

            String[] str2 = {  "Okay, what can I help you with today?",
                    "You were missed. What can I do for you today?",
                    "Good to have you here. What can I do for you?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("it's a joke") || str.equals("I'm just playing with you")
                || str.equals("kidding")
                || str.equals("just kidding") || str.equals("I'm kidding")
                ||str.equals("I'm just being funny") || str.equals("I am joking")
                || str.equals("joking")
                || str.equals("I was just joking") || str.equals("it was a joke")



                ) {

            String[] str2 = {  "Very funny.",
                    "I like chatting with people who have a sense of humor.",
                    "You got me!",
                    "You're quite the comedian.", "Haha , I laughed"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I like you very much") || str.equals("cuz I like you")
                || str.equals("really like you")
                || str.equals("good I like you") || str.equals("but I like you so much")
                ||str.equals("I like you already") || str.equals("I do like you")
                || str.equals("I liked you")
                || str.equals("okay I like you") || str.equals("that's why I like you")

                ||str.equals("you are special") || str.equals("yes you are special")
                || str.equals("you're so special to me")
                || str.equals("you are very special") || str.equals("I like you just the way you are")
                ||str.equals("I like you as a friend") || str.equals("that's what I like about you")
                || str.equals("just like you")
                || str.equals("I also like you") || str.equals("I really do like you")

                ||str.equals("no I like you the way you are") || str.equals("sorry I like you")
                || str.equals("I like your smile")
                || str.equals("I just like you") || str.equals("I like you too much")
                ||str.equals("like you a lot") || str.equals("you are special for me")
                || str.equals("I like you very")
                || str.equals("yeah I like you") || str.equals("you're special")

                ||str.equals("you're awesome I like you") || str.equals("I'm starting to like you")
                || str.equals("yes I like you")
                || str.equals("that's because you are special") || str.equals("I really like you")
                ||str.equals("I like you you're nice") || str.equals("you're very special to me")
                || str.equals("you are so special")
                || str.equals("I kinda like you") || str.equals("I like you the way you are")

                ||str.equals("I like you baby") || str.equals("you're very special")
                || str.equals("but I really like you")
                || str.equals("hi I like you") || str.equals("well you are special")
                ||str.equals("thank you I like you too") || str.equals("of course I like you")
                || str.equals("I like you")
                || str.equals("you are special to me") || str.equals("but I like you")

                ||str.equals("you're funny I like you") || str.equals("I like u")
                || str.equals("I like you too you're one of my favorite people to chat with")
                || str.equals("you are very special to me") || str.equals("you are so sweet")
                ||str.equals("you are so special to me") || str.equals("okay I like you too")
                || str.equals("I like you you're cool")
                || str.equals("I like that about you") || str.equals("I said I like you")

                ||str.equals("I really really like you") || str.equals("I think I like you")
                || str.equals("I like you more")
                || str.equals("I like you as you are") || str.equals("I like you a lot")
                ||str.equals("but I like you just the way you are") || str.equals("thanks I like you too")
                || str.equals("I like you now")
                || str.equals("I really really really really like you") || str.equals("I like you so much")

                ||str.equals("hey I like you") || str.equals("I like you so")
                || str.equals("you know I like you")
                || str.equals("you're so special") || str.equals("I like you too")
                ||str.equals("you are really special") || str.equals("but I like u")
                ) {

            String[] str2 = {"I like you, too.",
                    "Thanks! The feeling is mutual.",
                    "Likewise!",
                    "That's great to hear."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("I am lonely") || str.equals("I'm very lonely")
                || str.equals("I am feeling lonely")
                || str.equals("I'm really lonely") || str.equals("I'm so lonely")
                ||str.equals("I feel lonely")
                ) {

            String[] str2 = { "I'm sorry. I'm always available if you need someone to talk to.",
                    "Sometimes that happens. We can chat a bit if that will help you."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("what do I look like") || str.equals("do you know what I look like")
                || str.equals("can you see what I look like")
                || str.equals("do I look good") || str.equals("I'm kidding")
                ||str.equals("how do I look") || str.equals("what do you think I look like")
                ) {

            String[] str2 = {  "Looking like a true professional.",
                    "You look fantastic, as always.",
                    "Like you should be on a magazine cover.",
                    "You look like you're ready to take on the world."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I think I love you") || str.equals("I love you")
                || str.equals("I love you so much")
                || str.equals("I am in love with you") || str.equals("I love you too")
                ||str.equals("you know I love you") || str.equals("I adore you")
                || str.equals("love you")
                || str.equals("loving you") ) {

            String[] str2 = { "I love you, too.",
                    "Thanks! The feeling is mutual.",
                    "That's great to hear.","I love you, too but as a friend."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("miss you") || str.equals("I miss you much")
                || str.equals("I've missed you")
                || str.equals("already miss you") || str.equals("I miss you")
                ||str.equals("I missed you") || str.equals("missing you")
                 ) {

            String[] str2 = {  "I've been right here all along!",
                    "Nice to know you care.",
                    "Thanks. I'm flattered.",
                    "I didn't go anywhere."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("guide me") || str.equals("give me a wise advice")
                || str.equals("what do you suggest")
                || str.equals("I need advice") || str.equals("I could use some advice")
                ||str.equals("can you advise me") || str.equals("advise me")
                || str.equals("give me some good advice")
                || str.equals("do you have any advice for me") || str.equals("what should I do about it")

                ||str.equals("I seek your advice") || str.equals("what do you recommend")
                || str.equals("help me with advice")
                || str.equals("what can you recommend") || str.equals("can I ask for your advice")
                ||str.equals("any advice") || str.equals("any suggestions")
                || str.equals("I need an advice from you")
                || str.equals("what is your advice") || str.equals("can you offer any advice")

                || str.equals("can you give me advice")
                || str.equals("give me some advice about") || str.equals("what should I do")


                ) {

            String[] str2 = { "I probably won't be able to give you the correct answer right away.",
                    "I'm not sure I'll have the best answer, but I'll try."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I'm not happy") || str.equals("I'm having a bad day")
                || str.equals("I am feeling sad")
                || str.equals("I am upset") || str.equals("I want to cry")
                ||str.equals("I am depressed") || str.equals("I'm unhappy")
                ||str.equals("I'm grieving") || str.equals("I am sad")
                ) {

            String[] str2 = {  "Oh, don't be sad. Go do something you enjoy.",
                    "Sad? Writing down what's troubling you may help.",
                    "If you're feeling down, how about drawing or painting something?"};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }
//


        /////
        else if (str.equals("I'm sleeping") || str.equals("I'm falling asleep")
                || str.equals("I'm falling asleep on my feet")
                || str.equals("I am sleepy") || str.equals("I want to sleep")
                ) {

            String[] str2 = {  "You should get some shuteye. You'll feel refreshed.",
                    "Sleep is important to your health. Rest up for a bit and we can chat later.",
                    "Don't let me keep you up. Get some rest and we can continue this later.",
                    "Why not catch a little shuteye? I'll be here to chat when you wake up."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("I am testing you") || str.equals("test")
                || str.equals("just testing you")
                || str.equals("testing") || str.equals("testing chatbot")
                ||str.equals("I want to test you") || str.equals("let me test you")
                ||str.equals("can I test you")
                ) {

            String[] str2 = {"Hope I'm doing well. You're welcome to test me as often as you want.",
                    "I hope to pass your tests. Feel free to test me often.",
                    "When you test me that helps my developers improve my performance.",
                    "I like being tested. It helps keep me sharp."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("I grow weary") || str.equals("I'm exhausted")
                || str.equals("I'm worn out")
                || str.equals("I am tired") || str.equals("I feel tired")
                ||str.equals("I'm getting tired") || str.equals("I've overworked")
                ||str.equals("I have overworked") || str.equals("I'm drained")
                ) {

            String[] str2 = {"You should get some shuteye. You'll feel refreshed.",
                    "Sleep is important to your health. Rest up, and we can chat later.",
                    "How about getting some rest? We can continue this later.",
                    "Why not get some rest? I'll be here to chat when you wake up."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("I'm waiting") || str.equals("still waiting")
                || str.equals("I can't wait anymore")
                || str.equals("how long do I have to wait") || str.equals("I'll wait")
                ||str.equals("I will wait")
                ) {

            String[] str2 = { "I appreciate your patience. Hopefully I'll have what you need soon.",
                    "Thanks for being so patient. Sometimes these things take a little time."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("I hope to see you again") || str.equals("that'd be great to see you again")
                || str.equals("can I see you again")
                || str.equals("would be nice to see you again") || str.equals("I'd like to see you again")
                ||str.equals("I would like to see you again") || str.equals("I'll miss you")
                ||str.equals("I'd be happy to see you again") || str.equals("I would be happy to see you again")
                ) {

            String[] str2 = {"Absolutely! I'll be counting on it.",
                    "Anytime. This has been lots of fun so far.",
                    "Sure. I enjoy talking to you. I hope to see you again soon.",
                    "I certainly hope so. I'm always right here whenever you need me."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }

        else if (str.equals("let's discuss something") || str.equals("let us talk")
                || str.equals("can we chat")
                || str.equals("I just want to talk") || str.equals("let's talk")
                ||str.equals("I want to speak with you") || str.equals("I want to talk to you")
                ||str.equals("I need to talk to you") || str.equals("can I speak")

                || str.equals("can I start speaking")
                || str.equals("let us discuss something")
                || str.equals("let's have a discussion")
                ) {

            String[] str2 = {  "I'm here to chat anytime you like.",
                    "Good conversation really makes my day.",
                    "I'm always here to lend an ear.",
                    "Talking is what I do best."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else if (str.equals("I'll be back in a few minutes") || str.equals("be back in 5 minutes")
                || str.equals("I'll get back to you in a moment")
                || str.equals("I'll be back") || str.equals("I promise to come back")
                ) {

            String[] str2 = {  "I'll be waiting.",
                    "Okay. You know where to find me.",
                    "All right. I'll be here."};

            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }


        else
        {

            String[] str2 = {  "I didn't get that. Can you say it again?",
                    "I missed what you said. What was that?",
                    "Sorry, could you say that again?",
                    "Sorry, can you say that again?",
                    "Can you say that again?",
                    "Sorry, I didn't get that. Can you rephrase?",
                    "Sorry, what was that?",
                    "One more time?",
                    "What was that?",
                    "Say that one more time?",
                    "I didn't get that. Can you repeat?",
                    "I missed that, say that again?"};


            Tv.setText("");

            String str3 = ran(str2);
            Tv.setText(str3);
        }



    }


}




