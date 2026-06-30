# Plan de trabajo — ArteCIMA (evaluación)

Plan acordado para alinear el proyecto con la guía del profesor (modelo con CRUD + controlador + vista unificada), manteniendo **JFrame** en lugar de MDI/JInternalFrame.

**Orden clave:** Modelo → Controlador → Vista → eliminar DAOs

---

## Paso 0 — Limpieza rápida (opcional, ~10 min)

Borrar carpetas vacías o obsoletas:

- `src/main/java/ArteCIMA/Conexion` (vacía; `Conexion.java` ya está en `Modelo`)
- `src/main/java/ArteCIMA/Service` (vacía)
- `src/main/java/ArteCIMA/Util` (vacía)
- `src/main/java/ArteCIMA/model/dao` (vacía)
- `src/main/java/ArteCIMA/model/service` (vacía)
- `src/main/java/ArteCIMA/lib` (JARs viejos; Maven los trae en `pom.xml`)

Después: clic derecho en el proyecto → **Clean and Build**.

**No borrar:** `Modelo`, `Vista`, `Controlador`, `Main.java`.

---

## Paso 1 — CRUD dentro del modelo

Mover la lógica de los DAO a las clases del modelo. Métodos típicos: `listar()`, `insertar()`, `modificar()`, `eliminar()`, `buscar()`.

| Modelo | Origen del código |
|--------|-------------------|
| `Estudiante.java` | `InsertarEstudianteDAO` + `ConsultarEstudiantesDAO` |
| `Instructor.java` | `InsertarInstructorDAO` + `ConsultarInstructorDAO` |
| `Usuario.java` | `UsuarioDAO` |
| `Taller.java` | **Crear desde cero** (aún no existe) |

---

## Paso 2 — Crear controladores

| Controlador | Responsabilidad |
|-------------|-----------------|
| `ControladorEstudiante` | `controlarAccion(evento, unEstudiante)` — Insertar / Modificar / Eliminar |
| `ControladorInstructor` | Igual para instructor |
| `ControladorUsuario` | Registro y acciones de usuario (o ampliar `LoginControlador`) |
| `ControladorTaller` | Cuando exista `Taller.java` con CRUD |

Patrón de la guía:

```java
public void controlarAccion(ActionEvent evento, Estudiante unEstudiante) {
    String accion = evento.getActionCommand();
    switch (accion) {
        case "Insertar": unEstudiante.insertar(); break;
        case "Modificar": unEstudiante.modificar(); break;
        case "Eliminar": unEstudiante.eliminar(); break;
    }
}
```

---

## Paso 3 — Unificar formularios CRUD (vista)

Un formulario por entidad (como `FRMCliente` en la guía SENA), no dos ventanas separadas.

| Entidad | Estado actual | Acción |
|---------|---------------|--------|
| **Estudiante** | `InsertarEstudiante` + `ConsultarEstudiantes` funcionan | Unificar en un solo formulario (campos + tabla + Insertar/Modificar/Eliminar/Buscar) |
| **Instructor** | `InsertarInstructor` + `ConsultarInstructores` funcionan | Igual |
| **Taller** | `InsertarTaller` y `ConsultarTalleres` están vacíos (400×300) | **Diseñar y programar desde cero**, no “unificar” |

---

## Paso 4 — Conectar vista → controlador → modelo

En cada formulario unificado:

1. Instanciar el controlador (ej. `ControladorEstudiante`).
2. Los botones llaman `controlarAccion(...)` y un método tipo `obtenerEstudiante()` que lee los campos.
3. La vista **no** llama DAO ni SQL directamente.

---

## Paso 5 — Eliminar DAOs (solo cuando todo compile)

Borrar:

- `InsertarEstudianteDAO.java`
- `ConsultarEstudiantesDAO.java`
- `InsertarInstructorDAO.java`
- `ConsultarInstructorDAO.java`
- `UsuarioDAO.java`

Opcional: eliminar formularios viejos duplicados si ya existen los unificados:

- `InsertarEstudiante` / `ConsultarEstudiantes`
- `InsertarInstructor` / `ConsultarInstructores`
- `InsertarTaller` / `ConsultarTalleres` (reemplazados por el formulario unificado de taller)

---

## Paso 6 — Actualizar `PagPrincipal`

Los botones deben abrir los **formularios nuevos unificados**, no los viejos.

