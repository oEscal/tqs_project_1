package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.tqs1.api.model.PollutantConcentration;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class PollutantConcentrationDeserializer extends StdDeserializer<PollutantConcentration> {


    private static final long serialVersionUID = 6106269076155338041L;

    protected PollutantConcentrationDeserializer(Class<?> vc) {
        super(vc);
    }

    protected PollutantConcentrationDeserializer() {
        this(null);
    }

    @Override
    public PollutantConcentration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode mainNode = jsonParser.getCodec().readTree(jsonParser);

        double value = mainNode.get("value").doubleValue();
        String units = mainNode.get("units").textValue();

        return new PollutantConcentration(value, units);
    }
}
