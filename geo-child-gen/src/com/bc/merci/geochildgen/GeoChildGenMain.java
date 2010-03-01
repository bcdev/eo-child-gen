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
            GeoChildGen.printUsageTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ChildGenException e) {
            e.printStackTrace();
        }
    }
}