---

## Paso 7 — Resto del sistema ✅

MVC completo para las entidades restantes del esquema:

| Entidad | Modelo | Controlador | Vista |
|---------|--------|-------------|-------|
| Grupo | `Grupo.java` | `ControladorGrupo` | `FRMGrupo` |
| Beca | `Beca.java` | `ControladorBeca` | `FRMBeca` |
| Acudiente | `Acudiente.java` | `ControladorAcudiente` | `FRMAcudiente` |
| Asistencia | `Asistencia.java` | `ControladorAsistencia` | `FRMAsistencia` |
| Corporación | `Corporacion.java` | `ControladorCorporacion` | `FRMCorporacion` |
| Método | `Metodo.java` | `ControladorMetodo` | `FRMMetodo` |
| Convocatoria | `Convocatoria.java` | `ControladorConvocatoria` | `FRMConvocatoria` |
| Alianza | `Alianza.java` | `ControladorAlianza` | `FRMAlianza` |
| Movimiento contable | `MovimientoContable.java` | `ControladorMovimientoContable` | `FRMMovimientoContable` |
| Pago instructor | `PagoInstructor.java` | `ControladorPagoInstructor` | `FRMPagoInstructor` |

`PagPrincipal` actualizado con botones para todos los módulos.

---

## Verificación pasos 0–7 (26 jun 2026)

Revisión minuciosa en disco (`dir /s` sobre `src/`, `inputFiles.lst` de Maven, búsqueda de referencias en `.java`). Sin prueba funcional en runtime aún.

| Paso | Estado | Notas |
|------|--------|-------|
| **0** Limpieza | ✅ | `Conexion`, `Service`, `Util`, `lib` eliminados. |
| **1** CRUD en modelo | ✅ | 16 clases en `Modelo/` con CRUD; sin archivos `*DAO.java`. |
| **2** Controladores | ✅ | Todos los controladores existen, incluido `LoginControlador`. |
| **3** Formularios unificados | ✅ | `FRMEstudiante`, `FRMInstructor`, `FRMTaller`. Sin `Insertar*` ni `Consultar*` en `Vista/`. |
| **4** Vista → controlador → modelo | ✅ | Las vistas `FRM*` instancian controlador; ningún `.java` menciona `DAO`. |
| **5** Eliminar DAOs | ✅ | Los 5 `*DAO.java` y los 6 formularios viejos ya no están en `src/`. Última compilación Maven tampoco los incluye. |
| **6** `PagPrincipal` | ✅ | Abre solo formularios nuevos (`FRM*`). `Main.java` arranca en `Login`. |
| **7** Resto del sistema | ✅ | Tabla completa arriba; 14 módulos en el menú principal. |

**Inventario actual en disco:**

- `Modelo/`: 16 `.java` (entidades + `Conexion` + `SesionUsuario`)
- `Vista/`: 17 `.java` (`FRM*` × 14, `Login`, `PagPrincipal`, `RegistrarUsuario`)
- `target/classes/`: sin `.class` de DAO ni formularios viejos

**Pendiente:** prueba funcional (login, CRUD por módulo, menú completo).

**Base ya existente para el paso 8:** `SesionUsuario` guarda rol al login; `Usuario` tiene `nombre_rol`; registro ofrece 7 roles. Solo hay un permiso esbozado (`puedeModificarInstructores`) y aún no se aplica en las vistas.

---

## Paso 8 — RBAC (Control de Acceso Basado en Roles)

Objetivo: que cada rol vea y use solo lo que le corresponde, no solo autenticarse.

**Roles actuales en el sistema:** Administrador, Coordinador, Instructor, Administrativo, Contabilidad, Auxiliar.

| Tarea | Descripción |
|-------|-------------|
| **8.1 Matriz de permisos** | Definir qué módulo/acción puede cada rol (ver, crear, editar, eliminar). Ej.: Contabilidad → movimientos y pagos; Instructor → asistencias y grupos asignados; Auxiliar → consulta limitada. |
| **8.2 Centralizar en `SesionUsuario`** | Métodos tipo `puedeAcceder(modulo)`, `puedeInsertar(modulo)`, `puedeEliminar(modulo)` según la matriz. |
| **8.3 Aplicar en `PagPrincipal`** | Ocultar o deshabilitar botones de módulos no permitidos según el rol en sesión. |
| **8.4 Aplicar en formularios** | Deshabilitar Insertar / Modificar / Eliminar donde el rol solo tenga lectura. |
| **8.5 Validar en controlador** | Comprobar permiso antes de ejecutar la acción (defensa además de la UI). |
| **8.6 (Opcional) BD** | Tablas `roles` y `permisos` si se quiere RBAC configurable; si no, matriz fija en código es suficiente para el proyecto. |

