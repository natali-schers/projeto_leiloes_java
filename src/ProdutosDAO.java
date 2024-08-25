/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DataTruncation;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {   
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public boolean cadastrarProduto (ProdutosDTO produto){    
        conn = new conectaDAO().connectDB();
        PreparedStatement pst = null;
        
        try {
            String query = "INSERT INTO produtos(nome, valor, status) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getValor().toString());
            pst.setString(3, produto.getStatus());
            pst.executeUpdate();
            
            return true;
        } catch (DataTruncation dt) {
            System.out.println("Falha ao cadastrar usuário: Dados muito longos para a coluna.");
            return false;
        } catch (SQLException ex) {
            System.out.println("Falha ao cadastrar usuário " + ex.getMessage());
            return false;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos " + e.getMessage());
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(String statusProduto){
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        conn = new conectaDAO().connectDB();
        
        try {
            String query = "SELECT * FROM produtos";
            
            if (statusProduto != null) {
                query += " WHERE status = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, statusProduto);
            } else {
                pst = conn.prepareStatement(query);
            }
            
            rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int valor = rs.getInt("valor");
                String status = rs.getString("status");
                ProdutosDTO prod = new ProdutosDTO(id, nome, valor, status);
                produtos.add(prod);
            }
            
            return produtos;
        } catch (SQLException ex) {
            System.out.println("Falha ao consultar produtos " + ex.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos " + e.getMessage());
            }
        }
    }    
    
    public boolean venderProduto(int idProduto) {
        conn = new conectaDAO().connectDB();
        PreparedStatement pst = null;
        
        try {
           String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?"; 
           pst = conn.prepareStatement(query);
           pst.setInt(1, idProduto);
           int rowsAffected = pst.executeUpdate();
           
           return rowsAffected > 0;
           
        } catch(SQLException ex) {
            System.out.println("Erro ao vender produto " + ex.getMessage());
            return false;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos " + e.getMessage());
            }
        }
    }
}