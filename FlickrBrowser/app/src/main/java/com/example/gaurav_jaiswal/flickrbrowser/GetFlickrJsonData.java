package com.example.gaurav_jaiswal.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav_jaiswal on 7/3/17.
 */

class GetFlickrJsonData extends AsyncTask<String,Void,List<Photo>>implements GetRawData.OnDownloadComplete {

    private static final String TAG = "GetFlickrJsonData";
    private List<Photo> mPhotoList=null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;

    private boolean runningOnSameThread=false;

    interface OnDataAvailable{

        Void OnDataAvailable(List<Photo> data,DownloadStatus status);

    }





    void executeOnSameThread(String searchCriteria){
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUri=createUri(searchCriteria ,mMatchAll);
        GetRawData getRawData=new GetRawData(this);
        runningOnSameThread=true;
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread ends");

    }

    @Override
    protected void onPostExecute(List<Photo> photos) {

        Log.d(TAG, "onPostExecute: start");
        if(mCallBack!=null){
            mCallBack.OnDataAvailable(mPhotoList,DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");


    }


    @Override
    protected List<Photo> doInBackground(String... params) {

        Log.d(TAG, "doInBackground: start");
        String destinationUri=createUri(params[0],mMatchAll);
        GetRawData getRawData=new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground: ends");



        return mPhotoList;
    }



    public GetFlickrJsonData(OnDataAvailable callBack, String baseURL, boolean matchAll) {
        Log.d(TAG, "GetFlickrJsonData called");

        this.mBaseURL = baseURL;
        this.mCallBack = callBack;

        this.mMatchAll = matchAll;
    }






    private String createUri(String searchCriteria,boolean matchAll){
        Log.d(TAG, "createUri: uri started");

        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",matchAll ?"All":"ANY")
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();

    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        Log.d(TAG, "onDownloadComplete: status ="+status);
        if(status==DownloadStatus.OK){

            mPhotoList=new ArrayList<>();
            try {
                JSONObject jsonData=new JSONObject(data);
                JSONArray itemsArray=jsonData.getJSONArray("items");
                     for(int i=0;i<itemsArray.length();i++){
                        JSONObject jsonPhoto=itemsArray.getJSONObject(i);
                         String title=jsonPhoto.getString("title");
                         String author=jsonPhoto.getString("author");
                         String authorId=jsonPhoto.getString("author_id");
                         String tags=jsonPhoto.getString("tags");


                         JSONObject jsonMedia=jsonPhoto.getJSONObject("media");
                         String photoUrl=jsonMedia.getString("m");

                         String link=photoUrl.replaceFirst("_m.","_b.");


                          Photo photoObject=new Photo(title,author,authorId,link,tags,photoUrl);
                           mPhotoList.add(photoObject);

                         Log.d(TAG, "onDownloadComplete "+photoObject.toString());
                         

                     }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing json data "+e.getMessage() );
                status=DownloadStatus.FAILED_OR_EMPTY;
            }

        }

        if(runningOnSameThread && mCallBack!=null){

            //now inform the caller that processing is done  - possibly returning null if there
            //was an erreor

            mCallBack.OnDataAvailable(mPhotoList,status);

        }


        Log.d(TAG, "onDownloadComplete ends");

    }
}
