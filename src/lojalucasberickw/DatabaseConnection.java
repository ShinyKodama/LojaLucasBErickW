package lojalucasberickw;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    public enum TipoBanco {
        MYSQL5,
        MYSQL8
    }

    private static TipoBanco bancoAtual = TipoBanco.MYSQL8;
    
    public static void setBancoAtual(TipoBanco tipoBanco) {
        bancoAtual = tipoBanco;
    }
    
    private static Connection conectar(String caminhoJar, String classeDriver, String url,
            String usuario, String senha) throws SQLException {

        try {
            Path jar = Path.of(
                    System.getProperty("user.dir"),
                    caminhoJar
            );

            URLClassLoader loader = new URLClassLoader(
                    new URL[]{jar.toUri().toURL()},
                    DatabaseConnection.class.getClassLoader()
            );

            Class<?> classe = Class.forName(
                    classeDriver,
                    true,
                    loader
            );

            Driver driver = (Driver) classe
                    .getDeclaredConstructor()
                    .newInstance();

            Properties propriedades = new Properties();
            propriedades.setProperty("user", usuario);
            propriedades.setProperty("password", senha);

            Connection conexao = driver.connect(url, propriedades);

            if (conexao == null) {
                throw new SQLException(
                        "O driver não aceitou a URL: " + url
                );
            }

            return conexao;

        } catch (SQLException e) {
            throw e;

        } catch (Exception e) {
            throw new SQLException(
                    "Erro ao carregar o driver JDBC",
                    e
            );
        }
    }

    public static Connection MySQL5Connect() throws SQLException {
        return conectar(
                "libs/mysql5/mysql-connector-java-5.1.49.jar",
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3307/Loja?useSSL=false",
                "root",
                ""
        );
    }

    public static Connection MySQL8Connect() throws SQLException {
        return conectar(
                "libs/mysql8/mysql-connector-j-9.7.0.jar",
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/Loja?sslMode=DISABLED&allowPublicKeyRetrieval=true",
                "root",
                ""
        );
    }

    public static Connection Connect() throws SQLException {
        if (bancoAtual == TipoBanco.MYSQL5) {
            return MySQL5Connect();
        }

        return MySQL8Connect();
    }
}
