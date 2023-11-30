package negocio;

import datos.CategoriaDao;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class CategoriaControl {

    private final CategoriaDao DATOS;
    private Categoria obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados;

    public CategoriaControl() {
        this.DATOS = new CategoriaDao();
        this.obj = new Categoria();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto) {

        List<Categoria> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto));

        String[] titulos = {"Id", "Nombre", "Descripcion", "Estado"};
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[4];

        for (Categoria item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = item.getDescripcion();
            registro[3] = estado;
            this.modeloTabla.addRow(registro);
        }

        return this.modeloTabla;
    }

    public int total() {
        return DATOS.total();
    }

    public int totalMostrados() {
        return this.registrosMostrados;
    }

    // metodo para insertar
    public String insertar(String nombre, String descripcion) {
        // Se verifica si ya existe un registro con el mismo nombre.
        if (DATOS.existe(nombre)) {
            return "El registro ya existe.";
        } else {
            // Se establecen los valores para el objeto 'obj'.
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);

            // Se intenta realizar la inserción llamando al método 'insertar' de la clase 'DATOS'.
            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el registro.";
            }
        }
    }

    public String actualizar(int id, String nombre, String nombreAnt, String descripcion) {
        // Se verifica si el nuevo nombre es igual al nombre original.
        if (nombre.equals(nombreAnt)) {
            // Los nombres son iguales, se actualizan los datos y se verifica la actualización.
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);

            // Se intenta realizar la actualización llamando al método 'actualizar' de la clase 'DATOS'.
            if (DATOS.actualizar(obj)) {
                return "OK"; // La actualización fue exitosa, se retorna "OK".
            } else {
                return "Error en la actualización."; // Hubo un error en la actualización, se retorna un mensaje de error.
            }
        } else {
            // Los nombres son diferentes, se verifica si el nuevo nombre ya existe en la base de datos.
            if (DATOS.existe(nombre)) {
                return "El registro ya existe."; // El nuevo nombre ya existe, se retorna un mensaje indicando esto.
            } else {
                // El nuevo nombre no existe, se actualizan los datos y se verifica la actualización.
                obj.setId(id);
                obj.setNombre(nombre);
                obj.setDescripcion(descripcion);

                // Se intenta realizar la actualización llamando al método 'actualizar' de la clase 'DATOS'.
                if (DATOS.actualizar(obj)) {
                    return "OK"; // La actualización fue exitosa, se retorna "OK".
                } else {
                    return "Error en la actualización."; // Hubo un error en la actualización, se retorna un mensaje de error.
                }
            }
        }
    }

    public String desactivar(int id) {

        if (DATOS.desactivar(id)) {
            return "OK";
        } else {
            return "No se puede desactivar el registro";
        }

    }
    
    
    public String activar(int id) {

        if (DATOS.activar(id)) {
            return "OK";
        } else {
            return "No se puede Activar el registro";
        }
    }
    

}
