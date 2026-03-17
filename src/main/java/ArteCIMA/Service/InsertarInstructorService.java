package ArteCIMA.Service;

import ArteCIMA.DAO.InsertarInstructorDAO;
import ArteCIMA.Modelo.Instructor;
import java.sql.SQLException;

public class InsertarInstructorService {
    
    private InsertarInstructorDAO dao;
    
    public InsertarInstructorService() {
        this.dao = new InsertarInstructorDAO(); // Inicializa la dependencia con el DAO
    }
    
    public String registrarInstructor(Instructor inst) {
        
        if (inst.getNumDocumento() == null || inst.getNumDocumento().trim().isEmpty() ||
            inst.getNombreCompleto() == null || inst.getNombreCompleto().trim().isEmpty() ||
            inst.getEspecialidadArtistica() == null || inst.getEspecialidadArtistica().trim().isEmpty()) {
            return "Error de Lógica: Documento, nombre y especialidad son campos obligatorios.";
        }
        
        String correo = inst.getCorreo();
        if (!correo.isEmpty() && !correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
            return "Error de Lógica: Formato de correo electrónico inválido.";
        }
        
        if (inst.getDiscapacidad() && 
            (inst.getTipoDiscapacidad() == null || inst.getTipoDiscapacidad().trim().isEmpty())) {
            return "Error de Lógica: Si marcó Discapacidad 'Sí', debe especificar el tipo.";
        }
        
        if (inst.getValorPorClase() == null || inst.getValorPorClase() <= 0) {
            return "Error de Lógica: El valor por clase debe ser un número positivo mayor a cero.";
        }
              
        try {
            
            if (dao.existeDocumento(inst.getNumDocumento())) {
                return "Error de Integridad: Ya existe un instructor registrado con el número de documento " + inst.getNumDocumento() + ".";
            }
                       
            if (dao.insertarInstructor(inst)) {
                return "Éxito: Instructor guardado exitosamente.";
            } else {
                return "Error de Persistencia: No se pudo guardar el instructor (0 filas afectadas).";
            }
            
        } catch (SQLException e) {
            return "Error de Base de Datos: Fallo al intentar conectar o ejecutar la consulta. Detalle: " + e.getMessage();
        }
    }
}