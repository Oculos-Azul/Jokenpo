package joken;

import java.rmi.*;
import java.util.Scanner;

public class JokenClient {
    public static void main(String[] args) {
        try {
            // Conectar ao servidor RMI
            Jokenpo servidor = (Jokenpo) Naming.lookup("rmi://192.168.208.101:2000/Jokenpo");
            Scanner scanner = new Scanner(System.in);

            // Solicitar nome do jogador
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();

            // Adicionar o jogador
            if (!servidor.adicionarJogador(nome)) {
                System.out.println("Não foi possível adicionar o jogador. O jogo já está cheio.");
                return;
            }

            System.out.println("Você foi adicionado ao jogo!");

            // Escolhas disponíveis
            String[] escolhas = { "pedra", "papel", "tesoura" };
            System.out.println("Escolha uma das opções: pedra, papel ou tesoura");

            int primeiraRodada = 0;
            while (true) {
                // Solicitar a escolha do jogador
                System.out.print("Sua escolha: ");
                String escolha = scanner.nextLine().toLowerCase();
                
                if (servidor.jogar(escolha, nome).contains("Resultado")) {
                    System.out.println(servidor.jogar(escolha, nome));
                    break;
                }

                // Verificar se a escolha é válida
                if (!"pedra".equals(escolha) && !"papel".equals(escolha) && !"tesoura".equals(escolha)) {
                    System.out.println("Escolha inválida! Digite 'pedra', 'papel' ou 'tesoura'.");
                    continue;
                }

                // Verificar se o jogador já fez a escolha
                if (primeiraRodada == 1) {
                    System.out.println("Você já fez sua escolha, não pode mudar.");
                    continue;
                }

                primeiraRodada = 1;
                System.out.println(servidor.jogar(escolha, nome));

            }

        } catch (Exception e) {
            System.out.println("Erro no cliente: " + e);
        }
    }
}
