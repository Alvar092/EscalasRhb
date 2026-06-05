# EscalasRhb

App Android para profesionales de la rehabilitación que permite administrar escalas clínicas estandarizadas a pacientes y exportar los resultados en PDF. Funciona completamente offline.

---

## Escalas clínicas

| Escala | Descripción | Puntuación |
|--------|-------------|------------|
| **Berg Balance Scale** | Evaluación del equilibrio funcional mediante 14 ítems | 0 – 56 |
| **Motricity Index** | Recuperación motora post-ictus (miembro superior e inferior) | 0 – 100 por lado |
| **Trunk Control Test** | Control de tronco en 4 movimientos | 0 – 100 |

---

## Funcionalidades

- Gestión de pacientes (crear, editar, eliminar)
- Administración de escalas clínicas con registro de puntuaciones
- Historial clínico por paciente
- Exportación de resultados a PDF
- Interfaz disponible en español e inglés

---

## Stack tecnológico

- **Kotlin** + **Jetpack Compose** (Material 3)
- **Clean Architecture** — capas `data` / `domain` / `presentation`
- **Hilt** — inyección de dependencias
- **Room** — base de datos local
- **PDF nativo** — `android.graphics.pdf.PdfDocument` con patrón Strategy
- **Navigation Compose** — navegación entre pantallas

| | Versión |
|---|---|
| compileSdk | 36 |
| minSdk | 30 |
| Kotlin | 2.0.21 |
| AGP | 8.11.2 |

---

## Estructura del proyecto

```
app/src/main/java/com/aentrena/escalasrhb/
│
├── data/
│   ├── local/              # Entities, DAOs, Mappers, AppDatabase (Room)
│   ├── repositories/       # Implementaciones de los repositorios
│   └── services/pdf/       # PdfGenerator + estrategias de PDF por escala
│
├── domain/
│   ├── model/              # Modelos puros (Patient, BergTest, MotricityIndex…)
│   ├── interfaces/         # ClinicalTest, SideTest, repositorios
│   └── useCases/           # Un caso de uso por operación
│
├── presentation/
│   ├── bergTest/
│   ├── motricityIndex/
│   ├── trunkControlTest/
│   ├── patients/
│   ├── scalesMenu/
│   ├── results/
│   ├── contact/
│   ├── navigation/         # AppNavGraph + Routes
│   └── theme/
│
└── di/                     # Módulos Hilt
```

---

## Tests

Tests unitarios de la capa de dominio con el patrón **GIVEN / WHEN / THEN**.

```bash
# Requiere JDK 17
JAVA_HOME=<path-to-jdk17> ./gradlew testDebugUnitTest
```

Cobertura actual:

| Suite | Use cases cubiertos |
|---|---|
| `PatientUseCasesTest` | Create, Edit, Delete, GetAll, GetById, GetTests |
| `ScalesUseCasesTest` | CreateTest, SaveBerg, SaveMotricity, SaveTrunk, GetBergById, GetMotricityById, GetTrunkById, GetTestResult |

---

## Requisitos

- Android Studio Hedgehog o superior
- JDK 17 (requerido para ejecutar tests desde terminal)
- Dispositivo o emulador con Android 11 (API 30) o superior
