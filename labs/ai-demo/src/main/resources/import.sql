-- Demo seed data for AI_VEHICLE. Loaded only in %dev profile (see application.properties).
-- Assumes drop-and-create schema strategy. Switching to update would cause duplicate-key errors on reboot.
INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (1, 'Volkswagen', 'Golf VII', 2015);
INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (2, 'BMW', 'M3 E46', 2003);
INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (3, 'Tesla', 'Model 3', 2022);
INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (4, 'Toyota', 'Corolla', 2019);
INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (5, 'Mercedes-Benz', 'W123 240D', 1981);
INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (6, 'Audi', 'Quattro', 1984);
INSERT INTO AI_VEHICLE (V_ID, V_MAKE, V_MODEL, V_CONSTRUCTION_YEAR) VALUES (7, 'Porsche', '911 Carrera', 2024);
ALTER SEQUENCE vehicle_id_seq RESTART WITH 100;
