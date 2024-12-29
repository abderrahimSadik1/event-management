package com.backend.event_management;

import org.junit.jupiter.api.Test;

import com.backend.event_management.model.Role;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testEnumValues() {
        assertEquals(2, Role.values().length);
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
        assertEquals(Role.USER, Role.valueOf("USER"));
    }

    @Test
    void testEnumOrdinal() {
        assertEquals(0, Role.ADMIN.ordinal());
        assertEquals(1, Role.USER.ordinal());
    }
}
