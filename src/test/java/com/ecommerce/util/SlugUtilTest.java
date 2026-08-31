package com.ecommerce.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SlugUtilTest {

    @Test
    void toSlug_SimpleString_ReturnsSlug() {
        assertEquals("hello-world", SlugUtil.toSlug("Hello World"));
    }

    @Test
    void toSlug_SpecialCharacters_RemovesSpecialChars() {
        assertEquals("hello-world-123", SlugUtil.toSlug("Hello World! @#$%^&*() 123"));
    }

    @Test
    void toSlug_MultipleSpaces_CollapsesToSingleHyphen() {
        assertEquals("hello-world", SlugUtil.toSlug("Hello   World"));
    }

    @Test
    void toSlug_NullInput_ReturnsEmpty() {
        assertEquals("", SlugUtil.toSlug(null));
    }

    @Test
    void toSlug_EmptyInput_ReturnsEmpty() {
        assertEquals("", SlugUtil.toSlug(""));
    }

    @Test
    void toSlug_UnicodeCharacters_Normalizes() {
        String result = SlugUtil.toSlug("Café Résumé");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
