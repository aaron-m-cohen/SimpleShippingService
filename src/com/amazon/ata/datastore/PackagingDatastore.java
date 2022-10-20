package com.amazon.ata.datastore;

import com.amazon.ata.types.Box;
import com.amazon.ata.types.FcPackagingOption;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.PolyBag;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Stores all configured packaging pairs for all fulfillment centers.
 */
public class PackagingDatastore {

    /**
     * The stored pairs of fulfillment centers to the packaging options they support.
     */
    private final List<FcPackagingOption> fcPackagingOptions = Arrays.asList(
            createFcPackagingOption("IND1", new Box(new BigDecimal("10"), new BigDecimal("10"), new BigDecimal("10"))),
            createFcPackagingOption("ABE2", new Box(new BigDecimal("20"), new BigDecimal("20"), new BigDecimal("20"))),
            createFcPackagingOption("ABE2", new Box(new BigDecimal("40"), new BigDecimal("40"), new BigDecimal("40"))),
            createFcPackagingOption("YOW4", new Box(new BigDecimal("20"), new BigDecimal("20"), new BigDecimal("20"))),
            createFcPackagingOption("YOW4", new Box(new BigDecimal("10"), new BigDecimal("10"), new BigDecimal("10"))),
            createFcPackagingOption("YOW4", new Box(new BigDecimal("60"), new BigDecimal("60"), new BigDecimal("60"))),
            createFcPackagingOption("IAD2", new Box(new BigDecimal("20"), new BigDecimal("20"), new BigDecimal("20"))),
            createFcPackagingOption("IAD2", new Box(new BigDecimal("20"), new BigDecimal("20"), new BigDecimal("20"))),
            createFcPackagingOption("PDX1", new Box(new BigDecimal("40"), new BigDecimal("40"), new BigDecimal("40"))),
            createFcPackagingOption("PDX1", new Box(new BigDecimal("60"), new BigDecimal("60"), new BigDecimal("60"))),
            createFcPackagingOption("PDX1", new Box(new BigDecimal("60"), new BigDecimal("60"), new BigDecimal("60"))),
            createFcPackagingOption("IAD2", new PolyBag(new BigDecimal("2000"))),
            createFcPackagingOption("IAD2", new PolyBag(new BigDecimal("10000"))),
            createFcPackagingOption("IAD2", new PolyBag(new BigDecimal("5000"))),
            createFcPackagingOption("YOW4", new PolyBag(new BigDecimal("2000"))),
            createFcPackagingOption("YOW4", new PolyBag(new BigDecimal("5000"))),
            createFcPackagingOption("YOW4", new PolyBag(new BigDecimal("10000"))),
            createFcPackagingOption("IND1", new PolyBag(new BigDecimal("2000"))),
            createFcPackagingOption("IND1", new PolyBag(new BigDecimal("5000"))),
            createFcPackagingOption("ABE2", new PolyBag(new BigDecimal("2000"))),
            createFcPackagingOption("ABE2", new PolyBag(new BigDecimal("6000"))),
            createFcPackagingOption("PDX1", new PolyBag(new BigDecimal("5000"))),
            createFcPackagingOption("PDX1", new PolyBag(new BigDecimal("10000"))),
            createFcPackagingOption("YOW4", new PolyBag(new BigDecimal("5000")))
    );

    /**
     * Create fulfillment center packaging option from provided parameters.
     */
    private FcPackagingOption createFcPackagingOption(String fcCode, Packaging packaging) {
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(fcCode);

        return new FcPackagingOption(fulfillmentCenter, packaging);
    }

    public List<FcPackagingOption> getFcPackagingOptions() {
        return fcPackagingOptions;
    }
}
