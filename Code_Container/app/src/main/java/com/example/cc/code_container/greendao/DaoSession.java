package com.example.cc.code_container.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.cc.code_container.Code;

import com.example.cc.code_container.greendao.CodeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig codeDaoConfig;

    private final CodeDao codeDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        codeDaoConfig = daoConfigMap.get(CodeDao.class).clone();
        codeDaoConfig.initIdentityScope(type);

        codeDao = new CodeDao(codeDaoConfig, this);

        registerDao(Code.class, codeDao);
    }
    
    public void clear() {
        codeDaoConfig.getIdentityScope().clear();
    }

    public CodeDao getCodeDao() {
        return codeDao;
    }

}