**Entregable:** usuario logueado con rol distinto a Administrador no accede a módulos ni acciones fuera de su perfil.

---

## Paso 9 — Módulo de Reportes Automatizados

Objetivo: generar informes útiles del sistema sin consultas manuales repetidas.

| Tarea | Descripción |
|-------|-------------|
| **9.1 Definir reportes** | Priorizar según negocio. Propuesta inicial: asistencia por grupo/período, becas activas, pagos a instructores, movimientos contables por rango de fechas, estudiantes por taller. |
| **9.2 Capa de consultas** | Métodos en modelo o clase `Reporte` / `ReporteDAO` con SQL de agregación (JOINs, filtros por fecha, `GROUP BY`). |
| **9.3 `ControladorReporte`** | Recibir filtros (fechas, taller, grupo), invocar consultas y devolver datos tabulares. |
| **9.4 Vista `FRMReportes`** | Pantalla con combo de tipo de reporte, filtros y tabla de resultados. |
| **9.5 Exportación** | Exportar a CSV o PDF (JasperReports, iText o CSV simple con `FileWriter`). |
| **9.6 Menú y RBAC** | Botón en `PagPrincipal`; en paso 8, restringir qué roles pueden generar cada reporte. |

**Entregable:** al menos 3–4 reportes operativos con filtros y exportación básica.

---

## Paso 10 — Mejora de interfaz y experiencia de usuario (UI/UX)

Objetivo: unificar el aspecto visual del sistema y mejorar la usabilidad, **respetando la identidad de Arte CIMA** (logo, colores institucionales, tono accesible).

**Base actual en el proyecto:**

- Logo principal: `logo2.png` (usado en `PagPrincipal` y formularios `FRM*`)
- Colores recurrentes: fondo blanco, texto azul oscuro (`RGB 0, 51, 102`), eslogan *"Arte para todos"*
- Tipografías mezcladas hoy (Pristina, NSimSun, Monospaced, Gill Sans…) — hay que unificarlas

| Tarea | Descripción |
|-------|-------------|
| **10.1 Guía visual** | Definir paleta (primario, secundario, éxito, error), tipografía base y tamaños. Extraer colores del logo si hace falta. Documentar en una sección breve del plan o un `UI_GUIA.md`. |
| **10.2 Componentes comunes** | Clase utilitaria o plantilla base para formularios: encabezado con logo, título del módulo, panel de botones alineado, tabla con mismo estilo. Evitar repetir estilos a mano en cada `FRM*`. |
| **10.3 `Login` y `PagPrincipal`** | Primera impresión: login claro, menú con jerarquía visual (agrupar módulos por área: académico, administrativo, financiero). Mostrar nombre y rol del usuario en sesión. |
| **10.4 Formularios `FRM*`** | Aplicar guía a los 14 módulos: espaciado consistente, labels alineados, botones con mismo orden (Buscar / Insertar / Modificar / Eliminar / Limpiar). |
| **10.5 UX operativa** | Mensajes de error claros, confirmación antes de eliminar, estados vacíos en tablas (“No hay registros”), deshabilitar botones mientras procesa, validación en campos obligatorios. |
| **10.6 Accesibilidad básica** | Contraste legible, tamaños de fuente mínimos, tooltips en botones del menú si el texto es corto. |
| **10.7 Icono de ventana** | Usar el logo (`loguito.jpeg` o variante) como icono de las ventanas JFrame. |

**Entregable:** todas las pantallas con look & feel coherente con Arte CIMA; navegación más clara sin cambiar la arquitectura JFrame.

**Momento sugerido:** después de la prueba funcional y preferiblemente **después del paso 8 (RBAC)**, para no rediseñar dos veces botones que luego se ocultan por rol.

---

## Paso 11 — PostgreSQL en Coolify (conexión remota vía túnel SSH)

