package ArteCIMA.Modelo;

public class Estudiante {

    private int idEstudiante;
    private String tipoDocumento;
    private String numDocumento;
    private String nombreCompleto;
    private int edad;
    private String telefono;
    private String correo;
    private String discapacidad;
    private String tipoDiscapacidad;
    private String tipoBeneficio;
    
    private Integer idGrupo;
    private Integer idBeca;
    private Integer idAcudiente;
    
    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumDocumento() { return numDocumento; }
    public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDiscapacidad() { return discapacidad; }
    public void setDiscapacidad(String discapacidad) { this.discapacidad = discapacidad; }

    public String getTipoDiscapacidad() { return tipoDiscapacidad; }
    public void setTipoDiscapacidad(String tipoDiscapacidad) { this.tipoDiscapacidad = tipoDiscapacidad; }

    public String getTipoBeneficio() { return tipoBeneficio; }
    public void setTipoBeneficio(String tipoBeneficio) { this.tipoBeneficio = tipoBeneficio; }


    public Integer getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; } 

    public Integer getIdBeca() { return idBeca; }
    public void setIdBeca(Integer idBeca) { this.idBeca = idBeca; } 

    public Integer getIdAcudiente() { return idAcudiente; }
    public void setIdAcudiente(Integer idAcudiente) { this.idAcudiente = idAcudiente; } 
}