package ht.ihsi.inventaireterrain.Models;

/**
 * Created by JFDuverseau on 7/13/2016.
 */
public class DepartementModel extends BaseModel {

    private String DeptId;
    private String DeptNom;

    public DepartementModel() {
    }

    //region DepartementModel getters and setters

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String deptId) {
        DeptId = deptId;
    }

    public String getDeptNom() {
        return DeptNom;
    }

    public void setDeptNom(String deptNom) {
        DeptNom = deptNom;
    }

    //endregion
}
