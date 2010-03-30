package com.bc.product.intersect;

public class ProductIntersectMain {

    public static void main(String[] args) {
        final CmdLineParams params = new CmdLineParams();
        params.parse(args);

        if (params.isPrintUsage()) {
            ProductIntersect.printUsageTo(System.out);
            return;
        }
                
        final ProductIntersect productIntersect = new ProductIntersect();
        productIntersect.run(params);
    }
}
