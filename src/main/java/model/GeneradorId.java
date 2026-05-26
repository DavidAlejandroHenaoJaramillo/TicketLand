package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneradorId {

    private static volatile GeneradorId instancia;
    private final Map<String, AtomicInteger> contadores;

    private GeneradorId() {
        contadores = new HashMap<>();
        contadores.put("USR", new AtomicInteger(0));
        contadores.put("EVT", new AtomicInteger(0));
        contadores.put("CMP", new AtomicInteger(0));
        contadores.put("ENT", new AtomicInteger(0));
        contadores.put("PAG", new AtomicInteger(0));
        contadores.put("INC", new AtomicInteger(0));
        contadores.put("ZON", new AtomicInteger(0));
        contadores.put("REC", new AtomicInteger(0));
        contadores.put("TAR", new AtomicInteger(0));
    }

    public static GeneradorId getInstance() {
        if (instancia == null) {
            synchronized (GeneradorId.class) {
                if (instancia == null) {
                    instancia = new GeneradorId();
                }
            }
        }
        return instancia;
    }

    public String generarId(String tipo) {
        contadores.putIfAbsent(tipo, new AtomicInteger(0));
        int siguiente = contadores.get(tipo).incrementAndGet();
        return String.format("%s-%04d", tipo, siguiente);
    }
}