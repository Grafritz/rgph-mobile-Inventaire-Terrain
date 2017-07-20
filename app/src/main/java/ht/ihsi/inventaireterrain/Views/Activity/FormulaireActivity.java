package ht.ihsi.inventaireterrain.Views.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Managers.LoadStaticDataMngr;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.KeyValueModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Services.GPSService;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Utilities.TypeSafeConversion;
import ht.ihsi.inventaireterrain.Views.Adapters.DisplayListAdapter;
import ht.ihsi.inventaireterrain.Views.Decorator.SimpleDividerItemDecoration;


public class FormulaireActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //region ATTRIBUTS
    public int TYPE_FORMULAIRE = 0;
    public EditText et_NoBatiment;
    public EditText et_AdrBat_LocaliteNouveauNom_Urbain;
    public LinearLayout LL_AdrBat_LocaliteNouveauNom_Rural;
    public LinearLayout LL_Institution;
    public LinearLayout LL_ListeLogement;

    public EditText et_AdrBat_HabitationAncienNom;
    public EditText et_AdrBat_HabitationNouveauNom;
    public EditText et_AdrBat_LocaliteAncienNom;
    public EditText et_AdrBat_LocaliteNouveauNom;
    public EditText et_Longitude;
    public EditText et_Latitude;
    public EditText et_NbrEtage;
    public EditText et_NbrLogement;
    public EditText et_Remarques;
    public Spinner sp_EtatActuel;
    public Spinner sp_TypeBatiment;
    public Spinner sp_UsageBatiment;
    public EditText et_NomInstitution;
    public EditText et_PhoneInstitution;
    int nbrLogementSave = 0;
    int UsageBatiment = 0;
    String NbrLogement = "0";
    // LOGEMENT
    public TextView tv_TitreListeLogement;
    public TextView tv_GrandTitre;
    public EditText et_NomCompletChefMenage;
    public EditText et_PhoneChefMenage;
    public TextView tv_NumeroLogement;
    public EditText et_NbrHommeVivant;
    public EditText et_NbrFemmeVivant;
    public Button btnContinuer;
    public Button Btn_GetGPS;
    public BootstrapButton Btn_SaveData;

    private String message;
    public AlertDialog.Builder aBuilder;
    public Dialog dialog;
    public BatimentModel batimentModel;
    private int listType = 0;

    private RecyclerView recyclerView;
    private DisplayListAdapter mAdapter;
    private ProgressBar progressBar;
    private List<RowDataListModel> targetList = new ArrayList<RowDataListModel>();

    public final Context context = FormulaireActivity.this;

    private RowDataListModel rowDada;
    private long IDBATIMENT = 0;
    private long IDLOGEMENT = 0;
    private long ID_INVENTAIRE = 0;
    int noBatiment = 0;
    Shared_Preferences SPref;

    String CodeSDE = "";
    String NumOrdreSDE = "";
    String DepartementId = "";
    String CommuneId = "";
    String VqseId = "";

    EditText et_RemarquesLogement;

    // Declaring a Location Manager
    protected LocationManager locationManager;
    // Location and co-ordinates coordinates
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);
        try {
            Intent intent = getIntent();
            //LLGrandTitre = (LinearLayout) findViewById(R.id.LLGrandTitre);
            tv_GrandTitre = (TextView) findViewById(R.id.tv_GrandTitre);
            //rowDada = (RowDataListModel) intent.getSerializableExtra(Constant.PARAM_MODEL);
            TYPE_FORMULAIRE = intent.getIntExtra(Constant.PARAM_TYPE_FORMULAIRE, 0);
            //IDBATIMENT = intent.getLongExtra(Constant.PARAM_ID, 0);
            //ID_INVENTAIRE = intent.getLongExtra(Constant.PARAM_ID, 0);
            SPref = Tools.SharedPreferences(this);
            /*if (SPref.getPref_TypeInventaire() != null) {
                TYPE_FORMULAIRE = Integer.parseInt(SPref.getPref_TypeInventaire());
            }*/
            if (SPref.getPref_IdInventaire() != null) {
                ID_INVENTAIRE = SPref.getPref_IdInventaire();
            }

            if (listType != Constant.LIST_INVENTAIRE_SDE || listType != Constant.LIST_INVENTAIRE_ALL_SDE) {
                message = "" + SPref.getDefaultConfiguration();
                tv_GrandTitre.setText(Html.fromHtml("" + message));
            } else {
                tv_GrandTitre.setText(Html.fromHtml("<b>" + Tools.GetStringTypeInventaire(TYPE_FORMULAIRE, "") + "</b><br />" + SPref.getDefaultConfiguration()));
            }
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(toolbar);

            init(Constant.FORM_DATA_MANAGER);
            init(Constant.CU_RECORD_MANAGER);
            init(Constant.QUERY_RECORD_MANAGER);

            tv_TitreListeLogement = (TextView) this.findViewById(R.id.tv_TitreListeLogement);
            et_NoBatiment = (EditText) this.findViewById(R.id.et_NoBatiment);
            sp_TypeBatiment = (Spinner) this.findViewById(R.id.sp_TypeBatiment);
            sp_TypeBatiment.setOnItemSelectedListener(this);

            et_AdrBat_LocaliteNouveauNom_Urbain = (EditText) this.findViewById(R.id.et_AdrBat_LocaliteNouveauNom_Urbain);

            et_AdrBat_HabitationAncienNom = (EditText) this.findViewById(R.id.et_AdrBat_HabitationAncienNom);
            et_AdrBat_HabitationNouveauNom = (EditText) this.findViewById(R.id.et_AdrBat_HabitationNouveauNom);
            et_AdrBat_LocaliteAncienNom = (EditText) this.findViewById(R.id.et_AdrBat_LocaliteAncienNom);
            et_AdrBat_LocaliteNouveauNom = (EditText) this.findViewById(R.id.et_AdrBat_LocaliteNouveauNom);
            et_NomInstitution = (EditText) this.findViewById(R.id.et_NomInstitution);
            et_PhoneInstitution = (EditText) this.findViewById(R.id.et_PhoneInstitution);

            et_Longitude = (EditText) this.findViewById(R.id.et_Longitude);
            et_Latitude = (EditText) this.findViewById(R.id.et_Latitude);

            Btn_GetGPS = (Button) this.findViewById(R.id.Btn_GetGPS);
            Btn_GetGPS.setOnClickListener(this);

            Btn_SaveData = (BootstrapButton) this.findViewById(R.id.Btn_SaveData);
            Btn_SaveData.setOnClickListener(this);

            LL_AdrBat_LocaliteNouveauNom_Rural = (LinearLayout) this.findViewById(R.id.LL_AdrBat_LocaliteNouveauNom_Rural);
            LL_Institution = (LinearLayout) this.findViewById(R.id.LL_Institution);
            LL_ListeLogement = (LinearLayout) this.findViewById(R.id.LL_ListeLogement);

            sp_EtatActuel = (Spinner) this.findViewById(R.id.sp_EtatActuel);
            et_NbrEtage = (EditText) this.findViewById(R.id.et_NbrEtage);

            sp_UsageBatiment = (Spinner) this.findViewById(R.id.sp_UsageBatiment);
            sp_UsageBatiment.setOnItemSelectedListener(this);

            et_NbrLogement = (EditText) this.findViewById(R.id.et_NbrLogement);
            et_Remarques = (EditText) this.findViewById(R.id.et_Remarques);

            this.setTitle(getFormulaireString(TYPE_FORMULAIRE));

            Load_DATA(Constant.DATA_TYPE_BATIMENT);
            Load_DATA(Constant.DATA_ETAT_BATIMENT);
            Load_DATA(Constant.DATA_USAGE_BATIMENT);

            noBatiment = queryRecordMngr.countBatimentByIdInventaire(ID_INVENTAIRE) + 1;
            ToastUtility.LogCat(this, "noBatiment: " + noBatiment);
            et_NoBatiment.setText("" + noBatiment);
            //NoBatiment = TypeSafeConversion.TranslateStringToInterger(et_NoBatiment.getText().toString());
            et_NoBatiment.setEnabled(false);

            // init DATA Preference
            CodeSDE = SPref.getPref_CodeSDE();
            NumOrdreSDE = SPref.getPref_NumeroOdreSDE();
            DepartementId = SPref.getPref_Departement();
            CommuneId = SPref.getPref_Commune();
            VqseId = SPref.getPref_Vqse();

            //initialize the progress bar
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            //initialize the recycle view
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            // First param is number of columns and second param is orientation i.e Vertical or Horizontal
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            // Attach the layout manager to the recycler view
            gridLayoutManager.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
            recyclerView.setHasFixedSize(true);
            //  initialize the adapter
            mAdapter = new DisplayListAdapter(this, targetList, Constant.LIST_LOGEMENT);
            mAdapter.setOnMenuItemClickListener(getMenuItemListener());

            //inject the adapter into the recycle view
            recyclerView.setAdapter(mAdapter);

            SetDataFromListe();

            new AsynDisplayDataListLogementTask().execute();

        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onCreate(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//

    //region EVENTS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true; //super.onCreateOptionsMenu(menu);
    }//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.btn_actionbar_Nouveau:
                    finish();
                    //Intent intent = new Intent(this, this.getClass());
                    //intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, TYPE_FORMULAIRE);
                    //startActivity(intent);
                    getFormulaireBatiment(TYPE_FORMULAIRE, ID_INVENTAIRE);
                    break;
                case R.id.btn_actionbar_Sauvegarder:
                    SaveBatiment();
                    break;
                case R.id.btn_actionbar_Annuler:
                    finish();
                    break;
                default:
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onOptionsItemSelected(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }//

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnContinuer:
                    ValidationControls();
                    break;
                case R.id.btnQuitter:
                    message = "Batiment enregistré avec succès. \n Nbre de Logement saisie:" + nbrLogementSave
                            + "\n Nbre de Logement à enregistrer:" + (batimentModel.getNbrLogement() - nbrLogementSave);
                    ToastUtility.ToastMessage(this, message, Constant.GravityCenter);
                    if (IDBATIMENT > 0) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    } else {
                        finish();
                    }
                    break;
                case R.id.Btn_SaveData:
                    SaveBatiment();
                    break;
                case R.id.Btn_GetGPS:
                    getServiceGPS();
                    //getCoordonnees(v);
                    break;
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onClick(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    //endregion

    private String getFormulaireString(int type_inventaire) {
        if (type_inventaire == Constant.FORM_URBAIN) {
            LL_AdrBat_LocaliteNouveauNom_Rural.setVisibility(View.GONE);
            et_AdrBat_LocaliteNouveauNom_Urbain.setVisibility(View.VISIBLE);
            return ".: Formulaire Urbain :.";
        } else if (type_inventaire == Constant.FORM_RURAL) {
            LL_AdrBat_LocaliteNouveauNom_Rural.setVisibility(View.VISIBLE);
            et_AdrBat_LocaliteNouveauNom_Urbain.setVisibility(View.GONE);
            return ".: Formulaire Rural :.";
        }
        return null;
    }

    //region LOAD DATA
    public void Load_DATA(String typeDeDonnee) {
        try {
            InputStream is = null;
            Gson gson = new Gson();
            Type collectionType = null;
            AssetManager assetManager = this.getAssets();
            Spinner spinner = sp_TypeBatiment;
            String msgVide = "";
            if (typeDeDonnee.equalsIgnoreCase(Constant.DATA_TYPE_BATIMENT)) {
                spinner = sp_TypeBatiment;
                msgVide = this.getResources().getString(R.string.label_Type_Batiment);
            } else if (typeDeDonnee.equalsIgnoreCase(Constant.DATA_ETAT_BATIMENT)) {
                spinner = sp_EtatActuel;
                msgVide = this.getResources().getString(R.string.label_EtatActuel);
            } else if (typeDeDonnee.equalsIgnoreCase(Constant.DATA_USAGE_BATIMENT)) {
                spinner = sp_UsageBatiment;
                msgVide = this.getResources().getString(R.string.label_UsageBatiment);
            }
            is = assetManager.open(typeDeDonnee);
            collectionType = new TypeToken<List<KeyValueModel>>() {
            }.getType();
            List<KeyValueModel> keyValueModelList = gson.fromJson(LoadStaticDataMngr.getStringJson(is), collectionType);
            int size = 0;
            if (keyValueModelList != null) {
                size = keyValueModelList.size();
            }
            ArrayList<KeyValueModel> keyValueModels = new ArrayList<KeyValueModel>();
            keyValueModels.add(new KeyValueModel("", "- " + msgVide + " (" + size + ") -"));
            keyValueModels.addAll(keyValueModelList);
            // Creating adapter for spinner
            ArrayAdapter<KeyValueModel> dataAdapter = new ArrayAdapter<KeyValueModel>(this, android.R.layout.simple_list_item_1, keyValueModels);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-Load_DATA(" + typeDeDonnee + "): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//
    //endregion

    //region SAVE AND Validation Fields
    private void SaveBatiment() {
        try {
            boolean isValide = true;
            KeyValueModel keyValueModel = null;
            String TypeInventaire = "" + TYPE_FORMULAIRE;
            String AdrBat_HabitationAncienNom = et_AdrBat_HabitationAncienNom.getText().toString();
            String AdrBat_HabitationNouveauNom = et_AdrBat_HabitationNouveauNom.getText().toString();
            String AdrBat_LocaliteAncienNom = et_AdrBat_LocaliteAncienNom.getText().toString();
            String AdrBat_LocaliteNouveauNom = et_AdrBat_LocaliteNouveauNom.getText().toString();
            String NomInstitution = et_NomInstitution.getText().toString();
            String PhoneInstitution = et_PhoneInstitution.getText().toString();

            String AdrBat_LocaliteNouveauNom_Urbain = et_AdrBat_LocaliteNouveauNom_Urbain.getText().toString();

            if (TYPE_FORMULAIRE == Constant.FORM_URBAIN) {
                AdrBat_HabitationAncienNom = AdrBat_LocaliteNouveauNom_Urbain;
                AdrBat_HabitationNouveauNom = "";
                AdrBat_LocaliteAncienNom = "";
                AdrBat_LocaliteNouveauNom = "";
            }
            String Longitude = et_Longitude.getText().toString();
            String Latitude = et_Latitude.getText().toString();

            keyValueModel = ((KeyValueModel) sp_TypeBatiment.getSelectedItem());
            String TypeBatiment = keyValueModel.getKey();

            keyValueModel = ((KeyValueModel) sp_EtatActuel.getSelectedItem());
            String EtatActuel = keyValueModel.getKey();

            String NbrEtage = et_NbrEtage.getText().toString();

            keyValueModel = ((KeyValueModel) sp_UsageBatiment.getSelectedItem());
            String UsageBatiment = keyValueModel.getKey();

            //NbrLogement = et_NbrLogement.getText().toString();
            String Remarques = et_Remarques.getText().toString();

            // Contrainte

            et_NoBatiment.setError(null);
            et_AdrBat_HabitationAncienNom.setError(null);
            et_AdrBat_HabitationNouveauNom.setError(null);
            et_AdrBat_LocaliteAncienNom.setError(null);
            et_AdrBat_LocaliteNouveauNom.setError(null);
            et_AdrBat_LocaliteNouveauNom_Urbain.setError(null);
            et_NomInstitution.setError(null);
            et_PhoneInstitution.setError(null);
            et_Longitude.setError(null);
            et_Latitude.setError(null);
            et_NbrEtage.setError(null);
            et_NbrLogement.setError(null);

            isValide = ValidationFields();
            if (isValide) {

                NbrEtage = et_NbrEtage.getText().toString();
                //NbrLogement = et_NbrLogement.getText().toString();

                BatimentModel batMdel = new BatimentModel();
                batMdel.setNoBatiment(noBatiment);
                batMdel.setCodeSDE(CodeSDE);
                batMdel.setNumOrdreSDE(NumOrdreSDE);
                batMdel.setInventaireId(ID_INVENTAIRE);
                batMdel.setTypeInventaire(TypeInventaire);
                batMdel.setAdrBat_HabitationAncienNom(AdrBat_HabitationAncienNom);
                batMdel.setAdrBat_HabitationNouveauNom(AdrBat_HabitationNouveauNom);
                batMdel.setAdrBat_LocaliteAncienNom(AdrBat_LocaliteAncienNom);
                batMdel.setAdrBat_LocaliteNouveauNom(AdrBat_LocaliteNouveauNom);
                batMdel.setLongitude(Longitude);
                batMdel.setLatitude(Latitude);
                batMdel.setTypeBatiment(TypeBatiment);
                batMdel.setEtatActuel(EtatActuel);
                batMdel.setNbrEtage(TypeSafeConversion.TranslateStringToInterger(NbrEtage));
                batMdel.setUsageBatiment(UsageBatiment);
                batMdel.setNbrLogement(TypeSafeConversion.TranslateStringToInterger(NbrLogement));
                batMdel.setDepartementId(DepartementId);
                batMdel.setCommuneId(CommuneId);
                batMdel.setVqseId(VqseId);
                batMdel.setNomInstitution(NomInstitution);
                batMdel.setPhoneInstitution(PhoneInstitution);
                batMdel.setRemarques(Remarques);
                batMdel.setIsValidated(false);
                batMdel.setIsSynchroToAppSup(false);
                batMdel.setIsSynchroToCentrale(false);
                //batMdel.setDateDebutCollecte();
                //batMdel.setDateFinCollecte();
                //ToastUtility.ToastMessage(this, "BEFORE CALL Save ");

                batimentModel = cuRecordMngr.SaveBatiment(IDBATIMENT, batMdel);
                //if( batimentModel.getNbrLogement() > 0) {
                nbrLogementSave = queryRecordMngr.countLogementByIdBatiment(batimentModel.getIdBatiment());
                //}

                if ( batimentModel.getIdBatiment() > 0) {
                    if (batimentModel.getNbrLogement() == nbrLogementSave) {
                        message = "Batiment enregistré avec succès. \n Logement:" + nbrLogementSave;
                        ToastUtility.ToastMessage(this, message);
                        ShowMessage_AskAddNewBatiment(message);
                    } else if (batimentModel.getNbrLogement() > nbrLogementSave) {
                        //if ((nbrLogementSave + 1) < batimentModel.getNbrLogement()) {
                        ShowMessage_AskAddNewLogement( null);
                        //} else if (IDBATIMENT > 0) {
                        //    if (nbrLogementSave < batimentModel.getNbrLogement()) {
                        //        ShowMessage_AskAddNewLogement(null);
                        //    } else {
                        //        message = "Batiment enregistré avec succès. \n Logement:" + nbrLogementSave;
                        //        ToastUtility.ToastMessage(this, message);
                        //        finish();
                        //    }
                        //}
                    }
                }
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-SaveBatiment(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    private boolean ValidationFields() {
        boolean isValide = true;
        try {
            KeyValueModel keyValueModel = null;
            String AdrBat_HabitationAncienNom = et_AdrBat_HabitationAncienNom.getText().toString();
            String AdrBat_HabitationNouveauNom = et_AdrBat_HabitationNouveauNom.getText().toString();
            String AdrBat_LocaliteAncienNom = et_AdrBat_LocaliteAncienNom.getText().toString();
            String AdrBat_LocaliteNouveauNom = et_AdrBat_LocaliteNouveauNom.getText().toString();
            String AdrBat_LocaliteNouveauNom_Urbain = et_AdrBat_LocaliteNouveauNom_Urbain.getText().toString();
            String NomInstitution = et_NomInstitution.getText().toString();
            String PhoneInstitution = et_PhoneInstitution.getText().toString();
            keyValueModel = ((KeyValueModel) sp_TypeBatiment.getSelectedItem());
            String TypeBatiment = keyValueModel.getKey();

            if (TextUtils.isEmpty(TypeBatiment)) {
                isValide = false;
                message = getString(R.string.label_Type_Batiment) + " " + getString(R.string.msg_Obligatoire);
                Tools.SetErrorField(this, sp_TypeBatiment, message);
            } else if (TYPE_FORMULAIRE == Constant.FORM_RURAL) { // 4 type d'adresse
                if (TextUtils.isEmpty(AdrBat_HabitationAncienNom)) {
                    isValide = false;
                    message = getString(R.string.label_adresse_du_batiment_Anc_Nom) + " " + getString(R.string.label_adresse_du_batiment_habitation) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, et_AdrBat_HabitationAncienNom, message);
                } else if (TextUtils.isEmpty(AdrBat_HabitationNouveauNom)) {
                    isValide = false;
                    message = getString(R.string.label_adresse_du_batiment_Nouv_Nom) + " " + getString(R.string.label_adresse_du_batiment_habitation) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, et_AdrBat_HabitationNouveauNom, message);
                } else if (TextUtils.isEmpty(AdrBat_LocaliteAncienNom)) {
                    isValide = false;
                    message = getString(R.string.label_adresse_du_batiment_Anc_Nom) + " " + getString(R.string.label_adresse_du_batiment_Localite) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, et_AdrBat_LocaliteAncienNom, message);
                } else if (TextUtils.isEmpty(AdrBat_LocaliteNouveauNom)) {
                    isValide = false;
                    message = getString(R.string.label_adresse_du_batiment_Nouv_Nom) + " " + getString(R.string.label_adresse_du_batiment_Localite) + " " + getString(R.string.msg_Obligatoire);;
                    Tools.SetErrorField(this, et_AdrBat_LocaliteNouveauNom, message);
                }
            } else if (TYPE_FORMULAIRE == Constant.FORM_URBAIN) {
                if (TextUtils.isEmpty(AdrBat_LocaliteNouveauNom_Urbain)) {
                    isValide = false;
                    message = getString(R.string.label_adresse_du_batiment) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, et_AdrBat_LocaliteNouveauNom_Urbain, message);
                }
            }
            if (isValide) {
                String Longitude = et_Longitude.getText().toString();
                String Latitude = et_Latitude.getText().toString();

                keyValueModel = ((KeyValueModel) sp_EtatActuel.getSelectedItem());
                String EtatActuel = keyValueModel.getKey();

                String NbrEtage = et_NbrEtage.getText().toString();
                NbrLogement = et_NbrLogement.getText().toString();

                keyValueModel = ((KeyValueModel) sp_UsageBatiment.getSelectedItem());
                String UsageBatiment = keyValueModel.getKey();
                if (TextUtils.isEmpty(Longitude)) {
                    isValide = false;
                    message = getString(R.string.label_Longitude) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, et_Longitude, message);
                } else if (TextUtils.isEmpty(Latitude)) {
                    isValide = false;
                    message = getString(R.string.label_Latitude) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, et_Latitude, message);
                } else if (TextUtils.isEmpty(EtatActuel)) {
                    isValide = false;
                    message = getString(R.string.label_EtatActuel) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, sp_EtatActuel, message);
                } else if (TextUtils.isEmpty(NbrEtage)) {
                    isValide = false;
                    message = getString(R.string.label_NbrEtage) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, et_NbrEtage, message);
                } else if (TypeSafeConversion.TranslateStringToInterger(TypeBatiment) <= Constant.MAISON_BASE_OU_SIMPLE_8 && TypeSafeConversion.TranslateStringToInterger(NbrEtage) > 0) {
                    // Si Type Batiment <= 08 alors Nbr Etage = 0
                    isValide = false;
                    Tools.SetErrorField(this, et_NbrEtage, "Pour ce type de batiment, Le nombre d'etage doit être égale a zero (0).");
                } else if (TextUtils.isEmpty(UsageBatiment)) {
                    isValide = false;
                    message = getString(R.string.label_UsageBatiment) + " " + getString(R.string.msg_Obligatoire);
                    Tools.SetErrorField(this, sp_UsageBatiment, message);
                } else if (TypeSafeConversion.TranslateStringToInterger(UsageBatiment) < Constant.COMMERCE_3) {
                    // Si l'Usage du batiment >=03 aller à Nom du chef de manage puis remarques
                    // Si Usage Batiment < 3 le Nbre de Logement doit etre > 0
                    if (TextUtils.isEmpty(NbrLogement)) {
                        isValide = false;
                        message = getString(R.string.label_NbrLogement) + " " + getString(R.string.msg_Obligatoire);
                        Tools.SetErrorField(this, et_NbrLogement, message);
                    } else if (TypeSafeConversion.TranslateStringToInterger(NbrLogement) <= 0) {
                        isValide = false;
                        message = "Le nombre de logement doit être supérieur a zéro pour l'usage de ce batiment";
                        Tools.SetErrorField(this, et_NbrLogement, message);
                    }
                } else if (TypeSafeConversion.TranslateStringToInterger(UsageBatiment) >= Constant.COMMERCE_3) {
                    //NbrLogement = "1";
                    //et_NbrLogement.setText(NbrLogement);
                    if (TypeSafeConversion.TranslateStringToInterger(NbrLogement) > 0) {
                        isValide = false;
                        message = "Le nombre de logement doit être égal a zéro pour l'usage de ce batiment";
                        Tools.SetErrorField(this, et_NbrLogement, message);
                    } else if (TextUtils.isEmpty(NomInstitution)) {
                        isValide = false;
                        message = getString(R.string.label_NomInstitution) + " " + getString(R.string.msg_Obligatoire);
                        Tools.SetErrorField(this, et_NomInstitution, message);
                    }
                }
            }
            if (isValide) {
                if (IDBATIMENT > 0) {
                    nbrLogementSave = queryRecordMngr.countLogementByIdBatiment(IDBATIMENT);
                    int NbrNbrLogement = TypeSafeConversion.TranslateStringToInterger(et_NbrLogement.getText().toString());
                    if (NbrNbrLogement < nbrLogementSave) {
                        isValide = false;
                        message = "Désolé le nombre de Logement ne peut pas être [" + NbrNbrLogement + "]. \n Parce que vous avez déjà enregistrer [" + nbrLogementSave + "] Logements pour ce batiment.";
                        Tools.SetErrorField(this, et_NbrLogement, message);
                    }
                }
            }
        } catch (Exception ex) {
            isValide = false;
            ToastUtility.LogCat("Exception-ShowMessage_AskAddNewLogement(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
        return isValide;
    }
    //endregion

    //region ShowMessage Ask AddNew
    private void ShowMessage_AskAddNewLogement(RowDataListModel rowDada) {
        try {
            int nbrLogement = (nbrLogementSave + 1);
            message = "Batiment enregistré avec succès. \n Logement:" + nbrLogementSave;
            ToastUtility.ToastMessage(this, message);
            //if( nbrLogement )

            dialog = new Dialog(this);
            dialog.setContentView(R.layout.logement);
            dialog.setTitle("Ajouter Logement [" + nbrLogement + " / " + batimentModel.getNbrLogement() + "]");
            dialog.setCancelable(false);

            TextView tv_NomCompletChefMenage = (TextView) dialog.findViewById(R.id.tv_NomCompletChefMenage);
            et_NomCompletChefMenage = (EditText) dialog.findViewById(R.id.et_NomCompletChefMenage);
            TextView tv_PhoneChefMenage = (TextView) dialog.findViewById(R.id.tv_PhoneChefMenage);
            et_PhoneChefMenage = (EditText) dialog.findViewById(R.id.et_PhoneChefMenage);
            tv_NumeroLogement = (TextView) dialog.findViewById(R.id.tv_NumeroLogement);
            et_NbrHommeVivant = (EditText) dialog.findViewById(R.id.et_NbrHommeVivant);
            et_NbrFemmeVivant = (EditText) dialog.findViewById(R.id.et_NbrFemmeVivant);
            et_RemarquesLogement = (EditText) dialog.findViewById(R.id.et_Remarques);
            LinearLayout LLNbreHommeEtFemme = (LinearLayout) dialog.findViewById(R.id.LLNbreHommeEtFemme);
            LLNbreHommeEtFemme.setVisibility(View.VISIBLE);

            tv_NumeroLogement.setText("Logement " + nbrLogement);
            if( batimentModel.getUsageBatiment() != null &&
                    TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) >= Constant.COMMERCE_3  ){
                dialog.setTitle(".: Informations Supplémentaires :.");
                LLNbreHommeEtFemme.setVisibility(View.GONE);
                tv_NumeroLogement.setVisibility(View.GONE);
                tv_NomCompletChefMenage.setText("Nom Institution");
                et_NomCompletChefMenage.setHint("Nom Institution");
                tv_PhoneChefMenage.setText("Téléphone de l'institution");
                et_PhoneChefMenage.setHint("Téléphone de l'institution\"");
            }

            message = "" + SPref.getDefaultConfiguration() + "<br /> <b>Batiment " + batimentModel.getNoBatiment() + "</b>";
            tv_GrandTitre = (TextView) dialog.findViewById(R.id.tv_GrandTitre);
            tv_GrandTitre.setText(Html.fromHtml("" + message));

            // Buttons
            Button btnQuitter = (Button) dialog.findViewById(R.id.btnQuitter);
            btnQuitter.setOnClickListener(this);
            if (IDBATIMENT > 0) {
                btnQuitter.setText("Fermer");
            }
            btnContinuer = (Button) dialog.findViewById(R.id.btnContinuer);
            btnContinuer.setOnClickListener(this);

            if (rowDada != null) {
                LogementModel log = (LogementModel) rowDada.getModel();
                IDLOGEMENT = log.getIdLogement();
                btnContinuer.setText("Modifier >>");
                et_NomCompletChefMenage.setText("" + log.getNomCompletChefMenage());
                et_PhoneChefMenage.setText("" + log.getPhoneChefMenage());
                tv_NumeroLogement.setVisibility(View.VISIBLE);
                tv_NumeroLogement.setText("Logement " + log.getNumeroLogement());
                et_NbrHommeVivant.setText("" + log.getNbrHommeVivant());
                et_NbrFemmeVivant.setText("" + log.getNbrFemmeVivant());
                et_RemarquesLogement.setText("" + log.getRemarques());
                dialog.setTitle("Modifier Logement [" + log.getNumeroLogement() + " / " + batimentModel.getNbrLogement() + "]");

                if( TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) >= Constant.COMMERCE_3  ){
                    tv_NumeroLogement.setVisibility(View.GONE);
                    dialog.setTitle("Modifier Informations supplémentaires");
                }
            }
            dialog.show();

        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-ShowMessage_AskAddNewLogement(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    //
    private void ShowMessage_AskAddNewBatiment(String message) {
        try {
            aBuilder = new AlertDialog.Builder(this);
            String titleMsg = getString(R.string.msg_title_succesInformation);
            aBuilder.setTitle(titleMsg);
            aBuilder.setMessage(message);
            aBuilder.setCancelable(false);

            aBuilder.setPositiveButton(getString(R.string.label_Ajouter_Nouveau_Batiment),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            getFormulaireBatiment(TYPE_FORMULAIRE, ID_INVENTAIRE);
                        }
                    }
            );
            aBuilder.setNegativeButton(getString(R.string.label_Fermer),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (IDLOGEMENT > 0) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            } else {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                finish();
                            }
                        }
                    }

            );
            aBuilder.show();
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-ShowMessageChoix(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }
    //endregion

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
                 if( batimentModel.getUsageBatiment() != null &&
                        TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) >= Constant.COMMERCE_3  ){
                     Tools.SetErrorField(this, et_NomCompletChefMenage, getString(R.string.label_NomInstitution) + obligatoire);
                 }else{
                    Tools.SetErrorField(this, et_NomCompletChefMenage, getString(R.string.label_NomCompletChefMenage) + obligatoire);
                }
            } else if ( batimentModel.getUsageBatiment() != null &&
                    TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) < Constant.COMMERCE_3 ) {
                // Si l'usage du batiment est >= 3 ou demande uniquement le nom du chef de menage.
                if (TextUtils.isEmpty(et_NbrHommeVivant.getText().toString())) {
                    cancel = true;
                    Tools.SetErrorField(this, et_NbrHommeVivant, getString(R.string.label_NbrHommeVivant) + obligatoire);
                } else if (TextUtils.isEmpty(et_NbrFemmeVivant.getText().toString())) {
                    cancel = true;
                    Tools.SetErrorField(this, et_NbrFemmeVivant, getString(R.string.label_NbrFemmeVivant) + obligatoire);
                }
            }
            if (!cancel) {
                LogementModel inv = new LogementModel();
                inv.setBatimentId(batimentModel.getIdBatiment());
                inv.setNumeroLogement(nbrLogementSave + 1);
                inv.setNomCompletChefMenage(NomCompletChefMenage);
                inv.setPhoneChefMenage(PhoneChefMenage);
                inv.setNbrHommeVivant(NbrHommeVivant);
                inv.setNbrFemmeVivant(NbrFemmeVivant);
                inv.setRemarques(remarquesLogement);

                LogementModel logementModel = null;
                logementModel = cuRecordMngr.SaveLogement(IDLOGEMENT, inv);
                nbrLogementSave = queryRecordMngr.countLogementByIdBatiment(batimentModel.getIdBatiment());

                new AsynDisplayDataListLogementTask().execute();
                if (IDLOGEMENT > 0) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if ( batimentModel.getUsageBatiment() != null &&
                            TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) < Constant.COMMERCE_3 ) {
                        message = "Logement enregistré avec succès. \n Nbre de Logement saisie:  " + nbrLogementSave
                                + "\n Nbre de Logement à enregistrer:  " + (batimentModel.getNbrLogement() - nbrLogementSave);
                        ToastUtility.ToastMessage(this, message);
                    }else{
                        message = "Informations Supplémentaire enregistrée avec succès.";
                        ToastUtility.ToastMessage(this, message);
                    }
                } else {
                    if (logementModel.getIdLogement() > 0) {
                        if ((nbrLogementSave + 1) == batimentModel.getNbrLogement()) {
                            btnContinuer.setText(getString(R.string.label_Terminer));
                        }
                        if (nbrLogementSave < batimentModel.getNbrLogement()) {
                            ClearAllFieldLogement();
                            tv_NumeroLogement.setText("Logement " + (nbrLogementSave + 1));
                            dialog.setTitle("Ajouter Logement [" + (nbrLogementSave + 1) + " / " + batimentModel.getNbrLogement() + "]");
                        } else {
                            if ( batimentModel.getUsageBatiment() != null &&
                                    TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) < Constant.COMMERCE_3 ) {
                                message = "Bâtiment enregistré avec succès. \n Nbre de Logement saisie:  " + nbrLogementSave
                                        + "\n Nbre de Logement à enregistrer:  " + (batimentModel.getNbrLogement() - nbrLogementSave);
                            }else{
                                message = "Informations Supplémentaire enregistrée avec succès.";
                            }
                            ShowMessage_AskAddNewBatiment(message);
                        }
                    }
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
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-ClearAllFieldLogement(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            // TEST POUR LE TYPE DU BATIMENT
            KeyValueModel keyValueTB = null;
            if (parent.getId() == R.id.sp_TypeBatiment) {
                keyValueTB = ((KeyValueModel) sp_TypeBatiment.getSelectedItem());
            }
            if (keyValueTB != null) {
                int typeBatiment = 0;
                if (!keyValueTB.getKey().trim().equalsIgnoreCase("")) {
                    if (parent.getId() == R.id.sp_TypeBatiment) {
                        typeBatiment = TypeSafeConversion.TranslateStringToInterger(keyValueTB.getKey());
                        // Si Type Batiment <= 08 alors Nbr Etage = 0
                        if (typeBatiment <= Constant.MAISON_BASE_OU_SIMPLE_8) {
                            et_NbrEtage.setText("0");
                        } else if (typeBatiment >= Constant.MAISON_A_ETAGE_9) {

                        }
                    }
                }
            }
            // TEST POUR L'USAGE DU BATIMENT
            KeyValueModel keyValueUB = null;
            if (parent.getId() == R.id.sp_UsageBatiment) {
                keyValueUB = ((KeyValueModel) sp_UsageBatiment.getSelectedItem());
            }
            if (keyValueUB != null) {
                LL_Institution.setVisibility(View.GONE);
                LL_ListeLogement.setVisibility(View.VISIBLE);

                int usageBatiment = 0;
                if (!keyValueUB.getKey().trim().equalsIgnoreCase("")) {
                    if (parent.getId() == R.id.sp_UsageBatiment) {
                        usageBatiment = TypeSafeConversion.TranslateStringToInterger(keyValueUB.getKey());
                        // Si l'Usage du batiment >=03 aller à Nom du chef de manage puis remarques
                        if (usageBatiment >= Constant.COMMERCE_3) {
                            et_NbrLogement.setText("0");
                            LL_Institution.setVisibility(View.VISIBLE);
                            LL_ListeLogement.setVisibility(View.GONE);
                        } else if (usageBatiment >= Constant.MAISON_A_ETAGE_9) {

                        }
                    }
                }
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onItemSelected(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //endregion

    //region LISTE LOGEMENT
    public class AsynDisplayDataListLogementTask extends AsyncTask<String, Void, Integer> {
        List<RowDataListModel> logs = null;

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                targetList.clear();
                if (batimentModel != null) {
                    logs = queryRecordMngr.searchListLogementByIdBatiment(batimentModel);
                    //logs = queryRecordMngr.searchListLogementByIdBatiment(batimentModel.getIdBatiment());
                    if (logs != null) {
                        //nbrLogementSave = logs.size();
                        targetList.addAll(logs);
                    }
                    //targetList.addAll(queryRecordMngr.searchListLogementByIdBatiment(batimentModel.getIdBatiment()));
                }
            } catch (Exception ex) {
                ToastUtility.LogCat("Exception-AsynDisplayDataListTask():doInBackground() getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
                ex.printStackTrace();
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);
            if (result == 1) {
                int logs_size = 0;
                if (logs != null) {
                    logs_size = logs.size();
                }
                if (batimentModel != null) {
                    if( TypeSafeConversion.TranslateStringToInterger(batimentModel.getUsageBatiment()) >= Constant.COMMERCE_3  ){
                        tv_TitreListeLogement.setText("Informations Supplémentaires");
                    }else{
                        tv_TitreListeLogement.setText(getString(R.string.label_ListeLogement) + " (" + logs_size + " / " + batimentModel.getNbrLogement() + ")");
                    }
                }
                mAdapter.setFilter(targetList);
            }
        }
    }//

    public DisplayListAdapter.OnMenuItemClickListener getMenuItemListener() {
        return new DisplayListAdapter.OnMenuItemClickListener() {

            @Override
            public void onMenuItemAfficher(RowDataListModel row) {
                ShowMessage_AskAddNewLogement( row);
                //Toast.makeText(getApplicationContext(), row.getTitle() + " Module is selected!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMenuItemModifier(RowDataListModel row) {
                ShowMessage_AskAddNewLogement( row);
                //Toast.makeText(getApplicationContext(), row.getTitle() + " Update is selected!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMenuItemModuleMenu(RowDataListModel row) {
                //goToMenu(row);
                Toast.makeText(getApplicationContext(), row.getTitle() + " View is selected!", Toast.LENGTH_LONG).show();
            }
        };
    }
    //endregion

    //region SET VALUES
    private void SetDataFromListe() {
        try {
            ToastUtility.LogCat(this, "Inside SetDataFromListe()");
            Intent intent = getIntent();
            rowDada = (RowDataListModel) intent.getSerializableExtra(Constant.PARAM_MODEL);
            if (rowDada != null) {
                batimentModel = (BatimentModel) rowDada.getModel();
            }
            if (batimentModel != null) {
                CodeSDE = batimentModel.getCodeSDE();
                NumOrdreSDE = batimentModel.getNumOrdreSDE();
                DepartementId = batimentModel.getDepartementId();
                CommuneId = batimentModel.getCommuneId();
                VqseId = batimentModel.getVqseId();

                ID_INVENTAIRE = batimentModel.getInventaireId();
                IDBATIMENT = batimentModel.getIdBatiment();
                et_NoBatiment.setText("" + batimentModel.getNoBatiment());
                noBatiment = batimentModel.getNoBatiment();
                et_AdrBat_HabitationAncienNom.setText(batimentModel.getAdrBat_HabitationAncienNom());
                et_AdrBat_HabitationNouveauNom.setText(batimentModel.getAdrBat_HabitationNouveauNom());
                et_AdrBat_LocaliteAncienNom.setText(batimentModel.getAdrBat_LocaliteAncienNom());
                et_AdrBat_LocaliteNouveauNom.setText(batimentModel.getAdrBat_LocaliteNouveauNom());

                et_AdrBat_LocaliteNouveauNom_Urbain.setText(batimentModel.getAdrBat_HabitationAncienNom());

                et_Longitude.setText(batimentModel.getLongitude());
                et_Latitude.setText(batimentModel.getLatitude());
                et_NomInstitution.setText(batimentModel.getNomInstitution());
                et_PhoneInstitution.setText(batimentModel.getPhoneInstitution());

                et_Remarques.setText(batimentModel.getRemarques());

                SetValues(sp_TypeBatiment, batimentModel.getTypeBatiment());
                SetValues(sp_EtatActuel, batimentModel.getEtatActuel());
                SetValues(sp_UsageBatiment, batimentModel.getUsageBatiment());

                et_NbrEtage.setText("" + batimentModel.getNbrEtage());
                et_NbrLogement.setText("" + batimentModel.getNbrLogement());
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-SetDataFromListe(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    private void SetValues(Spinner spinner, String codeReponse) {
        try {
            ArrayAdapter<KeyValueModel> keyValueModel = (ArrayAdapter<KeyValueModel>) spinner.getAdapter();
            for (int i = 0; i < keyValueModel.getCount(); i++) {
                if (keyValueModel.getItem(i).getKey().equalsIgnoreCase(codeReponse)) {
                    ToastUtility.LogCat("W", "[+] getDeptId>> " + keyValueModel.getItem(i).getKey() + " / getDeptNom>> " + keyValueModel.getItem(i).getValue());
                    spinner.setSelection(i);
                    break;
                }
                ToastUtility.LogCat("[-] getDeptId>> " + keyValueModel.getItem(i).getKey() + " / getDeptNom>> " + keyValueModel.getItem(i).getValue());
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-SetValues(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }
    //endregion

    //region LOCALISATION GPS

    private void getServiceGPS() {
        try{
            GPSService gpsService = new GPSService(this);
            gpsService.getLocation();
            if (gpsService.isLocationAvailable == false) {
                // Here you can ask the user to try again, using return; for that
                ToastUtility.ToastMessage(this, "Votre localisation n'est pas disponnible, Essayez a nouveau!", Constant.GravityCenter);
            } else {
                // Getting location co-ordinates
                double latitude = gpsService.getLatitude();
                double longitude = gpsService.getLongitude();
                //String locationAddress = gpsService.getLocationAddress();
                message = "Latitude:" + latitude + " | Longitude: " + longitude;
                ToastUtility.LogCat("W", message);
                ToastUtility.ToastMessage(this, message);

                et_Latitude.setText("" + latitude );
                et_Longitude.setText("" + longitude);
                //et_AdrBat_HabitationAncienNom.setText("Address: " + locationAddress);
            }
            // make sure you close the gps after using it. Save user's battery power
            //gpsService.closeGPS();
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-getServiceGPS(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    //region NOT USE
/*
    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlertGpsDisabled();
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlertGpsDisabled() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Activer la localisation GPS")
                .setMessage("Le GPS est inactif, voulez-vous l'activer ?")
                .setPositiveButton("Activer GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Ne pas l'activer ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    //region LISTENER
    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    et_Longitude.setText(longitude + "");
                    et_Latitude.setText(latitude + "");
                    ToastUtility.LogCat("W", getString(R.string.msg_Coordonnee_Update));
                    ToastUtility.ToastMessage(FormulaireActivity.this, getString(R.string.msg_Coordonnee_Update));
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            ToastUtility.LogCat("W", "GPS Provider onStatusChanged");
            //Toast.makeText(FormulaireActivity.this, "GPS Provider onStatusChanged", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String s) {
            ToastUtility.LogCat("W", "GPS onProviderEnabled");
            //Toast.makeText(FormulaireActivity.this, "GPS Provider onStatusChanged", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String s) {
            ToastUtility.LogCat("W", "GPS onProviderDisabled");
        }
    };

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    et_Longitude.setText(longitude + "");
                    et_Latitude.setText(latitude + "");
                    ToastUtility.LogCat("W", getString(R.string.msg_Coordonnee_Update));
                    ToastUtility.ToastMessage(FormulaireActivity.this, getString(R.string.msg_Coordonnee_Update));
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    //endregion

    //region getCoordonnee
    public void getCoordonnees(View view) {
        try {
            if (!checkLocation())
                return;
            Button button = (Button) view;
            if (button.getText().equals(getResources().getString(R.string.label_PauseGPS))) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.removeUpdates(locationListenerGPS);
                button.setText(R.string.label_ResumeGPS);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);
                button.setText(R.string.label_PauseGPS);
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-getCoordonnees(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }//

    public void getNetworkPoint(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.label_PauseGPS))) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(locationListenerNetwork);
            button.setText(R.string.label_ResumeGPS);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerNetwork);
            Toast.makeText(this, "Network provider started running", Toast.LENGTH_LONG).show();
            button.setText(R.string.label_PauseGPS);
        }
    }*/
    //endregion

    //endregion

}















        