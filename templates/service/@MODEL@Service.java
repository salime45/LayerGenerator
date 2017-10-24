package com.imonje.layergenerator.service;

import com.imonje.layergenerator.model.@MODEL@;
import java.util.List;

public interface @MODEL@Service {

    public List<@MODEL@> get@MODEL@s();

    public @MODEL@ get@MODEL@FromCod(String cod);

    public boolean nuevo@MODEL@(@MODEL@ o);

    public boolean update@MODEL@(@MODEL@ o);

}
