package ht.ihsi.inventaireterrain.Managers;

import ht.ihsi.inventaireterrain.Backend.Batiment;
import ht.ihsi.inventaireterrain.Backend.Logement;
import ht.ihsi.inventaireterrain.Exceptions.ManagerException;
import ht.ihsi.inventaireterrain.Exceptions.TextEmptyException;
import ht.ihsi.inventaireterrain.Models.BatimentModel;
import ht.ihsi.inventaireterrain.Models.InventaireModel;
import ht.ihsi.inventaireterrain.Models.LogementModel;
import ht.ihsi.inventaireterrain.Models.PersonnelModel;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public interface CURecordMngr {
    /**
     * Save a new Batiment
     *
     * @param batimentModel the object Inventaire Batiment
     * @return BatimentModel
     * @throws ManagerException
     */
    BatimentModel saveBatiment(BatimentModel batimentModel) throws ManagerException;

    BatimentModel SaveBatiment(long id, BatimentModel batimentModel) throws ManagerException;

    /**
     * Save a new Logement
     *
     * @param logementModel the object LogementModel
     * @return LogementModel
     * @throws ManagerException
     */
    LogementModel saveLogement(LogementModel logementModel) throws ManagerException;

    LogementModel SaveLogement(long id, LogementModel logementModel) throws ManagerException;

    /**
     * Save a new inventaire
     *
     * @param inventaireModel the object InventaireModel
     * @return InventaireModel
     * @throws ManagerException
     */
    InventaireModel saveInventaire(InventaireModel inventaireModel) throws ManagerException;

    InventaireModel SaveInventaire(long id, InventaireModel inventaireModel) throws ManagerException, TextEmptyException;

    PersonnelModel savePersonnel(PersonnelModel personnelModel) throws ManagerException;

    PersonnelModel SavePersonnel(long id, PersonnelModel personnelModel) throws ManagerException, TextEmptyException;

     /**
     * Save a new entity
     *
     * @param entite
     * @param <T>    the type of the entity
     * @return
     */
    <T> T saveEntity(T entite) throws ManagerException;

    /**
     * Update a Batiment
     *
     * @param batiment
     * @return BatimentModel
     * @throws ManagerException
     */
    BatimentModel updateBatiment(BatimentModel batiment) throws ManagerException;

    /**
     * Update a Logement
     *
     * @param logement
     * @return LogementModel
     * @throws ManagerException
     */
    LogementModel updateLogement(LogementModel logement) throws ManagerException;

    /**
     * Update a Inventaire
     *
     * @param inventaireModel
     * @return InventaireModel
     * @throws ManagerException
     */
    InventaireModel updateInventaire(InventaireModel inventaireModel) throws ManagerException;

    PersonnelModel updatePersonnel(PersonnelModel personnelModel) throws ManagerException;

    PersonnelModel updateAllPersonnel(long persId) throws ManagerException;

    /**
     * Update an entity
     *
     * @param entite
     * @param <T>    the entity type.
     * @return T entity.
     */
    <T> T updateEntity(T entite) throws ManagerException;

    void closeDbConnections();




}
