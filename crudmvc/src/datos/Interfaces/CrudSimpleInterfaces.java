package datos.Interfaces;

import java.util.List;

public interface CrudSimpleInterfaces<T> {

    public List<T> listar(String texto);
    public int total();

    public boolean insertar(T obj);
    public boolean actualizar(T obj);
    public boolean desactivar(int id);
    public boolean activar(int id);
    
    public boolean existe(String texto);
}
