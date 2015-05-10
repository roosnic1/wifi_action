package com.koki.app.wifiaction;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.koki.app.wifiaction.model.Action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by koki on 25/03/15.
 */
public class ContentHandler {
    private static final String TAG = "ContentHandler";
    public static final String FILE = "com.koki.app.wifiaction.actionlist";

    private Context mContext;
    private IContentHandlerCallback mCallback;

    public ContentHandler(Context mContext, IContentHandlerCallback mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }


    public void startLoadingActionList() {
        new LoadContent().execute();
    }

    public void startSavingActionList(ArrayList<Action> actionList) {
        new SaveContent().execute(actionList);
    }


    private class LoadContent extends AsyncTask<Void,Void,ArrayList<Action>> {

        @Override
        protected ArrayList<Action> doInBackground(Void... params) {
            ArrayList<Action> actionList = null;
            try {
                FileInputStream fis = mContext.openFileInput(FILE);
                ObjectInputStream ois = new ObjectInputStream(fis);
                actionList = (ArrayList<Action>) ois.readObject();
                ois.close();
            } catch(FileNotFoundException e) {
                //e.printStackTrace();
                Log.i(TAG,"File not Found... (Not critical)");
                actionList = new ArrayList<>();
            } catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
                //TODO: Implement proper Error handling
                Log.e(TAG,"Error while loading File: " + FILE);
            }

            return actionList;
        }

        @Override
        protected void onPostExecute(ArrayList<Action> actions) {
            super.onPostExecute(actions);
            if(actions != null) {
                mCallback.onContentLoaded(actions);
            } else {
                mCallback.onError("Could not load ActionList");
            }

        }
    }


    private class SaveContent extends AsyncTask<ArrayList<Action>,Void,String> {
        @Override
        protected String doInBackground(ArrayList<Action>... params) {
            String errorMessage = null;
            try {
                FileOutputStream fos = mContext.openFileOutput(FILE,Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(params[0]);
                oos.close();
            } catch(IOException e) {
                //e.printStackTrace();
                Log.e(TAG,"Error while saving File: " + FILE);
                Log.e(TAG,"With error: " + e.toString());
                errorMessage = e.toString();
            }
            return errorMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null) {
                mCallback.onContentSaved();
            } else {
                mCallback.onError("Could not save ActionList with error: " + s);
            }
        }
    }


    public interface IContentHandlerCallback {
        void onContentLoaded(ArrayList<Action> actionList);
        void onContentSaved();
        void onError(String errorMessage);
    }
}
