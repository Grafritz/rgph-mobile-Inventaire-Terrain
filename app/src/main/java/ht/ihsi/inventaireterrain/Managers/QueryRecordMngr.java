package ht.ihsi.inventaireterrain.Managers;

import java.util.Collection;
import java.util.List;

import ht.ihsi.inventaireterrain.Exceptions.ManagerException;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.CommuneModel;
import ht.ihsi.inventaireterrain.Models.DepartementModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.RowDataListModel;
import ht.ihsi.inventaireterrain.Models.VqseModel;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
 public interface QueryRecordMngr {

    /**
     * Get a batiment by its id.
     *
     * @param batId the id of batiment.
     * @return BatimentModel
     * @throws ManagerException
     */
    BatimentModel getBatimentById(long batId) throws ManagerException;

    LogementModel getLogementById(long logId) throws ManagerException;

   /**
     * Liste des Batiments
     *
    * @param typeInventaire le code du type d'Inventaire
    * @return List<RowDataListModel>
     * @throws ManagerException
     */
    List<RowDataListModel> searchListBatByTypeInventaire(String typeInventaire) throws ManagerException;
    /**
     * Liste des Batiments
     *
     * @param typeInventaire le code du type d'Inventaire
     * @param idInventaireSDE le code de l'Inventaire
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    List<RowDataListModel> searchListBatByTypeInventaire_ByIdInventaireSDE(String typeInventaire, long idInventaireSDE) throws ManagerException;

    /**
     * Liste des Logements par Batiment
     *
     * @param batimentId le code du batiment
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    List<RowDataListModel> searchListLogementByIdBatiment(long batimentId) throws ManagerException;

    List<RowDataListModel> searchListLogementByIdBatiment(BatimentModel batiment) throws ManagerException;

    /**
     * Liste des SDE     *
     * @return List<RowDataListModel>
     * @throws ManagerException
     */
    List<RowDataListModel> searchAllListInventaireSDE() throws ManagerException;

    List<RowDataListModel> searchListProfilUser(Shared_Preferences SPref) throws ManagerException;
   /**
    * Liste des SDE par type Formulaire ( Rural ou Urbain ) *
    * @return List<RowDataListModel>
    * @throws ManagerException
    */
   List<RowDataListModel> searchAllListInventaireSDE_ByTypeInventaire(String typeInventaire) throws ManagerException;

    /**
     * Retourne le nombre de logement par Batiment
     * @param idBatiment le code du batiment
     * @return int
     */
    int countLogementByIdBatiment(long idBatiment);

   /**
    * Retourne le nombre de Batiment par typeInventaire
    * @param typeInventaire le code du typeInventaire
    * @return int
    */
   long countBatimentByTypeInventaire(String typeInventaire);

    /**
     * Retourne le nombre de SDE
     * @param idInventaire le code de la SDE
     * @return int
     */
    int countBatimentByIdInventaire(Long idInventaire);

    /**
     * Retourne le nombre d'SDE inventorier
     * @return int
     */
    int countAllInventaireSDE();


    //region LOCALITE / ZONE

    int countAllDepartement();

    DepartementModel getDepartementById(String deptId) throws ManagerException;
    DepartementModel GetDepartementById(String departementId);

    CommuneModel getCommuneById(String comId) throws ManagerException;
    CommuneModel GetCommuneById(String comId);

    VqseModel getVqseById(String vqseId) throws ManagerException;
    VqseModel GetVqseById(String vqseId);
    //endregion
    void closeDbConnections();

}
