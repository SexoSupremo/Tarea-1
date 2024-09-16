/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package taller1.entities.proyecto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ehequ
 */
@Embeddable
public class PresupuestoCategoriaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_presupuesto")
    private int idPresupuesto;
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    public PresupuestoCategoriaPK() {
    }

    public PresupuestoCategoriaPK(int idPresupuesto, String id) {
        this.idPresupuesto = idPresupuesto;
        this.id = id;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPresupuesto;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresupuestoCategoriaPK)) {
            return false;
        }
        PresupuestoCategoriaPK other = (PresupuestoCategoriaPK) object;
        if (this.idPresupuesto != other.idPresupuesto) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "taller1.entities.proyecto.PresupuestoCategoriaPK[ idPresupuesto=" + idPresupuesto + ", id=" + id + " ]";
    }
    
}
