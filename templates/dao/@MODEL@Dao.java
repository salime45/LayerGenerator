package @PACKAGE@.dao;

import @PACKAGE@.model.@MODEL@;
import java.util.List;

public interface @MODEL@Dao {

    public List<@MODEL@> get@MODEL@s();

    public @MODEL@ get@MODEL@FromCod(String cod);

    public boolean save@MODEL@(@MODEL@ o);

}
