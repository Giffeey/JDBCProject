package book.controller;

import java.sql.Connection;
import java.sql.SQLException;
import book.db.ConnectionManager;
import book.model.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class BookController {

    private Connection con;

    public BookController(String username, String psw) throws ClassNotFoundException, SQLException {
        con = ConnectionManager.createConnection("jdbc:derby://localhost:1527/BookStore", username, psw);
    }

    public void createBookTable() throws SQLException {
        String sql = "create table booklist"
                + "(isbn bigint,"
                + "bookName varchar(60),"
                + "author varchar(50),"
                + "publisher varchar(50),"
                + "category varchar(50),"
                + "price double,"
                + "primary key(isbn))";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        System.out.println("create booklist table successfully");
    }

    public void dropBookTable() throws SQLException {
        String sql = "drop table booklist";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        System.out.println("drop booklist table successfully");
    }

    public void insertBook(Book book) throws SQLException {
        String sql = "insert into booklist(isbn,bookName,author,publisher,category,price) values(" + book.getIsbn() + ",'"
                + book.getBookName() + "','" + book.getAuthor() + "','" + book.getPublisher() +"','"
                + book.getCategory()+ "'," + book.getPrice() + ")";

        Statement stmt = con.createStatement();
        int insertedRec = stmt.executeUpdate(sql);

        System.out.println("inserted " + insertedRec + " book successfully");
    }

    public void insertBookFromFile(String fileName) throws FileNotFoundException, SQLException {
        Scanner scFile = new Scanner(new File(fileName));
        scFile.useDelimiter(",");

        int insertedRec = 0;
        PreparedStatement pStmt = con.prepareStatement("insert into booklist(isbn,bookName,author,publisher,category,price)"
                + "values (?,?,?,?,?,?)");

        while (scFile.hasNext()) {
            long isbn = Long.parseLong(scFile.next().trim());
            String bookName = (scFile.next()).toLowerCase().trim();
            String author = (scFile.next()).toLowerCase().trim();
            String publisher = (scFile.next()).toLowerCase().trim();
            String category = (scFile.next()).toLowerCase().trim();
            double price = scFile.nextDouble();
            pStmt.setLong(1, isbn);
            pStmt.setString(2, bookName);
            pStmt.setString(3, author);
            pStmt.setString(4, publisher);
            pStmt.setString(5, category);
            pStmt.setDouble(6, price);

            insertedRec += pStmt.executeUpdate();
            
        }

        System.out.println("inserted " + insertedRec + " book successfully");
    }

    public void executeSQLFromUser(String sql) throws SQLException {
        Statement stmt = con.createStatement();
        boolean hasResultSet = stmt.execute(sql);
        if (hasResultSet) {
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                long bIsbn = rs.getLong("isbn");
                String bName = rs.getString("bookName");
                String bAuthor = rs.getString("author");
                String bPublisher = rs.getString("publisher");
                String bCategory = rs.getString("category");
                double bPrice = rs.getDouble("price");

                Book bookTemp = new Book(bIsbn, bName, bAuthor, bPublisher, bCategory, bPrice);
                System.out.println(bookTemp);
            }
        } else {

            int updatedRecs = stmt.getUpdateCount();
            System.out.println("Update " + updatedRecs + " book successfully");
        }

    }

    public void deleteBook(String sql) throws SQLException {
        Statement stmt = con.createStatement();
        boolean hasResultSet = stmt.execute(sql);
        if (hasResultSet) {
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                long bIsbn = rs.getLong("isbn");
                String bName = rs.getString("bookName");
                String bAuthor = rs.getString("author");
                String bPublisher = rs.getString("publisher");
                String bCategory = rs.getString("category");
                double bPrice = rs.getDouble("price");

                Book bookTemp = new Book(bIsbn, bName, bAuthor, bPublisher, bCategory, bPrice);
                System.out.println(bookTemp);
            }
        } else {
            int deleteRecs = stmt.getUpdateCount();
            System.out.println("Delete " + deleteRecs + " book successfully");
        }
    }

    public ArrayList<Book> selectAllBook() throws SQLException {
        ArrayList<Book> bookList = new ArrayList<Book>();

        Statement stmt = con.createStatement();
        String sql = "select * from booklist";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            long bIsbn = rs.getLong("isbn");
            String bName = rs.getString("bookName");
            String bAuthor = rs.getString("author");
            String bPublisher = rs.getString("publisher");
            String bCategory = rs.getString("category");
            double bPrice = rs.getDouble("price");

            Book bookTemp = new Book(bIsbn, bName, bAuthor, bPublisher, bCategory, bPrice);
            bookList.add(bookTemp);
        }
        return bookList;
    }

    public ArrayList<Book> selectSomeBook(String sql) throws SQLException {
        ArrayList<Book> bookList = new ArrayList<Book>();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            long bIsbn = rs.getLong("isbn");
            String bName = rs.getString("bookName");
            String bAuthor = rs.getString("author");
            String bPublisher = rs.getString("publisher");
            String bCategory = rs.getString("category");
            double bPrice = rs.getDouble("price");

            Book bookTemp = new Book(bIsbn, bName, bAuthor, bPublisher, bCategory, bPrice);
            bookList.add(bookTemp);
        }
        return bookList;
    }

    public void closeConnection() throws SQLException {
        ConnectionManager.closeConnection(con);
    }

}
