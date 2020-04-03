package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.tqs1.api.model.SimpleAirQuality;

import java.io.IOException;


public class AirQualityDeserializer extends StdDeserializer<SimpleAirQuality> {

    private static final long serialVersionUID = 6106269076155338045L;

    protected AirQualityDeserializer(Class<?> vc) {
        super(vc);
    }

    protected AirQualityDeserializer() {
        this(null);
    }

    @Override
    public SimpleAirQuality deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode main_node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode air_quality_node = main_node.get("data").get("indexes").get("baqi");

        String dominant_pollutant = air_quality_node.get("dominant_pollutant").textValue();
        String air_quality_color = air_quality_node.get("color").textValue();
        String air_quality_category = air_quality_node.get("category").textValue();
        int air_quality_score = (Integer) ((IntNode) air_quality_node.get("aqi")).numberValue();

        return new SimpleAirQuality(dominant_pollutant, air_quality_color, air_quality_category, air_quality_score);
    }
}
