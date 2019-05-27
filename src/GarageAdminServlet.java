import se.rifr.GarageAdmin;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GarageAdminServlet extends HttpServlet {

    private String title = "Garage Enter / Leaving";
    GarageAdmin GarageAdmin = new GarageAdmin();

    String regid    = "";
    String garageid = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pos = request.getParameter("pos");

        String message = "";
        if (pos == null)
            message = vehicleLoginScr(); //loginScr();
        else {
            System.out.println(pos);
            switch (pos) {
                case "m":
                    message = vehicleLoginScr(); //loginScr();
                    break;
                case "0": // from vehicleLoginScr(), param cardid
                    regid    = request.getParameter("regid");
                    garageid = request.getParameter("garageid");
                    String res;
                    if (GarageAdmin.isRegistered(regid)) {
                        if (GarageAdmin.isParked(regid)) {
                            res = GarageAdmin.unparkVehicle(GarageAdmin.getVehicle(regid));
                        } else {
                            res = GarageAdmin.parkVehicle(GarageAdmin.getVehicle(regid), GarageAdmin.getGarage(garageid));
                        }
                        if (res.isEmpty()) vehicleLoginScr();
                        else message = InfoMenu("Parking slot" + res, "m", "");
                    } else
                        message = getCustomer();
                    break;
                case "1": // from , param
                    message = vehicleLoginScr(); //loginScr();
                    break;
                case "2": // from, param
                    message = vehicleLoginScr(); //loginScr();
                    break;
                case "3": // from , param
                    message = vehicleLoginScr(); //loginScr();
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
            returnStr = "<form action = \"GarageAdminServlet\" method = \"GET\">\n";
            returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"0\">\n";
            returnStr += "    Registration Number : <input type = \"text\" name = \"regid\">\n";
            returnStr += "    <br />";
            returnStr += "    Garage Name : <input type = \"text\" name = \"garageid\">\n";
            returnStr += "    <br />";
            returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
            returnStr += "</form>";
            title = "Vehicle Scanning";
            return returnStr;
        }

        private String getCustomer () {
            String returnStr;
            returnStr = "<form action = \"GarageAdminServlet\" method = \"GET\">\n";
            returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"1\">\n";
            returnStr += "    Customer : <input type = \"text\" name = \"pnr\">\n";
            returnStr += "    <br />";
            returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
            returnStr += "</form>";
            title = "Get Csutomer";
            return returnStr;

        }

        private String createCustomerMenu () {

            String amount = "";
            String returnStr;
            returnStr = "<form action = \"GarageAdminServlet\" method = \"GET\">\n";
            returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"3\">\n";
            returnStr += "    Amount: <input type = \"text\" name = \"amount\" value = \"" + amount + "\">\n";
            returnStr += "    <br />";
            returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
            returnStr += "</form>";
            title = "MENU create customer ";

            return returnStr;

        }

        private String InfoMenu (String infoLine1, String pos, String hiddenLine1){

            String returnStr;
            returnStr = "<form action = \"GarageAdminServlet\" method = \"GET\">\n";
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
