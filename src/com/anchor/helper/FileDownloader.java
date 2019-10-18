package com.anchor.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Image_Gellary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class FileDownloader {
    private static final int  MEGABYTE = 2024 * 2024;
    static Context context;
    static ProgressDialog dialogs;
    static File pdfFile;
    static DataBaseHelper dbvoc;
    static int hash_size_count = 0;
    public static void downloadFile(final Context contexts, ProgressDialog dialog, String file_format){
        try {



            context = contexts;
            dialogs = dialog;
            dbvoc = new DataBaseHelper(context);

            new MyTask().execute();

//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                   // dialogs = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
//                    dialogs.setMessage("Please Wait...");
//                    dialogs.setCancelable(false);
//                    dialogs.setTitle("iPortal Intranet");
//                    dialogs.show();
//                }
//            });






//            ((Activity)context).runOnUiThread(new Runnable() {
//                public void run() {
//
//                }
//            });



//            if(file_format.equalsIgnoreCase("pdf"))
//            {
//                File pdfFiles = new File(Environment.getExternalStorageDirectory() + "/PORTELPDF/" + file_name);  // -> filename = maven.pdf
//                Uri path = Uri.fromFile(pdfFiles);
//                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//                pdfIntent.setDataAndType(path, "application/pdf");
//                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                try{
//                    pdfIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(pdfIntent);
//                }catch(ActivityNotFoundException e){
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialogs.dismiss();
//                            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//
//            }else {
//                File pdfFiles = new File(Environment.getExternalStorageDirectory() + "/PORTELPDF/" + file_name);  // -> filename = maven.pdf
//                Uri path = Uri.fromFile(pdfFiles);
//                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//                pdfIntent.setDataAndType(path, "text/html");
//                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                try{
//                    pdfIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(pdfIntent);
//                }catch(ActivityNotFoundException e){
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialogs.dismiss();
//                            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }





        } catch (Exception e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    dialogs.dismiss();

                }
            });
            e.printStackTrace();
        }
    }


    static class MyTask extends AsyncTask<Void, Void, Void> {
        //ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd = new ProgressDialog(context);
//            pd.setMessage("loading");
//            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Anchor_NewLaunch");
            folder.mkdir();

            Iterator myVeryOwnIterator = Global_Data.Download_hashmap.keySet().iterator();

            while(myVeryOwnIterator.hasNext()) {
                String key=(String)myVeryOwnIterator.next();
                String value= Global_Data.Download_hashmap.get(key);
                Log.d("Map Key: ",key);
                Log.d("Map Value: ",value);

                hash_size_count++;

                pdfFile = new File(folder, key);
                File file = new File(extStorageDirectory,"Anchor_NewLaunch"+"/"+key);

                try{
                    pdfFile.createNewFile();

                    URL url = new URL(value);
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    //urlConnection.setRequestMethod("GET");
                    //urlConnection.setDoOutput(true);
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                    int totalSize = urlConnection.getContentLength();

                    byte[] buffer = new byte[MEGABYTE];
                    int bufferLength = 0;
                    while((bufferLength = inputStream.read(buffer))>0 ){
                        fileOutputStream.write(buffer, 0, bufferLength);
                    }
                    fileOutputStream.close();
                    // Global_Data.Download_hashmap.remove(key);



                }
                catch (FileNotFoundException e) {
                    pdfFile.delete();
                    dbvoc.getDeleteImageData("file:" + file.getAbsolutePath());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            //dialogs.dismiss();

                        }
                    });
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            dialogs.dismiss();

                        }
                    });
                    e.printStackTrace();
                } catch (IOException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            dialogs.dismiss();

                        }
                    });
                    e.printStackTrace();
                }


            }



            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
//                    if(hash_size_count == Global_Data.Download_hashmap.size())
//                    {
                    dialogs.dismiss();

                    Intent intent1 = new Intent(context, Image_Gellary.class);
                    // intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                    //  }

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            if (pd != null)
//            {
//                pd.dismiss();
//            }
        }
    }



}
