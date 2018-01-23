package com.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.communications.client.things.VirtualThing;

public class Client extends ConnectedThingClient {
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);
	private static final String appKey = "c281eba7-a8e8-4dc1-b9ff-0f5a50184d56";
	private static final String thingworxLink = "ws://127.0.0.1:80/Thingworx/WS";

	public Client(ClientConfigurator config) throws Exception {
		super(config);
	}

	public static void startConnection() {
		ClientConfigurator config = new ClientConfigurator();

		config.setUri(thingworxLink);
		config.setAppKey(appKey);
		config.ignoreSSLErrors(true);

		try {
			Client client = new Client(config);
			client.start();

			while (!client.getEndpoint().isConnected()) {
				Thread.sleep(1000);
			}

			ValueCollection parameters = new ValueCollection();
			parameters.SetStringValue("Room", "Room");
			client.invokeService(ThingworxEntityTypes.Things, "RoomCreator", "CreateRoom", parameters, 5000);

			Room thisThing = new Room("Room", "", client);
			client.bindThing(thisThing);

			while(!client.isShutdown()) {
				// Loop over all the Virtual Things and process them
				if(client.isConnected()) {
					LOG.info("SEND");
					for(VirtualThing thing : client.getThings().values()) {
						try {
							thing.processScanRequest();
						}
						catch(Exception eProcessing) {
							System.out.println("Error Processing Scan Request for [" + thing.getName() + "] : " + eProcessing.getMessage());
						}
					}
					LOG.info("SLEEP");
					Thread.sleep(5000);
				}
			}
			LOG.info("END");
		} catch (Exception e) {
			LOG.info("ERROR");
			e.printStackTrace();
		}
	}
}
