package application;

import model.entities.Seller;
import modeo.dao.DaoFactory;
import modeo.dao.SellerDao;

import java.util.Date;

public class Program {

    public static void main(String[] args){

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);

    }
}
