package ht.ihsi.inventaireterrain.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ht.ihsi.inventaireterrain.Backend.PersonnelDao;
import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Managers.LoadStaticDataMngr;
import ht.ihsi.inventaireterrain.Mappers.ModelMapper;
import ht.ihsi.inventaireterrain.Models.InventaireModel;
import ht.ihsi.inventaireterrain.Models.KeyValueModel;
import ht.ihsi.inventaireterrain.Models.PersonnelModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Views.Activity.FormulaireActivity;

/**
 * Created by JFDuverseau on 7/14/2016.
 */
public class Tools {
    static AlertDialog.Builder ad = null;

    public static void AlertDialogMsg(Context context, String msg){
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        String titleMsg = context.getResources().getString(R.string.msg_title_succesInformation);
        aBuilder.setTitle(titleMsg);
        aBuilder.setMessage(msg);
        aBuilder.setCancelable(false);
        aBuilder.setIcon(R.mipmap.ic_launcher);

        //set Positive Button 1
        aBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }//
        );
        aBuilder.show();
    }//
    public static void AlertDialogMsg(Context context, String msg, String E_or_S){
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        String titleMsg = context.getResources().getString(R.string.msg_title_succesInformation);
        if (E_or_S.equalsIgnoreCase("")) E_or_S = "E";
        if (E_or_S.equalsIgnoreCase("E")) titleMsg = "Attention!";

        aBuilder.setTitle(titleMsg);
        aBuilder.setMessage(msg);
        aBuilder.setCancelable(false);
        aBuilder.setIcon(R.mipmap.ic_launcher);

        //set Positive Button 1
        aBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }//
        );
        aBuilder.show();
    }//
    public static void ShowAlerDialog(Context context, String Title, String Message) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(Title);
        ad.setMessage(Message);
        ad.setCancelable(false);
        ad.setIcon(R.mipmap.ic_launcher);

        //set Positive Button 1
        ad.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }//
        );
        ad.show();
    }//
    //
    public static void ShowMsgAlerDialog(Context context, String Message, String E_or_S) {
        ad = new AlertDialog.Builder(context);
        if (E_or_S.trim().equals("")) E_or_S = "E";
        String title = context.getResources().getString(R.string.msg_title_Attention);
        if (E_or_S.equals("S"))
            title = context.getResources().getString(R.string.msg_title_Information);

        ad.setTitle(title);
        ad.setMessage(Message);
        ad.setCancelable(true);
        ad.setIcon(R.mipmap.ic_launcher);

        //set Positive Button 1
        ad.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }//
        );
        ad.show();
    }//
    //
    public static Shared_Preferences SharedPreferences(Context context){ return new Shared_Preferences(context); }

    public static void StoreInfoPresonnel_PreferenceManager(Context context,  PersonnelModel personnelModel){
        ModelMapper.MapToPreferences(context, personnelModel);
    }

    public static boolean CheckPrefIsUseConnected(Context context) {
        return SharedPreferences(context).getIsConnected();
    }//
    //
    public static void StoreInfoInventairePreferenceManager(Context context,  InventaireModel inventaireModel){
        ModelMapper.MapToPreferences(context, inventaireModel);
    }
    public static boolean IsNePlusAfficherCetteFenetre(Context context) {
        return SharedPreferences(context).isPref_NePlusAfficherCetteFenetre();
    }//

    public static Shared_Preferences MapDefaultConfiguration(Context context, String DefaultConfiguration) {
        Shared_Preferences sharedPreferences = new Shared_Preferences(context);
        sharedPreferences.set_DefaultConfiguration(DefaultConfiguration);
        return sharedPreferences;
    }//

    public static String GetStringTypeInventaire(int typeInventaire, String DefaultValue) {
        if(typeInventaire == Constant.FORM_URBAIN){
            return " Formulaire Urbain ";
        }else if(typeInventaire == Constant.FORM_RURAL) {
            return " Formulaire Rural ";
        }
        return DefaultValue;
    }

    public static String getStringEtatBatiment(Context context, String codeValues, String typeDeDonnee) {
        try{
            String result="";
            InputStream is =null;
            Gson gson = new Gson();
            Type collectionType =null;
            AssetManager assetManager = context.getAssets();
            is = assetManager.open(typeDeDonnee);
            collectionType = new TypeToken<List<KeyValueModel>>(){}.getType();
            List<KeyValueModel> keyValueModelList = gson.fromJson(LoadStaticDataMngr.getStringJson(is), collectionType);

            if ( keyValueModelList != null  && keyValueModelList.size()>0){
                for(KeyValueModel key : keyValueModelList){
                    if( key.getKey().equalsIgnoreCase(codeValues)){
                        ToastUtility.LogCat("W", "[+] getKey>> " + key.getKey() + " / getValue>> " + key.getValue());
                        result = key.getValue();
                        break;
                    }
                }
            }
            return result;
        } catch (IOException ex) {
            ToastUtility.LogCat("IOException-getStringEtatBatiment("+ typeDeDonnee +"): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-getStringEtatBatiment("+ typeDeDonnee +"): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
        return null;
    }

    public static void SetErrorField(Context context, EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();
        ToastUtility.LogCat(message);
        ToastUtility.ToastMessage(context, message);
        AlertDialogMsg(context, message);
    }
    public static void SetErrorField(Context context, Spinner spinner, String message) {
        //spinner.setError(message);
        spinner.requestFocus();
        ToastUtility.LogCat(message);
        ToastUtility.ToastMessage(context, message);
        AlertDialogMsg(context, message);
    }

    //region REPONSE STRING QUESTION
    public static String getString_Reponse(String codeReponse,  String nomChamps) {
        try{
            String result="";
            ArrayList<KeyValueModel> keyValueModels = new ArrayList<KeyValueModel>();
            //BATIMENT
            if (nomChamps.equalsIgnoreCase(PersonnelDao.Properties.ProfileId.columnName)) {
                keyValueModels.add(new KeyValueModel("1", "ACTIC"));
                keyValueModels.add(new KeyValueModel("2", "Superviseur/Controleur"));
                keyValueModels.add(new KeyValueModel("3", "Agent"));
            }else if (nomChamps.equalsIgnoreCase(PersonnelDao.Properties.EstActif.columnName)){
                keyValueModels.add(new KeyValueModel("1", "Actif"));
                keyValueModels.add(new KeyValueModel("0", "Inactif"));
            }

            if ( keyValueModels != null  && keyValueModels.size()>0){
                for(KeyValueModel key : keyValueModels){
                    if( key.getKey().equalsIgnoreCase(codeReponse)){
                        result = key.getValue();
                        break;
                    }
                }
            }
            return result;
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-getString_Reponse("+ codeReponse +"): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
        return "";
    }
    //endregion
}
