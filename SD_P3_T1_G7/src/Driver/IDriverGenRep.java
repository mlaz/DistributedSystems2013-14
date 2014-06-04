package Driver;

/**
 * 
 * @author miguel
 * Interface para interação entre a thread de condutor (TDriver) e o repositório geral (MGenRep) 
 */
public interface IDriverGenRep {

    /**
     * Método chamado pelo condutor para actualizar o seu estado na facilidade de logging
     * @param state
     */
    void updateDriverState(EDriverStates state);

    /**
     * Método para registo do condutor no Repositório geral de informação 
     */
    void registerDriver();

    /**
     * Método para informar o repositório geral de informação que a thread de condutor irá finalizar
     */
    void setDriverAsDead();

}
