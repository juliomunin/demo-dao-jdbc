package modeo.dao;

import java.sql.SQLException;
import java.util.List;

import model.entities.Departament;
import model.entities.Seller;

public interface SellerDao {

    void insert(Seller obj) throws SQLException;
    void update(Seller obj);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Departament departament);

    }


