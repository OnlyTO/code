package com.example.cc.code_container;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Code {
    @Id(autoincrement = true)
    private Long mId;
    private String mMainName;
    private String mTitle;
    private String mName;
    private String mCode;
    private String mDescription;
    private String letter;


    @Generated(hash = 1477070017)
    public Code(Long mId, String mMainName, String mTitle, String mName,
            String mCode, String mDescription, String letter) {
        this.mId = mId;
        this.mMainName = mMainName;
        this.mTitle = mTitle;
        this.mName = mName;
        this.mCode = mCode;
        this.mDescription = mDescription;
        this.letter = letter;
    }

    @Generated(hash = 364261929)
    public Code() {
    }
    

    public Long getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Long getMId() {
        return this.mId;
    }

    public void setMId(Long mId) {
        this.mId = mId;
    }

    public String getMTitle() {
        return this.mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMCode() {
        return this.mCode;
    }

    public void setMCode(String mCode) {
        this.mCode = mCode;
    }

    public String getMDescription() {
        return this.mDescription;
    }

    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getMMainName() {
        return this.mMainName;
    }

    public void setMMainName(String mMainName) {
        this.mMainName = mMainName;
    }

    public String getMName() {
        return this.mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }




    public String getLetter() {
        return this.letter;
    }




    public void setLetter(String letter) {
        this.letter = letter;
    }
}
