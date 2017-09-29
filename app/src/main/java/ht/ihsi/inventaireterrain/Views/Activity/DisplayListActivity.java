package ht.ihsi.inventaireterrain.Views.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Views.Adapters.DisplayListAdapter;
import ht.ihsi.inventaireterrain.Views.Decorator.SimpleDividerItemDecoration;

/**
 * Created by ajordany on 4/7/2016.
 */
public class DisplayListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "DisplayListActivity";
    private List<RowDataListModel> targetList=new ArrayList<RowDataListModel>();
    private RecyclerView recyclerView;
    private DisplayListAdapter mAdapter;
    private ProgressBar progressBar;
    private MaterialSearchView searchView;
    private Toolbar toolbar;
    private String title;
    private int listType = 0;
    private int TYPE_FORMULAIRE = 0;
    private long id = 0;
    private long ID_INVENTAIRE = 0;
    public LinearLayout LLGrandTitre ;
    public TextView tv_GrandTitre;
    private RowDataListModel rowDada;
    private RowDataListModel rowDadaMere;
    public BatimentModel batimentModel;
    public LogementModel logementModel;
    public int nbrLogement;
    public int nbrLogementSave;
    FloatingActionButton fab_AddElement;
    Shared_Preferences SPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_2);

        try {
            LLGrandTitre = (LinearLayout) findViewById(R.id.LLGrandTitre);
            tv_GrandTitre = (TextView) findViewById(R.id.tv_GrandTitre);
            SPref = Tools.SharedPreferences(this);

            Intent intent = getIntent();
            title = intent.getStringExtra(Constant.PARAM_LIST_TITLE);
            listType = Integer.valueOf(intent.getStringExtra(Constant.PARAM_LIST_TYPE)).intValue();
            rowDada = (RowDataListModel) intent.getSerializableExtra(Constant.PARAM_MODEL);
            rowDadaMere = (RowDataListModel) intent.getSerializableExtra(Constant.PARAM_MODEL_MERE);

            TYPE_FORMULAIRE = Short.valueOf(intent.getStringExtra(Constant.PARAM_TYPE_FORMULAIRE));

            if (listType != Constant.LIST_INVENTAIRE_SDE || listType != Constant.LIST_INVENTAIRE_ALL_SDE) {
                id = Long.valueOf(intent.getStringExtra(Constant.PARAM_ID)).longValue();
                message = "" + SPref.getDefaultConfiguration();
                if (rowDadaMere != null) {
                    if (listType == Constant.LIST_BATIMENT) {
                        //batimentModel = (BatimentModel) rowDada.getModel();
                        message = "" + SPref.getDefaultConfiguration();
                    } else if (listType == Constant.LIST_LOGEMENT) {
                        batimentModel = (BatimentModel) rowDadaMere.getModel();
                        //logementModel = (LogementModel) rowDada.getModel();
                        nbrLogement = batimentModel.getNbrLogement();
                        message = "" + SPref.getDefaultConfiguration() + "<br /> <b>Batiment " + batimentModel.getNoBatiment() + "</b>";
                    }
                }
                tv_GrandTitre.setText(Html.fromHtml("" + message));
            } else {
                tv_GrandTitre.setText(Html.fromHtml("<b>" + Tools.GetStringTypeInventaire(TYPE_FORMULAIRE, "") + "</b><br />" + SPref.getDefaultConfiguration()));
            }

            ToastUtility.LogCat("W", "listType: " + listType + "/ title: " + title + "/ TYPE_FORMULAIRE: " + TYPE_FORMULAIRE + " / id: " + id);
            // Toolbar creation
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(title);
            //toolbar.setLogo(R.drawable.logo_rgph);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            fab_AddElement = (FloatingActionButton) findViewById(R.id.fab_AddElement);
            fab_AddElement.setOnClickListener(this);

            if (listType == Constant.LIST_INVENTAIRE_SDE || listType == Constant.LIST_INVENTAIRE_ALL_SDE) {
                //fab_AddElement.setVisibility(View.GONE);
            }
            createSearchView(); //create the search view
            init(Constant.QUERY_RECORD_MANAGER);

            //initialize the recycle view
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            //recyclerView.setLayoutManager(linearLayoutManager);

            // First param is number of columns and second param is orientation i.e Vertical or Horizontal
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            // Attach the layout manager to the recycler view
            gridLayoutManager.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(gridLayoutManager);

            //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            //recyclerView.addItemDecoration(new SpacesItemDecoration(10));

            //int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
            //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));

            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
            //recyclerView.setItemAnimator(new SlideInUpAnimator());
            recyclerView.setHasFixedSize(true);
       /* recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //Toast.makeText(getApplicationContext(), "endless scrooll page="+page+" totalItemsCount"+totalItemsCount, Toast.LENGTH_LONG).show();
                new AsynDisplayDataListTask().execute();
            }
        });*/

            //  initialize the adapter
            mAdapter = new DisplayListAdapter(DisplayListActivity.this, targetList, listType);
            mAdapter.setOnMenuItemClickListener(getMenuItemListener());

            //inject the adapter into the recycle view
            recyclerView.setAdapter(mAdapter);
            //initialize the progress bar

            //initialize the progress bar
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            //load asynchronously the list of the data
            //AsynDisplayDataListTask task=
            //new AsynDisplayDataListTask().execute();
        }catch (Exception ex) {
            ToastUtility.ToastMessage(getApplicationContext(), " ERREUR : \n toString: " + ex.toString());
            ToastUtility.LogCat("Exception-onCreate(): getMessage: ", ex);
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        ToastUtility.LogCat("W", "onResume");
        new AsynDisplayDataListTask().execute();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    public void createSearchView(){
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onTextSearchEvent(newText);
                return true;
            }


        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem action_ListeAllSDE = menu.findItem(R.id.action_ListeAllSDE);
        MenuItem action_ListeSDEParFormulaire = menu.findItem(R.id.action_ListeSDEParFormulaire);

        action_ListeAllSDE.setVisible(false);
        action_ListeSDEParFormulaire.setVisible(false);
        if( listType == Constant.LIST_INVENTAIRE_SDE ){
            action_ListeAllSDE.setVisible(true);
            //fab_AddElement.setVisibility(View.GONE);
        }
        if( listType == Constant.LIST_INVENTAIRE_ALL_SDE ){
            action_ListeSDEParFormulaire.setVisible(true);
            //fab_AddElement.setVisibility(View.GONE);
        }
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.action_RefreshList:
                    new AsynDisplayDataListTask().execute();
                    break;
                case R.id.action_ListeAllSDE:
                    // On affiche la liste de tous les SDE
                    finish();
                    showListView("Liste de tous les SDE", Constant.LIST_INVENTAIRE_ALL_SDE, TYPE_FORMULAIRE, 0);
                    break;
                case R.id.action_ListeSDEParFormulaire:
                    // On affiche la liste des SDE
                    finish();
                    showListView("Liste des SDE", Constant.LIST_INVENTAIRE_SDE, TYPE_FORMULAIRE, 0);
                    break;
                default:
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onOptionsItemSelected(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onTextSearchEvent(String newText) {
        if(targetList!=null && targetList.size()>0) {
            final List<RowDataListModel> filteredList = filter(targetList, newText);
            mAdapter.setFilter(filteredList);
        }
        return true;
    }

    private List<RowDataListModel> filter(List<RowDataListModel> rows, String query){
        query=query.toLowerCase();
        final List<RowDataListModel> filteredList=new ArrayList<>();
        if(rows!=null) {
            for (RowDataListModel row : rows) {
                final String text = row.getTitle().toLowerCase();
                if (text.contains(query)) {
                    filteredList.add(row);
                }
            }
        }
        return filteredList;
    }

    public DisplayListAdapter.OnMenuItemClickListener getMenuItemListener(){
        return new DisplayListAdapter.OnMenuItemClickListener() {

            @Override
            public void onMenuItemAfficher(RowDataListModel row) {
                goToForm(row);
                //Toast.makeText(getApplicationContext(), row.getTitle() + " Module is selected!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMenuItemModifier(RowDataListModel row) {
                goToForm(row);
                //Toast.makeText(getApplicationContext(), row.getTitle() + " Update is selected!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMenuItemModuleMenu(RowDataListModel row) {
                goToMenu(row);
                //Toast.makeText(getApplicationContext(), row.getTitle() + " View is selected!", Toast.LENGTH_LONG).show();
            }
        };
    }

    public void goToForm(RowDataListModel row){
        try {
            //message = "TYPE_FORMULAIRE>>"+ TYPE_FORMULAIRE +" | getTitle>>"+ row.getTitle() +" | getDesc>>"+ row.getDesc() +" | getTableName>>"+ row.getTableName() +" | getModel>>"+ row.getModel();
            //ToastUtility.LogCat("D", "INSIDE - goToForm() : " + message);
            if( listType == Constant.LIST_INVENTAIRE_SDE || listType == Constant.LIST_INVENTAIRE_ALL_SDE){
                // On affiche le formulaire de saisie
                intent = new Intent(this, InventaireSDEActivity.class);
                intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, TYPE_FORMULAIRE);
                intent.putExtra(Constant.PARAM_MODEL, row);
                startActivity(intent);
            }else  if( listType == Constant.LIST_BATIMENT){
                intent = new Intent(this, FormulaireActivity.class);
                intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, TYPE_FORMULAIRE);
                //intent.putExtra(Constant.PARAM_ID, row.getId());
                intent.putExtra(Constant.PARAM_MODEL, row);
                startActivity(intent);
            }else  if( listType == Constant.LIST_LOGEMENT){
                intent = new Intent(this, LogementActivity.class);
                intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, TYPE_FORMULAIRE);
                intent.putExtra(Constant.PARAM_MODEL_MERE, batimentModel);
                intent.putExtra(Constant.PARAM_MODEL, row);
                startActivity(intent);
            }else  if( listType == Constant.LIST_COMPTE_UTILISATEUR){
                intent = new Intent(this, FormulaireUtilisateur.class);
                intent.putExtra(Constant.PARAM_MODEL, row);
                intent.putExtra(Constant.PARAM_ID, row.getId());
                startActivity(intent);
            }

        }catch (Exception ex) {
            ToastUtility.ToastMessage(getApplicationContext(), " ERREUR : \n toString: " + ex.toString());
            ToastUtility.LogCat("Exception-goToForm(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    public void goToMenu(RowDataListModel row){
        try {
            if ( listType == Constant.LIST_INVENTAIRE_SDE || listType == Constant.LIST_INVENTAIRE_ALL_SDE ) {//3
                // On affiche la liste des batiments par SDE
                //InventaireModel  inventaireModel = (InventaireModel) row.getModel();
                long id_inventaire = row.getId();
                showListView(getString(R.string.label_Batiment) + " [" + Tools.GetStringTypeInventaire(TYPE_FORMULAIRE, "") + " ]", Constant.LIST_BATIMENT, TYPE_FORMULAIRE, id_inventaire, row);
            } else if (listType == Constant.LIST_BATIMENT) {//1
                long idBatiment = row.getId();
                showListView(getString(R.string.label_ListeLogement) + " [" + Tools.GetStringTypeInventaire(TYPE_FORMULAIRE, "") + " ]", Constant.LIST_LOGEMENT, TYPE_FORMULAIRE, idBatiment, row);
            }
            ToastUtility.LogCat(this, "goToMenu() : ... ... listType [ "+listType+" ] ");
        }catch (Exception ex) {
            ToastUtility.LogCat(this, "Exception:goToMenu() - getMessage:", ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try{
            Shared_Preferences SPref = Tools.SharedPreferences(this);
            if( SPref.getPref_IdInventaire() != null) {
                ID_INVENTAIRE = SPref.getPref_IdInventaire();
            }
            switch (v.getId()) {
                case R.id.fab_AddElement:
                    if( listType == Constant.LIST_INVENTAIRE_SDE || listType == Constant.LIST_INVENTAIRE_ALL_SDE  ){
                        // On affiche le formulaire de saisie
                        getFormulaire_InventaireSDE(0, ID_INVENTAIRE);
                    }else  if( listType == Constant.LIST_BATIMENT){
                        getFormulaireBatiment(TYPE_FORMULAIRE, ID_INVENTAIRE);
                    }else  if( listType == Constant.LIST_LOGEMENT){
                        if( nbrLogementSave < nbrLogement) {
                            intent = new Intent(this, LogementActivity.class);
                            intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, TYPE_FORMULAIRE);
                            intent.putExtra(Constant.PARAM_MODEL_MERE, batimentModel);
                            //intent.putExtra(Constant.PARAM_MODEL, row);
                            startActivity(intent);
                            //getFormulaireBatiment(TYPE_FORMULAIRE, ID_INVENTAIRE);
                        }else{
                            ToastUtility.ToastMessage(this, "Pas de logement à enregistrer", Constant.GravityCenter);
                        }
                    }else  if( listType == Constant.LIST_COMPTE_UTILISATEUR){
                        intent = new Intent(this, FormulaireUtilisateur.class);
                        intent.putExtra(Constant.PARAM_TYPE_FORMULAIRE, "");
                        intent.putExtra(Constant.PARAM_ID, 0);
                        startActivity(intent);
                    }
                    break;
                default:
            }
        }catch (Exception ex) {
            ToastUtility.ToastMessage(getApplicationContext(), " ERREUR : \n toString: " + ex.toString());
            ToastUtility.LogCat("Exception-onClick(): getMessage: ", ex);
            ex.printStackTrace();
        }
    }

    public class AsynDisplayDataListTask extends AsyncTask<String, Void, Integer>{
        List<RowDataListModel> logs = null;
        Shared_Preferences SPref = Tools.SharedPreferences(DisplayListActivity.this);

        @Override
        protected void onPreExecute() {
            //targetList.add(null);
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                if( targetList != null ) {
                    targetList.clear();
                }
                if (listType == Constant.LIST_INVENTAIRE_SDE) {
                    targetList.addAll(queryRecordMngr.searchAllListInventaireSDE_ByTypeInventaire("" + TYPE_FORMULAIRE));
                } else if (listType == Constant.LIST_INVENTAIRE_ALL_SDE) {
                    targetList.addAll(queryRecordMngr.searchAllListInventaireSDE());
                } else if (listType == Constant.LIST_BATIMENT) {
                    targetList.addAll(queryRecordMngr.searchListBatByTypeInventaire_ByIdInventaireSDE("" + TYPE_FORMULAIRE, id));
                } else if (listType == Constant.LIST_LOGEMENT) {
                    logs = queryRecordMngr.searchListLogementByIdBatiment(batimentModel);
                    if (logs != null) {
                        nbrLogementSave = logs.size();
                        targetList.addAll(logs);
                    }
                }else if (listType == Constant.LIST_COMPTE_UTILISATEUR) {
                    targetList.addAll(queryRecordMngr.searchListProfilUser(SPref));
                }
            }catch (Exception ex) {
                ToastUtility.ToastMessage(DisplayListActivity.this, " ERREUR : \n toString: " + ex.toString());
                ToastUtility.LogCat( "Exception:AsynDisplayDataListTask-doInBackground() - getMessage:", ex);
                ex.printStackTrace();
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);
            try {
                if (result == 1) {
                    if (listType == Constant.LIST_LOGEMENT) {
                        if (logs == null) {
                            ToastUtility.ToastMessage(DisplayListActivity.this, "Aucun logement n'est encore enregistré pour ce Batiment", Constant.GravityCenter);
                        }
                        if (nbrLogementSave == nbrLogement) {
                            fab_AddElement.setVisibility(View.GONE);
                        } else if (nbrLogementSave < nbrLogement) {
                            fab_AddElement.setVisibility(View.VISIBLE);
                        }
                    }
                    toolbar.setTitle(title + " (" + targetList.size() + ")");
                    mAdapter.setFilter(targetList);
                } else {
                    Log.e(TAG, "Failed to fetch data!");
                }
            }catch (Exception ex) {
                ToastUtility.ToastMessage(getApplicationContext(), " ERREUR : \n toString: " + ex.toString());
                ToastUtility.LogCat( "Exception:AsynDisplayDataListTask-onPostExecute() - getMessage:", ex);
                ex.printStackTrace();
            }
        }
    }
}
