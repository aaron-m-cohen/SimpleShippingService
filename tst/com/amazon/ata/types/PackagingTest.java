package com.amazon.ata.types;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PackagingTest {
    @Test
    public void height_width_length_variables_removed_from_packaging_class() {
        Field[] fields = Packaging.class.getDeclaredFields();
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("height")), "height attribute should be removed from Packaging class since it was moved to Box class");
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("width")),  "width attribute should be removed from Packaging class since it was moved to Box class");
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("length")), "length attribute should be removed from Packaging class since it was moved to Box class");
    }
}
