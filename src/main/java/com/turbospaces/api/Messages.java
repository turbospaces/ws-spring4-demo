package com.turbospaces.api;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Messages {

    public static GenericMessageWrapper parseJson(String json, ObjectMapper mapper) throws IOException, ClassNotFoundException {
        JsonNode tree = mapper.readTree( json );
        String qualifier = tree.get( "qualifier" ).asText();
        GenericMessageWrapper gmw = new GenericMessageWrapper();
        gmw.correlationId = tree.get( "correlationId" ).asText();
        gmw.data = (Messages) mapper.readValue( tree.get( "data" ).traverse(), Class.forName( qualifier ) );
        return gmw;
    }
    public static String toJson(GenericMessageWrapper req, Messages resp, WebSocketSession session, ObjectMapper mapper) throws IOException {
        String data = mapper.writeValueAsString( resp );
        StringWriter str = new StringWriter();
        JsonGenerator gen = mapper.getFactory().createGenerator( str );
        try {
            gen.writeStartObject();
            gen.writeStringField( "qualifier", resp.getClass().getName() );
            gen.writeStringField( "contextId", session.getId() );
            gen.writeStringField( "correlationId", req.correlationId );
            gen.writeFieldName( "data" );
            gen.writeRawValue( data );
            gen.writeEndObject();
        }
        finally {
            gen.close();
        }
        return str.toString();
    }
}
