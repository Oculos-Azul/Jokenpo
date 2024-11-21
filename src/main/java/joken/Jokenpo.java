package joken;

import java.rmi.*;

public interface Jokenpo extends Remote {
    // Inicia uma partida de Jokenpo entre dois jogadores
    String jogar(String escolha, String nome) throws RemoteException;

    // Adiciona um novo jogador
    boolean adicionarJogador(String nome) throws RemoteException;

    // Verifica o estado do jogo
    String getEstadoJogo() throws RemoteException;
}
