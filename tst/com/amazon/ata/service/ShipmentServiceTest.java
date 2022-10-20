package com.amazon.ata.service;

import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();

    private FulfillmentCenter existentFC = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");

    @Mock
    private PackagingDAO packagingDAO;


    @Mock
    private MonetaryCostStrategy monetaryCostStrategy;

    @InjectMocks
    private ShipmentService shipmentService;

    private ShipmentOption goodOption = ShipmentOption.builder().
            withItem(smallItem).
            withFulfillmentCenter(existentFC).
            withPackaging(new PolyBag(BigDecimal.valueOf(1000)))
            .build();

    @BeforeEach
    void setup() {
        openMocks(this);
        shipmentService = new ShipmentService(packagingDAO, monetaryCostStrategy);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        when(packagingDAO.findShipmentOptions(smallItem, existentFC)).thenReturn(List.of(goodOption));
        when(monetaryCostStrategy.getCost(goodOption)).thenReturn(new ShipmentCost(goodOption, BigDecimal.ONE));
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        when(packagingDAO.findShipmentOptions(largeItem, existentFC)).thenThrow(NoPackagingFitsItemException.class);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);


        // THEN
        assertNull(shipmentOption.getPackaging());
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_throwRuntimeException() {
        // GIVEN & WHEN
        //ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, nonExistentFC);

        // THEN
        assertThrows(RuntimeException.class, () -> {
            ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, nonExistentFC);
        }, "When no FC exists, throw exception");
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_throwRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, nonExistentFC);
        }, "When no FC exists, throw exception");
    }
}