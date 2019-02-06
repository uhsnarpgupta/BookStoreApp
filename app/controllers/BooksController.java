package controllers;

import model.Book;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.books.create;
import views.html.books.edit;
import views.html.books.index;
import views.html.books.show;

import javax.inject.Inject;
import java.util.Set;

public class BooksController extends Controller {

    @Inject
    FormFactory formFactory;

    //for all books
    public Result index() {
        Set<Book> books = Book.allBooks();
        return ok(index.render(books));
    }

    //to create a book
    public Result create() {
        Form<Book> bookForm = formFactory.form(Book.class);
        return ok(create.render(bookForm));
    }

    //to save book
    public Result save() {
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        Book book = bookForm.get();
        Book.add(book);
        return redirect(routes.BooksController.index());
    }

    //to edit a book
    public Result edit(int id) {
        Book book = Book.findById(id);
        if (book == null) {
            return notFound("Book Not Found");
        }
        Form<Book> bookForm = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookForm));
    }

    //update book
    public Result update() {
        Book book = formFactory.form(Book.class).bindFromRequest().get();
        Book oldBook = Book.findById(book.id);
        if (book == null) {
            return notFound("Book Not Found");
        }
        oldBook.title = book.title;
        oldBook.price = book.price;
        oldBook.author = book.author;
        return redirect(routes.BooksController.index());
    }

    //delete book
    public Result destroy(int id) {
        Book book = Book.findById(id);
        if (book == null) {
            return notFound("Book Not Found");
        }
        Book.remove(book);
        return redirect(routes.BooksController.index());
    }

    //display book details
    public Result show(int id) {
        Book book = Book.findById(id);
        if (book == null) {
            return notFound("Book Not Found");
        }
        return ok(show.render(book));
    }
}
