package org.example;

import java.util.Scanner;

public class BookStoreApp {
    private static BookDao bookDao = new BookDaoImpl(JDBConnection.getConnection());
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int choice;
        do{
            System.out.println("""
                    Welcome to BookStore
                    Enter your Option please
                    1 - Add a new Book
                    2 - Update a Book
                    3 - Delete a Book
                    4 - Display a Book
                    5 - Display all Books
                    6 - Exit the program
                    """);
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addBook(sc);
                    break;
                case 2:
                    updateBook(sc);
                    break;
                case 3:
                    deleteBook(sc);
                    break;
                case 4:
                    readBookById(sc);
                    System.out.println("Please enter book id ");
                    int bookId = sc.nextInt();
                    Book book = bookDao.getBookById(bookId);
                    if (book == null) {
                        System.out.println("No book");
                    } else
                    System.out.println(book);
                    break;
                case 5:
                    displayBooks(sc);
                    break;
                case 6:
                    System.out.println("Exiting the app");
                    break;
                default:
                    System.out.println("Wrong input");
            }
        } while (choice != 6);

    }

    private static void displayBooks(Scanner sc) {
        for (Book book: bookDao.getAllBooks()) {
            System.out.println(book);

        }
    }

    private static void readBookById(Scanner sc) {

    }

    private static void deleteBook(Scanner sc) {
        System.out.println("Enter a book id to delete ");
        int bookId = sc.nextInt();
        sc.nextLine();
        Book book = bookDao.getBookById(bookId);
        if (book == null) {
            System.out.println("No book found ");
        } else bookDao.deleteBook(bookId);
        System.out.println("Book deleted successfully");

    }

    private static void updateBook(Scanner sc) {
        System.out.println("Enter book id to update ");
        int bookId = sc.nextInt();
        sc.nextLine();
        Book book = bookDao.getBookById(bookId);
        if (book == null){
            System.out.println("Book not found");
        } else {
            System.out.println("Enter new book title (or press Enter to skip): ");
            String title = sc.nextLine();
            System.out.println("Enter new book author (or press Enter to skip): ");
            String author = sc.nextLine();
            System.out.println("Enter new book genre (or press Enter to skip): ");
            String genre = sc.nextLine();
            System.out.println("Enter new book title (or press 0 to skip): ");
            double price = sc.nextDouble();
            sc.nextLine();
            book.setTitle(title.isEmpty()? book.getTitle() : title);
            book.setAuthor(author.isEmpty()? book.getAuthor() : author);
            book.setGenre(genre.isEmpty()? book.getGenre() : genre);
            book.setPrice(price == 0 ? book.getPrice() : price);

            //update the book
            bookDao.updateBook(book);
            System.out.println("Book updated successfully");

        }
    }

    private static void addBook(Scanner sc) {
        System.out.println("Please enter Book title ");
        String title = sc.nextLine();
        System.out.println("Please enter a book author");
        String author = sc.nextLine();

        System.out.println("Please enter Book genre ");
        String genre = sc.nextLine();
        System.out.println("Please enter Book price");
        double price = sc.nextDouble();
        Book book = new Book(title, author, genre, price);
        bookDao.addBook(book);
        System.out.println("Book added successfully ");
    }
}