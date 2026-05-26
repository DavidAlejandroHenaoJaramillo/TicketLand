package service;

import model.Compra;
import model.Usuario;
import java.util.List;

public class CompraService {

    private List<Compra> compras;

    public CompraService(List<Compra> compras) {
        this.compras = compras;
    }

    // RF-034
    public void registrarCompra(Compra compra) {
        compras.add(compra);
    }

    // RF-016
    public boolean cancelarCompra(String idCompra, Usuario usuario) {
        Compra c = buscarPorId(idCompra);
        if (c == null) return false;
        if (!c.getUsuario().equals(usuario)) return false;
        return c.cancelar();
    }

    public Compra buscarPorId(String idCompra) {
        for (Compra c : compras) {
            if (String.valueOf(c.getIdCompra()).equals(idCompra)) return c;
        }
        return null;
    }

    public List<Compra> listarTodas() { return compras; }

    // RF-010
    public List<Compra> filtrarPorUsuario(Usuario usuario) {
        List<Compra> result = new java.util.ArrayList<>();
        for (Compra c : compras) {
            if (c.getUsuario().equals(usuario)) result.add(c);
        }
        return result;
    }
}