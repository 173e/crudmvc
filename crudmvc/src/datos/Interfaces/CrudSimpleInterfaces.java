package datos.Interfaces;

import java.util.List;

public interface CrudSimpleInterfaces<T> {    
    public List<T> listar(String texto);
    
}
