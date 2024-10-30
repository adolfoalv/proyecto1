package Interface;

import javax.swing.table.DefaultTableModel;

public interface IDAO<T> {

    DefaultTableModel mostrar(String buscar);

    boolean insertar(T entidad);

    boolean editar(T entidad);

    boolean eliminar(T entidad);
}
