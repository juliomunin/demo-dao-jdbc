package application;

import model.entities.Departament;
import model.entities.Seller;
import modeo.dao.DaoFactory;
import modeo.dao.SellerDao;

import java.util.Date;

public class Program {

    public static void main(String[] args){

        Departament obj = new Departament(1, "Books");
        Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, obj);
        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println(seller);

    }
}
