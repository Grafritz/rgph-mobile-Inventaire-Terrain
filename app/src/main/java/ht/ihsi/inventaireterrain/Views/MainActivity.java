package ht.ihsi.inventaireterrain.Views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Models.CommuneModel;
import ht.ihsi.inventaireterrain.Models.DepartementModel;
import ht.ihsi.inventaireterrain.Models.PersonnelModel;
import ht.ihsi.inventaireterrain.Models.VqseModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Views.Activity.BaseActivity;
import ht.ihsi.inventaireterrain.Views.Activity.FormulaireActivity;
import ht.ihsi.inventaireterrain.Views.Activity.LoginActivity;
import ht.ihsi.inventaireterrain.Views.Activity.SplashScreen;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    //region ATTRIBUTS
    Intent intent;
    public TextView tv_message;
    public TextView tv_Utilisateur;
    public TextView tv_definitionFormulaire;
    public TextView tv_GestionCompteUtilisateur;

    public Button btn_FomulaireRural;
    public Button btn_FomulaireUrbain;
    public Button btn_QuitterApplication;
    public Button btn_FomulaireSDE;
    public Button btn_GestionCompteUtilisateur;
    public Button btn_Connexion;

    public Dialog dialog;

    int TYPE_FORMULAIRE = 0;
    long ID_INVENTAIRE = 0;

    public LinearLayout LL_Fomulaire_MAIN;
    public LinearLayout LL_FomulaireSDE;
    //endregion

    //region METHODES Override
    @Override
    protected void onStart() {
        ToastUtility.LogCat("W", "onStart");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ToastUtility.LogCat("W", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SplashScreen.class));

        tv_definitionFormulaire = (TextView)this.findViewById(R.id.tv_definitionFormulaire);
        tv_Utilisateur = (TextView)this.findViewById(R.id.tv_Utilisateur);

        tv_message = (TextView)this.findViewById(R.id.tv_message);
        tv_message.setVisibility(View.GONE);

        btn_FomulaireRural = (Button)this.findViewById(R.id.btn_FomulaireRural);
        btn_FomulaireRural.setOnClickListener(this);

        btn_FomulaireUrbain = (Button)this.findViewById(R.id.btn_FomulaireUrbain);
        btn_FomulaireUrbain.setOnClickListener(this);

        btn_QuitterApplication = (Button)this.findViewById(R.id.btn_QuitterApplication);
        btn_QuitterApplication.setOnClickListener(this);

        btn_FomulaireSDE = (Button)this.findViewById(R.id.btn_FomulaireSDE);
        btn_FomulaireSDE.setOnClickListener(this);

        btn_Connexion = (Button)this.findViewById(R.id.btn_Connexion);
        btn_Connexion.setOnClickListener(this);

        tv_GestionCompteUtilisateur = (TextView)this.findViewById(R.id.tv_GestionCompteUtilisateur);
        btn_GestionCompteUtilisateur = (Button)this.findViewById(R.id.btn_GestionCompteUtilisateur);
        btn_GestionCompteUtilisateur.setOnClickListener(this);

        LL_Fomulaire_MAIN = (LinearLayout)this.findViewById(R.id.LL_Fomulaire_MAIN);
        LL_FomulaireSDE = (LinearLayout)this.findViewById(R.id.LL_FomulaireSDE);

        init(Constant.FORM_DATA_MANAGER);
        init(Constant.QUERY_RECORD_MANAGER);

        CheckPrefIsUseConnected(false);
        if( !cancel ) {
            //CheckPrivilegeUser();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            Shared_Preferences SPref = Tools.SharedPreferences(this);
            switch (v.getId()) {
                case R.id.btn_FomulaireRural:
                    GetFormBatiment_orList(SPref, Constant.FORM_RURAL, ID_INVENTAIRE);
                    break;
                case R.id.btn_FomulaireUrbain:
                    GetFormBatiment_orList(SPref, Constant.FORM_URBAIN, ID_INVENTAIRE);
                    break;
                case R.id.btn_FomulaireSDE:
                    CheckPrefIsUseConnected(true);
                    if (!cancel) {
                        if (SPref.getIsConfigured()) {
                            long nbrSde = queryRecordMngr.countAllInventaireSDE();
                            if (nbrSde > 0) {
                                // On affiche la liste des SDE
                                showListView("Liste SDE", Constant.LIST_INVENTAIRE_SDE, TYPE_FORMULAIRE, 0);
                            } else {
                                // On affiche le formulaire de saisie
                                getFormulaire_InventaireSDE(TYPE_FORMULAIRE, 0);
                            }
                        }else{
                            long nbrSde = queryRecordMngr.countAllInventaireSDE();
                            if (nbrSde > 0) {
                                // On affiche la liste des SDE
                                showListView("Liste SDE", Constant.LIST_INVENTAIRE_ALL_SDE, 0, 0);
                            } else {
                                // On affiche le formulaire de saisie
                                getFormulaire_InventaireSDE(0, 0);
                            }
                        }

                    }
                    break;
                case R.id.btn_Connexion:
                    CheckPrefIsUseConnected(true);
                    break;
                case R.id.btn_GestionCompteUtilisateur:
                    // On affiche la liste des SDE
                    showListView("Liste Compte Utilisateur", Constant.LIST_COMPTE_UTILISATEUR, 0, 0);
                    break;
                case R.id.btn_QuitterApplication:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    Disconnected();
                    finish();
                    break;
                default:
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onClick(): getMessage: ", ex);
            ex.printStackTrace();
        }
    }

    private void GetFormBatiment_orList(Shared_Preferences SPref, int type_inventaire, long id_inventaire) {
        try {
            ToastUtility.LogCat("W", "INSIDE GetFormBatiment_orList() : PARAM_TYPE_FORMULAIRE:" + type_inventaire + " / PARAM_ID:" + id_inventaire);
            CheckPrefIsUseConnected(true);
            if (!cancel) {
                // Verifier si l'appareil est deja configurer et pour quel formulaire
                if (SPref.getIsConfigured()) {
                    long nbrSDE = queryRecordMngr.countAllInventaireSDE();
                    if (nbrSDE <= 0) {
                        // Sinon Verifier si l'appareil contient au moin une SDE
                        getFormulaire_InventaireSDE(type_inventaire, id_inventaire);
                    } else {
                        // Verifier s'il existe deja des batiment deja enregistrer pour la SDE en cours
                        long nbrBat = queryRecordMngr.countBatimentByIdInventaire(id_inventaire);
                        if (nbrBat > 0) {
                            // On affiche la liste des batiments par SDE
                            showListView(getString(R.string.label_Batiment) + " [" + Tools.GetStringTypeInventaire(type_inventaire, "") + " ]", Constant.LIST_BATIMENT, type_inventaire, id_inventaire);
                        } else {
                            // Sinon afficher le formulaire pour la saisie des informations du batiment
                            getFormulaire(type_inventaire, id_inventaire);
                        }
                    }
                } else {
                    // Sinon Verifier si l'appareil n'est pas configurer
                    getFormulaire_InventaireSDE(type_inventaire, id_inventaire);
                }
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-GetFormBatiment_orList(): ", ex);
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        ToastUtility.LogCat("W", "onResume");
        CheckPrivilegeUser();
        super.onResume();
    }
    //endregion

    //region METHODES UTILES
    private void Disconnected() {
        PersonnelModel personnelModel = new PersonnelModel();
        personnelModel.setIsConnected(false);
        Tools.StoreInfoPresonnel_PreferenceManager(MainActivity.this, personnelModel);
    }

    public void CheckPrefIsUseConnected(boolean forConnexion) {
        Boolean checkPrefIsUseConnected =  Tools.CheckPrefIsUseConnected(this);
        if(!checkPrefIsUseConnected){
            cancel=true;
            btn_Connexion.setVisibility(View.VISIBLE);
            LL_Fomulaire_MAIN.setVisibility(View.GONE);
            btn_QuitterApplication.setText(getString(R.string.label_Quitter));
            if(forConnexion) {
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }else {
            cancel=false;
            btn_QuitterApplication.setText(getString(R.string.label_Deconnecter));
            btn_Connexion.setVisibility(View.GONE);
            LL_Fomulaire_MAIN.setVisibility(View.VISIBLE);
            //CheckPrivilegeUser();
        }

        Shared_Preferences SPref = Tools.SharedPreferences(this);
        if(SPref != null ) {
            message = "Bienvenu: " + SPref.getPrenom() + " " + SPref.getNom();
            tv_Utilisateur.setText(message);
        }
    }//
    //
    private void getFormulaire(int type_inventaire, long id_inventaire) {
        intent = new Intent(this, FormulaireActivity.class);
        intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, type_inventaire);
        intent.putExtra(Constant.PARAM_ID, id_inventaire);
        //ToastUtility.LogCat("W","PARAM_TYPE_FORMULAIRE:"+type_inventaire + " / PARAM_ID:"+id_inventaire );
        startActivity(intent);
    }
//endregion

    //region CONFIGURATION
    private void CheckPrivilegeUser(){
        try{
            message = "";
            SetVisibleControls(View.GONE);
            Shared_Preferences SPref = Tools.SharedPreferences(this);
            // On verifie si l'appareil est configure par l'ASTIC ou le superviseur
            tv_GestionCompteUtilisateur.setVisibility(View.GONE);
            btn_GestionCompteUtilisateur.setVisibility(View.GONE);
            if( !SPref.getIsConfigured() ){     // Si le formulaire n'est pas configurer sur la tablette
                // Verification de la connexion de l'utilisateur
                if( SPref.getIsConnected() ){
                    btn_Connexion.setVisibility(View.GONE);
                    LL_Fomulaire_MAIN.setVisibility(View.VISIBLE);
                    // Privilege pour le compte ASTIC
                    if( Constant.COMPTE_ASTIC == SPref.getProfileId() ){
                        SetVisibleControls(View.VISIBLE);
                        tv_GestionCompteUtilisateur.setVisibility(View.VISIBLE);
                        btn_GestionCompteUtilisateur.setVisibility(View.VISIBLE);
                    }else if( Constant.COMPTE_SUPERVISEUR == SPref.getProfileId() ){
                        // Privilege pour le compte SUPERVISEUR
                        SetVisibleControls(View.VISIBLE);
                        //if( SPref.getPrenom() != null )
                        //et_NomEtPrenomSuperviseur.setText(SPref.getPrenom() + " " + SPref.getNom().toUpperCase());
                    }else if( Constant.COMPTE_AGENT == SPref.getProfileId() ){
                        // Privilege pour le compte AGENT
                        message = "\n Contacter votre Superviseur";
                    }
                }
                message = "L'appareil n'est pas encore configuré "+ message +".";
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(message);
                ToastUtility.ToastMessage(this, message);
                Tools.ShowMsgAlerDialog(this, message, "E");
            }else{ // Si le formulaire est deja configurer sur la tablette
                if( SPref.getIsConnected() ) {
                    btn_Connexion.setVisibility(View.GONE);
                    LL_Fomulaire_MAIN.setVisibility(View.VISIBLE);
                    if (SPref.getPref_TypeInventaire() != null) {
                        TYPE_FORMULAIRE = Integer.parseInt(SPref.getPref_TypeInventaire());
                    }
                    if (SPref.getPref_IdInventaire() != null) {
                        ID_INVENTAIRE = SPref.getPref_IdInventaire();
                    }

                    if (SPref.getProfileId() != null && Constant.COMPTE_ASTIC == SPref.getProfileId()) {
                        SetVisibleControls(View.VISIBLE);
                        tv_GestionCompteUtilisateur.setVisibility(View.VISIBLE);
                        btn_GestionCompteUtilisateur.setVisibility(View.VISIBLE);
                    } else if (SPref.getProfileId() != null && Constant.COMPTE_SUPERVISEUR == SPref.getProfileId()) {
                        SetVisibleControls(View.VISIBLE);
                    } else if (SPref.getProfileId() != null && Constant.COMPTE_AGENT == SPref.getProfileId()) {
                    }
                    message = "Visualiser la liste des " + Tools.GetStringTypeInventaire(TYPE_FORMULAIRE, "formulaires");// + " ou Ajouter un nouveau Batiment";
                    tv_definitionFormulaire.setText(message);
                    DepartementModel dept = null;
                    CommuneModel com = null;
                    VqseModel vqse = null;

                    if (SPref.getPref_Departement() != null) {
                        dept = queryRecordMngr.GetDepartementById(SPref.getPref_Departement());
                    }
                    if (SPref.getPref_Commune() != null) {
                        com = queryRecordMngr.GetCommuneById(SPref.getPref_Commune());
                    }
                    if (SPref.getPref_Vqse() != null) {
                        vqse = queryRecordMngr.GetVqseById(SPref.getPref_Vqse());
                    }

                    String regionZone = "";
                    if (dept != null) {
                        regionZone = dept.getDeptNom();
                    }
                    if (com != null) {
                        if (!regionZone.equalsIgnoreCase("")) {
                            regionZone += " / " + com.getComNom();
                        } else {
                            regionZone = com.getComNom();
                        }
                    }
                    if (vqse != null) {
                        if (!regionZone.equalsIgnoreCase("")) {
                            regionZone += " / " + vqse.getVqseNom();
                        } else {
                            regionZone = vqse.getVqseNom();
                        }
                    }
                    message = "<b>SDE :</b> " + SPref.getPref_CodeSDE() + " <br /> <b>Région/Zone : </b> " + regionZone;
                    SPref.set_DefaultConfiguration(message);
                    tv_message.setVisibility(View.VISIBLE);
                    //tv_message.setTextAlignment(View.FOCUS_LEFT);
                    tv_message.setText(Html.fromHtml(message));

                    if (SPref.getPref_TypeInventaire().equalsIgnoreCase("" + Constant.FORM_RURAL)) {
                        btn_FomulaireUrbain.setVisibility(View.GONE);
                        btn_FomulaireRural.setVisibility(View.VISIBLE);
                    } else if (SPref.getPref_TypeInventaire() != null && SPref.getPref_TypeInventaire().equalsIgnoreCase("" + Constant.FORM_URBAIN)) {
                        btn_FomulaireUrbain.setVisibility(View.VISIBLE);
                        btn_FomulaireRural.setVisibility(View.GONE);
                    }
                }else{
                    btn_Connexion.setVisibility(View.VISIBLE);
                    LL_Fomulaire_MAIN.setVisibility(View.GONE);
                }
            }


            if(SPref != null ) {
                message = "Bienvenue: " + SPref.getPrenom() + " " + SPref.getNom();
                tv_Utilisateur.setText(message);
            }
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-CheckPrivilegeUser(): getMessage: ", ex);
            ex.printStackTrace();
        }
    }

    private void SetVisibleControls(int visible) {
        btn_FomulaireRural.setVisibility(visible);
        btn_FomulaireUrbain.setVisibility(visible);
        btn_FomulaireSDE.setVisibility(visible);
        LL_FomulaireSDE.setVisibility(visible);
        tv_definitionFormulaire.setVisibility(visible);
    }

    //endregion
}
