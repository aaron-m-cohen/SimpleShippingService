package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {

    private BigDecimal volume;

    /**
     * Parameterized constructor. The material of a PolyBag is always laminated plastic.
     * @param volume - The volume of the PolyBag.
     */
    public PolyBag(BigDecimal volume) {
        super(Material.LAMINATED_PLASTIC);
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * @param item the item to test fit for
     * @return - whether the item fits
     */
    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemVolume = item.getHeight().multiply(item.getLength()).multiply(item.getWidth());
        return itemVolume.compareTo(volume) < 0;
    }

    /**
     * @return the mass of the package
     */
    @Override
    public BigDecimal getMass() {
        double roughMass = Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6);
        return BigDecimal.valueOf(roughMass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PolyBag polyBag = (PolyBag) o;
        return Objects.equals(volume, polyBag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }
}
