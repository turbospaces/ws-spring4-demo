package com.turbospaces.api;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Messages {

    public static GenericMessageWrapper parseJson(String json, ObjectMapper mapper) throws IOException {
        JsonNode tree = mapper.readTree( json );
        GenericMessageWrapper gmw = new GenericMessageWrapper();
        gmw.correlationId = tree.get( "correlationId" ).asText();
        gmw.data = mapper.readValue( tree.get( "data" ).traverse(), Messages.class );
        return gmw;
    }
    public static String toJson(GenericMessageWrapper req, Messages resp, WebSocketSession session, ObjectMapper mapper) throws IOException {
        StringWriter str = new StringWriter();
        JsonGenerator jsonGenerator = mapper.getFactory().createGenerator( str );
        jsonGenerator.writeStringField( "qualifier", resp.getClass().getName() );
        jsonGenerator.writeStringField( "contextId", session.getId() );
        jsonGenerator.writeStringField( "correlationId", req.correlationId );
        jsonGenerator.writeObjectField( "data", resp );
        jsonGenerator.close();
        return str.toString();
    }
}
