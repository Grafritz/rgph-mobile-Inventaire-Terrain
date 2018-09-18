package ht.ihsi.inventaireterrain.Backend.Update;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ht.ihsi.inventaireterrain.Backend.CodeSDEDao;
import ht.ihsi.inventaireterrain.Managers.LoadStaticDataMngr;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;

/**
 * Created by JeanFritz on 5/26/2016.
 */
public class DataBaseUpgrade
{
    public final static int UPGRADE_TO_V4_TO_V5 = 4;

    //region UPDATA
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion, int newVersion) {
        while (oldVersion < newVersion) {
            //.oldVersion++;
            switch (oldVersion) {
                case UPGRADE_TO_V4_TO_V5:
                    MiseAjour_V4_to_V5(context, db, true);
                case 5:
                    //MiseAjour_V4_to_V5(context, db, true);
                    break;

            }
        }
    }
    //endregion

    //region VERSION 5 - MISE A JOUR 29-Aout-2017
    public static void MiseAjour_V4_to_V5(Context context, SQLiteDatabase db, boolean ifNotExists) {
        CodeSDEDao.dropTable(db, ifNotExists);
        Log.i(ToastUtility.TAG + "", "MiseAjour_V4_to_V5: dropTable CodeSDE!");
        CodeSDEDao.createTable(db, ifNotExists);
        Log.i(ToastUtility.TAG + "", "MiseAjour_V4_to_V5: createTable CodeSDE!");
        // Load All Data SDE
        LoadStaticDataMngr loadStaticDataMngr=new LoadStaticDataMngr();
        loadStaticDataMngr.loadData_CODE_SDE(context, db);
    }
    //endregion
//endregion

//endregion


}
