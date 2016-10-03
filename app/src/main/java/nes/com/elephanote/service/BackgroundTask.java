package nes.com.elephanote.service;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


public class BackgroundTask extends AsyncTask<Void, Integer, Void> {

    private Context context;


    public BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected Void doInBackground(Void... params) {
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("services.conServise");
        serviceIntent.setPackage(context.getPackageName());
        context.startService(serviceIntent);
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onCancelled(Void result) {

    }

}