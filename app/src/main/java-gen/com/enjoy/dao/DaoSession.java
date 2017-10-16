package com.enjoy.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.enjoy.entity.LastMusic;
import com.enjoy.entity.LoginInfo;

import com.enjoy.dao.LastMusicDao;
import com.enjoy.dao.LoginInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig lastMusicDaoConfig;
    private final DaoConfig loginInfoDaoConfig;

    private final LastMusicDao lastMusicDao;
    private final LoginInfoDao loginInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        lastMusicDaoConfig = daoConfigMap.get(LastMusicDao.class).clone();
        lastMusicDaoConfig.initIdentityScope(type);

        loginInfoDaoConfig = daoConfigMap.get(LoginInfoDao.class).clone();
        loginInfoDaoConfig.initIdentityScope(type);

        lastMusicDao = new LastMusicDao(lastMusicDaoConfig, this);
        loginInfoDao = new LoginInfoDao(loginInfoDaoConfig, this);

        registerDao(LastMusic.class, lastMusicDao);
        registerDao(LoginInfo.class, loginInfoDao);
    }
    
    public void clear() {
        lastMusicDaoConfig.clearIdentityScope();
        loginInfoDaoConfig.clearIdentityScope();
    }

    public LastMusicDao getLastMusicDao() {
        return lastMusicDao;
    }

    public LoginInfoDao getLoginInfoDao() {
        return loginInfoDao;
    }

}
