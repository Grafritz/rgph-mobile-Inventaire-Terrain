package ht.ihsi.inventaireterrain.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import ht.ihsi.inventaireterrain.Backend.Batiment;
import ht.ihsi.inventaireterrain.Backend.BatimentDao;
import ht.ihsi.inventaireterrain.Backend.DaoSession;
import ht.ihsi.inventaireterrain.Backend.Inventaire;
import ht.ihsi.inventaireterrain.Backend.InventaireDao;
import ht.ihsi.inventaireterrain.Backend.Logement;
import ht.ihsi.inventaireterrain.Backend.LogementDao;
import ht.ihsi.inventaireterrain.Backend.Personnel;
import ht.ihsi.inventaireterrain.Backend.PersonnelDao;
import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Exceptions.ManagerException;
import ht.ihsi.inventaireterrain.Exceptions.TextEmptyException;
import ht.ihsi.inventaireterrain.Mappers.ModelMapper;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.InventaireModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.PersonnelModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.TypeSafeConversion;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class CURecordMngrImpl extends AbstractDatabaseManager implements CURecordMngr {

    private static CURecordMngrImpl instance;

    public CURecordMngrImpl(final Context content) {
        super(content);
    }

    //region required methods
    public static CURecordMngrImpl getInstance(Context context) {

        if (instance == null) {
            instance = new CURecordMngrImpl(context);
        }

        return instance;
    }

    public DaoSession getDaoSession() {
        DaoSession session = daoSession;
        return session;
    }

    @Override
    public void closeDbConnections() {
        closeConnections();
        if (instance != null) {
            instance = null;
        }
    }
    //endregion

    //region database managers
    /**
     * Save a new batiment
     *
     * @param batimentModel the object Inventaire Batiment
     * @return BatimentModel
     * @throws ManagerException
     */
    @Override
    public BatimentModel saveBatiment(BatimentModel batimentModel) throws ManagerException {
        if (batimentModel != null) {
            openWritableDb();
            BatimentDao inventBatDao = daoSession.getBatimentDao();
            long idBat = inventBatDao.insert(ModelMapper.MapTo(batimentModel));
            if (idBat != 0) {
                Batiment bat = inventBatDao.load(idBat);
                Log.d(ToastUtility.TAG, "saveBatiment / Insert ID:" + bat.getIdBatiment() + " / NoBatiment:" + bat.getNoBatiment() + " / SDE: " + bat.getCodeSDE() +" / HabitationAncienNom: " + bat.getAdrBat_HabitationAncienNom());
                batimentModel.setIdBatiment(idBat);
                daoSession.clear();
                return batimentModel;
            } else {
                throw new ManagerException("Error while Inserting the Batiment ");
            }
        } else {
            Log.d(ToastUtility.TAG + "saveBatiment", " BatimentModel is null ");
        }
        return null;
    }

    @Override
    public BatimentModel SaveBatiment(long id, BatimentModel batimentModel) throws ManagerException {
        if( id <= 0){
            batimentModel = saveBatiment(batimentModel);
        }else{
            batimentModel.setIdBatiment(id);
            batimentModel = updateBatiment(batimentModel);
        }
        return batimentModel;
    }

    /**
     * Save a new Logement
     *
     * @param logementModel the object LogementModel
     * @return LogementModel
     * @throws ManagerException
     */
    @Override
    public LogementModel saveLogement(LogementModel logementModel) throws ManagerException {
        if (logementModel != null) {
            openWritableDb();
            LogementDao inventBatDao = daoSession.getLogementDao();
            long inventaireId = inventBatDao.insert(ModelMapper.MapTo(logementModel));
            if ( inventaireId != 0 ) {
                Logement bat = inventBatDao.load(inventaireId);
                Log.d(ToastUtility.TAG, "saveLogement / Insert ID:" + bat.getIdLogement() );
                logementModel.setIdLogement(inventaireId);
                daoSession.clear();
                return logementModel;
            } else {
                throw new ManagerException("Error while Inserting the Logement ");
            }
        } else {
            Log.d(ToastUtility.TAG + "saveLogement", "LogementModel is null ");
        }
        return null;
    }

    @Override
    public LogementModel SaveLogement(long id, LogementModel logementModel) throws ManagerException {
        if( id <= 0){
            logementModel = saveLogement(logementModel);
        } else{
            logementModel.setIdLogement(id);
            logementModel = updateLogement(logementModel);
        }
        return logementModel;
    }

    /**
     * Save a new inventaire
     *
     * @param inventaireModel the object InventaireModel
     * @return InventaireModel
     * @throws ManagerException
     */
    @Override
    public InventaireModel saveInventaire(InventaireModel inventaireModel) throws ManagerException {
        if (inventaireModel != null) {
            openWritableDb();
            InventaireDao inventBatDao = daoSession.getInventaireDao();
            long inventaireId = inventBatDao.insert(ModelMapper.MapTo(inventaireModel));
            if ( inventaireId != 0 ) {
                Inventaire bat = inventBatDao.load(inventaireId);
                Log.d(ToastUtility.TAG, "saveInventaire / Insert ID:" + bat.getIdInventaire() );
                inventaireModel.setIdInventaire(inventaireId);
                daoSession.clear();
                return inventaireModel;
            } else {
                throw new ManagerException("Error while Inserting the Inventaire ");
            }
        } else {
            Log.d(ToastUtility.TAG + "saveInventaire", "InventaireModel is null ");
        }
        return null;
    }

    @Override
    public InventaireModel SaveInventaire(long id, InventaireModel inventaireModel) throws ManagerException, TextEmptyException {
        try {
            if (id <= 0) {
                Validation(inventaireModel);
                inventaireModel = saveInventaire(inventaireModel);
            } else if (id > 0) {
                inventaireModel.setIdInventaire(id);
                inventaireModel = updateInventaire(inventaireModel);
            }
        }catch (TextEmptyException ex){
            throw ex;
        }
        return inventaireModel;
    }

    @Override
    public PersonnelModel savePersonnel(PersonnelModel personnelModel) throws ManagerException {
        if ( personnelModel != null ) {
            openWritableDb();
            PersonnelDao personnelDao = daoSession.getPersonnelDao();
            long idpersonne = personnelDao.insert(ModelMapper.MapTo(personnelModel));
            if ( idpersonne != 0 ) {
                Personnel bat = personnelDao.load(idpersonne);
                Log.d(ToastUtility.TAG, "savePersonnelModel / Insert ID:" + bat.getPersId() );
                personnelModel.setPersId(idpersonne);
                daoSession.clear();
                return personnelModel;
            } else {
                throw new ManagerException("Error while Inserting the personnelModel ");
            }
        } else {
            Log.d(ToastUtility.TAG + "savePersonnel", "personnel is null ");
        }
        return null;
    }

    @Override
    public PersonnelModel SavePersonnel(long id, PersonnelModel personnelModel) throws ManagerException, TextEmptyException {
        try {
            if (id <= 0) {
                Validation(personnelModel);
                personnelModel = savePersonnel(personnelModel);
            } else if (id > 0) {
                personnelModel.setPersId(id);
                personnelModel = updatePersonnel(personnelModel);
            }
        }catch (TextEmptyException ex){
            throw ex;
        }
        return personnelModel;
    }

    private void Validation(InventaireModel inventaireModel) throws TextEmptyException {
        if( GetInventaireByCodeSDE(inventaireModel) ){
            throw new TextEmptyException("Ce code SDE [ "+ inventaireModel.getCodeSDE() +" ] est déjà enregistré");
        }
    }

    private void Validation(PersonnelModel personnelModel) throws TextEmptyException {
        if( GetPersonnelByCompteUtilisateur(personnelModel) ){
            throw new TextEmptyException("Ce Compte Utilisateur [ "+ personnelModel.getNomUtilisateur() +" ] est déjà enregistré");
        }
    }

    private boolean GetPersonnelByCompteUtilisateur(PersonnelModel personnelModel) {
        Log.i(MANAGERS, "Inside of GetPersonnelByCompteUtilisateur! / codeSDE :"+personnelModel.getPersId());
        boolean result = false;
        //try {
            openReadableDb();
        Personnel loges = daoSession.getPersonnelDao().queryBuilder()
                    .where(PersonnelDao.Properties.NomUtilisateur.eq(personnelModel.getNomUtilisateur())).unique();
            //result= MapToRowsBatiment(loges);
            daoSession.clear();
            if (loges == null) {
                result = false;
            }else if (loges != null) {
                long idIneventaire = 0;
                if ( personnelModel.getPersId() > 0 && personnelModel != null ){
                    idIneventaire = personnelModel.getPersId();
                }
                if( idIneventaire == 0 ){
                    result = true;
                }else if (loges.getPersId() != idIneventaire ){
                    result = true;
                }else{
                    result = false;
                }
            }
        //} catch (Exception ex) {
        //    throw ex;
        //}
       return result;
    }

    private boolean GetInventaireByCodeSDE(InventaireModel inv) {
        Log.i(MANAGERS, "Inside of GetInventaireByCodeSDE! / codeSDE :"+inv.getCodeSDE());
        boolean result = false;
        //try {
        openReadableDb();
        Inventaire loges = daoSession.getInventaireDao().queryBuilder()
                .where(InventaireDao.Properties.CodeSDE.eq(inv.getCodeSDE())).unique();
        //result= MapToRowsBatiment(loges);
        daoSession.clear();
        if (loges == null) {
            result = false;
        }else if (loges != null) {
            long idIneventaire = 0;
            if ( inv.getIdInventaire() != null ){
                idIneventaire = inv.getIdInventaire();
            }
            if( idIneventaire == 0 ){
                result = true;
            }else if (loges.getIdInventaire() != idIneventaire ){
                result = true;
            }else{
                result = false;
            }
        }
        //} catch (Exception ex) {
        //    throw ex;
        //}
        return result;
    }

    /**
     * Save a new entity
     *
     * @param entite
     * @return
     */
    @Override
    public synchronized <T> T saveEntity(T entite) throws ManagerException {
        if (entite.getClass() == BatimentModel.class) {
            saveBatiment((BatimentModel) entite);
            return entite;
        }
        if (entite.getClass() == LogementModel.class) {
            saveLogement((LogementModel) entite);
            return entite;
        }
        if (entite.getClass() == InventaireModel.class) {
            saveInventaire((InventaireModel) entite);
            return entite;
        }
        return null;
    }

    /**
     * Update a inventaire Batiment
     *
     * @param batiment
     * @return BatimentModel
     * @throws ManagerException
     */
    @Override
    public BatimentModel updateBatiment(BatimentModel batiment) throws ManagerException {
        if (batiment != null) {
            openReadableDb();
            BatimentDao batimentDao = daoSession.getBatimentDao();
            Log.d(ToastUtility.TAG, "BATIMENT UPDATING / Batiment Insert ID:" + batiment.getIdBatiment() );
            Batiment bat = batimentDao.load(batiment.getIdBatiment());
            if (bat != null) {
                bat = ModelMapper.MapTo(batiment);
                bat.setIdBatiment(batiment.getIdBatiment());
                try {
                    batimentDao.update(bat);
                    Log.d(ToastUtility.TAG, "BATIMENT UPDATED / Batiment Insert ID:" + bat.getIdBatiment());
                    daoSession.clear();
                } catch (Exception ex) {
                    throw new ManagerException("" + ex.getMessage());
                }
                daoSession.clear();
                return batiment;
            }
        }
        return null;
    }

    /**
     * Update a Logement
     *
     * @param logement
     * @return LogementModel
     * @throws ManagerException
     */
    @Override
    public LogementModel updateLogement(LogementModel logement) throws ManagerException {
        if (logement != null) {
            openReadableDb();
            LogementDao logementDao = daoSession.getLogementDao();
            Logement log=logementDao.load(logement.getIdLogement());
            Log.d(ToastUtility.TAG, "LOGEMENT B. UPDATING / BID:"+logement.getBatimentId()+" / LOG:"+logement.getIdLogement() );
            if (log != null) {
                log = ModelMapper.MapTo(logement);
                log.setIdLogement(logement.getIdLogement());
                try {
                    logementDao.update(log);
                    Log.d(ToastUtility.TAG, "LOGEMENT B. UPDATED / BID:" + log.getBatimentId() + " / LOG:" + log.getIdLogement());
                    daoSession.clear();
                    return ModelMapper.MapToLogementModel(log);
                } catch (Exception ex) {
                    throw new ManagerException("Manager Exception: " + ex.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Update a Inventaire
     *
     * @param inventaireModel
     * @return InventaireModel
     * @throws ManagerException
     */
    @Override
    public InventaireModel updateInventaire(InventaireModel inventaireModel) throws ManagerException {
        if (inventaireModel != null) {
            openReadableDb();
            InventaireDao inventaireDao = daoSession.getInventaireDao();
            Inventaire inventaire = inventaireDao.load(inventaireModel.getIdInventaire());
            Log.d(ToastUtility.TAG, "INVENTAIRE B. UPDATING / BID:"+inventaireModel.getIdInventaire()  );
            if (inventaire != null) {
                inventaire = ModelMapper.MapTo(inventaireModel);
                inventaire.setIdInventaire(inventaireModel.getIdInventaire());
                try {
                    inventaireDao.update(inventaire);
                    Log.d(ToastUtility.TAG, "INVENTAIRE UPDATING / BID:"+inventaireModel.getIdInventaire()  );
                    daoSession.clear();
                    return ModelMapper.MapToInventaireModel(inventaire);
                } catch (Exception ex) {
                    throw new ManagerException("Manager Exception: " + ex.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public PersonnelModel updatePersonnel(PersonnelModel personnelModel) throws ManagerException {
        if (personnelModel != null) {
            openReadableDb();
            PersonnelDao personnelDao = daoSession.getPersonnelDao();
            Personnel personnel = personnelDao.load(personnelModel.getPersId());
            Log.d(ToastUtility.TAG, "INVENTAIRE B. UPDATING / BID:"+personnelModel.getPersId()  );
            if (personnel != null) {
                personnel = ModelMapper.MapTo(personnelModel);
                personnel.setPersId(personnelModel.getPersId());
                try {
                    personnelDao.update(personnel);
                    Log.d(ToastUtility.TAG, "PERSONNEL UPDATING / BID:"+personnelModel.getPersId()  );
                    daoSession.clear();
                    return ModelMapper.MapTo(personnel);
                } catch (Exception ex) {
                    throw new ManagerException("Manager Exception: " + ex.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public PersonnelModel updateAllPersonnel(long persId) throws ManagerException {
        try {
            openReadableDb();
            SQLiteDatabase db = getDatabase();

            ContentValues value = new ContentValues();
            value.put(PersonnelDao.Properties.EstActif.columnName, 0);

            db.update(PersonnelDao.TABLENAME, value, "" + PersonnelDao.Properties.PersId.columnName + " <> ?"
                    +" AND " + PersonnelDao.Properties.PersId.columnName + " <> 1"
                    +" AND " + PersonnelDao.Properties.PersId.columnName + " <> 2"
                    +" AND " + PersonnelDao.Properties.PersId.columnName + " <> 3"
                    +" AND " + PersonnelDao.Properties.PersId.columnName + " <> 4"
                    +" AND " + PersonnelDao.Properties.PersId.columnName + " <> 5"
                    +" AND " + PersonnelDao.Properties.ProfileId.columnName + " = " + Constant.COMPTE_AGENT,
                    new String[] { String.valueOf(persId) });

            db.close();
            daoSession.clear();
        } catch (Exception ex) {
            Log.i(MANAGERS, "<> unable to get data from the database " + ex.getMessage());
            throw new ManagerException("<> unable to get data from the database ", ex);
        }
        return null;
    }

    /**
     * Update an entity
     *
     * @param entite
     * @return T entity.
     */
    @Override
    public synchronized <T> T updateEntity(T entite) throws ManagerException {
        try {
            if (entite.getClass() == BatimentModel.class) {
                //updateInventaireBatiment((BatimentModel) entite);
                return entite;
            }
        } catch (Exception ex) {
            throw new ManagerException("" + ex.getMessage());
        }
        return null;
    }

    //endregion
}
