package ht.ihsi.inventaireterrain.Managers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ht.ihsi.inventaireterrain.Backend.Batiment;
import ht.ihsi.inventaireterrain.Backend.BatimentDao;
import ht.ihsi.inventaireterrain.Backend.Commune;
import ht.ihsi.inventaireterrain.Backend.CommuneDao;
import ht.ihsi.inventaireterrain.Backend.Departement;
import ht.ihsi.inventaireterrain.Backend.DepartementDao;
import ht.ihsi.inventaireterrain.Backend.Inventaire;
import ht.ihsi.inventaireterrain.Backend.InventaireDao;
import ht.ihsi.inventaireterrain.Backend.Logement;
import ht.ihsi.inventaireterrain.Backend.LogementDao;
import ht.ihsi.inventaireterrain.Backend.Personnel;
import ht.ihsi.inventaireterrain.Backend.PersonnelDao;
import ht.ihsi.inventaireterrain.Backend.Vqse;
import ht.ihsi.inventaireterrain.Backend.VqseDao;
import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Exceptions.ManagerException;
import ht.ihsi.inventaireterrain.Mappers.ModelMapper;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.CommuneModel;
import ht.ihsi.inventaireterrain.Models.DepartementModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.Models.VqseModel;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Utilities.TypeSafeConversion;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class QueryRecordMngrImpl extends AbstractDatabaseManager implements QueryRecordMngr {

    private static QueryRecordMngrImpl instance;

    public QueryRecordMngrImpl(final Context context) {
        super(context);
    }

    //region required methods
    public static QueryRecordMngrImpl getInstance(Context context){

        if (instance == null) {
            instance = new QueryRecordMngrImpl(context);
        }

        return instance;
    }

    /**
     * Get a batiment by its id.
     *
     * @param batId the id of batiment.
     * @return BatimentModel
     * @throws ManagerException
     */
    @Override
    public BatimentModel getBatimentById(long batId) throws ManagerException {
        Log.i(MANAGERS, "Inside of getBatimentById!");
        BatimentModel result=null;
        try {
            openReadableDb();
            Batiment bat = daoSession.getBatimentDao().load(batId);
            if(bat!=null){
                result= ModelMapper.MapTo(bat);
            }
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to get data from the database : "+ex.getMessage());
            throw  new ManagerException("<> unable to get data from the database : ",ex);
        }
        return result;
    }

    @Override
    public LogementModel getLogementById(long logId) throws ManagerException {
        Log.i(MANAGERS, "Inside of getLogementById! logId:" + logId);
        LogementModel result=null;
        try {
            openReadableDb();
            Logement log = daoSession.getLogementDao().load(logId);
            if( log != null ){
                result= ModelMapper.MapToLogementModel(log);
            }
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <-> unable to get data from the database : "+ex.getMessage());
            throw  new ManagerException("<-> unable to get data from the database : ",ex);
        }
        return result;
    }

    /**
     * Liste des Batiments
     *
     * @param typeInventaire le code du type d'Inventaire
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    @Override
    public List<RowDataListModel> searchListBatByTypeInventaire(String typeInventaire) throws ManagerException {
        Log.i(MANAGERS, "Inside of searchListLogementByIdBatiment!");
        List<RowDataListModel> result=null;
        try {
            openReadableDb();
            List<Batiment> loges = daoSession.getBatimentDao().queryBuilder()
                    .where(BatimentDao.Properties.TypeInventaire.eq(typeInventaire)).list();
            result= ModelMapper.MapToRowsBatiment(context,loges);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to search List Bat By TypeInventaire : " + ex.getMessage());
            throw  new ManagerException("<> unable to  search List Bat By TypeInventaire :",ex);
        }
        return result;
    }

    /**
     * Liste des Batiments
     *
     * @param typeInventaire  le code du type d'Inventaire
     * @param idInventaireSDE le code de l'Inventaire
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    @Override
    public List<RowDataListModel> searchListBatByTypeInventaire_ByIdInventaireSDE(String typeInventaire, long idInventaireSDE) throws ManagerException {
        Log.i(MANAGERS, "Inside of searchListBatByTypeInventaire_ByIdInventaireSDE! / typeInventaire:"+typeInventaire +" / idInventaireSDE:"+typeInventaire);
        List<RowDataListModel> result=null;
        try {
            openReadableDb();
            List<Batiment> batimentList = daoSession.getBatimentDao().queryBuilder()
                    .where(BatimentDao.Properties.TypeInventaire.eq(typeInventaire))
                    .where(BatimentDao.Properties.InventaireId.eq(idInventaireSDE)).list();
            result= ModelMapper.MapToRowsBatiment(context, batimentList);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to search List Bat By TypeInventaire By IdInventaireSDE: "+ex.getMessage());
            throw  new ManagerException("<> unable to search List Bat By TypeInventaire By IdInventaireSDE:",ex);
        }
        return result;
    }

    /**
     * Liste des Logements par Batiment
     *
     * @param batimentId le code du batiment
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    @Override
    public List<RowDataListModel> searchListLogementByIdBatiment(long batimentId) throws ManagerException {
        Log.i(MANAGERS, "Inside of searchListLogementByIdBatiment!");
        List<RowDataListModel> result=null;
        try {
            openReadableDb();
            List<Logement> loges=daoSession.getLogementDao().queryBuilder()
                    .where(LogementDao.Properties.BatimentId.eq(batimentId)).list();
            result= ModelMapper.MapToRowsLogement(loges, null);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to  get All Logement By IdBat : "+ex.getMessage());
            throw  new ManagerException("<> unable to get All Logement By IdBat :",ex);
        }
        return result;
    }

    @Override
    public List<RowDataListModel> searchListLogementByIdBatiment(BatimentModel batimentModel) throws ManagerException {
        Log.i(MANAGERS, "Inside of searchListLogementByIdBatiment!");
        List<RowDataListModel> result=null;
        try {
            if( batimentModel != null ) {
                openReadableDb();
                List<Logement> loges = daoSession.getLogementDao().queryBuilder()
                        .where(LogementDao.Properties.BatimentId.eq(batimentModel.getIdBatiment())).list();
                result = ModelMapper.MapToRowsLogement(loges, batimentModel);
                daoSession.clear();
            }
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to  get All Logement By IdBat : "+ex.getMessage());
            throw  new ManagerException("<> unable to get All Logement By IdBat :",ex);
        }
        return result;
    }

    /**
     * Liste des SDE     *
     *
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    @Override
    public List<RowDataListModel> searchAllListInventaireSDE() throws ManagerException {
        Log.i(MANAGERS, "Inside of searchAllListInventaireSDE!");
        List<RowDataListModel> result=null;
        try {
            openReadableDb();
            List<Inventaire> inventaireList = daoSession.getInventaireDao().queryBuilder().list();
            result= ModelMapper.MapToRowsInventaire(context, inventaireList);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to search All List Inventaire SDE: "+ex.getMessage());
            throw  new ManagerException("<> unable to search All List Inventaire SDE",ex);
        }
        return result;
    }

    @Override
    public List<RowDataListModel> searchListProfilUser(Shared_Preferences SPref) throws ManagerException {
        Log.i(MANAGERS, "Inside of searchListProfilUser!");
        List<RowDataListModel> result = null;
        try {
            openReadableDb();
            List<Personnel> personnels = daoSession.getPersonnelDao().queryBuilder().list();
            result = ModelMapper.MapToRows(SPref, personnels);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to search All  ListProfilUser: "+ex.getMessage());
            throw  new ManagerException("<> unable to search All  ListProfilUser ",ex);
        }
        return result;
    }

    /**
     * Liste des SDE par type Formulaire ( Rural ou Urbain ) *
     *
     * @param typeInventaire
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    @Override
    public List<RowDataListModel> searchAllListInventaireSDE_ByTypeInventaire(String typeInventaire) throws ManagerException {
        Log.i(MANAGERS, "Inside of searchAllListInventaireSDE_ByTypeInventaire! / typeInventaire:" + typeInventaire);
        List<RowDataListModel> result=null;
        try {
            openReadableDb();
            List<Inventaire> inventaireList = daoSession.getInventaireDao().queryBuilder()
                    .where(InventaireDao.Properties.TypeInventaire.eq(typeInventaire)).list();
            result= ModelMapper.MapToRowsInventaire(context, inventaireList);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to search All List Inventaire SDE By Type Inventaire: "+ex.getMessage());
            throw  new ManagerException("<> unable to search All List Inventaire SDE By Type Inventaire",ex);
        }
        return result;
    }

    /**
     * Retourne le nombre de logement par Batiment
     *
     * @param idBatiment le code du batiment
     * @return int
     */
    @Override
    public int countLogementByIdBatiment(long idBatiment) {
        Log.i(MANAGERS, "Inside of countLogementByIdBatiment!");
        long result=0;
        try {
            openReadableDb();
            result = daoSession.getLogementDao().queryBuilder()
                    .where(LogementDao.Properties.BatimentId.eq(idBatiment)).count();
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "<> unable to count Logement By IdBatiment : " + ex.getMessage());
        }
        return (int) result;
    }

    /**
     * Retourne le nombre de Batiment par typeInventaire
     *
     * @param typeInventaire le code du typeInventaire
     * @return int
     */
    @Override
    public long countBatimentByTypeInventaire(String typeInventaire) {
        Log.i(MANAGERS, "Inside of countBatimentByTypeInventaire!");
        long result=0;
        try {
            openReadableDb();
            result = daoSession.getBatimentDao().queryBuilder()
                    .where(BatimentDao.Properties.TypeInventaire.eq(typeInventaire)).count();
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "<> unable to count Batiment By TypeInventaire : " + ex.getMessage());
        }
        return result;
    }

    /**
     * Retourne le nombre de SDE
     *
     * @param idInventaire le code de la SDE
     * @return int
     */
    @Override
    public int countBatimentByIdInventaire(Long idInventaire) {
        Log.i(MANAGERS, "Inside of countBatimentByIdInventaire!");
        long result=0;
        try {
            openReadableDb();
            result = daoSession.getBatimentDao().queryBuilder()
                    .where(BatimentDao.Properties.InventaireId.eq(idInventaire)).count();
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "<> unable to count Batiment By IdInventaire: " + ex.getMessage());
        }
        return (int) result;
    }

    /**
     * Retourne le nombre d'SDE inventorier
     *
     * @return int
     */
    @Override
    public int countAllInventaireSDE() {
        Log.i(MANAGERS, "Inside of countAllInventaireSDE!");
        long result=0;
        try {
            openReadableDb();
            result = daoSession.getInventaireDao().queryBuilder().count();
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "<> unable to count All Inventaire SDE : " + ex.getMessage());
        }
        return (int) result;
    }

    @Override
    public synchronized int countAllDepartement() {
        Log.i(MANAGERS, "Inside of countAllDepartement!");
        long result=0;
        try {
            openReadableDb();
            result = daoSession.getDepartementDao().queryBuilder().count();
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "<> unable to count All Departement : " + ex.getMessage());
        }
        return (int) result;
    }

    @Override
    public DepartementModel getDepartementById(String deptId) throws ManagerException {
        Log.i(MANAGERS, "Inside of getDepartementById!");
        DepartementModel result = null;
        try {
            openReadableDb();
            Departement dept = daoSession.getDepartementDao().queryBuilder().where(
                    DepartementDao.Properties.DeptId.eq(deptId)).unique();
            if(dept!=null){
                result= ModelMapper.MapTo(dept);
            }
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to get Departement By Id: " + ex.getMessage());
            throw  new ManagerException("<> unable to get Departement By Id : ",ex);
        }
        return result;
    }

    @Override
    public DepartementModel GetDepartementById(String departementId) {
        DepartementModel departementModel = null;
        try {
            departementModel =  getDepartementById(departementId);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
        return departementModel;
    }

    @Override
    public CommuneModel getCommuneById(String comId) throws ManagerException {
        Log.i(MANAGERS, "Inside of getCommuneById!");
        CommuneModel result = null;
        try {
            openReadableDb();
            Commune com = daoSession.getCommuneDao().queryBuilder().where(
                    CommuneDao.Properties.ComId.eq(comId)).unique();
            if(com!=null){
                result= ModelMapper.MapTo(com);
            }
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to get Commune By Id: " + ex.getMessage());
            throw  new ManagerException("<> unable to get Commune By Id : ",ex);
        }
        return result;
    }

    @Override
    public CommuneModel GetCommuneById(String comId) {
        CommuneModel communeModel = null;
        try {
            communeModel =  getCommuneById(comId);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
        return communeModel;
    }

    @Override
    public VqseModel getVqseById(String vqseId) throws ManagerException {
        Log.i(MANAGERS, "Inside of getVqseById!");
        VqseModel result = null;
        try {
            openReadableDb();
            Vqse vqse = daoSession.getVqseDao().queryBuilder().where(
                    VqseDao.Properties.VqseId.eq(vqseId)).unique();
            if(vqse!=null){
                result= ModelMapper.MapTo(vqse);
            }
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to get Departement By Id: " + ex.getMessage());
            throw  new ManagerException("<> unable to get Departement By Id : ",ex);
        }
        return result;
    }

    @Override
    public VqseModel GetVqseById(String vqseId) {
        VqseModel vqseModel = null;
        try {
            vqseModel =  getVqseById(vqseId);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
        return vqseModel;
    }

    @Override
    public void closeDbConnections(){
       closeConnections();
        if (instance != null) {
            instance = null;
        }
    }
    //endregion

    //region additional managers
    //endregion
}
