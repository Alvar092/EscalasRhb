# Testing

## Herramientas

| Librería | Versión | Uso |
|---|---|---|
| JUnit 4 | 4.13.2 | Framework base de tests |
| kotlinx-coroutines-test | 1.8.1 | Ejecutar `suspend` functions y `Flow` con `runTest` |

No se usa Mockito ni MockK. Los dobles de test son implementaciones manuales.

## Alcance

Solo se testa la capa **domain**: use cases y el contrato de los repositorios.

| Capa | ¿Testada? | Motivo |
|---|---|---|
| `domain/useCases` | Sí | Lógica de negocio principal |
| `domain/model` | No (aún) | Propiedades calculadas pendientes de test |
| `data/repositories` | No | Requeriría base de datos real (Room) |
| `presentation/viewmodels` | No | Requeriría entorno Android |
| `presentation/screens` | No | Requeriría entorno UI (Compose) |

## Estructura de carpetas

```
src/test/java/com/aentrena/escalasrhb/
  domain/
    useCases/
      patient/    → PatientUseCasesTest.kt
      scales/     → ScalesUseCasesTest.kt
  mocks/          → Fake repositories reutilizables
```

Un único fichero de test por feature, agrupando todos sus use cases.

## Patrón de test

### Fake repositories

En lugar de Mockito, cada repositorio tiene una implementación manual en memoria (`MutableStateFlow`) dentro de `mocks/`. Se comportan como repositorios reales pero sin base de datos, lo que permite verificar el comportamiento completo: guardar y recuperar en el mismo test.

Cada test instancia su propio fake, por lo que el estado siempre empieza limpio sin necesidad de `@Before`/`@After`.

```kotlin
class FakeBergTestRepository : BergTestRepository {
    private val tests = MutableStateFlow<List<BergTest>>(emptyList())
    // implementación en memoria...
}
```

Fakes disponibles:

| Fake | Repositorio que implementa |
|---|---|
| `MockPatientRepository` | `PatientRepository` |
| `FakeBergTestRepository` | `BergTestRepository` |
| `FakeMotricityIndexRepository` | `MotricityIndexRepository` |
| `FakeTrunkControlRepository` | `TrunkControlRepository` |
| `FakeClinicalHistoryRepository` | `ClinicalHistoryRepository` |

### Estructura GIVEN / WHEN / THEN

```kotlin
@Test
fun `given a BergTest, when saved, then stored in repository`() = runTest {
    // GIVEN
    val repository = FakeBergTestRepository()
    val useCase = SaveBergTestUseCase(repository)
    val test = bergTest()

    // WHEN
    useCase(test)

    // THEN
    assertEquals(listOf(test), repository.getAll().first())
}
```

- Nombre del test en backticks, en inglés, describiendo el escenario completo.
- `runTest` para todo test que use `suspend` o `Flow`.
- Un `@Test` por comportamiento.

---

## Casos cubiertos

### PatientUseCasesTest

**CreatePatientUseCase**
- Un paciente creado queda almacenado en el repositorio
- Varios pacientes creados quedan todos almacenados
- El ID proporcionado al crear se preserva sin modificación

**EditPatientUseCase**
- Editar un paciente actualiza sus datos en el repositorio
- Editar un paciente no altera al resto de pacientes

**DeletePatientUseCase**
- Eliminar un paciente por ID lo borra del repositorio
- Eliminar uno de varios pacientes deja al resto intactos
- Eliminar un ID inexistente no modifica el repositorio

**GetPatientsUseCase**
- Repositorio vacío emite lista vacía
- Repositorio con pacientes emite todos ellos

**GetPatientByIdUseCase**
- ID existente emite el paciente correspondiente
- ID inexistente emite `null`

**GetPatientTestsUseCase**
- Paciente con historial emite sus entradas
- Con historial de varios pacientes, solo emite las del paciente solicitado
- Paciente sin historial emite lista vacía

---

### ScalesUseCasesTest

**CreateTestUseCase**
- Tipo `BERG` crea un `BergTest` con los 14 ítems esperados
- Tipo `MOTRICITY_INDEX` crea un `MotricityIndexTest` con los 6 ítems esperados
- Tipo `TRUNK_CONTROL_TEST` crea un `TrunkControlTest` con los 4 ítems esperados
- Al crear un test de tipo `BERG`, queda persistido en el repositorio Berg

**SaveBergTestUseCase**
- Un `BergTest` guardado queda almacenado en el repositorio
- Guardar un `BergTest` con todos los ítems a puntuación máxima refleja `totalScore` de 56

**SaveMotricityIndexUseCase**
- Un `MotricityIndexTest` guardado queda almacenado en el repositorio

**SaveTrunkControlTestUseCase**
- Un `TrunkControlTest` guardado queda almacenado en el repositorio

**GetBergByIdUseCase**
- ID existente emite el `BergTest` correspondiente
- ID inexistente emite `null`

**GetMotricityByIdUseCase**
- ID existente emite el `MotricityIndexTest` correspondiente
- ID inexistente emite `null`

**GetTrunkControlByIdUseCase**
- ID existente emite el `TrunkControlTest` correspondiente
- ID inexistente emite `null`

**GetTestResultUseCase**
- Entrada de tipo `BERG` en el historial emite el `BergTest` correspondiente
- Entrada de tipo `MOTRICITY_INDEX` en el historial emite el `MotricityIndexTest` correspondiente
- Entrada de tipo `TRUNK_CONTROL_TEST` en el historial emite el `TrunkControlTest` correspondiente
- ID sin entrada en el historial emite `null`
