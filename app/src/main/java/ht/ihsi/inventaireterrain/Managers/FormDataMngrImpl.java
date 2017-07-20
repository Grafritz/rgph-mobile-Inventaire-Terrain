package ht.ihsi.inventaireterrain.Managers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import ht.ihsi.inventaireterrain.Backend.CodeSDE;
import ht.ihsi.inventaireterrain.Backend.CodeSDEDao;
import ht.ihsi.inventaireterrain.Backend.Commune;
import ht.ihsi.inventaireterrain.Backend.CommuneDao;
import ht.ihsi.inventaireterrain.Backend.Departement;
import ht.ihsi.inventaireterrain.Backend.Logement;
import ht.ihsi.inventaireterrain.Backend.LogementDao;
import ht.ihsi.inventaireterrain.Backend.Personnel;
import ht.ihsi.inventaireterrain.Backend.PersonnelDao;
import ht.ihsi.inventaireterrain.Backend.Vqse;
import ht.ihsi.inventaireterrain.Backend.VqseDao;
import ht.ihsi.inventaireterrain.Exceptions.ManagerException;
import ht.ihsi.inventaireterrain.Mappers.ModelMapper;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.CodeSDEModel;
import ht.ihsi.inventaireterrain.Models.CommuneModel;
import ht.ihsi.inventaireterrain.Models.KeyValueModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.PersonnelModel;
import ht.ihsi.inventaireterrain.Models.VqseModel;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class FormDataMngrImpl extends AbstractDatabaseManager implements FormDataMngr {

    private static FormDataMngrImpl instance;

    public FormDataMngrImpl(final Context context) {
        super(context);
    }

    //region required methods
    public static FormDataMngrImpl getInstance(Context context){
        if (instance == null) {
            instance = new FormDataMngrImpl(context);
        }
        return instance;
    }

    @Override
    public void closeDbConnections(){
      closeConnections();
        if (instance != null) {
            instance = null;
        }
    }

    //endregion

    //region additional database managers

    @Override
    public synchronized List<KeyValueModel> getAllDepartement()
            throws ManagerException {
        Log.i(MANAGERS, "Inside of getAllDepartement!");
        List<KeyValueModel> result=null;
        try {
            openReadableDb();
            List<Departement> depts=daoSession.getDepartementDao().loadAll();
            if(depts!=null && depts.size()>0){
                result=new ArrayList<KeyValueModel>();
                for(Departement dept: depts){
                    result.add(new KeyValueModel(dept.getDeptId(),dept.getDeptNom()));
                }
            }
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to load Departement data:" + ex.getMessage());
            throw new ManagerException("unable to load Departement data:",ex);
        }
        return result;
    }

    @Override
    public synchronized List<CommuneModel> getAllCommuneByIdDept(String deptId)
            throws ManagerException {
        Log.i(MANAGERS, "Inside of getAllCommuneByIdDept!");
        List<CommuneModel> result=null;
        try {
            openReadableDb();
            List<Commune> coms = daoSession.getCommuneDao().queryBuilder().where(
                    CommuneDao.Properties.DeptId.eq(deptId)).list();
            result = ModelMapper.MapToCommune(coms);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to load commune data" + ex.getMessage());
            throw new ManagerException("unable to load commune data",ex);
        }
        return result;
    }


    @Override
    public synchronized List<VqseModel> getAllVqseByIdCom(String comId)
            throws ManagerException {
        Log.i(MANAGERS, "Inside of getAllVqseByIdCom!");
        List<VqseModel> result=null;
        try {
            openReadableDb();
            List<Vqse> vqses = daoSession.getVqseDao().queryBuilder().where(
                    VqseDao.Properties.ComId.eq(comId)).list();
            result = ModelMapper.MapToVqse(vqses);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to load vqse data" + ex.getMessage());
            throw new ManagerException("unable to load vqse data",ex);
        }
        return result;
    }

    @Override
    public List<CodeSDEModel> getAllCodeSDE_ByIdVqse(String vqseId) throws ManagerException {
        Log.i(MANAGERS, "Inside of getAllCodeSDE_ByIdVqse! / vqseId:" + vqseId);
        List<CodeSDEModel> result=null;
        try {
            openReadableDb();
            List<CodeSDE> codeSDEList = daoSession.getCodeSDEDao().queryBuilder().where(
                    CodeSDEDao.Properties.VqseId.eq(vqseId)).list();
            result = ModelMapper.MapToCodeSDE(codeSDEList);
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to get All Code SDE By IdVqse : " + ex.getMessage());
            throw new ManagerException("unable to get All Code SDE By IdVqse : ",ex);
        }
        return result;
    }

    /**
     * @param NomUtilisateur
     * @param MotDePasse
     * @return List<PersonnelModel>
     * @throws ManagerException
     */
    @Override
    public PersonnelModel getPersonnelInfo(String NomUtilisateur, String MotDePasse) throws ManagerException {
        Log.i(MANAGERS, "Inside of getPersonnelInfo!");
        PersonnelModel result = null;
        try {
            openReadableDb();
            Personnel personnel = daoSession.getPersonnelDao().queryBuilder()
                    .where(PersonnelDao.Properties.NomUtilisateur.eq(NomUtilisateur))
                    .where(PersonnelDao.Properties.MotDePasse.eq(MotDePasse)).unique();
            if( personnel != null ){
                result = ModelMapper.MapTo(personnel);
            }
            daoSession.clear();
        }catch(Exception ex){
            Log.e(MANAGERS, "Exception <> unable to get Personne lInfo : " + ex.getMessage());
            throw  new ManagerException("<> unable to get Personnel Info : ",ex);
        }
        return result;
    }

    //endregion
}
