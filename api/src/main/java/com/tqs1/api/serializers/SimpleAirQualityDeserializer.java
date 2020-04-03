package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.tqs1.api.model.AirQuality;

import java.io.IOException;


public abstract class SimpleAirQualityDeserializer extends StdDeserializer<AirQuality> {

    protected SimpleAirQualityDeserializer(Class<?> vc) {
        super(vc);
    }

    protected SimpleAirQualityDeserializer() {
        this(null);
    }

    @Override
    public AirQuality deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode main_node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode air_quality_node = main_node.get("data").get("indexes").get("baqi");

        String dominant_pollutant = air_quality_node.get("dominant_pollutant").textValue();
        String air_quality_color = air_quality_node.get("color").textValue();
        String air_quality_category = air_quality_node.get("category").textValue();
        int air_quality_score = (Integer) ((IntNode) air_quality_node.get("aqi")).numberValue();

        return new AirQuality(dominant_pollutant, air_quality_color, air_quality_category, air_quality_score);
    }
}
