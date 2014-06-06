package Passenger;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * Interface para interação entre a thread de passageiro (TPassenger) e a entrada do terminal de partida (MDepartureTerminalEntrance) 
 * @author miguel
 */
public interface IPassengerDepartureTerminalEntrance {

    /**
     * 
     * @param vecClock 
     * @return 
     * @throws InterruptedException
     */
    public VectorClock prepareNextLeg(VectorClock vecClock) throws InterruptedException, RemoteException;
}