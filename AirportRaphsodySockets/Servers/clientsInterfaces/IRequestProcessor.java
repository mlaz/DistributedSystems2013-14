package Servers.clientsInterfaces;

import messages.Message;

public interface IRequestProcessor {
	Message processAndReply(Message fromClient) throws InterruptedException;
}
