
/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    private void conectar() {
        conn = new conectaDAO().connectDB();
    }

    public String cadastrarProduto(ProdutosDTO produto) {
        conectar();
        try {
            prep = conn.prepareStatement("INSERT INTO produtos VALUES(?,?,?,?)");
            prep.setInt(1, 0);// para o sgbd controlar o id
            prep.setString(2, produto.getNome());
            prep.setDouble(3, produto.getValor());
            prep.setString(4, produto.getStatus());
            prep.executeUpdate();
            return "Cadastro realizado com sucesso!";
        } catch (SQLException ex) {
            return "Erro ao cadastrar!";
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conectar();
        try {
            prep = conn.prepareStatement("SELECT * FROM produtos");
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagem.add(produto);
            }

        } catch (SQLException e) {

        }
        return listagem;
    }

    public String venderProduto(int id) {
        try {
            conectar();

            prep = conn.prepareStatement("UPDATE produtos SET status = 'Vendido' WHERE produtos.id = ?");
            prep.setInt(1, id);
            prep.executeUpdate();
            return "Atualizado com sucesso";
        } catch (SQLException e) {
            return "Erro ao atualizar";
        }
    }

}
