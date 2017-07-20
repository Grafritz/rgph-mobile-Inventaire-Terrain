package ht.ihsi.inventaireterrain.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Exceptions.TextEmptyException;
import ht.ihsi.inventaireterrain.Models.CodeSDEModel;
import ht.ihsi.inventaireterrain.Models.CommuneModel;
import ht.ihsi.inventaireterrain.Models.InventaireModel;
import ht.ihsi.inventaireterrain.Models.KeyValueModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.Models.VqseModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;

public class InventaireSDEActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //region ATTRIBUTS
    Intent intent;
    public Spinner sp_Departement;
    public Spinner sp_Commune;
    public Spinner sp_Vqse;
    public Spinner sp_TypeFormulaire;
    public Spinner sp_CodeSDE;

    public TextView tv_Commune;
    public TextView tv_Vqse;
    public TextView tv_definitionFormulaire;
    public TextView tv_CodeSDE;

    public EditText et_NomEtPrenomCartographe;
    public EditText et_NomEtPrenomSuperviseur;
    public CheckBox cb_NePlusAfficherCetteFenetre;

    public Button btnContinuer;
    public Button btn_Modifier;

    private Toolbar toolbar;

    int TYPE_INVENTAIRE = 0;
    long ID_INVENTAIRE = 0;

    String idDeptLast = "";
    Boolean IsLoadCommuneAgain = true;
    String idCommLast = "";
    Boolean IsLoadVqseAgain = true;
    String idVqseLast = "";
    Boolean IsLoadCodeSDEAgain = true;
    private RowDataListModel rowDada;
    private InventaireModel inventaireModel;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventaire_sde);

        init(Constant.FORM_DATA_MANAGER);
        init(Constant.CU_RECORD_MANAGER);
        init(Constant.QUERY_RECORD_MANAGER);

        initControls();
    }

    //region initControls
    private void initControls() {
        try {
            Intent intent = getIntent();
            ID_INVENTAIRE = intent.getIntExtra(Constant.PARAM_ID, 0);
            TYPE_INVENTAIRE = intent.getIntExtra(Constant.PARAM_TYPE_FORMULAIRE, 0);

            String formulaire = getString(R.string.msg_Configuration_SDE) + " -|-"+ Tools.GetStringTypeInventaire(TYPE_INVENTAIRE, getString(R.string.app_name));

            // Toolbar creation
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(formulaire);

            tv_definitionFormulaire = (TextView) this.findViewById(R.id.tv_definitionFormulaire);
            ((TextView) this.findViewById(R.id.tv_TitreFormulaire)).setText(getString(R.string.msg_ActualisationCartographique).toString());

            sp_TypeFormulaire = (Spinner) this.findViewById(R.id.sp_TypeFormulaire);

            sp_Departement = (Spinner) this.findViewById(R.id.sp_Departement);
            sp_Departement.setOnItemSelectedListener(this);

            tv_Commune = (TextView) this.findViewById(R.id.tv_Commune);
            sp_Commune = (Spinner) this.findViewById(R.id.sp_Commune);
            sp_Commune.setOnItemSelectedListener(this);
            tv_Commune.setVisibility(View.GONE);
            sp_Commune.setVisibility(View.GONE);

            tv_Vqse = (TextView) this.findViewById(R.id.tv_Vqse);
            sp_Vqse = (Spinner) this.findViewById(R.id.sp_Vqse);
            sp_Vqse.setOnItemSelectedListener(this);
            tv_Vqse.setVisibility(View.GONE);
            sp_Vqse.setVisibility(View.GONE);

            tv_CodeSDE = (TextView) this.findViewById(R.id.tv_CodeSDE);
            sp_CodeSDE = (Spinner) this.findViewById(R.id.sp_CodeSDE);
            tv_CodeSDE.setVisibility(View.GONE);
            sp_CodeSDE.setVisibility(View.GONE);

            et_NomEtPrenomCartographe = (EditText) this.findViewById(R.id.et_NomEtPrenomCartographe);
            et_NomEtPrenomSuperviseur = (EditText) this.findViewById(R.id.et_NomEtPrenomSuperviseur);
            cb_NePlusAfficherCetteFenetre = (CheckBox) this.findViewById(R.id.cb_NePlusAfficherCetteFenetre);

            Shared_Preferences SPref = Tools.SharedPreferences(this);
            if( Constant.COMPTE_SUPERVISEUR == SPref.getProfileId() && SPref.getNom() != null && SPref.getPrenom() != null ){
                et_NomEtPrenomSuperviseur.setText("" + SPref.getNom() +" " + SPref.getPrenom());
            }
            // Buttons
            btnContinuer = (Button) this.findViewById(R.id.btnContinuer);
            btnContinuer.setOnClickListener(this);

            btn_Modifier = (Button) this.findViewById(R.id.btn_Modifier);
            btn_Modifier.setOnClickListener(this);

            ((Button) this.findViewById(R.id.btnQuitter)).setOnClickListener(this);
            //((Button) this.findViewById(R.id.btnContinuer)).setOnClickListener(this);

            // LOAD DATA
            Load_TypeFormulaire(sp_TypeFormulaire);
            SetValues(sp_TypeFormulaire, Constant.PARAM_TYPE_FORMULAIRE, "" + TYPE_INVENTAIRE);

            Load_Departement(sp_Departement);

            SetValuePreferenceControls();

            SetDataFromListe();

            if( ID_INVENTAIRE > 0 ){
                //btn_Modifier.setVisibility(View.GONE);
                //btnContinuer.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-ShowMessage_AskAddNewLogement(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }    //
    //endregion

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnContinuer:
                    SaveInventaireSDE(true);
                    break;
                case R.id.btn_Modifier:
                    SaveInventaireSDE(false);
                    break;
                case R.id.btnQuitter:
                    finish();
                    break;
                default:
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onClick(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    //region LOAD DATA
    public void Load_TypeFormulaire(Spinner spinner){
        try {
            ArrayList<KeyValueModel> keyValueModels = new ArrayList<KeyValueModel>();
            keyValueModels.add(new KeyValueModel("", "- " + this.getResources().getString(R.string.label_Selectionnez_TypeFormulaire) + " -"));
            keyValueModels.add(new KeyValueModel("" + Constant.FORM_RURAL, " " + this.getResources().getString(R.string.label_Formulaire_Rural) + ""));
            keyValueModels.add(new KeyValueModel("" + Constant.FORM_URBAIN, " " + this.getResources().getString(R.string.label_Formulaire_Urbain) + ""));
            // Creating adapter for spinner
            ArrayAdapter<KeyValueModel> dataAdapter = new ArrayAdapter<KeyValueModel>(this, android.R.layout.simple_list_item_1, keyValueModels);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }catch (Exception ex)        {
            ToastUtility.LogCat("Exception-Load_TypeFormulaire(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//

    public void Load_Departement(Spinner spinner){
        try {
            //ToastUtility.LogCat("D", "Load_Departement()");
            List<KeyValueModel> reponseModelList1 = formDataMngr.getAllDepartement();
            ArrayList<KeyValueModel> keyValueModels = new ArrayList<KeyValueModel>();
            int size = 0;
            if( reponseModelList1 != null){
                size = reponseModelList1.size();
            }
            keyValueModels.add(new KeyValueModel("", "- " +  this.getResources().getString(R.string.label_Selectionnez_Departement) +" (" + size +") -"));
            keyValueModels.addAll(reponseModelList1);
            // Creating adapter for spinner
            ArrayAdapter<KeyValueModel> dataAdapter = new ArrayAdapter<KeyValueModel>(this, android.R.layout.simple_list_item_1, keyValueModels);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }catch (Exception ex)        {
            ToastUtility.LogCat("Exception-Load_Departement(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//
    //
    public void Load_CommuneByIdDept(Spinner spinner, String idDept){
        try {
            //ToastUtility.LogCat("D", "Load_CommuneByIdDept("+ idDept +")");
            List<CommuneModel> reponseModelList1 = formDataMngr.getAllCommuneByIdDept(idDept);
            int size = 0;
            if( reponseModelList1 != null){
                size = reponseModelList1.size();
            }
            ArrayList<CommuneModel> keyValueModels = new ArrayList<CommuneModel>();
            keyValueModels.add(new CommuneModel("", "- " + this.getResources().getString(R.string.label_Selectionnez_Une_Commune)  +" (" + size +") -","0"));
            keyValueModels.addAll(reponseModelList1);

            // Creating adapter for spinner
            ArrayAdapter<CommuneModel> dataAdapter = new ArrayAdapter<CommuneModel>(this, android.R.layout.simple_list_item_1, keyValueModels);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }catch (Exception ex)        {
            ToastUtility.LogCat("Exception-Load_CommuneByIdDept(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//
    //
    public void Load_Vqse_ByIdCommune(Spinner spinner, String idCommune){
        try {
            //ToastUtility.LogCat("D", "Load_Vqse_ByIdCommune("+ idCommune +")");
            List<VqseModel> reponseModelList1 = formDataMngr.getAllVqseByIdCom(idCommune);
            int size = 0;
            if( reponseModelList1 != null){
                size = reponseModelList1.size();
            }
            ArrayList<VqseModel> keyValueModels = new ArrayList<VqseModel>();
            keyValueModels.add(new VqseModel("", "- " + this.getResources().getString(R.string.label_Selectionnez_SectionCommunnale) +" (" + size +") -", "0"));
            keyValueModels.addAll(reponseModelList1);

            // Creating adapter for spinner
            ArrayAdapter<VqseModel> dataAdapter = new ArrayAdapter<VqseModel>(this, android.R.layout.simple_list_item_1, keyValueModels);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }catch (Exception ex)        {
            ToastUtility.LogCat("Exception-Load_Vqse_ByIdCommune(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//

    public void Load_CodeSDE_ByIdVqse(Spinner spinner, String idVqse){
        try {
            List<CodeSDEModel> reponseModelList1 = formDataMngr.getAllCodeSDE_ByIdVqse(idVqse);
            int size = 0;
            if( reponseModelList1 != null){
                size = reponseModelList1.size();
            }
            ArrayList<CodeSDEModel> keyValueModels = new ArrayList<CodeSDEModel>();
            keyValueModels.add(new CodeSDEModel("", "- " + this.getResources().getString(R.string.label_Selectionnez_CodeSDE)  +" (" + size +") -", "0", "0", "0",0));
            keyValueModels.addAll(reponseModelList1);

            // Creating adapter for spinner
            ArrayAdapter<CodeSDEModel> dataAdapter = new ArrayAdapter<CodeSDEModel>(this, android.R.layout.simple_list_item_1, keyValueModels);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }catch (Exception ex)        {
            ToastUtility.LogCat("Exception-Load_CodeSDE_ByIdVqse(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//
    //endregion

    //region EVENTS SPINNER
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try{
            // TEST POUR LE CHARGEMENT DES COMMUNES
            KeyValueModel keyValueDept = null;
            if (parent.getId() == R.id.sp_Departement) {
                keyValueDept = ((KeyValueModel) sp_Departement.getSelectedItem());
                if ( !this.idDeptLast.equals(keyValueDept.getKey()) ){
                    this.IsLoadCommuneAgain = true;
                }
            }
            if (keyValueDept != null){
                if( !keyValueDept.getKey().trim().equalsIgnoreCase("")) {
                    if (parent.getId() == R.id.sp_Departement) {
                        tv_Commune.setVisibility(View.VISIBLE);
                        sp_Commune.setVisibility(View.VISIBLE);
                        if (this.IsLoadCommuneAgain) {
                            //ToastUtility.LogCat("[+] CALL ON onItemSelected ( Load_CommuneByIdDept(sp_Commune, keyValueDept.getKey()); )");
                            Load_CommuneByIdDept(sp_Commune, keyValueDept.getKey());
                        }
                    }
                }
            }
            // TEST POUR LE CHARGEMENT DES VQSE
            CommuneModel communeModel = null;
            if (parent.getId() == R.id.sp_Commune) {
                communeModel = ((CommuneModel) sp_Commune.getSelectedItem());
                if ( !this.idCommLast.equals(communeModel.getComId()) ){
                    this.IsLoadVqseAgain = true;
                }
            }
            if (communeModel != null){
                if( !communeModel.getComId().trim().equalsIgnoreCase("")) {
                    if (parent.getId() == R.id.sp_Commune) {
                        tv_Vqse.setVisibility(View.VISIBLE);
                        sp_Vqse.setVisibility(View.VISIBLE);
                        if (this.IsLoadVqseAgain) {
                            //ToastUtility.LogCat("[+] CALL ON onItemSelected ( Load_Vqse_ByIdCommune(sp_Vqse, communeModel.getComId()); )");
                            Load_Vqse_ByIdCommune(sp_Vqse, communeModel.getComId());
                        }
                    }
                }
            }

            // TEST POUR LE CHARGEMENT DES CODE SDE DU VQSE
            VqseModel vqseModel = null;
            if (parent.getId() == R.id.sp_Vqse) {
                vqseModel = ((VqseModel) sp_Vqse.getSelectedItem());
                if ( !this.idVqseLast.equals(vqseModel.getVqseId()) ){
                    this.IsLoadCodeSDEAgain = true;
                }
            }
            if (vqseModel != null){
                if( !vqseModel.getVqseId().trim().equalsIgnoreCase("")) {
                    if (parent.getId() == R.id.sp_Vqse) {
                        tv_CodeSDE.setVisibility(View.VISIBLE);
                        sp_CodeSDE.setVisibility(View.VISIBLE);
                        if (this.IsLoadCodeSDEAgain) {
                            //ToastUtility.LogCat("[+] CALL ON onItemSelected ( Load_Vqse_ByIdCommune(sp_Vqse, communeModel.getComId()); )");
                            Load_CodeSDE_ByIdVqse(sp_CodeSDE, vqseModel.getVqseId());
                        }
                    }
                }
            }
        }catch (Exception ex)        {
            ToastUtility.LogCat("Exception-onItemSelected(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //endregion

    //region SAVE DATA
    private void SaveInventaireSDE(boolean SetConfiguration) {
        try {
            boolean isValide = true;
            KeyValueModel keyValueModel = null;

            keyValueModel = ((KeyValueModel) sp_TypeFormulaire.getSelectedItem());
            String typeFormulaire = keyValueModel.getKey();

            keyValueModel = ((KeyValueModel) sp_Departement.getSelectedItem());
            String departementCode = keyValueModel.getKey();

            String CommuneCode = "";
            String VqseCode = "";
            String CodeSDE = "";
            String NumeroOrdre = "";
            int codeMilieu = 0;

            //et_CodeSDE.setError(null); et_CodeSDE.getText().toString();

            et_NomEtPrenomCartographe.setError(null);
            String NomEtPrenomCartographe = et_NomEtPrenomCartographe.getText().toString();

            et_NomEtPrenomSuperviseur.setError(null);
            String NomEtPrenomSuperviseur = et_NomEtPrenomSuperviseur.getText().toString();

            boolean NePlusAfficherCetteFenetre = cb_NePlusAfficherCetteFenetre.isChecked();

            if (TextUtils.isEmpty(typeFormulaire)) {
                isValide = false;
                message = getString(R.string.msg_TypeFormulaire_Obligatoire);
                ToastUtility.LogCat(message);
                ToastUtility.ToastMessage(this, message);
                Tools.AlertDialogMsg(this, message,"E");
            } else if (TextUtils.isEmpty(departementCode)) {
                isValide = false;
                message = getString(R.string.msg_Departement_Obligatoire);
                ToastUtility.LogCat(message);
                ToastUtility.ToastMessage(this, message);
                Tools.AlertDialogMsg(this, message,"E");
            } else {
                CommuneModel communeModel = ((CommuneModel) sp_Commune.getSelectedItem());
                CommuneCode = communeModel.getComId();
                if (TextUtils.isEmpty(CommuneCode)) {
                    isValide = false;
                    message = getString(R.string.msg_Commune_Obligatoire);
                    ToastUtility.LogCat(message);
                    ToastUtility.ToastMessage(this, message);
                    Tools.AlertDialogMsg(this, message,"E");
                } else {
                    VqseModel vqseModel = ((VqseModel) sp_Vqse.getSelectedItem());
                    VqseCode = vqseModel.getVqseId();

                    if (TextUtils.isEmpty(VqseCode)) {
                        isValide = false;
                        message = getString(R.string.msg_Vqse_Obligatoire);
                        ToastUtility.LogCat(message);
                        ToastUtility.ToastMessage(this, message);
                        Tools.AlertDialogMsg(this, message,"E");
                    } else {
                        CodeSDEModel codeSDEModel = ((CodeSDEModel) sp_CodeSDE.getSelectedItem());
                        NumeroOrdre = codeSDEModel.getNumeroOrdre();
                        codeMilieu = codeSDEModel.getMilieu();
                        CodeSDE = codeSDEModel.getCodeSDE();

                        if (TextUtils.isEmpty(NumeroOrdre)) {
                            isValide = false;
                            message = getString(R.string.label_CodeSDE) + " Obligatoire!";
                            ToastUtility.LogCat(message);
                            ToastUtility.ToastMessage(this, message, Constant.GravityCenter);
                        }else if ( !typeFormulaire.equals(""+ codeMilieu)) {
                            isValide = false;
                            message = " Le code SDE que vous avez sélectionnez n'est pas dans le ";
                            if ( typeFormulaire.equals(""+Constant.FORM_URBAIN) ) {
                                message += " [ Milieu Urbain ] ";
                            }else  if ( typeFormulaire.equals(""+Constant.FORM_RURAL) ) {
                                message += " [ Milieu Rural ] ";
                            }else{

                            }
                            message += "  que vous avez sélectionnez!";
                            ToastUtility.LogCat(message);
                            ToastUtility.ToastMessage(this, message);
                            Tools.AlertDialogMsg(this, message,"E");
                        } else if( TextUtils.isEmpty(NomEtPrenomCartographe )) {
                            isValide = false;
                            message = getString(R.string.label_NomEtPrenomCartographe) + " Obligatoire!";
                            et_NomEtPrenomCartographe.setError(message);
                            et_NomEtPrenomCartographe.requestFocus();
                            ToastUtility.LogCat(message);
                            ToastUtility.ToastMessage(this, message);
                            Tools.AlertDialogMsg(this, message,"E");
                        } else if (TextUtils.isEmpty(NomEtPrenomSuperviseur)) {
                            isValide = false;
                            message = getString(R.string.label_NomEtPrenomSuperviseur) + " Obligatoire!";
                            et_NomEtPrenomSuperviseur.setError(message);
                            et_NomEtPrenomSuperviseur.requestFocus();
                            ToastUtility.LogCat(message);
                            ToastUtility.ToastMessage(this, message);
                            Tools.AlertDialogMsg(this, message,"E");
                        }
                    }
                }
            }
            if (isValide) {
                //ToastUtility.LogCat(this, "- OK - SavePreferenceInfo");
                InventaireModel inv = new InventaireModel();
                inv.setTypeInventaire(typeFormulaire);
                inv.setDepartementId(departementCode);
                inv.setCommuneId(CommuneCode);
                inv.setVqseId(VqseCode);
                inv.setCodeSDE(CodeSDE);
                inv.setNumeroOdreSDE(NumeroOrdre);
                inv.setNomEtPrenomCartographe(NomEtPrenomCartographe);
                inv.setNomEtPrenomSuperviseur(NomEtPrenomSuperviseur);
                inv.setNePlusAfficherCetteFenetre(false);

                InventaireModel inventaireModel = null;
                inventaireModel = cuRecordMngr.SaveInventaire(ID_INVENTAIRE, inv);
                if (SetConfiguration) {
                    if (inventaireModel.getIdInventaire() > 0) {
                        inv.setIdInventaire(inventaireModel.getIdInventaire());
                        inv.setIsConfigured(true);
                        Tools.StoreInfoInventairePreferenceManager(this, inv);
                    }
                }
                finish();
            }
        } catch (TextEmptyException ex) {
            ToastUtility.LogCat("TextEmptyException-SaveInventaireSDE(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ToastUtility.ToastMessage(this, ex.getMessage(), Constant.GravityCenter);
            //ex.printStackTrace();
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-SaveInventaireSDE(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//
    //endregion

    //region SET VALUES
    private void SetDataFromListe() {
        try {
            btn_Modifier.setVisibility(View.GONE);
            ToastUtility.LogCat(this, "Inside SetDataFromListe()");
            Intent intent = getIntent();
            rowDada = (RowDataListModel) intent.getSerializableExtra(Constant.PARAM_MODEL);
            if (rowDada != null) {
                inventaireModel = (InventaireModel) rowDada.getModel();
                btn_Modifier.setVisibility(View.VISIBLE);
            }
            if (inventaireModel != null) {
                ID_INVENTAIRE = inventaireModel.getIdInventaire();
                SetValues(sp_TypeFormulaire, Constant.PARAM_TYPE_FORMULAIRE, inventaireModel.getTypeInventaire());
                SetValues(sp_Departement, Constant.TBL_DEPARTEMENT, inventaireModel.getDepartementId());
                this.idDeptLast = inventaireModel.getDepartementId();

                this.IsLoadCommuneAgain = false;
                this.idCommLast = inventaireModel.getCommuneId();

                this.IsLoadVqseAgain = false;
                Load_CommuneByIdDept(sp_Commune, inventaireModel.getDepartementId());
                SetValues(sp_Commune, Constant.TBL_COMMUNE, inventaireModel.getCommuneId());
                Load_Vqse_ByIdCommune(sp_Vqse, inventaireModel.getCommuneId());
                SetValues(sp_Vqse, Constant.TBL_VQSE, inventaireModel.getVqseId());

                this.idVqseLast =  inventaireModel.getVqseId();
                this.IsLoadCodeSDEAgain = false;
                Load_CodeSDE_ByIdVqse(sp_CodeSDE, inventaireModel.getVqseId());
                SetValues(sp_CodeSDE, Constant.TBL_CODE_SDE, inventaireModel.getCodeSDE());

                et_NomEtPrenomCartographe.setText(inventaireModel.getNomEtPrenomCartographe());
                et_NomEtPrenomSuperviseur.setText(inventaireModel.getNomEtPrenomSuperviseur());
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-SetDataFromListe(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    private void SetValuePreferenceControls() {
        try{
            Shared_Preferences pref = Tools.SharedPreferences(this);
            if( pref.getPref_TypeInventaire() != null && !pref.getPref_TypeInventaire().equalsIgnoreCase("")) {
                SetValues(sp_TypeFormulaire, Constant.PARAM_TYPE_FORMULAIRE, pref.getPref_TypeInventaire());
            }
            if( !pref.getPref_Departement().equalsIgnoreCase("") ) {
                SetValues(sp_Departement, Constant.TBL_DEPARTEMENT, pref.getPref_Departement());
                this.idDeptLast =  pref.getPref_Departement();
                this.IsLoadCommuneAgain = false;
                this.idCommLast =  pref.getPref_Commune();
                this.IsLoadVqseAgain = false;
                Load_CommuneByIdDept(sp_Commune, pref.getPref_Departement());
                SetValues(sp_Commune, Constant.TBL_COMMUNE, pref.getPref_Commune());
                Load_Vqse_ByIdCommune(sp_Vqse, pref.getPref_Commune());
                SetValues(sp_Vqse, Constant.TBL_VQSE, pref.getPref_Vqse());

                this.idVqseLast =  pref.getPref_Vqse();
                this.IsLoadCodeSDEAgain = false;
                Load_CodeSDE_ByIdVqse(sp_CodeSDE, pref.getPref_Vqse());
                SetValues(sp_CodeSDE, Constant.TBL_CODE_SDE, pref.getPref_CodeSDE());

                et_NomEtPrenomCartographe.setText(pref.getPref_NomEtPrenomCartographe());
                if( pref.getPref_NomEtPrenomSuperviseur() != null ) {
                    et_NomEtPrenomSuperviseur.setText(pref.getPref_NomEtPrenomSuperviseur());
                }
                cb_NePlusAfficherCetteFenetre.setChecked(pref.isPref_NePlusAfficherCetteFenetre());
            }
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-SetValuePreferenceControls(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//

    private void SetValues(Spinner spinner, String typeSpinner, String codeReponse) {
        try{
            if( typeSpinner == Constant.PARAM_TYPE_FORMULAIRE){
                ArrayAdapter<KeyValueModel> reponsModel = (ArrayAdapter<KeyValueModel>) spinner.getAdapter();
                for (int i=0; i<reponsModel.getCount(); i++){
                    if( reponsModel.getItem(i).getKey().equalsIgnoreCase(codeReponse)){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }else if( typeSpinner == Constant.TBL_DEPARTEMENT){
                ArrayAdapter<KeyValueModel> reponsModel = (ArrayAdapter<KeyValueModel>) spinner.getAdapter();
                for (int i=0; i<reponsModel.getCount(); i++){
                    if( reponsModel.getItem(i).getKey().equalsIgnoreCase(codeReponse)){
                        //ToastUtility.LogCat("W", "[+] getDeptId>> " + reponsModel.getItem(i).getKey() + " / getDeptNom>> " + reponsModel.getItem(i).getValue());
                        spinner.setSelection(i);
                        break;
                    }
                    //ToastUtility.LogCat( "[-] getDeptId>> " + reponsModel.getItem(i).getKey() + " / getDeptNom>> " + reponsModel.getItem(i).getValue());
                }
            }else if( typeSpinner == Constant.TBL_COMMUNE) {
                tv_Commune.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                ArrayAdapter<CommuneModel> communeModel= (ArrayAdapter<CommuneModel>) spinner.getAdapter();
                for (int i=0; i<communeModel.getCount(); i++){
                    if( communeModel.getItem(i).getComId().equalsIgnoreCase(codeReponse)){
                        //ToastUtility.LogCat("W", "[+] getDeptId>> " + communeModel.getItem(i).getDeptId() + " / getComId>> " + communeModel.getItem(i).getComId() + " / getComNom>> " + communeModel.getItem(i).getComNom());
                        spinner.setSelection(i);
                        break;
                    }
                    //ToastUtility.LogCat( "[-] getDeptId>> " + communeModel.getItem(i).getDeptId() + " / getComId>> " + communeModel.getItem(i).getComId() + " / getComNom>> " + communeModel.getItem(i).getComNom());
                }
            }else if( typeSpinner == Constant.TBL_VQSE) {
                tv_Vqse.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                ArrayAdapter<VqseModel> vqseModel = (ArrayAdapter<VqseModel>) spinner.getAdapter();
                for (int i=0; i<vqseModel.getCount(); i++){
                    if( vqseModel.getItem(i).getVqseId().equalsIgnoreCase(codeReponse)){
                        //ToastUtility.LogCat("W", "[+] getVqseId>> " + vqseModel.getItem(i).getVqseId() + " / getComId>> " + vqseModel.getItem(i).getComId() + " / getVqseNom>> " + vqseModel.getItem(i).getVqseNom());
                        spinner.setSelection(i);
                        break;
                    }
                    //ToastUtility.LogCat( "[-] getVqseId>> " + vqseModel.getItem(i).getVqseId() + " / getComId>> " + vqseModel.getItem(i).getComId() + " / getVqseNom>> " + vqseModel.getItem(i).getVqseNom());
                }
            }else if( typeSpinner == Constant.TBL_CODE_SDE) {
                tv_CodeSDE.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                ArrayAdapter<CodeSDEModel> CodeSDEModel = (ArrayAdapter<CodeSDEModel>) spinner.getAdapter();
                for (int i=0; i<CodeSDEModel.getCount(); i++){
                    if( CodeSDEModel.getItem(i).getCodeSDE().equalsIgnoreCase(codeReponse)){
                        //ToastUtility.LogCat("W", "[+] getVqseId>> " + CodeSDEModel.getItem(i).getVqseId() + " / getComId>> " + CodeSDEModel.getItem(i).getComId() + " / getVqseNom>> " + CodeSDEModel.getItem(i).getVqseNom());
                        spinner.setSelection(i);
                        break;
                    }
                    //ToastUtility.LogCat( "[-] getVqseId>> " + CodeSDEModel.getItem(i).getVqseId() + " / getComId>> " + CodeSDEModel.getItem(i).getComId() + " / getVqseNom>> " + CodeSDEModel.getItem(i).getVqseNom());
                }
            }
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-SetValues("+ typeSpinner +"): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }
    //endregion

}
