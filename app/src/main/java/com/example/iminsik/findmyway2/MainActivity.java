package com.example.iminsik.findmyway2;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        new AgentAsyncTask(this, "", "", "").execute();
    }


    private static class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        private WeakReference<AppCompatActivity> weakActivity;
        private String email;
        private String phone;
        private String license;

        public AgentAsyncTask(AppCompatActivity activity, String email, String phone, String license) {
            weakActivity = new WeakReference<>(activity);
            this.email = email;
            this.phone = phone;
            this.license = license;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            UserDao userDao = db.userDao();

            return userDao.getAll().size();
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {
            AppCompatActivity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            if (agentsCount > 0) {
                //2: If it already exists then prompt user
                Toast.makeText(activity, "Agent already exists!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, "Agent does not exist! Hurray :)", Toast.LENGTH_LONG).show();
                activity.onBackPressed();
            }
        }
    }
}

