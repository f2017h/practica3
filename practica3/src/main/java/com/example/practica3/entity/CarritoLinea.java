package com.example.practica3.entity;

//import com.example.practica3.entity.Carrito;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
public class CarritoLinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarritoLinea;

    @ManyToOne
    @JoinColumn(name = "idCarrito")
    @JsonIgnore
    private Carrito carrito;

    
    @Column(nullable = false)
    private Long idArticulo;

    @Column(nullable = false)
    private Long precioUnitario;

    @Column(nullable = false)
    private int numeroUnidades;

    @Column
    private Long costaLinea;

  

    public void incrementArticulo(int num) {
        this.numeroUnidades += num;
    }

    public Long getCostaLinea() {
        return numeroUnidades * precioUnitario;
    }

    public void updateCostaLinea() {
        this.costaLinea = getCostaLinea();
    }

   

    public Long getIdCarritoLinea() {
        return idCarritoLinea;
    }

    public void setIdCarritoLinea(Long idCarritoLinea) {
        this.idCarritoLinea = idCarritoLinea;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getNumeroUnidades() {
        return numeroUnidades;
    }

    public void setNumeroUnidades(int numeroUnidades) {
        this.numeroUnidades = numeroUnidades;
    }

    public Long getCostaLineaStored() {
        return costaLinea;
    }

    public void setCostaLinea(Long costaLinea) {
        this.costaLinea = costaLinea;
    }
}