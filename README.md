# CustomSkyMod

Mod para Minecraft 1.20.1 con builds para Forge y Fabric.

## Actualización de contenido

La carpeta `CustomSkyBoxes2/` contiene las clases compiladas de la versión de referencia actualizada. Durante la compilación, sus clases se aplican sobre `legacy/classes/`: las clases nuevas se añaden y las que cambiaron reemplazan su versión anterior. El resto de las clases existentes se conserva.

El proceso de renombrado sigue generando el paquete `org.cotii.customskymod` y los JAR separados para Forge y Fabric. Fabric excluye las clases específicas de Forge.

## Compilación

GitHub Actions compila ambos JAR al hacer push o mediante ejecución manual desde la pestaña **Actions**:

- `CustomSkyMod-1.20.1-forge.jar`
- `CustomSkyMod-1.20.1-fabric.jar`

Los archivos compilados se publican como artefactos del workflow **Build CustomSkyMod**.
