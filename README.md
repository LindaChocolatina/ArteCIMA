#🎨 Arte CIMA: Sistema de Gestión Humano y Creativo#
**Arte CIMA** es una plataforma integral desarrollada para la Corporación Arte CIMA en Bucaramanga. Este software trasciende la simple administración de datos; es una herramienta diseñada para sostener un ecosistema de formación artística inclusiva (artes plásticas, escénicas y circenses) en una entidad sin ánimo de lucro.

"Diseño para humanos, con la paciencia de quien espera a que una acuarela seque."

🌿 Filosofía del Proyecto
En Arte CIMA, el software es visto como un organismo vivo. Cada tabla en la base de datos y cada capa en el código de Java han sido cultivadas para que la tecnología sea un puente, no una barrera, permitiendo que las ideas "rompan el cascarón" y florezcan en la comunidad.

🏗️ Arquitectura Técnica (Modelo de Capas en Java)
El sistema implementa una arquitectura robusta y tipada, organizada en capas para garantizar la limpieza y el mantenimiento del código:

Capa de Interfaz: El lienzo donde el usuario interactúa con el sistema.

Capa de Negocio: La lógica que rige los procesos de la corporación (validación de becas, gestión de grupos).

Capa de Módulo: Representación fiel de las entidades (Estudiantes, Instructores, Movimientos).

Capa de Conexión: El sistema radicular que comunica el software con la persistencia en PostgreSQL.

📊 Diseño de Base de Datos (PostgreSQL)
El motor de este proyecto es una base de datos relacional detallada, optimizada para la transparencia administrativa:

Inclusión Total: Registro detallado de discapacidades y métodos de enseñanza adaptados (metodo).

Trazabilidad Financiera: Relación directa entre convocatoria, beca y el beneficio final del estudiante.

Control de Flujo: Gestión de movimiento_contable e ingresos/egresos vinculados a la fuente de origen (mensualidades o fondos externos).

Integridad de Datos: Uso de restricciones (CHECK, UNIQUE, NOT NULL) para asegurar que la información sea siempre confiable.

🛠️ Módulos Principales
1. Gestión de Formación e Inclusión
Administración de Talleres clasificados por tipo de arte y Grupos con control de aforo. El sistema permite registrar métodos inclusivos, asegurando que cada instructor y estudiante tenga el entorno adecuado para crear.

2. Bienestar y Apoyo Social
Seguimiento de Becas y Convocatorias (MinCultura, IMCT). El software vincula automáticamente el beneficio con el estudiante, permitiendo una gestión clara de la gratuidad y los apoyos económicos.

3. Operación y Contabilidad
Asistencia Diaria: Registro preciso por grupo y fecha.

Pagos a Instructores: Vinculación directa con los movimientos contables de la corporación para una auditoría transparente.

Gestión de Alianzas: Control de convenios con fundaciones y entidades financieras.

💻 Stack Tecnológico
Lenguaje: Java

Persistencia: PostgreSQL

Patrón de Diseño: Arquitectura por capas (Business, Interface, Module, Connection).

🛡️ Seguridad y Protección de Datos
Para honrar la confianza de los miembros de la corporación, el sistema implementa:

Seguridad Criptográfica: Hasheo de credenciales mediante BCrypt, asegurando que la información de acceso nunca sea vulnerable, ni siquiera en la base de datos.

Tratamiento de Datos Sensibles: Estructura preparada para cumplir con estándares de privacidad, protegiendo la identidad y condición de los beneficiarios.

🚀 Evolución del Proyecto (Roadmap)
Este organismo digital está diseñado para seguir creciendo. Las próximas fases de desarrollo incluyen:

Implementación de RBAC (Control de Acceso Basado en Roles): Creación de niveles de permisos diferenciados (Administrador, Instructor, Consulta) para blindar la integridad de los datos contables y personales.

Módulo de Reportes Automatizados: Generación de certificados de asistencia y reportes de impacto para las entidades financiadoras (MinCultura/IMCT).

Administración DB: pgAdmin.

📝 Nota de la Desarrolladora
Este software fue construido como un acto de generosidad futura. No busco solo la perfección técnica, sino la claridad y la coherencia. Cada decisión técnica, desde la clave foránea más pequeña hasta la interfaz de usuario, ha sido tomada con la intención de honrar la labor artística de la Corporación Arte CIMA.
