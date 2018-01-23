package com.main;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.primitives.NumberPrimitive;

@ThingworxPropertyDefinitions(properties = {	
		@ThingworxPropertyDefinition(name="ambient_temperature", description="", baseType="NUMBER",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:TRUE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		@ThingworxPropertyDefinition(name="air_humidity", description="", baseType="NUMBER",
				aspects={"dataChangeType:VALUE",
                        "dataChangeThreshold:0",
                        "cacheTime:0", 
                        "isPersistent:TRUE", 
                        "isReadOnly:FALSE", 
                        "pushType:ALWAYS", 
                        "isFolded:FALSE",
                        "defaultValue:0"}),
		
})

public class Room extends VirtualThing implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(Room.class);

	private final static String ambientTemperature = "ambient_temperature";
	private final static String airHumidity = "air_humidity";

	private Double ambientTemeratureValue;
	private Integer airHumidityValue;

	private String thingName = null;

	public Room(String thingName, String thingDescription, ConnectedThingClient connectedThingClient)
			throws Exception {
		super(thingName, thingDescription, connectedThingClient);
		this.thingName = thingName;
		super.initializeFromAnnotations();
		this.init();
	}

	public void run() {
		try {
			// Delay for a period to verify that the Shutdown service will return
			Thread.sleep(1000);
			// Shutdown the client
		} catch (Exception ex) {
			LOG.error("Error " + thingName, ex);
		}
	}

	private void init() {
		ambientTemeratureValue = new Double(0);
		airHumidityValue = new Integer(0);
	}

	@Override
	public void processScanRequest() throws Exception {
		Random random = new Random();
        setProperty(ambientTemperature, 25+(random.nextDouble()*10));
        setProperty(airHumidity, random.nextInt(100)+1);
        this.updateSubscribedProperties(5000);
	}

	public void synchronizeState() {
		super.synchronizeState();
		super.syncProperties();
	}

}
