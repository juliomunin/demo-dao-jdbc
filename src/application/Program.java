package application;

import model.entities.Departament;
import model.entities.Seller;
import modeo.dao.DaoFactory;
import modeo.dao.SellerDao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args) throws SQLException {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== Teste nº1: Seller findById ====");
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);

        System.out.println("\n=== Teste nº2: Departament findById ====");
        Departament departament = new Departament(2, null);
        List<Seller> list = sellerDao.findByDepartment(departament);
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=== Teste nº3: Departament findAll ====");
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=== Teste nº4: Seller Insert ====");
        Seller newSeller = new Seller(1, "Matheus", "matheus@gmail.com", new Date(), 4000.0, departament);
        sellerDao.insert(newSeller);
        System.out.println("Insert "+ newSeller.getId() +" realizado com sucesso");


        System.out.println("\n=== Teste nº5: Seller update ====");
        seller = sellerDao.findById(5);
        seller.setName("Martha");
        sellerDao.update(seller);
        System.out.println("Atualização "+ seller +" realizado com sucesso");
    }
}
