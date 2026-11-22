# BiCIAM

Práctica 5 de Laboratorio de Desarrollo y Herramientas · Curso 2026–2027

BiCIAM es una biblioteca Java de optimización que incluye generadores monoobjetivo y multiobjetivo. La demostración incluida ejecuta `RandomSearch` para minimizar `(x - 3)²` con candidatos enteros de −5 a 5.

## Compilación y pruebas

Se necesita un JDK 17 y Maven 3.9 o posterior.

```bash
mvn clean verify
java -jar target/biciam-1.0.0-SNAPSHOT-demo.jar
```

La prueba de ejecución debe imprimir:

```text
BiCIAM | RandomSearch | x=3 | f(x)=0.0
```

JaCoCo exige un mínimo del **80 % de cobertura de línea por clase** en `Problem`, `State`, `FactoryLoader`, `AcceptBest`, `Dominance`, `StopExecute` y `OnePointCrossover`. El informe completo está en `target/site/jacoco/index.html`.

## Calidad y seguridad

```bash
mvn clean verify -Pquality,dependency-audit
```

| Herramienta | Informe en `target/` |
| --- | --- |
| JaCoCo | `site/jacoco/index.html` y `site/jacoco/jacoco.xml` |
| JUnit 5 | `surefire-reports/` |
| PMD | `pmd.xml` |
| Checkstyle | `checkstyle-result.xml` |
| SpotBugs y FindSecBugs | `spotbugsXml.xml` |
| OWASP Dependency-Check | `dependency-check-report.html`, `.json` y `.sarif` |

El perfil `quality` conserva los avisos de los analizadores para su revisión. El perfil `dependency-audit` consulta NVD y el catálogo KEV de CISA y falla si detecta una vulnerabilidad con CVSS igual o superior a 7. La primera descarga de NVD puede tardar varios minutos.

## Documentación

```bash
doxygen Doxyfile
```

La documentación se abre en `target/doxygen/html/index.html`. El sitio también se adjunta como artefacto de la ejecución de GitHub Actions.

## Docker

Con el motor Docker en marcha:

```bash
bash scripts/docker-demo.sh
```

El script construye `biciam:practica5`, ejecuta el contenedor y verifica la salida de la demostración. Desde PowerShell, los comandos equivalentes son `docker build -t biciam:practica5 .` y `docker run --rm biciam:practica5`.

## SonarQube Cloud

El flujo [Calidad y despliegue](.github/workflows/calidad.yml) publica JaCoCo y los resultados externos cuando el repositorio está vinculado a SonarQube Cloud. Configura el secreto `SONAR_TOKEN` y las variables `SONAR_ORGANIZATION` y `SONAR_PROJECT_KEY` en GitHub Actions. La integración usa análisis mediante CI. En SonarQube Cloud, desactiva el análisis automático si está habilitado y ejecuta el flujo en `main`.

Los hallazgos confirmados por Dependency-Check se importan como incidencias externas asociadas a `pom.xml`. Los informes originales se conservan en el artefacto `informes-biciam`.
