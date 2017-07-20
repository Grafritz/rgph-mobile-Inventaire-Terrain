package ht.ihsi.inventaireterrain.Managers;

import java.util.List;

import ht.ihsi.inventaireterrain.Exceptions.ManagerException;
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
public  interface FormDataMngr {

     void closeDbConnections();

     /**
     * Get all the departements.Returns a pair of key and value.
     * The Key is the id of the country and the value is the name.
     *
     * @return List<KeyValueModel>
     * @throws ManagerException
     */
     List<KeyValueModel> getAllDepartement() throws ManagerException;

    /**
     * Get all the comunnes by departement. Returns a pair of key and value.
     * The Key is the id of the country and the value is the name.
     *
     * @param deptId the id of the departement
     * @return List<CommuneModel>
     * @throws ManagerException
     */
     List<CommuneModel> getAllCommuneByIdDept(String deptId) throws ManagerException;

    //CommuneModel getCommuneById(String comId) throws ManagerException;

    /**
     * Get all the VQSE by Commune. Returns a pair of key and value.
     * The Key is the id of the country and the value is the name.
     *
     * @param comId the id of a Commune
     * @return List<VqseModel>
     * @throws ManagerException
     */
     List<VqseModel> getAllVqseByIdCom(String comId) throws ManagerException;

    List<CodeSDEModel> getAllCodeSDE_ByIdVqse(String vqseId) throws ManagerException;

    //VqseModel getVqseById(String vqseId) throws ManagerException;

    /**
     *
     * @param NomUtilisateur
     * @param MotDePasse
     * @return PersonnelModel
     * @throws ManagerException
     */
    PersonnelModel getPersonnelInfo(String NomUtilisateur, String MotDePasse) throws ManagerException;

}
