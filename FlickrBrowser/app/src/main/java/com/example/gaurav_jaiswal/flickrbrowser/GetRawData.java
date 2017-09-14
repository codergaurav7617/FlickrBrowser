package com.example.gaurav_jaiswal.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE,PROCESSING,NOT_INITIALISED,FAILED_OR_EMPTY,OK}
/**
 * Created by gaurav_jaiswal on 5/3/17.
 */

class GetRawData extends AsyncTask<String,Void,String> {
    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    interface OnDownloadComplete{
        void onDownloadComplete(String data,DownloadStatus status);
    }



    public GetRawData(OnDownloadComplete callback) {

        this.mDownloadStatus=DownloadStatus.IDLE;
        mCallback=callback;
    }




    void runInSameThread(String s){

        Log.d(TAG, "runInSameThread: start");
     //   onPostExecute(doInBackground(s));
         if(mCallback!=null){

             mCallback.onDownloadComplete(doInBackground(s),mDownloadStatus);

         }
        Log.d(TAG, "runInSameThread: ends");

    }

    @Override
    protected void onPostExecute(String s) {

//        Log.d(TAG, "onPostExecute: parameter"+s);

        if(mCallback!=null){
            mCallback.onDownloadComplete(s,mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");

    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection=null;
        BufferedReader reader=null;

        if(params==null){

            mDownloadStatus=DownloadStatus.NOT_INITIALISED;
            return null;
        }

       try {
            mDownloadStatus=DownloadStatus.PROCESSING;
           URL url=new URL(params[0]);
           connection=(HttpURLConnection)url.openConnection();
           connection.connect();
           int response=connection.getResponseCode();
           Log.d(TAG, "doInBackground: Response code was "+response);
           StringBuilder result=new StringBuilder();
           reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
           String line;
           while (null!=(line=reader.readLine())){
               result.append(line).append("\n");
           }
           mDownloadStatus=DownloadStatus.OK;
           return result.toString();




       }catch (MalformedURLException e){
           Log.e(TAG, "doInBackground: Invalid Url"+e.getMessage() );
       }catch (IOException e){
           Log.e(TAG, "doInBackground: IO Exception reading Data"+e.getMessage());
       }catch (SecurityException e){
           Log.e(TAG, "doInBackground: Security permission.Need permission?"+e.getMessage() );
       }finally {
           if(connection!=null){
               connection.disconnect();
           }
           if(reader!=null){
               try{
                   reader.close();
               }catch (IOException e){
                   Log.e(TAG, "doInBackground: Error closing stream"+e.getMessage() );
               }
           }
       }

        mDownloadStatus=DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }



}
