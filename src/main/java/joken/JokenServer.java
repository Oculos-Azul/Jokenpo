package joken;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class JokenServer extends UnicastRemoteObject implements Jokenpo {
    private List<String> jogadores;
    private Map<String, String> escolhas;
    private static final long TEMPO_ESPERA = 40000;

    public JokenServer() throws RemoteException {
        super();
        jogadores = new ArrayList<>();
        escolhas = new HashMap<>();
    }

    @Override
    public boolean adicionarJogador(String nome) throws RemoteException {
        if (jogadores.size() < 2 && !jogadores.contains(nome)) {
            jogadores.add(nome);
            return true;
        }
        return false;
    }

    @Override
    public String jogar(String escolha, String nome) throws RemoteException {
        if (!jogadores.contains(nome)) {
            return "Jogador não encontrado!";
        }

        escolhas.put(nome, escolha);

        // // Usando um ExecutorService para simular o tempo de espera
        // ExecutorService executor = Executors.newSingleThreadExecutor();
        // Future<?> future = executor.submit(() -> {
        // try {
        // Thread.sleep(TEMPO_ESPERA);
        // } catch (InterruptedException e) {
        // Thread.currentThread().interrupt();
        // }
        // });
        //
        // try {
        // // Espera pela escolha do jogador 2 ou timeout
        // if (future.get(TEMPO_ESPERA, TimeUnit.MILLISECONDS) != null) {
        // return "Tempo esgotado! Jogador 2 não fez a escolha a tempo.";
        // }
        // } catch (TimeoutException e) {
        // future.cancel(true); // Cancela o thread se o tempo acabou
        // return "Tempo esgotado! O jogo terminou sem resposta do Jogador 2.";
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        if (escolhas.size() < 2) {
            return "Aguardando outro jogador...";
        }

        String jogador1 = jogadores.get(0);
        String jogador2 = jogadores.get(1);
        String escolha1 = escolhas.get(jogador1);
        String escolha2 = escolhas.get(jogador2);

        String resultado = calcularResultado(escolha1, escolha2);
        return "Resultado: " + resultado;
    }

    @Override
    public String getEstadoJogo() throws RemoteException {
        return "Jogadores: " + jogadores.toString() + "\nEscolhas: " + escolhas.toString();
    }

    private String calcularResultado(String escolha1, String escolha2) {
        if (escolha1.equals(escolha2)) {
            return "Empate!";
        }

        if ((escolha1.equals("pedra") && escolha2.equals("tesoura")) ||
                (escolha1.equals("papel") && escolha2.equals("pedra")) ||
                (escolha1.equals("tesoura") && escolha2.equals("papel"))) {
            return "Jogador 1 vence!";
        } else {
            return "Jogador 2 vence!";
        }
    }

    // public static void main(String[] args) {
    // try {
    // JokenServer server = new JokenServer();
    // Jokenpo stub = (Jokenpo) UnicastRemoteObject.exportObject(server, 0);
    // System.out.println("Euaqui");
    // Registry registro = LocateRegistry.getRegistry();
    //
    // registro.bind("Hello", stub);
    //// Naming.rebind("rmi://localhost/Jokenpo", server);
    // System.out.println("Servidor Jokenpo pronto.");
    // } catch (Exception e) {
    // System.out.println("Erro no servidor: " + e);
    // }
    // }
}
