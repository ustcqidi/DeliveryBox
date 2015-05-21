package com.ustc.deliverybox.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tangnan on 15-1-10.
 */
public class UpdateInfo implements Parcelable {

    private int versionCode;
    private String versionName;
    private String downloadUrl;
    private String md5Sum;
    private String fileName;

    public UpdateInfo() {
    }

    public UpdateInfo(int versionCode, String versionName, String url,String md5) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.downloadUrl = url;
        this.md5Sum = md5;
        this.fileName = url.substring(url.lastIndexOf("/")+1);
    }

    private UpdateInfo(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        versionCode = in.readInt();
        versionName = in.readString();
        downloadUrl = in.readString();
        md5Sum = in.readString();
        this.fileName = in.readString();
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMd5Sum() {
        return md5Sum;
    }

    public void setMd5Sum(String md5Sum) {
        this.md5Sum = md5Sum;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static final Parcelable.Creator<UpdateInfo> CREATOR = new Parcelable.Creator<UpdateInfo>() {
        public UpdateInfo createFromParcel(Parcel in) {
            return new UpdateInfo(in);
        }

        public UpdateInfo[] newArray(int size) {
            return new UpdateInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(versionCode);
        out.writeString(versionName);
        out.writeString(downloadUrl);
        out.writeString(md5Sum);
        out.writeString(fileName);
    }

}
