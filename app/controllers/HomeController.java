package controllers;

import com.avaje.ebean.Model;
import model.Client;
import org.apache.commons.io.FileUtils;
import play.data.Form;
import play.mvc.*;


import views.html.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
        //return ok("Got request " + request().remoteAddress()+ "!");
        //return ok("it Work");
        //Result notFound = notFound("<h1>Page not found</h1>").as("text/html");
         //Result badRequest = badRequest(views.html.form.render(formWithErrors));
        //Result oops = internalServerError("Oops");//500 internal server
        //Result anyStatus = status(488, "Strange response type");
        //return  redirect("/message");
        //return  temporaryRedirect("/count");
        //String content = Page.getContentOf(page);
        //response().setContentType("text/html");
        //return ok(content);
    }

    public Result addClient() {
        Client client = Form.form(Client.class).bindFromRequest().get();
        client.save();
        return redirect(routes.HomeController.index());

    }

    public Result getClients(){
        List<Client> clients = new Model.Finder(String.class,Client.class).all();
        return  ok(toJson(clients));
    }

    public Result upload() throws IOException {


        System.out.println("UPLOAD --------------£££££££££££££");

        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");

        if (body == null) {
            return badRequest("Invalid request, required is POST with enctype=multipart/form-data.");
        }

        if (picture == null) {
            return badRequest("Invalid request, no file has been sent.");
        }

        if (picture != null) {
            System.out.println("NOT NULL");
            File f = new File(System.getProperty("user.home") +"/app/uploads/");
            if(!f.exists()) {
                try {
                    f.mkdir();
                } catch (SecurityException se) {
                    se.printStackTrace();
                }
            }


            String fileName = picture.getFilename();
            System.out.println("FILE NAME = "+fileName);
            String contentType = picture.getContentType();
            File file = picture.getFile();

            System.out.println(f.getPath());
            File destination = new File(System.getProperty("user.home") +"/app/uploads/", fileName);
            FileUtils.moveFile(file, destination);
            //destination.delete();
            return redirect("/count");
        } else {
            System.out.println(" NULL");
            flash("error", "Missing file");
            return badRequest();
        }
    }

}
