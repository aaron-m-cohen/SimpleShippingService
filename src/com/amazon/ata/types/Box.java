package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class Box extends Packaging {
    /**
     * This packaging's length.
     */
    private final BigDecimal length;

    /**
     * This packaging's smallest dimension.
     */
    private final BigDecimal width;

    /**
     * This packaging's largest dimension.
     */
    private final BigDecimal height;

    /**
     * Instantiates a new Box object.
     * @param length - the length of the package
     * @param width - the width of the package
     * @param height - the height of the package
     */

    public Box(BigDecimal length, BigDecimal width, BigDecimal height) {
        super(Material.CORRUGATE);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    /**
     * Returns the mass of the packaging in grams. The packaging weighs 1 gram per square centimeter.
     * @return the mass of the packaging
     */
    public BigDecimal getMass() {
        BigDecimal two = BigDecimal.valueOf(2);

        // For simplicity, we ignore overlapping flaps
        BigDecimal endsArea = length.multiply(width).multiply(two);
        BigDecimal shortSidesArea = length.multiply(height).multiply(two);
        BigDecimal longSidesArea = width.multiply(height).multiply(two);

        return endsArea.add(shortSidesArea).add(longSidesArea);
    }

    /**
     * Returns whether the given item will fit in this packaging.
     *
     * @param item the item to test fit for
     * @return whether the item will fit in this packaging
     */
    public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
                this.width.compareTo(item.getWidth()) > 0 &&
                this.height.compareTo(item.getHeight()) > 0;
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
        Box box = (Box) o;
        return Objects.equals(length, box.length) &&
                Objects.equals(width, box.width) &&
                Objects.equals(height, box.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length, width, height);
    }
}
