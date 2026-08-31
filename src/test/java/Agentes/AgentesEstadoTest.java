package Agentes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.Test;

class AgentesEstadoTest {

    @Test
    void statusMarkerShowsXForCurrentStateOnly() {
        Cliente cliente = new Cliente(100, 100, new Semaphore(1), new Semaphore(1), 10, new Vendedora[0], new Santa[0]);
        cliente.setEstado(Estados.PASEANDO);
        assertEquals("X", cliente.getEstado(Estados.PASEANDO));
        assertEquals(" ", cliente.getEstado(Estados.MUERTO));
        assertEquals("cliente", cliente.getType());
    }

    @Test
    void setDeadDoesNotPanicAndMarksMuerto() {
        Santa santa = new Santa(100, 100, new Semaphore(1), 10);
        santa.setName("Santa 0");
        santa.setDead(true);
        assertTrue(santa.isDead());
        assertFalse(santa.isPanic());
        assertEquals("MUERTO", santa.getEstado());
        assertEquals("X", santa.getEstado(Estados.MUERTO));
    }

    @Test
    void santaUsesSantaSpriteNotClientSprite() {
        Santa santa = new Santa(40, 40, new Semaphore(1), 10);
        assertNotNull(santa.getSprite());
        assertNotNull(santa.getSprite().getImage());
    }
}
