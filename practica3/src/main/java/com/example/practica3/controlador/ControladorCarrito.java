package com.example.practica3.controlador;

import com.example.practica3.entity.Carrito;
import com.example.practica3.entity.CarritoLinea;
import com.example.practica3.servicio.CarritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
public class ControladorCarrito {

    @Autowired
    private CarritoServicio carritoServicio;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito crear(@RequestBody Carrito carritoNuevo){
        if(carritoNuevo.getCarritoLineas() != null) {
            long total = 0;
            for(CarritoLinea linea : carritoNuevo.getCarritoLineas()) {
                long lineCost = linea.getCostaLinea();
                linea.setCostaLinea(lineCost);
                linea.setCarrito(carritoNuevo);
                total += lineCost;
            }
            carritoNuevo.setPrecioTotal(total);
        } else {
            carritoNuevo.setPrecioTotal(0L);
        }

        return carritoServicio.creaCarrito(carritoNuevo);
    }


    @GetMapping("{id}")
    public Carrito get(@PathVariable Long id){
        System.out.println("GET /api/carritos/" + id + " called!");
        return carritoServicio.getCarrito(id);
    }

    @GetMapping("{id}/lineas")
    public List<CarritoLinea> getLineas(@PathVariable Long id){

        return carritoServicio.getCarritoLinea(id);
    }

    @PostMapping("{id}/lineas")
    @ResponseStatus(HttpStatus.CREATED)
    public CarritoLinea addItem(@PathVariable Long id, @RequestBody CarritoLinea cl){
        return carritoServicio.addItem(id, cl);
    }

    @PutMapping("{id}/lineas/{articuloId}")
    public CarritoLinea incrementar(
            @PathVariable Long id,
            @PathVariable Long articuloId,
            @RequestParam int unidades){
       return carritoServicio.incrementa(articuloId, id, unidades);
    }

    @DeleteMapping("{id}/lineas/{articuloId}")
    public Carrito deleteItem(
            @PathVariable Long id,
            @PathVariable Long articuloId){
        carritoServicio.deleteItem(id, articuloId);
        return carritoServicio.getCarrito(id);
    }

    @DeleteMapping("{id}")
    public void borrar(@PathVariable Long id){
        carritoServicio.deleteCart(id);
    }

}
