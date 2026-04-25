package com.example.practica3.servicio;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.practica3.entity.Carrito;
import com.example.practica3.entity.CarritoLinea;
import com.example.practica3.repositorio.CarritoLineaRepo;
import com.example.practica3.repositorio.CarritoRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepo carritoRepo;

    @Autowired
    private CarritoLineaRepo carritoLineaRepo;

    @Transactional
    public Carrito creaCarrito(Carrito c){
        if (c.getIdCarrito() != null && carritoRepo.existsById(c.getIdCarrito())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cart already exists");
        }
        Carrito carritoGuardado = carritoRepo.save(c);
        return carritoGuardado;
    }

    public Carrito getCarrito(Long id) {
        Carrito carrito = carritoRepo.findByIdCarrito(id);

        if (carrito == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return carrito;
    }

    public List<CarritoLinea> getCarritoLinea(Long id){
        List<CarritoLinea> carritoLinea = carritoLineaRepo.findByCarrito_IdCarrito(id);
        return carritoLinea;

    }

    @Transactional
    public CarritoLinea incrementa(Long idArticulo, Long idCarrito, int num){
        List<CarritoLinea> carritoLineas = getCarritoLinea(idCarrito);

        for(CarritoLinea cl : carritoLineas){
            if(Objects.equals(cl.getIdArticulo(), idArticulo)){
                cl.incrementArticulo(num);
                actualizarPrecioTotal(idCarrito);
                return cl;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private void actualizarPrecioTotal(Long carritoId){
        Carrito carrito = getCarrito(carritoId);
        Long total = 0L;

        for(CarritoLinea linea : carrito.getCarritoLineas()){
            total += linea.getCostaLinea();
        }
        carrito.setPrecioTotal(total);
        carritoRepo.save(carrito);
    }

    @Transactional
    public CarritoLinea addItem(Long carritoId,Long articuloId, CarritoLinea cl){
        try {

        Carrito carrito = carritoRepo.findByIdCarrito(carritoId);
        if (carrito == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

            if (cl.getPrecioUnitario() == null) {
                cl.setPrecioUnitario(0L); 
            } 

        cl.setCarrito(carrito);
        cl.setIdArticulo(articuloId);
        cl.setNumeroUnidades(cl.getNumeroUnidades());

        CarritoLinea saved = carritoLineaRepo.save(cl);
        actualizarPrecioTotal(carritoId);
        return saved;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @Transactional
    public void deleteItem(Long id, Long articuloId) {
        try {
            List<CarritoLinea> lineas = carritoLineaRepo.findByCarrito_IdCarrito(id);
            
            for (CarritoLinea linea : lineas) {
                if (Objects.equals(linea.getIdArticulo(), articuloId)) {
                    carritoLineaRepo.deleteByCarrito_IdCarritoAndIdArticulo(id, articuloId);
            
                }
            }
            actualizarPrecioTotal(id);
        } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
    }

    @Transactional
    public void deleteLinea(Long lineaId) {
        try {
            CarritoLinea linea = carritoLineaRepo.findByIdWithCarrito(lineaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Linea not found"));
            
            Long carritoId = linea.getCarrito().getIdCarrito();
            carritoLineaRepo.delete(linea);
            carritoLineaRepo.flush();
            
            // Recalculate total directly
            Carrito carrito = carritoRepo.findByIdCarrito(carritoId);
            if (carrito != null && carrito.getCarritoLineas() != null) {
                Long total = 0L;
                for (CarritoLinea l : carrito.getCarritoLineas()) {
                    total += l.getCostaLinea();
                }
                carrito.setPrecioTotal(total);
                carritoRepo.save(carrito);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void deleteCart(Long id) {
        if (!carritoRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        carritoRepo.deleteById(id);
    }
}
