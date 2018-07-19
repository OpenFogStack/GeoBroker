package de.hasenburg.geofencebroker.communication;

import de.hasenburg.geofencebroker.model.RouterMessage;
import de.hasenburg.geofencebroker.model.exceptions.CommunicatorException;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import zmq.socket.reqrep.Router;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class RouterCommunicator extends ZMQCommunicator {

	public RouterCommunicator(String address, int port) {
		super(address, port);
	}

	@Override
	public void init(String identity) {
		context = ZMQ.context(1);
		socket = context.socket(ZMQ.ROUTER);

		if (identity != null) { socket.setIdentity(identity.getBytes()); }
		// socket.setRouterMandatory(true);

		socket.bind(address + ":" + port);
	}

	public void sendDISCONNECT(String clientIdentifier, ReasonCode reasonCode) {
		RouterMessage message = new RouterMessage(clientIdentifier, ControlPacketType.DISCONNECT, reasonCode.name());
		sendMessage(message.getZmsg());
	}

	public void sendCONNACK(String clientIdentifier) {
		RouterMessage message = new RouterMessage(clientIdentifier, ControlPacketType.CONNACK);
		sendMessage(message.getZmsg());
	}

	public void sendPINGRESP(String clientIdentifier) {
		RouterMessage message = new RouterMessage(clientIdentifier, ControlPacketType.PINGRESP);
		sendMessage(message.getZmsg());
	}

	public void sendPINGRESP(String clientIdentifier, ReasonCode reasonCode) {
		RouterMessage message = new RouterMessage(clientIdentifier, ControlPacketType.PINGRESP, reasonCode.name());
		sendMessage(message.getZmsg());
	}

	public static void main(String[] args) throws CommunicatorException {
		RouterCommunicator router = new RouterCommunicator("tcp://localhost", 5559);
		router.init(null);
		BlockingQueue<ZMsg> blockingQueue = new LinkedBlockingDeque<>();
		router.startReceiving(blockingQueue);
	}

}