Objetivo: desplegar la base de datos en **Coolify**, conectar la aplicación Java desde el entorno de desarrollo usando **túnel SSH**, y dejar lista la BD desde cero (hoy no existe en el servidor).

**Base actual en el proyecto:**

- `Conexion.java` apunta a `localhost:5433` con usuario/contraseña fijos en código
- Scripts listos en `sql/`: `drop.sql`, `schema.sql`, `seed.sql`, `init.sql`
- Dependencia PostgreSQL ya en `pom.xml` (driver JDBC)

| Tarea | Descripción |
|-------|-------------|
| **11.1 Servicio en Coolify** | Crear instancia PostgreSQL en Coolify (nombre sugerido: `artecima`). Anotar host interno, puerto, usuario, contraseña y nombre de BD. |
| **11.2 Red y acceso** | Confirmar que Postgres no queda expuesto públicamente sin necesidad. Acceso desde tu PC vía SSH al servidor donde corre Coolify. |
| **11.3 Túnel SSH** | Abrir túnel local, por ejemplo: `ssh -L 5433:127.0.0.1:5432 usuario@servidor-coolify -N` (ajustar puertos según Coolify). La app sigue usando `localhost:5433` en desarrollo mientras el túnel esté activo. |
| **11.4 Crear BD y esquema** | Con el túnel activo, ejecutar `schema.sql` y `seed.sql` contra la BD remota (`psql` o pgAdmin apuntando a `localhost:5433`). |
| **11.5 Externalizar configuración** | Sacar URL, usuario y contraseña de `Conexion.java` hacia `database.properties` (o variables de entorno). **No commitear secretos** — usar `.gitignore` + `database.properties.example`. |
| **11.6 Actualizar `Conexion.java`** | Leer propiedades del archivo; mensaje de error útil si falla conexión o el túnel no está levantado. |
| **11.7 Probar end-to-end** | Login, un CRUD y listado con túnel activo. Documentar en el plan o README los pasos: 1) levantar túnel, 2) ejecutar app. |
| **11.8 (Opcional) Producción** | Si la app corre en el mismo servidor Coolify, conectar por red interna Docker sin túnel; el túnel queda solo para desarrollo local. |

**Entregable:** BD `ArteCIMA` operativa en Coolify, app conectada sin credenciales en código, y guía corta para levantar túnel + arrancar el sistema.

**Momento sugerido:** puede hacerse **en paralelo con la prueba funcional local** (sigues probando contra Postgres local) o justo después, cuando quieras validar contra el entorno real.

---

## Orden recomendado (fases posteriores al paso 7)

| Fase | Qué | Quién / cuándo |
|------|-----|----------------|
| **A** | Prueba funcional mínima (login, menú, 1–2 CRUD) | Tú, ahora (local) |
| **B** | Paso 8 — RBAC | Después de fase A |
| **C** | Paso 9 — Reportes | Después de RBAC |
| **D** | Paso 10 — UI/UX | Después de RBAC (evita retrabajo en menú) |
| **E** | Paso 11 — Coolify + túnel SSH | En paralelo con A o al cerrar pruebas locales |

---

## Decisiones ya tomadas

| Tema | Decisión |
|------|----------|
| **UI** | Mantener **JFrame** (no MDI / JInternalFrame), salvo que el profesor lo exija explícitamente |
| **Identidad visual** | Respetar logo Arte CIMA (`logo2.png`), colores institucionales y eslogan *Arte para todos* |
| **DAO** | Refactorizar: CRUD dentro del modelo; eliminar clases `*DAO` |
| **Service / Util** | No recrear; no los pide la guía nueva |
| **Login** | `LoginControlador` ya existe; mantener y alinear `Usuario` con el mismo patrón |
| **Base de datos** | PostgreSQL; scripts en `sql/`. Producción en **Coolify**; desarrollo local con **túnel SSH** |
| **Secretos** | Credenciales fuera del código (`database.properties`, no en git) |

---

## Resumen en una frase

**Pasos 0–7:** ✅ Completos en código (MVC + limpieza). Siguiente: prueba funcional.

**Pasos 8–9:** RBAC → reportes automatizados.

**Pasos 10–11:** UI/UX con identidad Arte CIMA → PostgreSQL en Coolify vía túnel SSH.

---

*Documento generado el 16 de junio de 2026. Actualizado el 26 de junio de 2026 (pasos 8–11, orden de fases).*
