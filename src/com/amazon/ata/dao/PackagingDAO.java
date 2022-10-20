package com.amazon.ata.dao;

import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.FcPackagingOption;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Access data for which packaging is available at which fulfillment center.
 */
public class PackagingDAO {
    /**
     * A list of fulfillment centers with a packaging options they provide.
     */
    //private List<FcPackagingOption> fcPackagingOptions;

    private final Map<FulfillmentCenter, Set<FcPackagingOption>> fcPackagingOptionMap;

    /**
     * Instantiates a PackagingDAO object.
     * @param datastore Where to pull the data from for fulfillment center/packaging available mappings.
     */
    public PackagingDAO(PackagingDatastore datastore) {
        //this.fcPackagingOptions =  new ArrayList<>(datastore.getFcPackagingOptions());
        fcPackagingOptionMap = new HashMap<>();

        for (FcPackagingOption fcPackagingOption : datastore.getFcPackagingOptions()) {
            FulfillmentCenter fc = fcPackagingOption.getFulfillmentCenter();
            Set<FcPackagingOption> options;
            if (fcPackagingOptionMap.containsKey(fc)) {
                options = fcPackagingOptionMap.get(fc);
            } else {
                options = new HashSet<>();
            }
            options.add(fcPackagingOption);
            fcPackagingOptionMap.put(fc, options);
        }
    }

    /**
     * Returns the packaging options available for a given item at the specified fulfillment center. The API
     * used to call this method handles null inputs, so we don't have to.
     *
     * @param item the item to pack
     * @param fulfillmentCenter fulfillment center to fulfill the order from
     * @return the shipping options available for that item; this can never be empty, because if there is no
     * acceptable option an exception will be thrown
     * @throws UnknownFulfillmentCenterException if the fulfillmentCenter is not in the fcPackagingOptions list
     * @throws NoPackagingFitsItemException if the item doesn't fit in any packaging at the FC
     */
    public List<ShipmentOption> findShipmentOptions(Item item, FulfillmentCenter fulfillmentCenter)
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {

        // Check all FcPackagingOptions for a suitable Packaging in the given FulfillmentCenter
        List<ShipmentOption> result = new ArrayList<>();
        Set<FcPackagingOption> fcPackagingOptions = fcPackagingOptionMap.get(fulfillmentCenter);

        if (fcPackagingOptions == null) {
            throw new UnknownFulfillmentCenterException(
                    String.format("Unknown FC: %s!", fulfillmentCenter.getFcCode()));
        }

        for (FcPackagingOption fcPackagingOption : fcPackagingOptions) {
            Packaging packaging = fcPackagingOption.getPackaging();
            if (packaging.canFitItem(item)) {
                result.add(ShipmentOption.builder()
                        .withItem(item)
                        .withPackaging(packaging)
                        .withFulfillmentCenter(fulfillmentCenter)
                        .build());
            }
        }

        if (result.isEmpty()) {
            throw new NoPackagingFitsItemException(
                    String.format("No packaging at %s fits %s!", fulfillmentCenter.getFcCode(), item));
        }

        return result;
    }
}
