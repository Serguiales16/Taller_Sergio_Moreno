package taller;

public class Reparacion {

    private String idCoche;
    private String descripcion;
    private float costo;

    public Reparacion(String idCoche, String descripcion, float costo) {
        this.idCoche = idCoche;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public String getIdCoche() {
        return idCoche;
    }

    public void setIdCoche(String idCoche) {
        this.idCoche = idCoche;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
