package se.rifr;

public class Str {

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) { return String.format("%" + n + "s", s); }

    public static String pad(Character s, int n) {
        StringBuilder temp = new StringBuilder("");
        for (int i=0; i < n; i++)
            temp.append(s);
        return temp.toString();
    }

    public static boolean readAcceptedValue (String header, String value, int countMax) {

        int noOfTries = 0;

        try {
            do {

                StdIO.write(header);
                String read = StdIO.readLine();

                if (read.equals(value)) return true;

            } while (++noOfTries <= countMax);

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }

        return false;

    } // End readAcceptedValue

} // End package