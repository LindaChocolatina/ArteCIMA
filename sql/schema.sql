-- =============================================================================
-- Arte CIMA - Esquema de base de datos (PostgreSQL)
-- Proyecto formativo SENA - Corporación Arte CIMA, Bucaramanga
-- =============================================================================
--
-- Uso:
--   1. Crear la base de datos en pgAdmin o psql:
--        CREATE DATABASE "ArteCIMA";
--   2. Conectarse a ArteCIMA y ejecutar este archivo.
--   3. Luego ejecutar seed.sql para cargar datos de prueba.
--
-- =============================================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ---------------------------------------------------------------------------
-- Tablas maestras (sin dependencias externas)
-- ---------------------------------------------------------------------------

CREATE TABLE corporacion (
    id_corporacion SERIAL PRIMARY KEY,
    nit_corporacion VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(100),
    tipo_entidad VARCHAR(50) DEFAULT 'ESAL'
);

CREATE TABLE metodo (
    id_metodo SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE convocatoria (
    id_convocatoria SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    entidad_otorgante VARCHAR(100),
    descripcion TEXT,
    fecha_inicio DATE,
    fecha_fin DATE,
    monto_aprobado NUMERIC(12, 2)
);

CREATE TABLE instructor (
    id_instructor SERIAL PRIMARY KEY,
    tipo_documento VARCHAR(20) NOT NULL,
    num_documento VARCHAR(20) UNIQUE NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    especialidad_artistica VARCHAR(50),
    discapacidad BOOLEAN DEFAULT FALSE,
    tipo_discapacidad VARCHAR(50),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    valor_por_clase NUMERIC(10, 2)
);

CREATE TABLE acudiente (
    id_acudiente SERIAL PRIMARY KEY,
    tipo_documento VARCHAR(20),
    num_documento VARCHAR(20) UNIQUE,
    nombre_completo VARCHAR(100),
    parentesco VARCHAR(30),
    telefono VARCHAR(15),
    correo VARCHAR(100)
);

-- Autenticación de la aplicación Java (no forma parte del modelo académico principal)
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    correo VARCHAR(100),
    password_hash VARCHAR(100) NOT NULL
);

-- ---------------------------------------------------------------------------
-- Tablas con dependencias de primer nivel
-- ---------------------------------------------------------------------------

CREATE TABLE alianza (
    id_alianza SERIAL PRIMARY KEY,
    nombre_fundacion VARCHAR(100) NOT NULL,
    tipo_alianza VARCHAR(50),
    fecha_inicio DATE,
    fecha_fin DATE,
    descripcion TEXT,
    id_corporacion INT REFERENCES corporacion(id_corporacion)
);

CREATE TABLE beca (
    id_beca SERIAL PRIMARY KEY,
    tipo_beca VARCHAR(100) NOT NULL,
    entidad_otorgante VARCHAR(100),
    vigencia VARCHAR(20),
    id_convocatoria INT REFERENCES convocatoria(id_convocatoria)
);

CREATE TABLE taller (
    id_taller SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_arte VARCHAR(20) CHECK (tipo_arte IN ('Plástico', 'Escénico', 'Circense')),
    horario VARCHAR(50),
    id_metodo INT REFERENCES metodo(id_metodo),
    id_instructor INT REFERENCES instructor(id_instructor),
    id_alianza INT REFERENCES alianza(id_alianza)
);

CREATE TABLE movimiento_contable (
    id_movimiento SERIAL PRIMARY KEY,
    tipo_movimiento VARCHAR(10) CHECK (tipo_movimiento IN ('Ingreso', 'Egreso')),
    concepto VARCHAR(150),
    monto NUMERIC(12, 2),
    fecha DATE NOT NULL,
    fuente VARCHAR(100),
    id_corporacion INT REFERENCES corporacion(id_corporacion)
);

-- ---------------------------------------------------------------------------
-- Tablas con dependencias de segundo nivel
-- ---------------------------------------------------------------------------

CREATE TABLE grupo (
    id_grupo SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    horario VARCHAR(50),
    num_max_estudiantes INT CHECK (num_max_estudiantes > 0),
    id_taller INT REFERENCES taller(id_taller)
);

CREATE TABLE estudiante (
    id_estudiante SERIAL PRIMARY KEY,
    tipo_documento VARCHAR(20) NOT NULL,
    num_documento VARCHAR(20) UNIQUE NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    edad INT CHECK (edad > 0),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    discapacidad BOOLEAN DEFAULT FALSE,
    tipo_discapacidad VARCHAR(50),
    tipo_beneficio VARCHAR(50),
    id_grupo INT REFERENCES grupo(id_grupo),
    id_beca INT REFERENCES beca(id_beca),
    id_acudiente INT REFERENCES acudiente(id_acudiente)
);

-- ---------------------------------------------------------------------------
-- Tablas transaccionales
-- ---------------------------------------------------------------------------

CREATE TABLE asistencia (
    id_asistencia SERIAL PRIMARY KEY,
    id_estudiante INT REFERENCES estudiante(id_estudiante) ON DELETE CASCADE,
    id_grupo INT REFERENCES grupo(id_grupo) ON DELETE CASCADE,
    fecha DATE NOT NULL,
    presente BOOLEAN DEFAULT FALSE,
    CONSTRAINT asistencia_unica UNIQUE (id_estudiante, id_grupo, fecha)
);

CREATE TABLE pago_instructor (
    id_pago SERIAL PRIMARY KEY,
    id_instructor INT REFERENCES instructor(id_instructor),
    fecha_pago DATE NOT NULL,
    monto NUMERIC(10, 2),
    concepto VARCHAR(100),
    id_movimiento INT REFERENCES movimiento_contable(id_movimiento)
);
