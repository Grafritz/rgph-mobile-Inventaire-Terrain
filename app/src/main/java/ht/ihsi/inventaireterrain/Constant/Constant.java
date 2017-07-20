package ht.ihsi.inventaireterrain.Constant;

import android.view.Gravity;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public  interface Constant {

    String DATABASE_NAME="InventaireTerrain_data-db";
    String TBL_DEPARTEMENT = "tbl_departement.json";
    String TBL_COMMUNE= "tbl_commune.json";
    String TBL_VQSE= "tbl_vqse.json";
    String DATA_TYPE_BATIMENT = "data_type_batiment.json";
    String DATA_ETAT_BATIMENT = "data_etat_batiment.json";
    String DATA_USAGE_BATIMENT = "data_usage_batiment.json";
    String DATA_TBL_PERSONNEL = "data_personnel.json";
    String TBL_CODE_SDE = "data_sde.json";

    String MODULE_NAME_BATIMENT = "Batiments";
    String MODULE_NAME_LOGEMENT = "Logements";

    //region MANAGER TYPE
    String PARAM_TYPE_FORMULAIRE = "PARAM_TYPE_FORMULAIRE";
    String PARAM_LIST_TYPE = "PARAM_LIST_TYPE";
    String PARAM_LIST_TITLE = "PARAM_LIST_TITLE";
    String PARAM_ID = "PARAM_ID";
    //String PARAM_STATUT = "PARAM_STATUT";
    String PARAM_MODEL = "PARAM_MODEL";
    String PARAM_MODEL_MERE = "PARAM_MODEL_MERE";

    int COMPTE_ASTIC = 1;
    int COMPTE_SUPERVISEUR = 2;
    int COMPTE_AGENT = 3;

    int LIST_BATIMENT = 1;
    int LIST_LOGEMENT = 2;
    int LIST_INVENTAIRE_SDE = 3;
    int LIST_INVENTAIRE_ALL_SDE = 4;
    int LIST_COMPTE_UTILISATEUR = 5;

    int FORM_RURAL = 1;
    int FORM_URBAIN = 2;
     int FORM_DATA_MANAGER =1 ;
     int CU_RECORD_MANAGER =2 ;
     int QUERY_RECORD_MANAGER =3 ;
    //endregion

     int GravityCenter = Gravity.CENTER;
     int GravityTop = Gravity.TOP;
     int GravityBottom = Gravity.BOTTOM;
     int GravityEnd = Gravity.END;

    // KEY DATA FOR PREFERENCE USE FOR LOGIN USER
     String prefKeyUserName = "keyUserName";
     String prefKeyIdGroupeUser = "KeyIdGroupeUser";

    //region VARIABLES PREFERENCE
    String prefPersId = "prefPersId";
    String prefSdeId = "prefSdeId";
    String prefPrenomEtNom = "prefPrenomEtNom";
    String prefNom = "prefNom";
    String prefPrenom = "prefPrenom";
    String prefSexe = "prefSexe";
    String prefNomUtilisateur = "prefNomUtilisateur";
    String prefEmail = "prefEmail";
    String prefEstActif = "prefEstActif";
    String prefProfileId = "prefProfileId";
    String prefIsConnected = "prefIsConnected";
    String prefLastLogin = "prefLastLogin";

    String pref_IdInventaire = "IdInventaire";
    String pref_Departement = "Departement";
    String pref_Commune = "Commune";
    String pref_Vqse = "Vqse";
    String pref_CodeSDE = "CodeSDE";
    String pref_NumeroOdreSDE = "NumeroOdreSDE";
    String pref_NomEtPrenomCartographe = "NomEtPrenomCartographe";
    String pref_NomEtPrenomSuperviseur = "NomEtPrenomSuperviseur";
    String pref_NePlusAfficherCetteFenetre = "NePlusAfficherCetteFenetre";
    String pref_IsConfigured = "IsConfigured";
    String pref_TypeInventaire = "TypeInventaire";

    String pref_DefaultConfiguration="pref_DefaultConfiguration";

    //endregion

    //region REPONSE QUESTION
    int MAISON_BASE_OU_SIMPLE_8 = 8;
    int MAISON_A_ETAGE_9 = 9;
    int COMMERCE_3 = 3;
    //endregion
}
