/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.yu38.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ehequ
 */
@Entity
@Table(name = "presupuesto_categoria")
@NamedQueries({
    @NamedQuery(name = "PresupuestoCategoria.findAll", query = "SELECT p FROM PresupuestoCategoria p"),
    @NamedQuery(name = "PresupuestoCategoria.findByIdPresupuesto", query = "SELECT p FROM PresupuestoCategoria p WHERE p.presupuestoCategoriaPK.idPresupuesto = :idPresupuesto"),
    @NamedQuery(name = "PresupuestoCategoria.findById", query = "SELECT p FROM PresupuestoCategoria p WHERE p.presupuestoCategoriaPK.id = :id"),
    @NamedQuery(name = "PresupuestoCategoria.findByPrevisto", query = "SELECT p FROM PresupuestoCategoria p WHERE p.previsto = :previsto"),
    @NamedQuery(name = "PresupuestoCategoria.findByReal", query = "SELECT p FROM PresupuestoCategoria p WHERE p.real = :real"),
    @NamedQuery(name = "PresupuestoCategoria.findByDiferencia", query = "SELECT p FROM PresupuestoCategoria p WHERE p.diferencia = :diferencia")})
public class PresupuestoCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresupuestoCategoriaPK presupuestoCategoriaPK;
    @Basic(optional = false)
    @Column(name = "previsto")
    private int previsto;
    @Basic(optional = false)
    @Column(name = "real")
    private int real;
    @Basic(optional = false)
    @Column(name = "diferencia")
    private int diferencia;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Categoria categoria;
    @JoinColumn(name = "id_presupuesto", referencedColumnName = "id_presupuesto", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PresupuestoMensual presupuestoMensual;

    public PresupuestoCategoria() {
    }

    public PresupuestoCategoria(PresupuestoCategoriaPK presupuestoCategoriaPK) {
        this.presupuestoCategoriaPK = presupuestoCategoriaPK;
    }

    public PresupuestoCategoria(PresupuestoCategoriaPK presupuestoCategoriaPK, int previsto, int real, int diferencia) {
        this.presupuestoCategoriaPK = presupuestoCategoriaPK;
        this.previsto = previsto;
        this.real = real;
        this.diferencia = diferencia;
    }

    public PresupuestoCategoria(int idPresupuesto, String id) {
        this.presupuestoCategoriaPK = new PresupuestoCategoriaPK(idPresupuesto, id);
    }

    public PresupuestoCategoriaPK getPresupuestoCategoriaPK() {
        return presupuestoCategoriaPK;
    }

    public void setPresupuestoCategoriaPK(PresupuestoCategoriaPK presupuestoCategoriaPK) {
        this.presupuestoCategoriaPK = presupuestoCategoriaPK;
    }

    public int getPrevisto() {
        return previsto;
    }

    public void setPrevisto(int previsto) {
        this.previsto = previsto;
    }

    public int getReal() {
        return real;
    }

    public void setReal(int real) {
        this.real = real;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(int diferencia) {
        this.diferencia = diferencia;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public PresupuestoMensual getPresupuestoMensual() {
        return presupuestoMensual;
    }

    public void setPresupuestoMensual(PresupuestoMensual presupuestoMensual) {
        this.presupuestoMensual = presupuestoMensual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (presupuestoCategoriaPK != null ? presupuestoCategoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresupuestoCategoria)) {
            return false;
        }
        PresupuestoCategoria other = (PresupuestoCategoria) object;
        if ((this.presupuestoCategoriaPK == null && other.presupuestoCategoriaPK != null) || (this.presupuestoCategoriaPK != null && !this.presupuestoCategoriaPK.equals(other.presupuestoCategoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.yu38.entities.PresupuestoCategoria[ presupuestoCategoriaPK=" + presupuestoCategoriaPK + " ]";
    }
    
}
