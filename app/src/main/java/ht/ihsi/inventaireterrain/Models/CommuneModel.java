package ht.ihsi.inventaireterrain.Models;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class CommuneModel extends BaseModel{

    private String ComId;
    private String ComNom;
    private String DeptId;

    public CommuneModel() {
    }
    public CommuneModel(String ComId, String ComNom, String DeptId) {
        this.ComId = ComId;
        this.ComNom = ComNom;
        this.DeptId = DeptId;
    }

    //region CommuneModel getters and setters

    public String getComId() {
        return ComId;
    }

    public void setComId(String comId) {
        ComId = comId;
    }

    public String getComNom() {
        return ComNom;
    }

    public void setComNom(String comNom) {
        ComNom = comNom;
    }

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String deptId) {
        DeptId = deptId;
    }

    @Override
    public String toString() {
        return this.ComNom.toString();
    }
    //endregion
}
