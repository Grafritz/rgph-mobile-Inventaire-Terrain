package ht.ihsi.inventaireterrain.Managers;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;
import ht.ihsi.inventaireterrain.Backend.CodeSDE;
import ht.ihsi.inventaireterrain.Backend.Commune;
import ht.ihsi.inventaireterrain.Backend.DaoMaster;
import ht.ihsi.inventaireterrain.Backend.DaoSession;
import ht.ihsi.inventaireterrain.Backend.Departement;
import ht.ihsi.inventaireterrain.Backend.Personnel;
import ht.ihsi.inventaireterrain.Backend.Vqse;
import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class LoadStaticDataMngr  implements AsyncOperationListener {

    protected SQLiteDatabase database;
    protected DaoMaster daoMaster;
    protected DaoSession daoSession;
    protected AsyncSession asyncSession;
    protected List<AsyncOperation> completedOperations;

   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);

    }

    public  void loadData(Context context, SQLiteDatabase db) {
        this.database = db;
        InputStream is =null;
        Gson gson = new Gson();
        Type collectionType =null;
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
        try {
            AssetManager assetManager = context.getAssets();
            Log.i(ToastUtility.TAG + "MainActivity", "loadData method is called!");

             /*  insert COMPTE */
            is = assetManager.open(Constant.DATA_TBL_PERSONNEL);
            collectionType = new TypeToken<List<Personnel>>(){}.getType();
            List<Personnel> personnels = gson.fromJson(getStringJson(is), collectionType);
            bulkInsertDaTa(Personnel.class, personnels);
            Log.w(ToastUtility.TAG + "MainActivity", "DATA_TBL_PERSONNEL Data inserted");

             /*  insert TBL_DEPARTEMENT */
            is = assetManager.open(Constant.TBL_DEPARTEMENT);
            collectionType = new TypeToken<List<Departement>>(){}.getType();
            List<Departement> departements = gson.fromJson(getStringJson(is), collectionType);
            bulkInsertDaTa(Departement.class, departements);
            Log.w(ToastUtility.TAG + "MainActivity", "TBL_DEPARTEMENT Data inserted");

            /*  insert TBL_COMMUNE */
            is = assetManager.open(Constant.TBL_COMMUNE);
            collectionType = new TypeToken<List<Commune>>(){}.getType();
            List<Commune> communes = gson.fromJson(getStringJson(is), collectionType);
            bulkInsertDaTa(Commune.class, communes);
            Log.w(ToastUtility.TAG + "MainActivity", "TBL_COMMUNE Data inserted");

            /*  insert TBL_VQSE */
            is = assetManager.open(Constant.TBL_VQSE);
            collectionType = new TypeToken<List<Vqse>>(){}.getType();
            List<Vqse> vqses = gson.fromJson(getStringJson(is), collectionType);
            bulkInsertDaTa(Vqse.class, vqses);
            Log.w(ToastUtility.TAG + "MainActivity", "TBL_VQSE Data inserted");

            /*  insert TBL_CODE_SDE */
            is = assetManager.open(Constant.TBL_CODE_SDE);
            collectionType = new TypeToken<List<CodeSDE>>(){}.getType();
            List<CodeSDE> codeSDEList = gson.fromJson(getStringJson(is), collectionType);
            bulkInsertDaTa(CodeSDE.class, codeSDEList);
            Log.w(ToastUtility.TAG + "MainActivity", "TBL_CODE_SDE Data inserted");

        } catch (Exception ex) {
            Log.e(ToastUtility.TAG + "MainActivity", "error loade data: " + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
    }

    String result = "";
    public synchronized String loadData_PERSONNEL(Context context, SQLiteDatabase db, TextView textView) {
        this.database = db;
        InputStream is =null;
        Gson gson = new Gson();
        Type collectionType =null;
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
        try {
            AssetManager assetManager = context.getAssets();
            Log.i(ToastUtility.TAG + "MainActivity", "loadData_PERSONNEL method is called!");

             /*  insert COMPTE */
            is = assetManager.open(Constant.DATA_TBL_PERSONNEL);
            result = "Conpte Utilisateur: \n";
            textView.setText(result);
            collectionType = new TypeToken<List<Personnel>>(){}.getType();
            List<Personnel> personnels = gson.fromJson(getStringJson(is), collectionType);
            result += "\t -Lecture du fichier JSON";
            textView.setText(result);
            result += "\n \t -Données Utilisateur" + ((personnels != null) ? " ["+personnels.size() +"]":"") +"\n";
            textView.setText(result);
            bulkInsertDaTa(Personnel.class, personnels);
           Log.w(ToastUtility.TAG + "MainActivity", result);
        } catch (Exception ex) {
            result += "Erreur:"+ ex.toString();
            textView.setText(result);
            Log.e(ToastUtility.TAG + "MainActivity", "error loade data: " + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
        return result;
    }

    public synchronized String loadData_DEPARTEMENT(Context context, SQLiteDatabase db, TextView textView) {
        this.database = db;
        InputStream is =null;
        Gson gson = new Gson();
        Type collectionType =null;
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
        try {
            AssetManager assetManager = context.getAssets();
            Log.i(ToastUtility.TAG + "MainActivity", "loadData_DEPARTEMENT method is called!");

             /*  insert TBL_DEPARTEMENT */
            is = assetManager.open(Constant.TBL_DEPARTEMENT);
            result = " \n Département:";
            textView.setText(result);
            collectionType = new TypeToken<List<Departement>>(){}.getType();
            result += " \n \t -Lecture du fichier JSON";
            textView.setText(result);
            List<Departement> departements = gson.fromJson(getStringJson(is), collectionType);
            result += " \n \t -Données " + ((departements != null) ? " ["+departements.size() +"]":"") +"\n";
            textView.setText(result);
            bulkInsertDaTa(Departement.class, departements);
            Log.w(ToastUtility.TAG + "MainActivity", result);
        } catch (Exception ex) {
            result += " \n \t * Erreur:"+ ex.toString();
            textView.setText(result);
            Log.e(ToastUtility.TAG + "MainActivity", "error loade data: " + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
        return result;
    }

    public synchronized String loadData_COMMUNE(Context context, SQLiteDatabase db, TextView textView) {
        this.database = db;
        InputStream is =null;
        Gson gson = new Gson();
        Type collectionType =null;
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
        try {
            AssetManager assetManager = context.getAssets();
            Log.i(ToastUtility.TAG + "MainActivity", "loadData_COMMUNE method is called!");

            /*  insert TBL_COMMUNE */
            is = assetManager.open(Constant.TBL_COMMUNE);
            result = " \n Commune:";
            textView.setText(result);
            collectionType = new TypeToken<List<Commune>>(){}.getType();
            List<Commune> communes = gson.fromJson(getStringJson(is), collectionType);
            result += " \n \t -Lecture du fichier JSON";
            textView.setText(result);
            result += " \n \t -Données " + ((communes != null) ? " ["+communes.size() +"]":"") +"\n";
            textView.setText(result);
            bulkInsertDaTa(Commune.class, communes);
            Log.w(ToastUtility.TAG + "MainActivity", result);
        } catch (Exception ex) {
            result += " \n \t * Erreur:"+ ex.toString();
            Log.e(ToastUtility.TAG + "MainActivity", "error loade data: " + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
        return result;
    }

    public synchronized String loadData_CODE_SDE(Context context, SQLiteDatabase db, TextView textView) {
        this.database = db;
        InputStream is =null;
        Gson gson = new Gson();
        Type collectionType =null;
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
        try {
            AssetManager assetManager = context.getAssets();
            Log.i(ToastUtility.TAG + "MainActivity", "loadData_CODE_SDE method is called!");

            /*  insert TBL_CODE_SDE */
            //is = assetManager.open(Constant.TBL_CODE_SDE);
            is = assetManager.open(Constant.DATA_SDE_CROIX_DES_BOUQUETS);
            result = " \n SDE:";
            textView.setText(result);
            collectionType = new TypeToken<List<CodeSDE>>(){}.getType();
            result += " \n \t -Lecture du fichier JSON";
            textView.setText(result);
            List<CodeSDE> codeSDEList = gson.fromJson(getStringJson(is), collectionType);
            result += " \n \t -Données " + ((codeSDEList != null) ? " ["+codeSDEList.size() +"]":"") +"\n";
            textView.setText(result);
            bulkInsertDaTa(CodeSDE.class, codeSDEList);
            Log.w(ToastUtility.TAG + "MainActivity", result);

        } catch (Exception ex) {
            Log.e(ToastUtility.TAG + "MainActivity", "error loade data: " + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
        return result;
    }
    public synchronized void loadData_CODE_SDE(Context context, SQLiteDatabase db) {
        this.database = db;
        InputStream is =null;
        Gson gson = new Gson();
        Type collectionType =null;
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
        try {
            AssetManager assetManager = context.getAssets();
            Log.i(ToastUtility.TAG + "MainActivity", "loadData_CODE_SDE method is called!");

            /*  insert TBL_CODE_SDE */
            is = assetManager.open(Constant.TBL_CODE_SDE);
            //is = assetManager.open(Constant.DATA_SDE_CROIX_DES_BOUQUETS);
            result = " \n SDE:";
            //textView.setText(result);
            collectionType = new TypeToken<List<CodeSDE>>(){}.getType();
            result += " \n \t -Lecture du fichier JSON";
            //textView.setText(result);
            List<CodeSDE> codeSDEList = gson.fromJson(getStringJson(is), collectionType);
            result += " \n \t -Données " + ((codeSDEList != null) ? " ["+codeSDEList.size() +"]":"") +"\n";
            //textView.setText(result);
            bulkInsertDaTa(CodeSDE.class, codeSDEList);
            Log.w(ToastUtility.TAG + "MainActivity", result);

        } catch (Exception ex) {
            Log.e(ToastUtility.TAG + "MainActivity", "error loade data: " + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
        //return result;
    }

    public synchronized String loadData_VQSE(Context context, SQLiteDatabase db, TextView textView) {
        this.database = db;
        InputStream is =null;
        Gson gson = new Gson();
        Type collectionType =null;
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
        try {
            AssetManager assetManager = context.getAssets();
            Log.i(ToastUtility.TAG + "MainActivity", "loadData_VQSE method is called!");

            /*  insert TBL_VQSE */
            is = assetManager.open(Constant.TBL_VQSE);
            result = " \n VQSE:";
            textView.setText(result);
            collectionType = new TypeToken<List<Vqse>>(){}.getType();
            result += " \n \t -Lecture du fichier JSON";
            textView.setText(result);
            List<Vqse> vqses = gson.fromJson(getStringJson(is), collectionType);
            result += " \n \t -Données " + ((vqses != null) ? " ["+vqses.size() +"]":"") +"\n";
            textView.setText(result);
            bulkInsertDaTa(Vqse.class, vqses);
            Log.w(ToastUtility.TAG + "MainActivity", result);
        } catch (Exception ex) {
            Log.e(ToastUtility.TAG + "MainActivity", "error loade data: " + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
        return result;
    }

    private synchronized void bulkInsertDaTa(Class cl, List<?> entities){
        try {
            if (entities != null && entities.size() > 0) {
                Log.w(ToastUtility.TAG + "MainActivity", " Classe:" + cl.getSimpleName().toUpperCase() + " / size(): " + entities.size());
                openWritableDb();
                asyncSession.insertOrReplaceInTx(cl, entities);
                assertWaitForCompletion1Sec();
                daoSession.clear();
                //Log.e(ToastUtility.TAG + "MainActivity", "bulkInsertDaTa : DATA INSERT = " + entities.size());
            }else{
                Log.e(ToastUtility.TAG + "MainActivity", "bulkInsertDaTa : entities.size() = 0 " );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(ToastUtility.TAG + "MainActivity", "Exception - bulkInsertDaTa error insertind data: " + e.getMessage());
        }
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(2000);
        asyncSession.isCompleted();
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    public static String getStringJson(InputStream is) {
        String json = "";
        try {
            //InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            //Log.i(ToastUtility.TAG + "MainActivity", "Load Json data json :" + json);
        } catch (Exception ex) {
            Log.i(ToastUtility.TAG + "MainActivity", "Load Json data failed!!" + ex.getMessage());
        }
        //Log.i(ToastUtility.TAG + "MainActivity", "getStringJson(): json=" + json);
        return json;
    }
}
