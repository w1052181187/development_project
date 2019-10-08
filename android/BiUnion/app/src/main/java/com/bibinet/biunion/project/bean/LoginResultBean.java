package com.bibinet.biunion.project.bean;

import com.bibinet.biunion.project.models.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bibinet on 2017-6-12.
 */

public class LoginResultBean extends BaseModel {

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable{

        private String image;
        private EnterpriseBean enterprise;
        private String name;
        private String userId;

        private String emchatUserName;
        private String emchatPassword;
        ProtalMember protalMember;
        private int liveFlag;

        public ProtalMember getProtalMember() {
            return protalMember;
        }

        public void setProtalMember(ProtalMember protalMember) {
            this.protalMember = protalMember;
        }

        public String getEmchatUserName() {
            return emchatUserName;
        }

        public void setEmchatUserName(String emchatUserName) {
            this.emchatUserName = emchatUserName;
        }

        public String getEmchatPassword() {
            return emchatPassword;
        }

        public void setEmchatPassword(String emchatPassword) {
            this.emchatPassword = emchatPassword;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public EnterpriseBean getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(EnterpriseBean enterprise) {
            this.enterprise = enterprise;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getLiveFlag() {
            return liveFlag;
        }

        public void setLiveFlag(int liveFlag) {
            this.liveFlag = liveFlag;
        }

        public static class EnterpriseBean implements Serializable{

            private String businessLicenseCardNo;
            private String uscCode;
            private String businessLicenseName;
            private String contactName;
            private String industry;
            private String contactCellphone;
            private String enterpriseCode;
            private String region;
            private String addr;
            private String enterpriseName;
            private String tradingCertificateURL;
            private String legalPersonFrontURL;
            private String legalPersonReverseURL;
            private String licenceOpenAccountURL;


            //图片上传用
//            private String thumbnailFileInfoId;
//            private String originalFileInfoId;
            private ArrayList<Certificate> certificates;

            private int status;
            private String checkOptions;

            public String getLegalPersonFrontURL() {
                return legalPersonFrontURL;
            }

            public void setLegalPersonFrontURL(String legalPersonFrontURL) {
                this.legalPersonFrontURL = legalPersonFrontURL;
            }

            public String getLegalPersonReverseURL() {
                return legalPersonReverseURL;
            }

            public void setLegalPersonReverseURL(String legalPersonReverseURL) {
                this.legalPersonReverseURL = legalPersonReverseURL;
            }

            public String getLicenceOpenAccountURL() {
                return licenceOpenAccountURL;
            }

            public void setLicenceOpenAccountURL(String licenceOpenAccountURL) {
                this.licenceOpenAccountURL = licenceOpenAccountURL;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCheckOptions() {
                return checkOptions;
            }

            public void setCheckOptions(String checkOptions) {
                this.checkOptions = checkOptions;
            }

            public String getBusinessLicenseCardNo() {
                return businessLicenseCardNo;
            }

            public void setBusinessLicenseCardNo(String businessLicenseCardNo) {
                this.businessLicenseCardNo = businessLicenseCardNo;
            }


            public String getBusinessLicenseName() {
                return businessLicenseName;
            }

            public void setBusinessLicenseName(String businessLicenseName) {
                this.businessLicenseName = businessLicenseName;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
            }

            public String getIndustry() {
                return industry;
            }

            public void setIndustry(String industry) {
                this.industry = industry;
            }

            public String getContactCellphone() {
                return contactCellphone;
            }

            public void setContactCellphone(String contactCellphone) {
                this.contactCellphone = contactCellphone;
            }

            public String getEnterpriseCode() {
                return enterpriseCode;
            }

            public void setEnterpriseCode(String enterpriseCode) {
                this.enterpriseCode = enterpriseCode;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getEnterpriseName() {
                return enterpriseName;
            }

            public void setEnterpriseName(String enterpriseName) {
                this.enterpriseName = enterpriseName;
            }

            public String getTradingCertificateURL() {
                return tradingCertificateURL;
            }

            public void setTradingCertificateURL(String tradingCertificateURL) {
                this.tradingCertificateURL = tradingCertificateURL;
            }

            public String getUscCode() {
                return uscCode;
            }

            public void setUscCode(String uscCode) {
                this.uscCode = uscCode;
            }

            public ArrayList<Certificate> getCertificates() {
                return certificates;
            }

            public void setCertificates(ArrayList<Certificate> certificates) {
                this.certificates = certificates;
            }
        }

        public static class Certificate {
            private String enterpriseId;
            private CompanyUpImageBean.FileInfoBean originalFileInfo;
            private CompanyUpImageBean.FileInfoBean thumbnailFileInfo;
            private int type;

            public String getEnterpriseId() {
                return enterpriseId;
            }

            public void setEnterpriseId(String enterpriseId) {
                this.enterpriseId = enterpriseId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public CompanyUpImageBean.FileInfoBean getOriginalFileInfo() {
                return originalFileInfo;
            }

            public void setOriginalFileInfo(CompanyUpImageBean.FileInfoBean originalFileInfo) {
                this.originalFileInfo = originalFileInfo;
            }

            public CompanyUpImageBean.FileInfoBean getThumbnailFileInfo() {
                return thumbnailFileInfo;
            }

            public void setThumbnailFileInfo(CompanyUpImageBean.FileInfoBean thumbnailFileInfo) {
                this.thumbnailFileInfo = thumbnailFileInfo;
            }
        }
    }

    public static class ProtalMember implements Serializable{
        String objectId;
        String code;
        String protalUserId;
        int type;
        long createtDate;
        long lastUpdateDate;
        int isValid;
        int scope;
        int status;
        int mode;
        int isTel;

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getProtalUserId() {
            return protalUserId;
        }

        public void setProtalUserId(String protalUserId) {
            this.protalUserId = protalUserId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getCreatetDate() {
            return createtDate;
        }

        public void setCreatetDate(long createtDate) {
            this.createtDate = createtDate;
        }

        public long getLastUpdateDate() {
            return lastUpdateDate;
        }

        public void setLastUpdateDate(long lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
        }

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }

        public int getScope() {
            return scope;
        }

        public void setScope(int scope) {
            this.scope = scope;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public int getIsTel() {
            return isTel;
        }

        public void setIsTel(int isTel) {
            this.isTel = isTel;
        }
    }
}
