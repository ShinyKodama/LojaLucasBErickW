package lojalucasberickw;

import java.sql.Connection;
import java.sql.SQLException;

public class LojaLucasBErickW {

    public static void main(String[] args) {

        DatabaseConnection.setBancoAtual(
                DatabaseConnection.TipoBanco.MYSQL8
        );

        try (Connection con = DatabaseConnection.Connect()) {

            System.out.println("Conectado!");

        } catch (SQLException e) {

            System.out.println("ERRO:");
            e.printStackTrace();
        }
    }
}