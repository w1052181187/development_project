package com.appmf.net.appmf.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.appmf.net.appmf.dao.AdStatisticsModel;

import com.appmf.net.appmf.dao.AdStatisticsModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig adStatisticsModelDaoConfig;

    private final AdStatisticsModelDao adStatisticsModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        adStatisticsModelDaoConfig = daoConfigMap.get(AdStatisticsModelDao.class).clone();
        adStatisticsModelDaoConfig.initIdentityScope(type);

        adStatisticsModelDao = new AdStatisticsModelDao(adStatisticsModelDaoConfig, this);

        registerDao(AdStatisticsModel.class, adStatisticsModelDao);
    }
    
    public void clear() {
        adStatisticsModelDaoConfig.clearIdentityScope();
    }

    public AdStatisticsModelDao getAdStatisticsModelDao() {
        return adStatisticsModelDao;
    }

}