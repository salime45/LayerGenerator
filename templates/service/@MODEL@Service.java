package @PACKAGE@.service;

import @PACKAGE@.model.@MODEL@;
import java.util.List;

public interface @MODEL@Service {

    public List<@MODEL@> get@MODEL@s();

    public @MODEL@ get@MODEL@FromCod(String cod);

    public boolean nuevo(@MODEL@ o);

    public boolean update(@MODEL@ o);

}
