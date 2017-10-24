package com.imonje.layergenerator.dao.impl;

import com.imonje.layergenerator.dao.@MODEL@Dao;
import com.imonje.layergenerator.model.@MODEL@;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Lazy
public class @MODEL@DaoImpl implements @MODEL@Dao {

    private static final String CLASS_NAME = @MODEL@DaoImpl.class.getName();

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public List<@MODEL@> get@MODEL@s() {
        try {
            return getCurrentSession().createQuery("from @MODEL@").list();
        } catch (HibernateException ex) {
            Logger.getLogger(@MODEL@DaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }

    }

   
    @Override
    public @MODEL@ get@MODEL@FromCod(String cod) {
        Session session = getCurrentSession();
        Query query = session.createQuery("from @MODEL@ where cod = :c");
        query.setParameter("c", cod);
        if (query.list().isEmpty()) {
            return null;
        }
        return (@MODEL@) query.list().get(0);
    }

   
   

    @Override
     public boolean save@MODEL@(@MODEL@ o){
        try {
            getCurrentSession().saveOrUpdate(o);
            return true;
        } catch (HibernateException ex) {
            Logger.getLogger(@MODEL@DaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

   

}
