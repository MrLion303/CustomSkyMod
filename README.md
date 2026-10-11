# CustomSkyMod

Mod para **Minecraft Java 1.20.1**, con compilaciones para **Forge** y **Fabric**. Permite personalizar el cielo mediante texturas, colores, efectos y ajustes globales o específicos por bioma.

> **Importante:** los comandos se registran como comandos del mod y la implementación actual está orientada al cliente/renderizado. La disponibilidad exacta de comandos puede depender del loader y de cómo se ejecute el juego. No asumas que un cambio visual del cliente se replica automáticamente a todos los jugadores de un servidor.

## Funciones

- **Cielos personalizados:** establecer texturas o colores para los tipos de cielo que admite el mod.
- **Texturas de paquetes de recursos:** búsqueda y resolución de texturas desde los recursos disponibles.
- **Texturas animadas y GIF:** soporte para texturas animadas mediante el sistema de texturas del mod.
- **Efectos y animaciones del cielo:** iniciar, detener y configurar efectos animados.
- **Parámetros visuales:** opciones como color, intensidad (*strength*), tamaño (*size*), escala (*scale*) y repetición (*loop*), según el efecto.
- **Ajustes por bioma:** establecer y limpiar personalizaciones para un bioma específico.
- **Capas de configuración:** permitir ajustes globales por tipo y por capa.
- **Presets y colores:** sugerencias para valores predefinidos, colores nombrados y colores hexadecimales.
- **Transiciones:** gestión de cambios y desvanecimientos entre skyboxes.
- **Elementos celestes y estrellas:** renderizado de componentes del cielo y presets celestes.
- **Clima, nubes y renderizado del cielo:** integración con el renderizado visual mediante mixins del cliente.
- **Compatibilidad de distribución:** generación de JAR separados para Forge y Fabric.

## Comandos

El comando raíz registrado por la versión actual es `/customskyboxes`. Minecraft suele permitir usar la tecla **Tab** para consultar las subopciones y valores sugeridos por el propio comando.

| Comando / subcomando | Para qué sirve |
|---|---|
| `/customskyboxes set ...` | Establece una personalización global del tipo de cielo indicado. El valor puede ser un preset, color o textura admitida. |
| `/customskyboxes clear ...` | Limpia una personalización global; puede limpiar un tipo concreto o todos los overrides globales, según el argumento. |
| `/customskyboxes reload` | Vuelve a cargar o actualizar los datos de recursos que utiliza el mod. |
| `/customskyboxes animate ...` | Inicia una animación o efecto para un tipo de cielo. Los argumentos de efecto admiten parámetros como `color`, `strength`, `size`, `scale` y `loop`, dependiendo de la animación. |
| `/customskyboxes time ...` | Ajusta el tiempo utilizado por las funciones de cielo que dependen del reloj del mod. |
| `/customskyboxes stop ...` | Detiene una animación identificada o las animaciones activas, según el argumento. |
| `/customskyboxes list` | Muestra la información de lista que ofrece el comando, incluidas las personalizaciones por bioma cuando corresponda. |
| `/customskyboxes biome set ...` | Asigna una personalización de cielo a un bioma concreto. |
| `/customskyboxes biome clear ...` | Elimina personalizaciones de un bioma o de un tipo concreto dentro de él. |
| `/customskyboxes biome list` | Lista las personalizaciones específicas por bioma registradas. |
| `/customskyboxes biome animate ...` | Configura un efecto animado asociado a un bioma, si el tipo y los parámetros indicados lo admiten. |

Los argumentos exactos dependen del subcomando y del tipo de cielo. Para evitar inventar sintaxis que no coincida con la compilación instalada, escribe `/customskyboxes` y usa **Tab** para inspeccionar las opciones disponibles. Los nombres de tipo, presets, texturas y parámetros también cuentan con sugerencias cuando están disponibles.

### Valores de animación

Los parámetros que reconoce el sistema de animaciones incluyen:

- `color`: color del efecto; admite colores nombrados y valores hexadecimales compatibles.
- `strength`: intensidad, de **0 a 5**.
- `size`: tamaño, de **0.1 a 5**.
- `scale`: escala, de **0 a 100**.
- `loop`: repetición; utiliza `true` o `false`.

No todos los efectos usan todos los parámetros. Si el comando informa de que un argumento debe escribirse como `nombre=valor`, conserva ese formato.

## Compilar y descargar

El workflow **Build CustomSkyMod** genera los siguientes artefactos:

- `CustomSkyMod-1.20.1-forge.jar`
- `CustomSkyMod-1.20.1-fabric.jar`

Abre [GitHub Actions](https://github.com/MrLion303/CustomSkyMod/actions), entra en la ejecución más reciente que haya terminado correctamente y descarga los artefactos. La compilación automática se ejecuta con los cambios del repositorio y puede ejecutarse manualmente si el workflow lo permite.

## Cómo se integra CustomSkyBoxes2

La carpeta `CustomSkyBoxes2/` contiene las clases compiladas de la versión de referencia actualizada. Durante la compilación, esas clases se aplican sobre `legacy/classes/`: las clases nuevas se añaden, las clases con cambios sustituyen a su versión anterior y las demás se conservan.

El proceso de renombrado sigue generando los paquetes esperados por este proyecto y mantiene JAR separados para Forge y Fabric. Fabric excluye las clases específicas de Forge.

## Servidores y selectores

La personalización del cielo es principalmente visual y del lado del cliente. Los selectores de comandos de Minecraft, como `@a`, solo funcionan en comandos que aceptan selectores de entidades y se ejecutan con los permisos adecuados; no convierten por sí solos un ajuste visual local en una configuración sincronizada para todos los jugadores. Comprueba el comportamiento en un servidor dedicado antes de asumir que una personalización se comparte con todos.
