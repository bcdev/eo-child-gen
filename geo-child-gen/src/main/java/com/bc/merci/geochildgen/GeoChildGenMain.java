/**
 *
 *
 */
package com.bc.merci.geochildgen;

import com.bc.childgen.ChildGenException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


/**
 * @author Ralf Quast (ralf.quast@brockmann-consult.de)
 */
public class GeoChildGenMain {

    public static void main(String[] args) {
        try {
            CmdLineParams params = CmdLineParser.parse(args);
            GeoChildGen.run(params);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            GeoChildGen.printUsageTo(System.out);
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ChildGenException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
