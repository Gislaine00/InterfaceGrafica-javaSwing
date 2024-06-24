import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        String nomeUser;
        String senha;
        int opcao;

        System.out.println("Digite seu nome de usuário:");
        nomeUser = scanner2.nextLine();

        System.out.println("Digite sua senha:");
        senha = scanner2.nextLine();

        if (nomeUser.equals("Gislaine") && senha.equals("senha123")){
            do {
                System.out.println("Digite o numero da opção desejada:");
                System.out.println("1 - Exibir interface gráfica");
                System.out.println("2 - Sair do programa");

                System.out.print("Opção: ");
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        System.out.println("Exibindo dados de CPU, RAM e Disco deste notebook... ");
                        JarGis.mostrarInterface();
                        break;
                    case 2:
                        System.out.println();
                        System.out.println("Programa encerrado.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 2);
        }else {
            System.out.println("Usuário e senha incorretos!");
        }
    }
}
