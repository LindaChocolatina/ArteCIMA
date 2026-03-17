package ArteCIMA.Modelo;

public class Instructor {

    private Integer idInstructor;
    private String tipoDocumento;
    private String numDocumento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private Boolean discapacidad;
    private String tipoDiscapacidad;
    private String especialidadArtistica;
    private Double valorPorClase;

    public Instructor() {
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public String getEspecialidadArtistica() {
        return especialidadArtistica;
    }

    public void setEspecialidadArtistica(String especialidadArtistica) {
        this.especialidadArtistica = especialidadArtistica;
    }

    public Double getValorPorClase() {
        return valorPorClase;
    }

    public void setValorPorClase(Double valorPorClase) {
        this.valorPorClase = valorPorClase;
    }
}
