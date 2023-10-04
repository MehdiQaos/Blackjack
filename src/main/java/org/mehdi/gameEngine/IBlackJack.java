package org.mehdi.gameEngine;

import org.mehdi.gameEngine.utils.Response;

public interface IBlackJack {
    public Response startRound(int bet);
    public Response hit();
    public Response stand();
    public Response surrender();
    public Response split();
    public Response doubleDown();
}
