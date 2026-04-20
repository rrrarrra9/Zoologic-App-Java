package Controller;

import Model.Usuario;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;



public class UserController {
    //obtener todos los usuarios
    public static List<Usuario> getAllUsers() {
        List<Usuario> users = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, email, contrasena, rol, created_at FROM usuario";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    u.setCreatedAt(ts.toLocalDateTime());
                }
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    //autenticar usuario
    public static Usuario authenticate(String email, String password) {
        String sql = "SELECT id, nombre, apellido, email, contrasena, rol, created_at FROM usuario WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("contrasena");
                    String providedHash = hashPassword(password);
                    if (storedHash.equals(providedHash)) {
                        Usuario u = new Usuario();
                        u.setId(rs.getInt("id"));
                        u.setNombre(rs.getString("nombre"));
                        u.setApellido(rs.getString("apellido"));
                        u.setEmail(rs.getString("email"));
                        u.setContrasena(storedHash);
                        u.setRol(rs.getString("rol"));
                        Timestamp ts = rs.getTimestamp("created_at");
                        if (ts != null) {
                            u.setCreatedAt(ts.toLocalDateTime());
                        }
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //registrar usuario
    public static void registerUser(Usuario user) {
        String sql = "INSERT INTO usuario (nombre, apellido, email, contrasena, rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getEmail());
            ps.setString(4, hashPassword(user.getContrasena()));
            ps.setString(5, user.getRol());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //codificar usuario
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
