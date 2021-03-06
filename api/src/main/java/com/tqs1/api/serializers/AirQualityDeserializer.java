package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Pollutant;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;


@Service
public class AirQualityDeserializer extends StdDeserializer<AirQuality> {

    private static final long serialVersionUID = 6106269076155338045L;

    protected AirQualityDeserializer(Class<?> vc) {
        super(vc);
    }

    protected AirQualityDeserializer() {
        this(null);
    }

    @Override
    public AirQuality deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode mainNode = jsonParser.getCodec().readTree(jsonParser);

        JsonNode airQualityNode = mainNode.get("indexes").get("baqi");

        String date = mainNode.get("datetime").textValue();
        String dominantPollutant = airQualityNode.get("dominant_pollutant").textValue();
        String airQualityColor = airQualityNode.get("color").textValue();
        String airQualityCategory = airQualityNode.get("category").textValue();
        int airQualityScore = (Integer) ((IntNode) airQualityNode.get("aqi")).numberValue();

        AirQuality airQuality = new AirQuality(dominantPollutant, date, airQualityColor, airQualityCategory,
                airQualityScore);

        Iterator<JsonNode> pollutantNodes = mainNode.get("pollutants").elements();
        while(pollutantNodes.hasNext()) {
            JsonNode currentPollutant = pollutantNodes.next();
            airQuality.addPollutant(new ObjectMapper().readValue(currentPollutant.toString(), Pollutant.class));
        }

        return airQuality;
    }
}
