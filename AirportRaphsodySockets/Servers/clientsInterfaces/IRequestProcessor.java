package Servers.clientsInterfaces;

import messages.Message;

/**
 *
 * @author miguel
 */
public interface IRequestProcessor {

    /**
     *
     * @param fromClient
     * @return
     * @throws InterruptedException
     */
    Message processAndReply(Message fromClient) throws InterruptedException;
}
