package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConexaoDB;

public class Carrinho {
	private String nome;
	private String codigoBarras;
	private double valor;
	private int quantidade;

	// contrutor da classe
	public Carrinho(String n, String cb, double v, int q) {
		this.nome = n;
		this.codigoBarras = cb;
		this.valor = v;
		this.quantidade = q;
	}
	
	@Override
	// Reescrição do metodo toString para conseguir mostrar corretamente a lista de produtos no aplicativo
    public String toString() {
        return  " Nome: '" + nome + '\'' +
                ", Código de Barras: '" + codigoBarras + '\'' +
                ", Valor: " + valor +
                ", Quantidade: " + quantidade;
    }

	// Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	// Método de pesquisa de produto no banco de dados para mostrar no carrinho de compras do cliente
	public static ArrayList<Carrinho> pesquisar(String nome) {

		Connection con = null;
		ResultSet resultado = null;
		ArrayList<Carrinho> item = new ArrayList<>();
		try {
			con = ConexaoDB.getInstance().getConnection(); // conexão com o banco de dados
			String query = "SELECT nome, CB, valor, quantidade FROM produtos WHERE nome LIKE ? OR CB = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + nome + "%");
			pstmt.setString(2, nome);
			resultado = pstmt.executeQuery();
			while (resultado.next()) {
				String cb2 = resultado.getString("CB");				// os resultados da busca são colocados num array list
				String nome2 = resultado.getString("nome");			// e a coleção é retornada
				double valor = resultado.getDouble("valor");
				int quantidade = resultado.getInt("quantidade");
	
				item.add(new Carrinho(nome2, cb2, valor, quantidade));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				if (resultado != null) {
					resultado.close();
				}
				if (con != null) {						// tratamento de excessões 
					con.close();
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return item;
	}
	

}
