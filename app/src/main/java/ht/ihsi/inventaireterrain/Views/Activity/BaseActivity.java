package ht.ihsi.inventaireterrain.Views.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Managers.CURecordMngr;
import ht.ihsi.inventaireterrain.Managers.CURecordMngrImpl;
import ht.ihsi.inventaireterrain.Managers.FormDataMngr;
import ht.ihsi.inventaireterrain.Managers.FormDataMngrImpl;
import ht.ihsi.inventaireterrain.Managers.QueryRecordMngr;
import ht.ihsi.inventaireterrain.Managers.QueryRecordMngrImpl;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;

public class BaseActivity extends AppCompatActivity {

    protected FormDataMngr formDataMngr;
    protected CURecordMngr cuRecordMngr;
    protected QueryRecordMngr queryRecordMngr;
    protected Intent intent;
    public Boolean cancel = true;
    public Boolean isConfigured = false;
    public String message = "";

    @Override
    protected void onDestroy() {
       /* if (formDataMngr != null)
            formDataMngr.closeDbConnections();

        if(cuRecordMngr!=null){
            cuRecordMngr.closeDbConnections();
        }

        if(queryRecordMngr!=null){
            queryRecordMngr.closeDbConnections();
        }*/

        ToastUtility.LogCat(this, "onDestroy : No closeDbConnections()");
        super.onDestroy();
    }

    public void init(int type){
        if(Constant.FORM_DATA_MANAGER==type){
            formDataMngr = new FormDataMngrImpl(this);
        }else if(Constant.CU_RECORD_MANAGER==type){
            cuRecordMngr=new CURecordMngrImpl(this);
        }else if(Constant.QUERY_RECORD_MANAGER==type){
            queryRecordMngr=new QueryRecordMngrImpl(this);
        }
    }

    /*public void CheckPrefIsUseConnected() {
        Boolean checkPrefIsUseConnected =  Tools.CheckPrefIsUseConnected(this);
        if(!checkPrefIsUseConnected){
            cancel=true;
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else {
            cancel=false;
        }
    }//*/
    //

    /*public void showListView(String title,int listType,int statut){
        intent= new Intent(this, DisplayListActivity.class);
        intent.putExtra(Constant.PARAM_LIST_TITLE, title);
        intent.putExtra(Constant.PARAM_LIST_TYPE, ""+listType);
        intent.putExtra(Constant.PARAM_MODULE_STATUT, ""+statut);
        startActivity(intent);
    }

    public void showListView(String title,int listType,int statut,long id){
        intent= new Intent(this, DisplayListActivity.class);
        intent.putExtra(Constant.PARAM_LIST_TITLE, title);
        intent.putExtra(Constant.PARAM_LIST_TYPE, ""+listType);
        intent.putExtra(Constant.PARAM_MODULE_STATUT, "" + statut);
        intent.putExtra(Constant.PARAM_MODULE_ID, "" + id);

        startActivity(intent);
    }*/

    public void showListView(String title, int listType, int typeformulaire, long idMere){
        intent= new Intent(this, DisplayListActivity.class);
        intent.putExtra(Constant.PARAM_LIST_TITLE, title);
        intent.putExtra(Constant.PARAM_LIST_TYPE, ""+listType);
        intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, ""+typeformulaire);
        intent.putExtra(Constant.PARAM_ID, ""+idMere);
        startActivity(intent);
    }

    public void showListView(String title, int listType, int typeformulaire, long idMere, RowDataListModel row){
        intent= new Intent(this, DisplayListActivity.class);
        intent.putExtra(Constant.PARAM_LIST_TITLE, title);
        intent.putExtra(Constant.PARAM_LIST_TYPE, ""+listType);
        intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, ""+typeformulaire);
        intent.putExtra(Constant.PARAM_MODEL_MERE, row);
        intent.putExtra(Constant.PARAM_ID, ""+idMere);
        startActivity(intent);
    }

    public void getFormulaire_InventaireSDE(int typeformulaire, long idInventaire) {
        intent = new Intent(this, InventaireSDEActivity.class);
        intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, typeformulaire);
        intent.putExtra(Constant.PARAM_ID, idInventaire);
        startActivity(intent);
    }

    public void getFormulaireBatiment(int formRural, long idBatiment) {
        intent = new Intent(this, FormulaireActivity.class);
        intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, formRural);
        intent.putExtra(Constant.PARAM_ID, idBatiment);
        startActivity(intent);
    }
}
