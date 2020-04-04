package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.tqs1.api.model.AirQuality;

import java.io.IOException;

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
        JsonNode airQualityNode = mainNode.get("data").get("indexes").get("baqi");

        String dominantPollutant = airQualityNode.get("dominant_pollutant").textValue();
        String airQualityColor = airQualityNode.get("color").textValue();
        String airQualityCategory = airQualityNode.get("category").textValue();
        int airQualityScore = (Integer) ((IntNode) airQualityNode.get("aqi")).numberValue();

        return new AirQuality(dominantPollutant, airQualityColor, airQualityCategory, airQualityScore);
    }
}
