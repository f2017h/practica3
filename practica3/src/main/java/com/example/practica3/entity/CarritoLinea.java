package com.example.practica3.entity;

import com.example.practica3.entity.Carrito;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class CarritoLinea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idCarrito;

    @ManyToOne
    @JoinColumn(name = "idCarrito", insertable = false, updatable = false)
    @JsonIgnore
    public Carrito carrito;


    @Column(nullable = false)
    public Long idArticulo;

    @Column
    public Long precioUnitario;
    @Column
    public int numeroUnidades;
    @Column
    public Long costaLinea;



    public void incrementArticulo(int num){
        numeroUnidades += num;
    }

    public Long getIdArticulo(){
        return this.idArticulo;
    }

    public Long getPrecioUnitario() { return this.precioUnitario;}

    public void setPrecioUnitario(Long num){ this.precioUnitario = num;}


    public void setCarrito(Carrito c){
        this.carrito = c;
    }

    public Long getCostaLinea() {
        return numeroUnidades * precioUnitario;
    }

    public void setCostaLinea(Long sum){
        this.costaLinea = sum;
    }
}
