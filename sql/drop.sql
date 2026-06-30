-- =============================================================================
-- Arte CIMA - Eliminar todas las tablas (solo desarrollo)
-- =============================================================================
--
-- Usar cuando necesites recrear el esquema desde cero.
-- Ejecutar ANTES de schema.sql si la base de datos ya tiene tablas creadas.
-- =============================================================================

DROP TABLE IF EXISTS
    asistencia,
    pago_instructor,
    estudiante,
    acudiente,
    grupo,
    taller,
    beca,
    convocatoria,
    movimiento_contable,
    alianza,
    instructor,
    metodo,
    corporacion,
    usuarios
CASCADE;
