package com.turbospaces.ws;

import static com.turbospaces.api.Messages.toJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turbospaces.api.DepositeResponse;
import com.turbospaces.api.GenericMessageWrapper;

public class WsHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger( getClass() );
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.debug( "Inbount message={}", message.getPayload() );

        //GenericMessageWrapper req = parseJson( message.getPayload(), mapper );
        GenericMessageWrapper req = new GenericMessageWrapper();
        DepositeResponse resp = new DepositeResponse();

        session.sendMessage( new TextMessage( toJson( req, resp, session, mapper ) ) );
    }
}
