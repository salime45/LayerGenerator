package com.imonje.layergenerator.service.impl;


import com.imonje.layergenerator.dao.MODEL@Dao;
import com.imonje.layergenerator.model.MODEL@;
import com.imonje.layergenerator.service.MODEL@Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Lazy
public class @MODEL@ServiceImpl implements @MODEL@Service {

    @Autowired
    private @MODEL@Dao @MODEL@Dao;


    @Override
    public List<@MODEL@> get@MODEL@s() {
        return @MODEL@Dao.get@MODEL@s();
    }


    @Override
    public @MODEL@ get@MODEL@FromCod(String cod) {
        return @MODEL@Dao.get@MODEL@FromCod(cod);
    }


    @Override
    public boolean update@MODEL@(@MODEL@ o) {
        return @MODEL@Dao.save@MODEL@(o);
    }

    @Override
    public boolean new@MODEL@(@MODEL@ o) {
        return @MODEL@Dao.save@MODEL@(o);
    }




}
