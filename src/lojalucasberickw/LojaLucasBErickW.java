package lojalucasberickw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LojaLucasBErickW {

    public static void main(String[] args) {

        DatabaseConnection.setBancoAtual(
                DatabaseConnection.TipoBanco.MYSQL5
        );

        java.awt.EventQueue.invokeLater(() -> {
            new uLoja().setVisible(true);
        });
    }
}
