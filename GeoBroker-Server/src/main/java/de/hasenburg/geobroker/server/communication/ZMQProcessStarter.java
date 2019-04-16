package de.hasenburg.geobroker.server.communication;

import de.hasenburg.geobroker.commons.communication.ZMQProcessManager;
import de.hasenburg.geobroker.commons.model.BrokerInfo;
import de.hasenburg.geobroker.server.distribution.BrokerAreaManager;
import de.hasenburg.geobroker.server.distribution.IDistributionLogic;
import de.hasenburg.geobroker.server.matching.IMatchingLogic;
import de.hasenburg.geobroker.server.storage.TopicAndGeofenceMapper;
import de.hasenburg.geobroker.server.storage.client.ClientDirectory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ZMQProcessStarter {

	private static final Logger logger = LogManager.getLogger();

	public static ZMQProcess_Server runZMQProcess_Server(ZMQProcessManager processManager, String ip, int port,
														 String identity) {
		ZMQProcess_Server zmqProcess = new ZMQProcess_Server(ip, port, identity);
		processManager.submitZMQProcess(ZMQProcess_Server.getServerIdentity(identity), zmqProcess);
		return zmqProcess;
	}

	public static ZMQProcess_MessageProcessor runZMQProcess_MessageProcessor(ZMQProcessManager processManager,
																			 String identity, int number,
																			 IMatchingLogic matchingLogic,
																			 int numberOfBrokerCommunicators) {
		ZMQProcess_MessageProcessor zmqProcess = new ZMQProcess_MessageProcessor(identity,
				number,
				matchingLogic,
				numberOfBrokerCommunicators);
		processManager.submitZMQProcess(ZMQProcess_MessageProcessor.getMessageProcessorIdentity(identity, number),
				zmqProcess);
		return zmqProcess;
	}

	public static ZMQProcess_BrokerCommunicator runZMQProcess_BrokerCommunicator(ZMQProcessManager processManager,
																				 String identity, int number,
																				 IDistributionLogic distributionLogic,
																				 List<BrokerInfo> otherBrokerInfos) {
		ZMQProcess_BrokerCommunicator zmqProcess = new ZMQProcess_BrokerCommunicator(identity,
				number,
				distributionLogic,
				otherBrokerInfos);
		processManager.submitZMQProcess(ZMQProcess_BrokerCommunicator.getBrokerCommunicatorId(identity, number),
				zmqProcess);
		return zmqProcess;
	}

}
