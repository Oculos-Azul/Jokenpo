package joken;

import java.rmi.registry.LocateRegistry;
import java.rmi.*;

public class ServerJokenRMI {

	public static void main(String[] args) {
		try {
			Jokenpo jokenpo = new JokenServer();
			String stubJoken = "rmi://192.168.200.27:2000/Jokenpo";

			System.out.println("Até aqui deu, agora vamos ver o Regsitro");
			LocateRegistry.createRegistry(2000);
			System.out.println("Aqui eu mano");
			Naming.rebind(stubJoken, jokenpo);

			System.out.println("Deu certo garai");

		} catch (Exception e) {
			System.out.println("Euaqui a");
		}
	}

}
