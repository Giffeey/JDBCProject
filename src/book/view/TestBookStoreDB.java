package book.view;

import book.controller.BookController;
import book.model.Book;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestBookStoreDB {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        int x = 0;
        do {
            try {

                BookController bookCtrl = new BookController("book", "book");

                System.out.println("--------------------- M E N U ---------------------");
                System.out.println("PRESS 1 to 'Create' booklist");
                System.out.println("PRESS 2 to 'Drop' booklist");
                System.out.println("PRESS 3 to 'Insert' book");
                System.out.println("PRESS 4 to 'Update' book");
                System.out.println("PRESS 5 to 'Delete' book");
                System.out.println("PRESS 6 to 'Show' booklist");
                System.out.println("PRESS 7 to 'Search' book");
                System.out.println("PRESS 0 to exit program");
                System.out.println("---------------------------------------------------");
                System.out.print("What do you want to do? : ");
                x = input.nextInt();

                if (x > 7 || x < 0) {
                    System.out.println("!-- Please press only number in the menu --!");
                    System.out.print("What do you want to do? : ");
                    x = input.nextInt();
                }

                switch (x) {
                    //Create option
                    case 1:
                        bookCtrl.createBookTable();
                        break;

                    //Drop option
                    case 2:
                        System.out.print("Do you want to drop table (booklist)? (Y/N): ");
                        String yN = input.next();
                        if(yN.equalsIgnoreCase("Y"))
                            bookCtrl.dropBookTable();
                        else
                            System.out.println("Cancel droping booklist");
                        break;

                    //Insert option
                    case 3:
                        String ans;
                        System.out.print("Do you want to insert from file? (Y/N): ");
                        //Y -- insert from file , N -- insert from keyboard
                        ans = input.next();
                        if (!ans.equalsIgnoreCase("N") && !ans.equalsIgnoreCase("Y")) {
                            System.out.println("!-- Please press only 'Y' or 'N' --!");
                            System.out.print("Do you want to insert from file? (Y/N): ");
                            ans = input.next();
                        }

                        if (ans.equalsIgnoreCase("N")) {
                            System.out.println("Please type book infomation");
                            System.out.print("ISBN: ");
                            long isbn = input.nextLong();
                            System.out.print("Book Name: ");
                            input.nextLine();
                            String bookName = input.nextLine().toLowerCase();
                            System.out.print("Author: ");
                            String author = input.nextLine().toLowerCase();
                            System.out.print("Publisher: ");
                            String publisher = input.nextLine().toLowerCase();
                            System.out.print("Category: ");
                            String category = input.nextLine().toLowerCase();
                            System.out.print("Price (baht): ");
                            double price = input.nextDouble();
                            
                            Book book = new Book(isbn, bookName, author, publisher, category, price);
                            System.out.println(book);

                            bookCtrl.insertBook(book);

                        } else if (ans.equalsIgnoreCase("Y")) {
                            System.out.print("Please type file name: ");
                            String fileName = input.next();

                            bookCtrl.insertBookFromFile(fileName);

                        }

                        break;
                    //Update option
                    case 4:
                        //show booklist
                        ArrayList<Book> bookList = bookCtrl.selectAllBook();
                        System.out.println("------------------B O O K L I S T------------------");
                        for (int i = 0; i < bookList.size(); i++) {
                            System.out.println((i+1)+") "+bookList.get(i));
                        }
                        System.out.println("---------------------------------------------------");
                        //update
                        System.out.print("Which book do you want to update? (ISBN): ");
                        long bUpdate = input.nextLong();
                        String update = bUpdate+"";
                        int c = 0;
                        for (int i = 0; i < bookList.size(); i++) {
                            if (!(update.equalsIgnoreCase(bookList.get(i).getIsbn()+""))) {
                                c += 0;
                            } else {
                                c += 1;
                            }
                        }

                        if (c == 1) {
                            int index;
                            String sentence;
                            double bookPrice;

                            System.out.println("----------------U P D A T E  M E N U----------------");

                            System.out.println("PRESS 1: Book Name");
                            System.out.println("PRESS 2: Author");
                            System.out.println("PRESS 3: Publisher");
                            System.out.println("PRESS 4: Category");
                            System.out.println("PRESS 5: Price");
                            System.out.println("PRESS 0: Cancel updating book");
                            System.out.println("---------------------------------------------------");
                            System.out.print("What do you want to update? : ");
                            index = input.nextInt();
                            bookCtrl.executeSQLFromUser("select * from booklist where isbn=" + bUpdate);

                            switch (index) {
                                //Cancel update
                                case 0:
                                    System.out.println("Cancel updating book");
                                    break;
                                //Update bookName
                                case 1:
                                    input.nextLine();
                                    System.out.print("Book Name: ");
                                    sentence = input.nextLine().toLowerCase();
                                    bookCtrl.executeSQLFromUser("update booklist set bookName='" + sentence 
                                            + "' where isbn=" + bUpdate);
                                    break;

                                //Update author
                                case 2:
                                    System.out.print("Author: ");
                                    input.nextLine();
                                    sentence = input.nextLine().toLowerCase();
                                    bookCtrl.executeSQLFromUser("update booklist set author='" + sentence 
                                            + "' where isbn=" + bUpdate);
                                    break;

                                //Update publisher
                                case 3:
                                    System.out.print("Publisher: ");
                                    input.nextLine();
                                    sentence = input.nextLine().toLowerCase();
                                    bookCtrl.executeSQLFromUser("update booklist set publisher='" + sentence 
                                            + "' where isbn=" + bUpdate);

                                    break;

                                //Update category
                                case 4:
                                    System.out.print("Category: ");
                                    input.nextLine();
                                    sentence = input.nextLine().toLowerCase();
                                    bookCtrl.executeSQLFromUser("update booklist set category='" + sentence 
                                            + "' where isbn=" + bUpdate);

                                //Update price
                                case 5:
                                    System.out.print("Price: ");
                                    bookPrice = input.nextDouble();
                                    bookCtrl.executeSQLFromUser("update booklist set price=" + bookPrice 
                                            + " where isbn=" + bUpdate);
                                    break;

                            }
                            bookCtrl.executeSQLFromUser("select * from booklist where isbn=" + bUpdate);
                        } else {
                            System.out.println("No data in system");
                        }
                        break;

                    //Delete option
                    case 5:
                        //show booklist
                        bookList = bookCtrl.selectAllBook();
                        System.out.println("------------------B O O K L I S T------------------");
                        for (int i = 0; i < bookList.size(); i++) {
                            System.out.println((i+1)+") " +bookList.get(i));
                        }
                        System.out.println("---------------------------------------------------");
                        //delete
                        int f = 0;
                        System.out.print("Which book do you want to delete? (ISBN): ");
                        long bDelete = input.nextLong();
                        String delete = bDelete+"";
                        for (int i = 0; i < bookList.size(); i++) {
                            if (!(delete.equalsIgnoreCase(bookList.get(i).getIsbn()+""))) {
                                f += 0;
                            } else {
                                f += 1;
                            }
                        }

                        if (f == 1) {
                            bookCtrl.deleteBook("select * from booklist where isbn=" + bDelete);
                            int z = 0;
                            do {
                                System.out.print("Do you want to delete this book? (Y/N): ");
                                String option = input.next().trim();
                                if (option.equalsIgnoreCase("Y")) {
                                    bookCtrl.deleteBook("delete from booklist where isbn=" + bDelete);
                                    z = 0;
                                } else if (option.equalsIgnoreCase("N")) {
                                    System.out.println("cancel deleting book");
                                    z = 0;
                                } else {
                                    System.out.println("!-- Please press only 'Y' or 'N' --!");
                                    z++;
                                }
                            } while (z != 0);
                        } else {
                            System.out.println("No data in system");
                        }
                        break;

                    //Show (Select All)
                    case 6:
                        bookList = bookCtrl.selectAllBook();
                        System.out.println("------------------B O O K L I S T------------------");
                        for (int i = 0; i < bookList.size(); i++) {
                            System.out.println((i+1)+") "+bookList.get(i));
                        }
                        break;

                    //Search (Select)
                    case 7:

                        System.out.println("----------------S E A R C H L I S T----------------");
                        System.out.println("PRESS 1: ISBN");
                        System.out.println("PRESS 2: Book Name");
                        System.out.println("PRESS 3: Author");
                        System.out.println("PRESS 4: Publisher");
                        System.out.println("PRESS 5: Category");
                        System.out.println("PRESS 6: Price");
                        System.out.println("PRESS 0: Cancel searching book");
                        System.out.println("---------------------------------------------------");
                        System.out.print("Which one that you use to search: ");
                        int option = input.nextInt();

                        switch (option) {
                            //cancel
                            case 0:
                                System.out.println("Cancel Searching book");
                                break;

                            //use isbn
                            case 1:
                                System.out.print("ISBN: ");
                                long inputIsbn = input.nextLong();
                                String sql = "select * from booklist where isbn=" + inputIsbn;

                                bookList = bookCtrl.selectSomeBook(sql);
                                System.out.println("-------------S E A R C H   O U T P U T-------------");
                                for (int i = 0; i < bookList.size(); i++) {
                                    if (bookList.size() == 1) {
                                        System.out.println(bookList.get(i));
                                    } else {
                                        System.out.println((i+1) + ") " + bookList.get(i));
                                    }
                                }
                                if (bookList.isEmpty()) {
                                    System.out.println("No data in system");
                                }
                                break;

                            //use bookName
                            case 2:
                                System.out.print("Book Name: ");
                                input.nextLine();
                                String inputBName = input.nextLine().toLowerCase();
                                sql = "select * from booklist where bookName like '%" + inputBName + "%'";

                                bookList = bookCtrl.selectSomeBook(sql);
                                System.out.println("-------------S E A R C H   O U T P U T-------------");
                                for (int i = 0; i < bookList.size(); i++) {
                                    if (bookList.size() == 1) {
                                        System.out.println(bookList.get(i));
                                    } else {
                                        System.out.println((i+1) + ") " + bookList.get(i));
                                    }
                                }
                                if (bookList.isEmpty()) {
                                    System.out.println("No data in system");
                                }
                                break;

                            //use author
                            case 3:
                                System.out.print("Author: ");
                                input.nextLine();
                                String inputAuthor = input.nextLine().toLowerCase();
                                sql = "select * from booklist where author like '%" + inputAuthor + "%'";

                                bookList = bookCtrl.selectSomeBook(sql);
                                System.out.println("-------------S E A R C H   O U T P U T-------------");
                                for (int i = 0; i < bookList.size(); i++) {
                                    if (bookList.size() == 1) {
                                        System.out.println(bookList.get(i));
                                    } else {
                                        System.out.println((i+1) + ") " + bookList.get(i));
                                    }
                                }
                                if (bookList.isEmpty()) {
                                    System.out.println("No data in system");
                                }
                                break;

                            //use publisher
                            case 4:
                                System.out.print("Publisher: ");
                                input.nextLine();
                                String inputPb = input.nextLine().toLowerCase();
                                sql = "select * from booklist where publisher like '%" + inputPb + "%'";

                                bookList = bookCtrl.selectSomeBook(sql);
                                System.out.println("-------------S E A R C H   O U T P U T-------------");
                                for (int i = 0; i < bookList.size(); i++) {
                                    if (bookList.size() == 1) {
                                        System.out.println(bookList.get(i));
                                    } else {
                                        System.out.println((i+1) + ") " + bookList.get(i));
                                    }
                                }
                                if (bookList.isEmpty()) {
                                    System.out.println("No data in system");
                                }
                                break;

                            //use category  
                            case 5:
                                System.out.print("Category: ");
                                input.nextLine();
                                String inputCate = input.nextLine().toLowerCase();
                                sql = "select * from booklist where category like '%" + inputCate + "%'";

                                bookList = bookCtrl.selectSomeBook(sql);
                                System.out.println("-------------S E A R C H   O U T P U T-------------");
                                for (int i = 0; i < bookList.size(); i++) {
                                    if (bookList.size() == 1) {
                                        System.out.println(bookList.get(i));
                                    } else {
                                        System.out.println((i+1) + ") " + bookList.get(i));
                                    }
                                }
                                if (bookList.isEmpty()) {
                                    System.out.println("No data in system");
                                }
                                break;

                            //use price
                            case 6:
                                System.out.print("Price: ");
                                double inputPrice = input.nextDouble();
                                sql = "select * from booklist where price=" + inputPrice;

                                bookList = bookCtrl.selectSomeBook(sql);
                                System.out.println("-------------S E A R C H   O U T P U T-------------");
                                for (int i = 0; i < bookList.size(); i++) {
                                    if (bookList.size() == 1) {
                                        System.out.println(bookList.get(i));
                                    } else {
                                        System.out.println((i+1) + ") " + bookList.get(i));
                                    }
                                }
                                if (bookList.isEmpty()) {
                                    System.out.println("No data in system");
                                }
                                break;
                        }
                        break;
                }

                bookCtrl.closeConnection();
            } catch (ClassNotFoundException cnf) {
                System.out.println(cnf);
            } catch (FileNotFoundException fnf) {
                System.out.println(fnf);
            } catch (SQLException sql) {
                System.out.println(sql);
            } catch (InputMismatchException imm) {
                System.out.println(imm);
                input.nextLine();
                System.out.println("!-- Invalid input --!");
                x = 8;
            }

        } while (x != 0);

        System.out.println("-------------- E N D   P R O G R A M --------------");

    }

}
