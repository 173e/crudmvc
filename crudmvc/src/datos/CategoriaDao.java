package datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.Conexion;
import datos.Interfaces.CrudSimpleInterfaces;
import entidades.Categoria;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriaDao implements CrudSimpleInterfaces<Categoria> {

    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;

    private boolean resp; // variable auxiliar para los metos de insertar y actualizar

    public CategoriaDao() {
        this.CON = Conexion.getInstancia();
    }

    @Override
    public List<Categoria> listar(String texto) {
        List<Categoria> registros = new ArrayList();

        try {

            ps = CON.conectar().prepareStatement("SELECT * FROM categoria WHERE nombre LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {

                registros.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return registros;
    }

    @Override
    public int total() {
        // Se inicializa la variable que almacenará el total de registros.
        int totalRegistros = 0;

        try {
            // Se prepara la consulta SQL para obtener el recuento de registros en la tabla "categoria".
            ps = CON.conectar().prepareStatement("SELECT COUNT(id) FROM categoria");
            rs = ps.executeQuery();

            // Se recorre el conjunto de resultados (result set).
            while (rs.next()) {
                // Se obtiene el valor del recuento y se asigna a la variable totalRegistros.
                totalRegistros = rs.getInt("COUNT(id)");
            }

            // Se cierran los recursos utilizados para la consulta.
            ps.close();
            rs.close();
        } catch (SQLException e) {
            // En caso de una excepción SQLException, se muestra un mensaje de error.
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            // Se establecen los recursos a null y se desconecta la conexión a la base de datos.
            ps = null;
            rs = null;
            CON.desconectar();
        }

        // Se retorna el total de registros obtenido.
        return totalRegistros;
    }

    @Override
    public boolean insertar(Categoria obj) {
        // Se inicializa la variable de respuesta como false.
        resp = false;

        try {
            // Se prepara la consulta SQL para la inserción de datos en la tabla "categoria".
            ps = CON.conectar().prepareStatement("INSERT INTO categoria (nombre,descripcion,activo) VALUES (?,?,1)");

            // Se establecen los valores para los parámetros de la consulta.
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDescripcion());

            // Se verifica si la ejecución de la consulta fue exitosa (afectó más de 0 filas).
            if (ps.executeUpdate() > 0) {
                resp = true;
            }

            // Se cierra el recurso PreparedStatement.
            ps.close();
        } catch (SQLException e) {
            // En caso de una excepción SQLException, se muestra un mensaje de error.
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            // Se establece el recurso PreparedStatement a null y se desconecta la conexión a la base de datos.
            ps = null;
            CON.desconectar();
        }

        // Se retorna el valor de la variable de respuesta.
        return resp;
    }

    @Override
    public boolean existe(String texto) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("SELECT nombre FROM categoria WHERE nombre=?");
            ps.setString(1, texto);
            rs = ps.executeQuery();
            rs.last();
            if (rs.getRow() > 0) {
                resp = true;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Categoria obj) {
        resp = false; // Se inicializa la variable de respuesta como false.

        try {
            // Se prepara la consulta SQL para actualizar datos en la tabla "categoria" mediante un UPDATE.
            ps = CON.conectar().prepareStatement("UPDATE categoria SET nombre=?, descripcion=? WHERE id=?");

            // Se establecen los valores para los parámetros de la consulta.
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDescripcion());
            ps.setInt(3, obj.getId());

            // Se verifica si la ejecución de la consulta fue exitosa (afectó más de 0 filas).
            if (ps.executeUpdate() > 0) {
                resp = true; // La actualización fue exitosa, se actualiza la variable de respuesta a true.
            }

            // Se cierra el recurso PreparedStatement.
            ps.close();
        } catch (SQLException e) {
            // En caso de una excepción SQLException, se muestra un mensaje de error.
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            // Se establece el recurso PreparedStatement a null y se desconecta la conexión a la base de datos.
            ps = null;
            CON.desconectar();
        }

        // Se retorna el valor de la variable de respuesta.
        return resp;

    }

    @Override
    public boolean desactivar(int id) {
        resp = false;

        try {
            ps = CON.conectar().prepareStatement("UPDATE categoria SET activo=0 WHERE id=?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                resp = true;

            }
            ps.close();

        } catch (SQLException e) {
            // En caso de una excepción SQLException, se muestra un mensaje de error.
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            // Se establece el recurso PreparedStatement a null y se desconecta la conexión a la base de datos.
            ps = null;
            CON.desconectar();
        }

        return true;

    }

    @Override
    public boolean activar(int id) {
        resp = false;

        try {
            ps = CON.conectar().prepareStatement("UPDATE categoria SET activo=1 WHERE id=?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                resp = true;

            }
            ps.close();

        } catch (SQLException e) {
            // En caso de una excepción SQLException, se muestra un mensaje de error.
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            // Se establece el recurso PreparedStatement a null y se desconecta la conexión a la base de datos.
            ps = null;
            CON.desconectar();
        }

        return true;
    }

}
