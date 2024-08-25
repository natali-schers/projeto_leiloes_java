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
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
    }
    
    
    
        
}

