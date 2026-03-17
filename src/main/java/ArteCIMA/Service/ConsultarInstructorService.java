package ArteCIMA.Service;

import ArteCIMA.DAO.ConsultarInstructorDAO;
import ArteCIMA.Modelo.Instructor;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultarInstructorService {

    private final ConsultarInstructorDAO dao;
    private static final Logger logger = Logger.getLogger(ConsultarInstructorService.class.getName());

    public ConsultarInstructorService() {
        this.dao = new ConsultarInstructorDAO();
    }

    public List<Instructor> listarInstructores(
            String idInstructor,
            String numDocumento,
            String nombreCompleto,
            Boolean discapacidad) {

        if (idInstructor != null && idInstructor.isBlank()) idInstructor = null;
        if (numDocumento != null && numDocumento.isBlank()) numDocumento = null;
        if (nombreCompleto != null && nombreCompleto.isBlank()) nombreCompleto = null;

        try {
            return dao.listarInstructores(idInstructor, numDocumento, nombreCompleto, discapacidad);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error listando instructores", e);
            return Collections.emptyList();
        }
    }

    public Instructor obtenerInstructorPorId(int id) {
        try {
            return dao.obtenerInstructorPorId(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener instructor", e);
            return null;
        }
    }

    public boolean actualizarInstructor(Instructor i) {
        try {
            return dao.actualizarInstructor(i);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar instructor", e);
            return false;
        }
    }

    public boolean eliminarInstructor(int id) {
        try {
            return dao.eliminarInstructor(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar instructor", e);
            return false;
        }
    }
}
