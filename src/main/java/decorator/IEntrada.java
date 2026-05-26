package decorator;

import model.EstadoEntrada;

// Extiende EntradaBase para que todo el sistema use un solo contrato
public interface IEntrada extends EntradaBase {
    EstadoEntrada getEstadoEntrada();
}