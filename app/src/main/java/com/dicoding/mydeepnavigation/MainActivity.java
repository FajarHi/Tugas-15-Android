package com.dicoding.mydeepnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOpenDetail = (Button)findViewById(R.id.btn_open_detail);
        btnOpenDetail.setOnClickListener(this);
        DelayAsync delayAsync = new DelayAsync();
        delayAsync.execute();
    }

    @Override
    public void onClick(View v) {
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_TITLE,"Holla Good News");
        detailIntent.putExtra(DetailActivity.EXTRA_MESSAGE,"Now you can learn android in dicoding");
        startActivity(detailIntent);
    }

    private class DelayAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            showNotification(MainActivity.this,"Hi, how are you?","Do you have any plan this weekend? let,s hangout",110);
        }
    }
    private void showNotification(Context context,String title, String message,int notifId){
        Intent notifDetailIntent= new Intent(MainActivity.this,DetailActivity.class);
        notifDetailIntent.putExtra(DetailActivity.EXTRA_TITLE,title);
        notifDetailIntent.putExtra(DetailActivity.EXTRA_MESSAGE,message);
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(DetailActivity.class)
                .addNextIntent(notifDetailIntent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManagerCompat = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_email_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.white))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManagerCompat.notify(notifId, builder.build());

    }
}
