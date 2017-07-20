package ht.ihsi.inventaireterrain.Views.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ht.ihsi.inventaireterrain.Backend.DaoMaster;
import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Managers.AbstractDatabaseManager;
import ht.ihsi.inventaireterrain.Managers.LoadStaticDataMngr;
import ht.ihsi.inventaireterrain.Models.CodeSDEModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Views.MainActivity;

/**
 * Created by JFDuverseau on 9/1/2016.
 */
public class SplashScreen extends BaseActivity {

    protected boolean _active = true;
    protected int _splashTime = 3000;
    private TextView tv_MsgSplashScreen; // time to display the splash screen in ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState, persistentState);
        //setContentView(R.layout.splashscreen);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        ToastUtility.LogCat( "onCreate() : SplashScreen");

        tv_MsgSplashScreen = (TextView)this.findViewById(R.id.tv_MsgSplashScreen);

        init(Constant.QUERY_RECORD_MANAGER);
        init(Constant.FORM_DATA_MANAGER);



        /*Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                        tv_MsgSplashScreen.setText("Chargement ..." + waited);
                        ToastUtility.LogCat("Chargement ..." + waited);
                    }
                } catch (Exception e) {

                } finally {
                    //startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    //finish();
                }
            };
        };*/

        //splashTread.start();
        //new AsynDataTask().execute();
        //LoadData();
    }

    public void LoadData(){
        try {
            DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, Constant.DATABASE_NAME, null);
            SQLiteDatabase database = mHelper.getReadableDatabase();
            LoadStaticDataMngr loadStaticDataMngr=new LoadStaticDataMngr();
            //loadStaticDataMngr.loadData(this, db);

            int countDept = queryRecordMngr.countAllDepartement();
            //ToastUtility.ToastMessage(this, "countDept:"+countDept);
            //ToastUtility.LogCat("countDept:"+countDept);
            if( countDept <= 0 ){
                message = loadStaticDataMngr.loadData_PERSONNEL(this, database, tv_MsgSplashScreen);
                tv_MsgSplashScreen.setText( message );
                message += loadStaticDataMngr.loadData_DEPARTEMENT(this, database, tv_MsgSplashScreen);
                tv_MsgSplashScreen.setText( message );
                message += loadStaticDataMngr.loadData_COMMUNE(this, database, tv_MsgSplashScreen);
                tv_MsgSplashScreen.setText( message );
                message += loadStaticDataMngr.loadData_VQSE(this, database, tv_MsgSplashScreen);
                tv_MsgSplashScreen.setText( message );
                message += loadStaticDataMngr.loadData_CODE_SDE(this, database, tv_MsgSplashScreen);

                tv_MsgSplashScreen.setText( message );

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        CheckPrefIsUseConnected();
                    }
                }, 1000);
            }else{
                CheckPrefIsUseConnected();
            }
        }catch (Exception ex) {
            ToastUtility.LogCat( "Exception:LoadData() - getMessage:" + ex.getMessage() + " / toString:" + ex.toString());
            ex.printStackTrace();
        }
    }

    public void CheckPrefIsUseConnected() {
        Boolean checkPrefIsUseConnected =  Tools.CheckPrefIsUseConnected(this);
        if(!checkPrefIsUseConnected){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }//

    @Override
    protected void onResume() {
        ToastUtility.LogCat( "onResume() : SplashScreen");
        new Handler().postDelayed(new Runnable() {
                public void run() {
                    LoadData();
               }
            }, 1000);
        //LoadData();
        super.onResume();
    }

    public class AsynDataTask extends AsyncTask<String, Void, Boolean> {
        List<RowDataListModel> targetList = null;
        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                LoadData();
                //int countDept = queryRecordMngr.countAllDepartement();
                return true;
            }catch (Exception ex) {
                ToastUtility.LogCat( "Exception:AsynDataTask-doInBackground() - getMessage:" + ex.getMessage() + " / toString:" + ex.toString());
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //progressBar.setVisibility(View.GONE);
            if ( result ) {
                /*message = "DATA_TBL_PERSONNEL  inserted";
                message += "\n" + "TBL_DEPARTEMENT  inserted";
                message += "\n" + "TBL_COMMUNE  inserted";
                message += "\n" + "TBL_VQSE  inserted";
                message += "\n" + "TBL_CODE_SDE  inserted";
                tv_MsgSplashScreen.setText("Chargement ... \n" + message );*/
            } else {
            }

        }
    }
}
