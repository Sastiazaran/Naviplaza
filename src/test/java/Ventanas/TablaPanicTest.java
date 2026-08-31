package Ventanas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.Test;

import Agentes.Agentes;
import Agentes.Cliente;
import Agentes.Estados;
import Agentes.Santa;
import Agentes.Vendedora;

class TablaPanicTest {

    @Test
    void panicSkipsTheKilledGlobalIndex() {
        Vendedora shopkeeper = new Vendedora(80, 80, new Semaphore(1), new Semaphore(1), 10);
        Cliente client = new Cliente(80, 80, new Semaphore(1), new Semaphore(1), 10, new Vendedora[] { shopkeeper },
                new Santa[0]);
        Santa santa = new Santa(80, 80, new Semaphore(1), 10);
        shopkeeper.setName("Vendedora 0");
        client.setName("Cliente 0");
        santa.setName("Santa 0");

        Agentes[] agents = { shopkeeper, client, santa };
        client.setDead(true);

        Tabla.panicAllExcept(agents, 1);

        assertFalse(shopkeeper.isDead());
        assertTrue(shopkeeper.isPanic());
        assertEquals(Estados.PANICO.name(), shopkeeper.getEstado());

        assertTrue(client.isDead());
        assertFalse(client.isPanic());
        assertEquals("MUERTO", client.getEstado());

        assertTrue(santa.isPanic());
        assertEquals("X", santa.getEstado(Estados.PANICO));
    }

    @Test
    void panicIgnoresNullArray() {
        Tabla.panicAllExcept(null, 0);
    }
}
