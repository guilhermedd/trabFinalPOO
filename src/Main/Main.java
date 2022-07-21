package Main;
import GUI.GUI;
import negocio.*;
import dados.*;
import sistema.*;
import persistencia.*;

//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.time.temporal.TemporalAdjuster;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Main {
    public static Aplication dielGram = new Aplication();
    public static Scanner scanner = new Scanner(System.in);
    public static Sistema sistema = new Sistema();
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
        System.out.println("8 - deletar conta");
        System.out.println("9 - dar like no post");
        System.out.println("10 - seguir pessoa");
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
        aux.setImage(scanner.nextLine());
        System.out.println("Digite a descricao da imagem");
        aux.setDescription(scanner.nextLine());

        return aux;
    }
    public static User procurar() {
        System.out.println("Digite o nome do usuario que deseja procurar");
        return dielGram.searchUser(scanner.nextLine());
    }
    public static void GUILogin() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);

        panel.setLayout(null);

        JLabel label = new JLabel("User");
        label.setBounds(10,20,80,25);
        panel.add(label);

        JTextField userText = new JTextField();
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        frame.setVisible(true);

        JLabel passwordLable = new JLabel("Password");
        passwordLable.setBounds(10,50,80,25);
        panel.add(passwordLable);
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {
//        GUILogin();


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

                    System.out.println("Digite o username");
                    String usuarioN = scanner.nextLine();

                    System.out.println("Digite a senha");
                    String senhaN = scanner.nextLine();

                    try {
                        usuarioAtual = sistema.selectProfile(usuarioN,senhaN);
                        if (usuarioAtual == null) {
                            System.out.println("Nao foi possivel acessar essa conta");
                            return;
                        }

                    } catch (SQLException e) {
                        System.out.println("Nao foi possivel entrar na conta");
                    }
                    System.out.println("Logado com sucesso!\n");
                    break;
                case 2:

                    sistema.createUser(addUsuario());
                    break;

                case 3:
                    if (usuarioAtual != null) {
                        sistema.createPost(postarPost(), usuarioAtual, sistema.searchFriends(usuarioAtual));
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
                    List<User> friends = sistema.searchFriends(usuarioAtual);
                    for(User friend: friends) {
                        System.out.println(friend.getId());
                    }

                    break;
                case 8:
                    sistema.deleteUser(usuarioAtual.getId());
                    break;
                case 9:

                    break;
                case 10:
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    sistema.follow(usuarioAtual, sistema.selectProfileInt(id));
                    break;
            }
        }
    }
}
