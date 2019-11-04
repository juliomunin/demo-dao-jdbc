package application;

import model.entities.Departament;
import model.entities.Seller;
import modeo.dao.DaoFactory;
import modeo.dao.SellerDao;

import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args){

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


    }
}
