# EscalasRhb — CLAUDE.md

App Android para profesionales de la salud que administran escalas clínicas estandarizadas a pacientes y exportan los resultados en PDF. Uso exclusivamente offline.

## Stack

- **Kotlin + Jetpack Compose** (Material 3)
- **MVVM + Clean Architecture** (capas: `data` / `domain` / `presentation`)
- **Hilt** para inyección de dependencias
- **Room** para base de datos local (sin red, sin backend)
- **PDF nativo** con `android.graphics.pdf.PdfDocument` + patrón Strategy
- compileSdk 36, minSdk 30

## Restricciones del proyecto

- **Sin backend ni red**: todo es local. No hay ni habrá servidor remoto, autenticación cloud ni sincronización.
- **Sin nuevas dependencias externas**: usar únicamente las librerías ya incluidas en `build.gradle.kts`.
- **Protocolo clínico estricto**: las escalas (Berg, Motricity Index, Trunk Control Test) siguen sus protocolos internacionales estandarizados. No modificar criterios de puntuación ni ítems sin justificación clínica explícita.
- **Localización solo es/en**: los strings van en `res/values/strings*.xml` (español) y `res/values-en/strings*.xml` (inglés). No añadir otros idiomas.

## Arquitectura y convenciones

### Capas

```
domain/
  model/           → Modelos puros (Patient, BergTest, MotricityIndex, TrunkControlTest)
  interfaces/      → ClinicalTest, SideTest, repositorios
  useCases/        → Un caso de uso por operación

data/
  local/entities/  → Entidades Room
  local/daos/      → DAOs Room
  local/mappers/   → Conversión Entity ↔ Domain model
  repositories/    → Implementaciones de los repositorios del dominio
  services/pdf/    → PdfGenerator + PdfLayout + estrategias por escala

presentation/
  <feature>/       → Screen.kt + ViewModel.kt
  <feature>/resources/ → ItemCatalog, ItemDefinition, ScoreOption
  navigation/      → AppNavGraph.kt + Routes.kt
  theme/           → Color.kt, Type.kt, Theme.kt
```

### Añadir una nueva escala clínica

Seguir este patrón exacto (ver Berg o MotricityIndex como referencia):

1. **Domain model**: `domain/model/scales/NuevaEscala.kt` implementando `ClinicalTest`
2. **Añadir `TestType`**: enum en `domain/model/TestType`
3. **Room**: Entity → DAO → Mapper → `AppDatabase` (añadir la entidad y el DAO)
4. **Repositorio**: interfaz en `domain/interfaces/repositories/` + implementación en `data/repositories/`
5. **Use cases**: crear/guardar/obtener por id en `domain/useCases/scales/`
6. **DI**: registrar en `di/RepositoryModule.kt`
7. **Resources de presentación**: `ItemDefinition`, `ScoreOption`, `ItemCatalog` (objeto con `Map<ItemType, ItemDefinition>`)
8. **Screen + ViewModel** en `presentation/<nuevaEscala>/`
9. **PDF**: `ItemPdf` + `PdfStrategy` implementando `TestPdfStrategy`, registrar en `di/PdfModule.kt`
10. **Strings**: añadir en `strings_<nuevaescala>.xml` (es y en)
11. **Navegación**: añadir ruta en `Routes.kt` y destino en `AppNavGraph.kt`

### Interfaz ClinicalTest

Todo test debe implementar `ClinicalTest`:
```kotlin
interface ClinicalTest {
    val id: UUID
    val date: Long
    val evaluator: String?
    val patientId: UUID
    val side: BodySide?   // null si la escala no tiene lado (Berg, Trunk Control)
    val maxScore: Int?
    val totalScore: Int
    val items: List<ClinicalTestItem>
    val testType: TestType
}
```

Si la escala tiene lateralidad (izquierdo/derecho), implementar también `SideTest`.

### PDF

El generador usa el patrón Strategy: cada escala tiene su propia clase que implementa `TestPdfStrategy`. El `PdfGenerator` recibe la estrategia correcta vía Hilt.

## Strings y localización

Los strings de cada escala están en ficheros separados:
- `strings_berg.xml` — Berg Balance Scale
- `strings_motricity.xml` — Motricity Index
- `strings_trunkControl.xml` — Trunk Control Test
- `strings_scalesInfo.xml` — Textos informativos de las escalas
- `strings.xml` — Strings generales de la app

Siempre añadir la traducción en inglés en `res/values-en/`.

## Testing

### Alcance

Solo se testea la capa **domain**: modelos puros y use cases. No se testean repositorios Room, ViewModels ni Composables.

### Dependencias de test

```kotlin
// build.gradle.kts
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
testImplementation("junit:junit:4.13.2") // ya incluida
```

`kotlinx-coroutines-test` es la única excepción a la regla de no nuevas dependencias — es del mismo grupo de librerías ya usado y necesaria para testear `suspend` y `Flow` con `runTest`.

### Estructura de carpetas

Los tests siguen el mismo paquete que el código de producción:

```
src/test/java/com/aentrena/escalasrhb/
  domain/
    model/         → Tests de modelos puros (sin mocks)
    useCases/
      patient/     → PatientUseCasesTest.kt
      scales/      → ScalesUseCasesTest.kt
  mocks/           → Fake repositories reutilizables
```

**Un único fichero de test por feature**, agrupando todos sus use cases. No crear un fichero por use case.

### Patrón de mocks

Usar **fake implementations manuales** de las interfaces de repositorio. No usar Mockito ni MockK.

```kotlin
class FakeBergTestRepository : BergTestRepository {
    private val tests = MutableStateFlow<List<BergTest>>(emptyList())

    override fun getAll(): Flow<List<BergTest>> = tests
    override fun getByPatient(patientId: UUID): Flow<List<BergTest>> =
        tests.map { it.filter { t -> t.patientId == patientId } }
    override fun getById(id: UUID): Flow<BergTest?> =
        tests.map { it.firstOrNull { t -> t.id == id } }
    override suspend fun save(test: BergTest) {
        val current = tests.value.toMutableList()
        val index = current.indexOfFirst { it.id == test.id }
        if (index >= 0) current[index] = test else current.add(test)
        tests.value = current
    }
}
```

Los fakes se colocan en `src/test/.../mocks/` y se reutilizan entre tests.

### Estilo GIVEN / WHEN / THEN

```kotlin
@Test
fun `given items with scores, when totalScore is accessed, then returns the sum`() {
    // GIVEN
    ...

    // WHEN
    val result = ...

    // THEN
    assertEquals(expected, result)
}
```

- Nombre del test en backticks, en inglés, describiendo el escenario completo.
- Secciones `// GIVEN`, `// WHEN`, `// THEN` siempre presentes como comentarios.
- Un `@Test` por comportamiento, no por función.
- Para `suspend` functions y `Flow` usar `runTest { }` (no `runBlocking`).

### Qué testear en cada capa

| Capa | Qué testear |
|------|-------------|
| `domain/model` | Propiedades calculadas (`totalScore`, `age`), lógica pura (`withTimeScoring`) |
| `domain/useCases` | Que delega correctamente al repositorio, que el resultado es el esperado |

No testear: getters/setters triviales, constructores, mappers Room.

## Navegación

```
Home → Patients → PatientDetail → ScaleMenu → ScaleInfo → Test → TestResult
                                                                ↘ (export PDF)
Home → Contact
```

Las rutas con parámetros usan `UUID` y `TestType.name` (string del enum).
