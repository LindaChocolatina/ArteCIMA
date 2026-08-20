# 🎨 Arte CIMA: Sistema de Gestión Humano y Creativo

**Arte CIMA** es una plataforma integral de escritorio desarrollada en Java para la Corporación Arte CIMA en Bucaramanga. Este software trasciende la simple administración de datos; es una herramienta diseñada para sostener un ecosistema de formación artística inclusiva (artes plásticas, escénicas y circenses) en una entidad sin ánimo de lucro.

## 🌿 Filosofía del Proyecto
En Arte CIMA, el software es visto como un organismo vivo. Cada tabla en la base de datos y cada clase en el código ha sido cultivada para que la tecnología sea un puente, no una barrera, permitiendo que las ideas germinen y florezcan en la comunidad.

## 🏗️ Arquitectura Técnica (Patrón MVC)
El sistema implementa una arquitectura robusta y tipada basada en el patrón **Modelo-Vista-Controlador (MVC)**, garantizando un código limpio y mantenible:

- **Vista:** Interfaz gráfica centralizada y moderna construida con Java Swing (JFrames) y estilizada uniformemente con el Look & Feel **FlatLaf**. Cuenta con 14 formularios unificados (CRUDs completos) y un panel principal que facilita la navegación respetando la identidad visual institucional (Logo, colores y tipografía).
- **Controlador:** Gestiona los eventos de la vista y la lógica de negocio, comunicando los formularios con los modelos. Cada entidad tiene su propio controlador dedicado.
- **Modelo:** Representación fiel de las entidades (Estudiantes, Instructores, Talleres, etc.) y encapsulación de las operaciones CRUD hacia la base de datos, manteniendo la limpieza de la arquitectura.

## 📊 Diseño de Base de Datos (PostgreSQL)
El motor de este proyecto es una base de datos relacional robusta, preparada para funcionar tanto en entornos locales como de manera remota mediante conexiones seguras (ej. túneles SSH para despliegues en Coolify), optimizada para la transparencia administrativa:

- **Inclusión Total:** Registro detallado de discapacidades y métodos de enseñanza adaptados.
- **Trazabilidad Financiera:** Relación directa entre convocatorias, becas y el beneficio final de los estudiantes.
- **Control de Flujo:** Gestión de movimientos contables y pagos a instructores vinculados a la corporación.
- **Integridad de Datos:** Uso de restricciones (CHECK, UNIQUE, NOT NULL) para asegurar que la información sea siempre confiable.

## 🛠️ Módulos Principales (Funcionalidad Actual)
El sistema cuenta actualmente con los siguientes módulos 100% funcionales integrados bajo la arquitectura MVC:

**1. Gestión de Formación e Inclusión**
- **Talleres y Grupos:** Administración de programas de formación y control de aforo por grupo.
- **Estudiantes e Instructores:** Registro de actores principales, incluyendo datos de inclusión, acudientes y métodos pedagógicos.
- **Asistencia:** Registro diario y preciso por grupo y fecha.

**2. Bienestar y Apoyo Institucional**
- **Becas y Convocatorias:** Seguimiento a estímulos (MinCultura, IMCT) y vinculación directa con los beneficiarios.
- **Alianzas y Corporación:** Control de convenios institucionales y datos base de la entidad.

**3. Operación y Contabilidad**
- **Movimientos Contables:** Registro de ingresos y egresos de la corporación.
- **Pagos a Instructores:** Auditoría transparente de honorarios y vinculación con la contabilidad general.
- **Reportes:** Base operativa para consulta y generación de informes consolidados de la actividad institucional.

## 💻 Stack Tecnológico
- **Lenguaje:** Java (Maven)
- **Interfaz de Usuario:** Java Swing con Look & Feel **FlatLaf**
- **Patrón de Diseño:** MVC (Modelo - Vista - Controlador)
- **Persistencia:** PostgreSQL

## 🛡️ Seguridad y Control de Acceso (RBAC)
El sistema implementa un Control de Acceso Basado en Roles (RBAC) para honrar la confianza y privacidad de la corporación:
- **Autenticación Segura:** Hasheo unidireccional de credenciales mediante BCrypt.
- **Permisos por Perfil:** La sesión de usuario identifica los roles (Administrador, Coordinador, Instructor, Contabilidad, etc.) para restringir qué módulos y acciones (Lectura, Inserción, Edición, Eliminación) están disponibles de manera diferenciada.

## 🚀 Evolución del Proyecto (Roadmap)
Este organismo digital está diseñado para seguir creciendo. Las próximas fases de desarrollo incluyen:
- Ampliación del módulo de reportes automatizados con exportación a PDF y hojas de cálculo.
- Consolidación del entorno de producción y bases de datos en plataformas cloud como Coolify.

## 📝 Nota de la Desarrolladora
Este software fue construido como un acto de generosidad futura. No busco solo la perfección técnica, sino la claridad, la coherencia y una excelente experiencia de usuario. Cada decisión técnica, desde la estructuración del MVC hasta la paleta de colores institucional, ha sido tomada con la intención de honrar la labor artística de la Corporación Arte CIMA.
