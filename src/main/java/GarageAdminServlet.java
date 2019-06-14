import se.rifr.service.GarageServiceImpl;
import se.rifr.service.GarageService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GarageAdminServlet extends HttpServlet {

    private String title = "Garage Enter / Leaving";

    GarageService garageAdmin = new GarageServiceImpl();

    String regid    = "";
    String garageid = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pos = request.getParameter("pos");

        String message = "";
        if (pos == null) {
            garageid = "";
            garageAdmin.LoadReloadData();
            message = vehicleLoginScr(); //loginScr();
        } else {
            System.out.println(pos);

            //garageAdmin.listGarages();
            //garageAdmin.listVehicles();

           switch (pos) {
                case "m":
                    message = vehicleLoginScr(); //loginScr();
                    break;
                case "0": // from vehicleLoginScr(), param cardid
                    regid    = request.getParameter("regid").trim();
                    garageid = request.getParameter("garageid").trim();
                    //System.out.println(garageid +":" +regid);

                    String res;
                    if (!garageAdmin.garageExists(garageid)) {
                        System.out.println(garageid +": NOT FOUND");
                        message  = vehicleLoginScr();
                        garageid = "";
                    } else if (garageAdmin.isRegistered(regid)) {
                        if (garageAdmin.isParked(regid)) {
                            System.out.println(regid +": PARKED");
                            res = garageAdmin.unparkVehicle(garageAdmin.getVehicle(regid));
                        }
                        else {
                            System.out.println(regid +": NOT PARKED");
                            res = garageAdmin.parkVehicle(garageAdmin.getVehicle(regid), garageAdmin.getGarage(garageid));
                        }

                        if (res.isEmpty()) {
                            System.out.println(regid +": REQUEST FAILED");
                            message = vehicleLoginScr();
                        }
                        else message = InfoMenu("Parking slot" + res, "m", "");
                    } else {
                        System.out.println(regid +": NOT REGISTERED");
                        message = getCustomer();
                    }
                     break;
                case "1": // from getCustomer, param pnr
                    String pnr = request.getParameter("pnr").trim();
                    System.out.println(garageid +":" + regid +":" +pnr);
                    if (garageAdmin.customerExists(pnr)) {
                        System.out.println(pnr +": Customer exists");
                        message = createVehicleMenu (pnr);
                    } else {
                        System.out.println(pnr +": unknown Customer");
                        message = createCustomerMenu(pnr);
                    }
                     break;
                case "2": // from createVehicleMenu, param
                    String pnr2    = request.getParameter("pnr");
                    String kind    = request.getParameter("kind");
                    String model2  = request.getParameter("model");
                    String colour3 = request.getParameter("colour");

                    System.out.println(pnr2+":" +kind+":"+model2 +":"+colour3);

                    garageAdmin.createVehicle(regid,kind,model2, colour3,garageAdmin.getCustomer(pnr2));

                    message = vehicleLoginScr(); //loginScr();
                    break;
                case "3": // from createCustomerMenu, param firstname, lastname, email, telephone
                    String pnr3 = request.getParameter("pnr");
                    String firstname3 = request.getParameter("firstname");
                    String lastname3 = request.getParameter("lastname");
                    String email3 = request.getParameter("email");
                    String telephone3 = request.getParameter("telephone");

                    System.out.println(firstname3+":" +lastname3+":"+pnr3 +":"+email3+":"+telephone3);

                    garageAdmin.createCustomer(request.getParameter("firstname"),
                                               request.getParameter("lastname"),
                                               request.getParameter("pnr"),
                                               request.getParameter("email"),
                                               request.getParameter("telephone"));

                    message = createVehicleMenu (pnr3);
                    break;
                }
            }

            // Set response content type
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String docType =
                    "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

            out.println(docType +
                    "<html>\n" +
                    "<head><title>" + title + "</title></head>\n" +
                    "<body bgcolor = \"#f0f0f0\">\n" +
                    "<h1 align = \"center\">" + title + "</h1>\n" +
                    message +
                    "</body>" +
                    "</html>"
            );
        }

        private String vehicleLoginScr() {

            String returnStr;
            returnStr = "<form action = \"main.java.GarageAdminServlet\" method = \"GET\">\n";
            returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"0\">\n";
            if (!garageid.isEmpty()) {
                returnStr += "    <input type = \"hidden\" name = \"garageid\" value = \"" + garageid + "\">\n";
            }
            returnStr += " Registration No : <input type = \"text\" name = \"regid\">\n";
            returnStr += "    <br />";
            if (garageid.isEmpty()) {
                returnStr += " Garage Name....: <input type = \"text\" name = \"garageid\">\n";
                returnStr += "    <br />";
            }
            returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
            returnStr += "</form>";
            title = "Vehicle Scanning";
            return returnStr;
        }

        private String getCustomer () {
            String returnStr;
            returnStr = "<form action = \"main.java.GarageAdminServlet\" method = \"GET\">\n";
            returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"1\">\n";
            returnStr += "    Customer : <input type = \"text\" name = \"pnr\">\n";
            returnStr += "    <br />";
            returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
            returnStr += "</form>";
            title = "Get Customer";
            return returnStr;

        }

    private String createVehicleMenu (String pnr) {

        String kind   = "";
        String model  = "";
        String colour = "";
        String returnStr;
        returnStr = "<form action = \"main.java.GarageAdminServlet\" method = \"GET\">\n";
        returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"2\">\n";
        returnStr += "    <input type = \"hidden\" name = \"pnr\" value = \"" + pnr + "\">\n";
        returnStr += "    Kind: <input type = \"text\" name = \"kind\" value = \"" + kind + "\">\n";
        returnStr += "    <br />";
        returnStr += "    Model: <input type = \"text\" name = \"model\" value = \"" + model + "\">\n";
        returnStr += "    <br />";
        returnStr += "    Colour: <input type = \"text\" name = \"colour\" value = \"" + colour + "\">\n";
        returnStr += "    <br />";
        returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
        returnStr += "</form>";
        title = "MENU add Vehicle ";

        return returnStr;

    }
    private String createCustomerMenu (String pnr) {

        String firstName   = "";
        String lastName    = "";
        String email       = "";
        String telephoneNo = "";
        String returnStr;
        returnStr = "<form action = \"main.java.GarageAdminServlet\" method = \"GET\">\n";
        returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"3\">\n";
        returnStr += "    <input type = \"hidden\" name = \"pnr\" value = \"" + pnr + "\">\n";
        returnStr += " First Name: <input type = \"text\" name = \"firstname\" value = \"" + firstName + "\">\n";
        returnStr += "    <br />";
        returnStr += " Last Name.: <input type = \"text\" name = \"lastname\" value = \"" + lastName + "\">\n";
        returnStr += "    <br />";
        returnStr += " email.....: <input type = \"text\" name = \"email\" value = \"" + email + "\">\n";
        returnStr += "    <br />";
        returnStr += " Telephone.: <input type = \"text\" name = \"telephone\" value = \"" + telephoneNo + "\">\n";
        returnStr += "    <br />";
        returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
        returnStr += "</form>";
        title = "MENU add customer ";

        return returnStr;

        }

        private String InfoMenu (String infoLine1, String pos, String hiddenLine1){

            String returnStr;
            returnStr = "<form action = \"main.java.GarageAdminServlet\" method = \"GET\">\n";
            //returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"m\">\n";
            returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"" + pos + "\">\n";
            if (!hiddenLine1.isEmpty())
                returnStr += hiddenLine1;
            returnStr += "    <h1> " + infoLine1 + " </h1>\n";
            returnStr += "    <br />";
            returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
            returnStr += "</form>";
            title = "Info Menu ";

            return returnStr;

        }
    }
