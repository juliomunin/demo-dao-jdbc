package model.dao.impl;

import com.mysql.cj.protocol.Resultset;
import db.DB;
import db.DbException;
import model.entities.Departament;
import model.entities.Seller;
import modeo.dao.SellerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) throws SQLException {
        PreparedStatement st =null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                         +"(Nome, Email, BirthDate, BaseSalary, DepartmentId) "
                         +"VALUES "
                         +"(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){

                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DbException("Erro Inexperado! Nenhuma linha inserida!");
            }
        }
        catch (SQLException e ){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st =null;
        try{
            st = conn.prepareStatement(
                    "UPDATE seller "
                            +"SET Nome = ?, Email = ?, BaseSalary = ?, BirthDate = ?, DepartmentId = ? "
                            +"WHERE id = ?" );

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDouble(3, obj.getBaseSalary());
            st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setInt(5, obj.getDepartament().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();

        }
        catch (SQLException e ){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM Seller where Id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "Select seller.*,department.Name as DepName "
                    + "From seller  INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");

            st.setInt(1, id);
           rs = st.executeQuery();
            if (rs.next()){
                Departament dep = instantiateDepartament(rs);
                Seller obj = instantiateSeller(rs,dep);
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
           throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Departament dep) throws SQLException{
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Nome"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartament(dep);
        return obj;
    }

    private Departament instantiateDepartament(ResultSet rs) throws SQLException{
        Departament dep = new Departament();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "Select seller.*,department.Name as DepName "
                            + "From seller  INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Nome");

            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            while (rs.next()){

                Departament dep =map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartament(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Departament departament) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "Select seller.*,department.Name as DepName "
                            + "From seller  INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Nome");

            st.setInt(1, departament.getId());
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            while (rs.next()){

                Departament dep =map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartament(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }


}
