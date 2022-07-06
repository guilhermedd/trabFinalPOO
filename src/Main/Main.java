package Main;
import negocio.*;
import dados.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.temporal.TemporalAdjuster;
import java.util.*;

public class Main {
    public static Aplication dielGram = new Aplication();
    public static Scanner scanner = new Scanner(System.in);
    public static void menu() {
        System.out.println("Escolha uma opcao");
        System.out.println("0 - sair");
        System.out.println("1 - Logar");
        System.out.println("2 - adicionar usuario");
        System.out.println("3 - postar post");
        System.out.println("4 - visualizar feed");
        System.out.println("5 - mostrar usuario");
        System.out.println("6 - mostrar post");
        System.out.println("7 - mostrar amigos");
    }
    public static User logar() {
        if (dielGram.getUsers().size() < 1) {
            System.out.println("Nao ha usuarios no banco de dados, crie um novo");
            return null;
        }
        System.out.println("Digite o username do usuario:");
        String nome = scanner.nextLine();
        System.out.println("Digite a senha:");
        String senha = scanner.nextLine();

        return dielGram.login(nome, senha);
    }
    public static User addUsuario() {
        User aux = new User();
        System.out.println("Digite seu nome completo:");
        aux.setFullName(scanner.nextLine());
        System.out.println("Digite ssua bio:");
        aux.setBio(scanner.nextLine());
        System.out.println("Digite o usuario:");
        aux.setUsername(scanner.nextLine());
        System.out.println("Digite a senha:");
        aux.setPassword(scanner.nextLine());

        if (dielGram.login(aux.getUsername(), aux.getPassword()) != null) {
            System.out.println("Esse usuario ja existe");
            return null;
        }
        dielGram.createUser(aux);
        return aux;

    }
    public static Post postarPost() {
        Post aux = new Post();
        System.out.println("Digite o path da imagem");
//        try{
//            File file = new File(scanner.nextLine());
//            Byte[] imagem = new byte[](500, 500, BufferedImage.TYPE_INT_ARGB);
//            imagem = ImageIO.read(file);
//            aux.setImage(imagem);
//        } catch (IOException e) {
//            System.out.println("Nao foi possivel ler a imagem");
//            return null;
//        }
        System.out.println("Digite a descricao da imagem");
        aux.setDescription(scanner.nextLine());

        return aux;
    }
    public static User procurar() {
        System.out.println("Digite o nome do usuario que deseja procurar");
        return dielGram.searchUser(scanner.nextLine());
    }
    public static void main(String[] args) {
        User usuarioAtual = null;
        int escolha = -1;

        while (escolha != 0) {
            menu();
            escolha = scanner.nextInt();
            scanner.nextLine();
            switch (escolha) {
                case 0:
                    break;
                case 1:
                    usuarioAtual = logar();
                    if (usuarioAtual == null) {
                        System.out.println("Nao foi possivel acessar essa conta");
                    }
                    break;
                case 2:
                    addUsuario();
                    break;
                case 3:
                    if (usuarioAtual != null) {
                        dielGram.postFoto(usuarioAtual, postarPost());
                        break;
                    }
                    System.out.println("Voce deve estar logado para poder postar uma foto");
                    break;
                case 4:
                    for (Post post: usuarioAtual.getFeed()) {
                        System.out.println(post);
                    }
                    break;
                case 5:
                    System.out.println("Deseja pesquisar qual usuario");
                    String nome = scanner.nextLine();
                    dielGram.searchUser(nome);
                    if (dielGram.searchUser(nome) != null) {
                        if (!dielGram.isFollowing(usuarioAtual, dielGram.searchUser(nome))) {
                            System.out.println("Voce deseja ser amigo dessa pessoa?");
                            if (scanner.nextLine().equals("S")) {
                                if (dielGram.follow(usuarioAtual, dielGram.searchUser(nome)) ){
                                    System.out.println("Voces sao amigos agora");
                                }
                            }
                        } else {
                            System.out.println("Deseja dar unfollow nesse usuario?");
                            if (scanner.nextLine().equals("S")) {
                                dielGram.unfollow(usuarioAtual, dielGram.searchUser(nome));
                                System.out.println("Voces nao sao mais amigos");
                            }
                        }
                    }
                    break;
                case 6:
                    for (Post post: usuarioAtual.getPosts()) {
                        System.out.println(post);
                    }
                    break;
                case 7:
                    for(User friends: usuarioAtual.getFriends()) {
                        System.out.println(friends);
                    }
                    break;
            }
        }
    }
}
