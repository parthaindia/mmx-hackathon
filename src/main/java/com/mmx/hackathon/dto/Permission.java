package com.mmx.hackathon.dto;

/**
 *
 * @author Manindar
 */
public class Permission {

    private Object _id;
    private String loginid;
    private String fileid;
    private String filename;
    private String extenstion;
    private String securitycode;
    private String status;
    private String createdate;
    private String updatedate;
    private String recieverid;

    public Object getId() {
        return _id;
    }

    public void setId(Object _id) {
        this._id = _id;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtenstion() {
        return extenstion;
    }

    public void setExtenstion(String extenstion) {
        this.extenstion = extenstion;
    }

    public String getSecuritycode() {
        return securitycode;
    }

    public void setSecuritycode(String securitycode) {
        this.securitycode = securitycode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getRecieverid() {
        return recieverid;
    }

    public void setRecieverid(String recieverid) {
        this.recieverid = recieverid;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

}
