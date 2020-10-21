package org.e.store.loja.websockets;

import java.io.IOException;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/canal/promos")
public class PromosEndpoint {

	//websockets - mantêm uma conexão aberta com o cliente e assim o servidor envia info sem necessidade do usuário fazer requisições
    @OnOpen
    public void onMessage(Session session) {
        if(session.isOpen()) {
            try {
                session.getBasicRemote().sendText("{\"titulo\": \"Livro Java OO com 40% de desconto?\", \"livroId\": 1}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}