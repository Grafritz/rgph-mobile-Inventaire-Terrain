package ht.ihsi.inventaireterrain.Views.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Utilities.TypeSafeConversion;

public class LogementActivity extends BaseActivity implements View.OnClickListener {

    //region ATTRIBUTS
    public TextView tv_GrandTitre;
    public EditText et_NomCompletChefMenage;
    public EditText et_PhoneChefMenage;
    public TextView tv_NumeroLogement;
    public EditText et_NbrHommeVivant;
    public EditText et_NbrFemmeVivant;
    public Button btnContinuer;
    private long IDLOGEMENT = 0;
    int nbrLogementSave = 0;
    int NumeroLogement = 0;

    private String title;
    private int listType = 0;
    private RowDataListModel rowDada;
    private RowDataListModel rowDadaMere;
    private int TYPE_INVENTAIRE = 0;
    public BatimentModel batimentModel;
    EditText et_RemarquesLogement;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logement);

        init(Constant.FORM_DATA_MANAGER);
        init(Constant.CU_RECORD_MANAGER);
        init(Constant.QUERY_RECORD_MANAGER);

        iniControls();
    }


    private void iniControls() {
        try {
            Shared_Preferences SPref = Tools.SharedPreferences(this);

            Intent intent= getIntent();
            //title = intent.getStringExtra(Constant.PARAM_LIST_TITLE);
            //listType = Integer.valueOf(intent.getStringExtra(Constant.PARAM_LIST_TYPE)).intValue();
            rowDada = (RowDataListModel) intent.getSerializableExtra(Constant.PARAM_MODEL);
            batimentModel = (BatimentModel) intent.getSerializableExtra(Constant.PARAM_MODEL_MERE);
            //if( rowDadaMere != null ){
            //    batimentModel = (BatimentModel) rowDadaMere.getModel();
            //}
            nbrLogementSave = queryRecordMngr.countLogementByIdBatiment(batimentModel.getIdBatiment());
            //Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
            //setSupportActionBar(toolbar);
            int nbrLogement = (nbrLogementSave + 1);
            setTitle("Ajouter Logement [" + nbrLogement + " / " + batimentModel.getNbrLogement() + "]");

            message = "" + SPref.getDefaultConfiguration() + "<br /> <b>Batiment " + batimentModel.getNoBatiment() +"</b>";
            tv_GrandTitre = (TextView) findViewById(R.id.tv_GrandTitre);
            tv_GrandTitre.setText(Html.fromHtml("" + message));

            TextView tv_NomCompletChefMenage = (TextView) this.findViewById(R.id.tv_NomCompletChefMenage);
            et_NomCompletChefMenage = (EditText) this.findViewById(R.id.et_NomCompletChefMenage);
            TextView tv_PhoneChefMenage = (TextView) this.findViewById(R.id.tv_PhoneChefMenage);
            et_PhoneChefMenage = (EditText) this.findViewById(R.id.et_PhoneChefMenage);
            tv_NumeroLogement = (TextView) this.findViewById(R.id.tv_NumeroLogement);
            tv_NumeroLogement.setText("Logement " + nbrLogement);
            tv_NumeroLogement.setVisibility(View.VISIBLE);
            NumeroLogement =  nbrLogement;
            et_NbrHommeVivant = (EditText) this.findViewById(R.id.et_NbrHommeVivant);
            et_NbrFemmeVivant = (EditText) this.findViewById(R.id.et_NbrFemmeVivant);
            et_RemarquesLogement = (EditText) this.findViewById(R.id.et_Remarques);
            LinearLayout LLNbreHommeEtFemme = (LinearLayout) this.findViewById(R.id.LLNbreHommeEtFemme);
            LLNbreHommeEtFemme.setVisibility(View.VISIBLE);

            tv_NumeroLogement.setText("Logement " + nbrLogement);
            if( batimentModel.getUsageBatiment() != null &&
                    TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) >= Constant.COMMERCE_3  ){
                LLNbreHommeEtFemme.setVisibility(View.GONE);
                tv_NomCompletChefMenage.setText("Nom Institution");
                et_NomCompletChefMenage.setHint("Nom Institution");
                tv_PhoneChefMenage.setText("Téléphone de l'institution");
                et_PhoneChefMenage.setHint("Téléphone de l'institution\"");
            }

            // Buttons
            ((Button) this.findViewById(R.id.btnQuitter)).setOnClickListener(this);
            btnContinuer = (Button) this.findViewById(R.id.btnContinuer);
            btnContinuer.setOnClickListener(this);

            if (rowDada != null) {
                LogementModel log = (LogementModel) rowDada.getModel();
                IDLOGEMENT = log.getIdLogement();
                btnContinuer.setText("Modifier >>");
                et_NomCompletChefMenage.setText("" + log.getNomCompletChefMenage());
                et_PhoneChefMenage.setText("" + log.getPhoneChefMenage());
                NumeroLogement = log.getNumeroLogement();
                tv_NumeroLogement.setText("Logement " + NumeroLogement);
                et_NbrHommeVivant.setText("" + log.getNbrHommeVivant());
                et_NbrFemmeVivant.setText("" + log.getNbrFemmeVivant());
                et_RemarquesLogement.setText("" + log.getRemarques());
                setTitle("Modifier Logement [" + log.getNumeroLogement() + " / " + batimentModel.getNbrLogement() + "]");

                if( TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) >= Constant.COMMERCE_3  ){
                    tv_NumeroLogement.setVisibility(View.GONE);
                    setTitle("Modifier Informations supplémentaires");
                }
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-ShowMessage_AskAddNewLogement(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    //region Validation Controls
    private void ValidationControls() {
        try {
            et_NomCompletChefMenage.setError(null);
            et_NbrHommeVivant.setError(null);
            et_NbrFemmeVivant.setError(null);
            String remarquesLogement = et_RemarquesLogement.getText().toString();
            String NomCompletChefMenage = et_NomCompletChefMenage.getText().toString();
            String PhoneChefMenage = et_PhoneChefMenage.getText().toString();
            int NbrHommeVivant = TypeSafeConversion.TranslateStringToInterger(et_NbrHommeVivant.getText().toString());
            int NbrFemmeVivant = TypeSafeConversion.TranslateStringToInterger(et_NbrFemmeVivant.getText().toString());
            boolean cancel = false;

            String obligatoire = " " + getString(R.string.label_Obligatoire);
            if (TextUtils.isEmpty(NomCompletChefMenage)) {
                cancel = true;
                Tools.SetErrorField(this, et_NomCompletChefMenage, getString(R.string.label_NomCompletChefMenage) + obligatoire);
            } else if ( TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) < Constant.COMMERCE_3 ){
                // Si l'usage du batiment est >= 3 ou demande uniquement le nom du chef de menage.
                if (TextUtils.isEmpty(et_NbrHommeVivant.getText().toString())) {
                    cancel = true;
                    Tools.SetErrorField(this, et_NbrHommeVivant, getString(R.string.label_NbrHommeVivant) + obligatoire);
                } else if (TextUtils.isEmpty(et_NbrFemmeVivant.getText().toString())) {
                    cancel = true;
                    Tools.SetErrorField(this, et_NbrFemmeVivant, getString(R.string.label_NbrFemmeVivant) + obligatoire);
                }
            }
            if( !cancel ){
                LogementModel inv = new LogementModel();
                if( IDLOGEMENT == 0 ){
                    inv.setNumeroLogement(nbrLogementSave + 1);
                }else{
                    inv.setNumeroLogement(NumeroLogement);
                }
                inv.setBatimentId(batimentModel.getIdBatiment());
                inv.setNomCompletChefMenage(NomCompletChefMenage);
                inv.setPhoneChefMenage(PhoneChefMenage);
                inv.setNbrHommeVivant(NbrHommeVivant);
                inv.setNbrFemmeVivant(NbrFemmeVivant);
                inv.setRemarques(remarquesLogement);

                LogementModel logementModel = null;
                logementModel = cuRecordMngr.SaveLogement(IDLOGEMENT, inv);
                nbrLogementSave = queryRecordMngr.countLogementByIdBatiment(batimentModel.getIdBatiment());

                //new AsynDisplayDataListLogementTask().execute();

                if (logementModel.getIdLogement() > 0) {
                    if ((nbrLogementSave + 1) == batimentModel.getNbrLogement()) {
                        btnContinuer.setText(getString(R.string.label_Terminer));
                    }else{
                        btnContinuer.setText(getString(R.string.label_Continuer));
                    }
                    if (nbrLogementSave < batimentModel.getNbrLogement()) {
                        ClearAllFieldLogement();
                        tv_NumeroLogement.setText("Logement " + (nbrLogementSave + 1));
                        setTitle("Ajouter Logement [" + (nbrLogementSave + 1) + " / " + batimentModel.getNbrLogement() + "]");
                    } else {
                        message = "Bâtiment enregistré avec succès. \n Nbre de Logement saisie:  " + nbrLogementSave
                                + "\n Nbre de Logement à enregistrer:  " + (batimentModel.getNbrLogement() - nbrLogementSave);
                        ToastUtility.ToastMessage(this, message);
                        //ShowMessage_AskAddNewBatiment(message);
                        finish();
                    }
                    IDLOGEMENT=0;
                }
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-ValidationControls(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    private void ClearAllFieldLogement() {
        try {
            et_NomCompletChefMenage.setText(null);
            et_PhoneChefMenage.setText(null);
            et_NbrHommeVivant.setText(null);
            et_NbrFemmeVivant.setText(null);
            et_RemarquesLogement.setText(null);
            et_NomCompletChefMenage.requestFocus();
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-ClearAllFieldLogement(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }
    //endregion
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnContinuer:
                    ValidationControls();
                    break;
                case R.id.btnQuitter:
                    //message = "Batiment enregistré avec succès. \n Nbre de Logement saisie:" + nbrLogementSave
                    //        + "\n Nbre de Logement à enregistrer:" + (batimentModel.getNbrLogement() - nbrLogementSave);
                    //ToastUtility.ToastMessage(this, message, Constant.GravityCenter);
                    finish();
                    break;
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onClick(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }
    //
}
