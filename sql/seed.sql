-- =============================================================================
-- Arte CIMA - Datos de prueba
-- =============================================================================
--
-- Ejecutar DESPUÉS de schema.sql, conectado a la base de datos ArteCIMA.
--
-- Usuarios de prueba para login (contraseña: admin123):
--   admin      -> Administrador
--   instructor -> Instructor
--
-- =============================================================================

TRUNCATE TABLE
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
RESTART IDENTITY CASCADE;

-- Corporación
INSERT INTO corporacion (nit_corporacion, nombre, direccion, tipo_entidad) VALUES
('900123456-7', 'Corporación Arte CIMA', 'Calle 35 # 18-42, Bucaramanga', 'ESAL');

-- Métodos de enseñanza
INSERT INTO metodo (nombre, descripcion) VALUES
('Tradicional', 'Enseñanza convencional con enfoque en técnica y expresión artística.'),
('Inclusivo', 'Metodología que integra a personas con y sin discapacidad en el mismo espacio formativo.'),
('Adaptado', 'Ajustes pedagógicos según las necesidades específicas de cada estudiante o grupo.');

-- Convocatorias
INSERT INTO convocatoria (nombre, entidad_otorgante, descripcion, fecha_inicio, fecha_fin, monto_aprobado) VALUES
('Formación artística inclusiva 2025', 'Ministerio de Cultura', 'Apoyo a procesos formativos en artes plásticas y escénicas.', '2025-01-15', '2025-12-15', 45000000.00),
('Semilleros culturales Bucaramanga', 'Instituto Municipal de Cultura y Turismo', 'Fortalecimiento de semilleros en artes circenses.', '2025-03-01', '2025-11-30', 28000000.00);

-- Becas
INSERT INTO beca (tipo_beca, entidad_otorgante, vigencia, id_convocatoria) VALUES
('Beca MinCultura', 'Ministerio de Cultura', '2025', 1),
('Beca IMCT', 'Instituto Municipal de Cultura y Turismo', '2025', 2),
('Gratuidad por discapacidad', 'Corporación Arte CIMA', 'Permanente', NULL);

-- Alianza
INSERT INTO alianza (nombre_fundacion, tipo_alianza, fecha_inicio, fecha_fin, descripcion, id_corporacion) VALUES
('Fundación Manos que Crean', 'Convenio formativo', '2024-06-01', '2026-06-01', 'Apoyo en materiales y difusión por canal WhatsApp.', 1);

-- Instructores
INSERT INTO instructor (tipo_documento, num_documento, nombre_completo, especialidad_artistica, discapacidad, tipo_discapacidad, telefono, correo, valor_por_clase) VALUES
('CC', '63547821', 'María Fernanda Rincón', 'Artes plásticas', FALSE, NULL, '3001234567', 'mfrincon@artecima.org', 85000.00),
('CC', '1098765432', 'Carlos Andrés Mejía', 'Teatro escénico', FALSE, NULL, '3109876543', 'camejia@artecima.org', 90000.00),
('CC', '91234567', 'Laura Patricia Duque', 'Circo y malabarismo', TRUE, 'Visual parcial', '3205558899', 'lduque@artecima.org', 95000.00);

-- Talleres
INSERT INTO taller (nombre, tipo_arte, horario, id_metodo, id_instructor, id_alianza) VALUES
('Pintura inclusiva', 'Plástico', 'Lunes y miércoles 14:00-16:00', 2, 1, 1),
('Teatro para jóvenes', 'Escénico', 'Martes y jueves 16:00-18:00', 1, 2, NULL),
('Malabarismo inicial', 'Circense', 'Sábados 09:00-11:00', 3, 3, 1);

-- Grupos
INSERT INTO grupo (nombre, horario, num_max_estudiantes, id_taller) VALUES
('Grupo A - Pintura', 'Lunes y miércoles 14:00-16:00', 15, 1),
('Grupo B - Teatro', 'Martes y jueves 16:00-18:00', 20, 2),
('Grupo C - Circo', 'Sábados 09:00-11:00', 12, 3);

-- Acudientes (estudiantes menores de edad)
INSERT INTO acudiente (tipo_documento, num_documento, nombre_completo, parentesco, telefono, correo) VALUES
('CC', '37891234', 'Ana Lucía Gómez', 'Madre', '3012223344', 'ana.gomez@correo.com'),
('CC', '91237845', 'Jorge Iván Pérez', 'Padre', '3023334455', 'jorge.perez@correo.com');

-- Estudiantes
INSERT INTO estudiante (tipo_documento, num_documento, nombre_completo, edad, telefono, correo, discapacidad, tipo_discapacidad, tipo_beneficio, id_grupo, id_beca, id_acudiente) VALUES
('TI', '1090123456', 'Santiago Gómez Ruiz', 14, NULL, NULL, FALSE, NULL, 'Beca MinCultura', 1, 1, 1),
('TI', '1090654321', 'Valentina Pérez Ortiz', 13, NULL, NULL, TRUE, 'Auditiva leve', 'Gratuidad por discapacidad', 2, 3, 2),
('CC', '1099887766', 'Daniela Morales Castro', 22, '3154445566', 'daniela.m@correo.com', FALSE, NULL, 'Mensualidad', 2, NULL, NULL),
('CC', '1100223344', 'Andrés Felipe Sierra', 19, '3165556677', 'andres.s@correo.com', FALSE, NULL, 'Beca IMCT', 3, 2, NULL);

-- Asistencia (muestras recientes)
INSERT INTO asistencia (id_estudiante, id_grupo, fecha, presente) VALUES
(1, 1, '2025-06-02', TRUE),
(1, 1, '2025-06-04', TRUE),
(1, 1, '2025-06-09', FALSE),
(2, 2, '2025-06-03', TRUE),
(2, 2, '2025-06-05', TRUE),
(3, 2, '2025-06-03', TRUE),
(4, 3, '2025-06-07', TRUE);

-- Movimientos contables
INSERT INTO movimiento_contable (tipo_movimiento, concepto, monto, fecha, fuente, id_corporacion) VALUES
('Ingreso', 'Mensualidad estudiante Daniela Morales', 180000.00, '2025-06-01', 'Mensualidad', 1),
('Ingreso', 'Desembolso convocatoria MinCultura - primer trimestre', 15000000.00, '2025-06-05', 'Beca MinCultura', 1),
('Egreso', 'Pago clases instructor María Fernanda Rincón - mayo', 680000.00, '2025-06-03', 'Pago instructor', 1),
('Egreso', 'Compra materiales pintura acrílica', 320000.00, '2025-06-08', 'Materiales', 1);

-- Pagos a instructores (vinculados al movimiento contable de egreso)
INSERT INTO pago_instructor (id_instructor, fecha_pago, monto, concepto, id_movimiento) VALUES
(1, '2025-06-03', 680000.00, '8 clases dictadas en mayo - Taller Pintura inclusiva', 3);

-- Usuarios del sistema (contraseña: admin123)
INSERT INTO usuarios (nombre_rol, nombre_completo, nombre_usuario, correo, password_hash) VALUES
('Administrador', 'Administrador Arte CIMA', 'admin', 'admin@artecima.org', crypt('admin123', gen_salt('bf', 10))),
('Instructor', 'María Fernanda Rincón', 'instructor', 'mfrincon@artecima.org', crypt('admin123', gen_salt('bf', 10)));
