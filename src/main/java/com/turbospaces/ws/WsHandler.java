package com.turbospaces.ws;

import static com.turbospaces.api.Messages.parseJson;
import static com.turbospaces.api.Messages.toJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.turbospaces.api.DepositeRequest;
import com.turbospaces.api.DepositeResponse;
import com.turbospaces.api.GenericMessageWrapper;

public class WsHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger( getClass() );
    private final ObjectMapper mapper = new ObjectMapper();

    {
        mapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.debug( "Inbount message={}", message.getPayload() );

        GenericMessageWrapper req = parseJson( message.getPayload(), mapper );
        DepositeRequest deposite = (DepositeRequest) req.data;
        DepositeResponse resp = new DepositeResponse();
        resp.currentBalance = 123.35 - deposite.balance;

        String out = toJson( req, resp, session, mapper );
        session.sendMessage( new TextMessage( out ) );

        logger.debug( "Outbound message={}", out );
    }
}
