package book.model;
public class Book {
    private long isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String category;
    private double price;

    public Book(long isbn, String bookName, String author, String publisher,String category, double price) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.price = price;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + ", Book Name: " + bookName + ", Author: " + author + ", Publisher: " + publisher 
                + ", Category: " + category + ", Price: " + price + " baht";
    }

}
