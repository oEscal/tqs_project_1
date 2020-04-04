package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;

import java.io.IOException;


public class PollutantDeserializer extends StdDeserializer<Pollutant> {

    private static final long serialVersionUID = 6106269076155338042L;

    protected PollutantDeserializer(Class<?> vc) {
        super(vc);
    }

    protected PollutantDeserializer() {
        this(null);
    }

    @Override
    public Pollutant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode mainNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode airQualityNode = mainNode.get("aqi_information").get("baqi");

        String simpleName = mainNode.get("display_name").textValue();
        String fullName = mainNode.get("full_name").textValue();
        String airQualityColor = airQualityNode.get("color").textValue();
        String airQualityCategory = airQualityNode.get("category").textValue();
        int airQualityScore = (Integer) ((IntNode) airQualityNode.get("aqi")).numberValue();

        JsonNode concentrationNode = mainNode.get("concentration");
        return new Pollutant(simpleName, fullName, airQualityColor, airQualityCategory, airQualityScore,
                new ObjectMapper().readValue(concentrationNode.toString(), PollutantConcentration.class));
    }
}