/*
 * Created on 19.03.2004
 */
package com.bc.childgen;

import com.bc.childgen.modules.aatsr.AatsrConfig;
import com.bc.childgen.modules.meris.MerisFRConfig;
import com.bc.childgen.modules.meris.MerisFRSConfig;
import com.bc.childgen.modules.meris.MerisRRConfig;

/**
 * Factory class to create instances of an implementation of the <code>ChildGenerator</code> interface.
 *
 * @author Alexander Gruenewald (alexander.gruenewald@informus.de)
 */
public final class ChildGeneratorFactory {

    /**
     * Creates an instance of a <code>ChildGenerator</code> implementation.
     *
     * @param productType The product type name. Supported names start with "MER_RR", "MER_FR", "AT".
     * @return The new instance.
     * @throws ChildGenException on creation failues (e.g. unsupported format ..)
     */
    public static ChildGeneratorImpl createChildGenerator(String productType) throws ChildGenException {
        final ChildGeneratorImpl childGeneratorImpl = new ChildGeneratorImpl();

        final Config config = getConfigFor(productType);
        childGeneratorImpl.setConfig(config);

        return childGeneratorImpl;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    static Config getConfigFor(String productType) throws ChildGenException {
        ConfigImpl config = new ConfigImpl();
        final String fileNameCapitals = productType.toUpperCase();

        if (fileNameCapitals.startsWith("MER_RR")) {
            return new MerisRRConfig();
        } else if (fileNameCapitals.startsWith("MER_FR_")) {
           return new MerisFRConfig();
        } else if (fileNameCapitals.startsWith("MER_FRS")) {
            return new MerisFRSConfig();
        } else if (fileNameCapitals.startsWith("AT")) {
            return new AatsrConfig();
        } else {
            throw new ChildGenException("Invalid file name: " + productType);
        }
    }
}