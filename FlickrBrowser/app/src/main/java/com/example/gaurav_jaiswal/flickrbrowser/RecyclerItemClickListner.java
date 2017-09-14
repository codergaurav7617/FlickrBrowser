package com.example.gaurav_jaiswal.flickrbrowser;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gaurav_jaiswal on 1/4/17.
 */

class RecyclerItemClickListner extends RecyclerView.SimpleOnItemTouchListener {

    private static final String TAG = "RecyclerItemClickListne";



    interface OnRecyclerClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);

    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListner(Context context, final RecyclerView recyclerView, final OnRecyclerClickListener mListener) {
        this.mListener = mListener;
        mGestureDetector=new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG, "onSingleTapConfirmed: starts");

                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null &&mListener!=null){

                    Log.d(TAG, "onSingleTapConfirmed: calling listner .onItemClick");
                    mListener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView));

                }

                return true;

            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");


                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null &&mListener!=null){


                    Log.d(TAG, "onSingleTapConfirmed: calling listner .onItemClick");
                    mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));


                }


            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        Log.d(TAG, "onInterceptTouchEvent: starts");
        if(mGestureDetector!=null){

            boolean result=mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned: "+result);
            return result;
        }
        
        else{
            Log.d(TAG, "onInterceptTouchEvent: returned false");
            return false;
        }

    }
}
