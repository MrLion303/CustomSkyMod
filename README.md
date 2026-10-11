# CustomSkyMod

Mod para **Minecraft Java 1.20.1**, con compilaciones para **Forge** y **Fabric**. Permite personalizar el cielo mediante texturas, colores, efectos y ajustes globales o específicos por bioma.

> **Importante:** los comandos se registran como comandos del mod y la implementación actual está orientada al cliente/renderizado. La disponibilidad exacta puede depender del loader y del contexto en que se ejecute el juego. Un cambio visual local no se replica automáticamente a todos los jugadores de un servidor.

## Funciones

- **Cielos personalizados:** establecer texturas o colores para los tipos de cielo admitidos por el mod.
- **Texturas de paquetes de recursos:** buscar y resolver texturas entre los recursos disponibles.
- **Texturas animadas y GIF:** mostrar texturas animadas cuando el recurso y el efecto sean compatibles.
- **Efectos y animaciones del cielo:** iniciar, detener y ajustar efectos visuales.
- **Parámetros visuales:** valores de color, intensidad (*strength*), tamaño (*size*), escala (*scale*) y repetición (*loop*), según el efecto.
- **Ajustes por bioma:** aplicar y limpiar personalizaciones en un bioma específico.
- **Capas y overrides:** configurar valores globales y ajustes que se aplican a tipos de cielo o biomas.
- **Presets y colores:** utilizar valores sugeridos, colores con nombre y valores hexadecimales cuando sean aceptados.
- **Transiciones:** gestionar cambios visuales y desvanecimientos entre skyboxes.
- **Elementos celestes y estrellas:** renderizar componentes del cielo y presets celestes.
- **Clima, nubes y renderizado:** integración con el renderizado visual mediante mixins del cliente.
- **Dos loaders:** JAR separados para Forge y Fabric.

## Comandos

El comando raíz es `/customskyboxes`. La tabla describe la finalidad de cada rama. Como la sintaxis puede variar según la operación y los argumentos requeridos por el tipo de cielo, usa **Tab** después de escribir el comando para ver las opciones y valores que expone la compilación instalada. No añadas argumentos por intuición: si el juego sugiere un orden o formato concreto, sigue esa sugerencia.

### Referencia rápida

| Comando | Qué hace | Cuándo usarlo |
|---|---|---|
| `/customskyboxes set ...` | Crea o cambia una personalización global del cielo. El argumento de tipo determina qué parte del cielo se modifica; el valor puede ser una textura, color o preset admitido. | Para cambiar el aspecto general del cielo sin limitar el ajuste a un bioma. |
| `/customskyboxes clear ...` | Elimina una personalización global. Según los argumentos sugeridos, permite apuntar a una configuración concreta o limpiar overrides más amplios. | Para volver a dejar que el mod o el juego utilice el comportamiento normal en el ámbito seleccionado. |
| `/customskyboxes reload` | Solicita recargar o actualizar los recursos utilizados por el mod. | Después de cambiar un paquete de recursos o cuando una textura recién disponible no aparece. |
| `/customskyboxes animate ...` | Configura o inicia una animación/efecto de cielo. Puede aceptar parámetros visuales adicionales. | Para crear un cielo animado o ajustar el comportamiento de un efecto compatible. |
| `/customskyboxes time ...` | Modifica el valor de tiempo utilizado por las funciones del mod que dependen de su reloj. | Para probar variaciones visuales que dependan del tiempo. Consulta con Tab qué valor y formato acepta. |
| `/customskyboxes stop ...` | Detiene una animación o efecto activo, según el identificador o los argumentos admitidos. | Para detener un efecto sin tener que limpiar necesariamente toda la configuración del cielo. |
| `/customskyboxes list` | Muestra la información que el comando tenga disponible sobre las configuraciones registradas. | Para revisar qué ajustes expone el mod antes de modificarlos o limpiarlos. |
| `/customskyboxes biome set ...` | Define una personalización de cielo asociada a un bioma. | Para que un ajuste esté vinculado a un bioma, en lugar de aplicarse globalmente. |
| `/customskyboxes biome clear ...` | Quita la personalización vinculada al bioma indicado, o al ámbito que especifiquen los argumentos. | Para retirar un override de bioma y dejar de forzar ese aspecto en él. |
| `/customskyboxes biome list` | Lista las personalizaciones específicas por bioma que estén registradas. | Para revisar los ajustes por bioma antes de editarlos o borrarlos. |
| `/customskyboxes biome animate ...` | Define o activa una animación/efecto asociado a un bioma cuando el efecto lo admite. | Para dar un comportamiento animado a un bioma sin tratarlo como una configuración global. |

Los puntos suspensivos (`...`) son marcadores explicativos, **no se escriben literalmente**. Representan los argumentos que debes seleccionar mediante las sugerencias del comando.

### Cómo descubrir la sintaxis exacta

1. Escribe `/customskyboxes` y pulsa **Tab** para consultar las subopciones.
2. Completa la rama que necesitas, por ejemplo `set`, `animate` o `biome`, y vuelve a pulsar **Tab**.
3. Continúa de la misma forma hasta que aparezcan los tipos de cielo, nombres de bioma, presets, texturas o parámetros disponibles.
4. Si el juego muestra un error de uso, conserva el orden y el formato que indique ese mensaje.
5. Para los comandos de bioma, entra primero en la rama `biome`; no asumas que los argumentos son idénticos a los de la variante global.

### Parámetros de animación

La implementación contiene los siguientes nombres de parámetros. No todos los efectos admiten todos ellos; utiliza únicamente los que el subcomando y sus sugerencias acepten.

| Parámetro | Función | Rango o formato identificado |
|---|---|---|
| `color` | Color usado por el efecto. | Colores nombrados o valor hexadecimal compatible, según las sugerencias. |
| `strength` | Intensidad del efecto. | De **0 a 5**. |
| `size` | Tamaño del elemento o efecto. | De **0.1 a 5**. |
| `scale` | Escala del efecto. | De **0 a 100**. |
| `loop` | Indica si el efecto debe repetirse. | `true` o `false`. |

Cuando la sintaxis mostrada por el comando use el formato `nombre=valor`, escribe el nombre y el valor juntos, respetando exactamente ese formato. No supongas que todos los parámetros pueden combinarse entre sí.

### Ejemplos de flujo de uso

Estos ejemplos muestran el **orden de exploración**, no una sintaxis completa que debas copiar literalmente:

- **Cambiar el cielo global:** abre `/customskyboxes set`, usa Tab para elegir el tipo de cielo y después selecciona el valor disponible.
- **Probar una animación:** abre `/customskyboxes animate`, selecciona el tipo/efecto sugerido y agrega solo los parámetros que el comando permita.
- **Personalizar un bioma:** abre `/customskyboxes biome set`, selecciona el bioma sugerido y completa la personalización solicitada.
- **Retirar una personalización:** consulta las opciones de `clear` o `biome clear` para seleccionar exactamente qué override deseas quitar.
- **Actualizar recursos:** ejecuta `/customskyboxes reload` y comprueba si las texturas o recursos nuevos se detectan correctamente.
- **Detener un efecto:** consulta `/customskyboxes stop` con Tab para elegir el alcance o identificador que admita la versión instalada.

No se incluyen comandos completos con valores ficticios porque los nombres de tipos, presets, texturas y el orden de los argumentos deben corresponder a los registrados por la versión del mod que estás usando.

## Servidores y selectores

La personalización del cielo es principalmente visual y del lado del cliente. Los selectores de Minecraft, como `@a`, solo son válidos en comandos que acepten selectores de entidades y se ejecuten en un contexto con permisos adecuados. **No** convierten por sí mismos un cambio visual local en una configuración sincronizada para todos los jugadores. Antes de dar por hecho que una acción afecta a todo el servidor, prueba el comando en un servidor dedicado y confirma qué clientes reciben el cambio.

## Compilar y descargar

El workflow **Build CustomSkyMod** genera los siguientes artefactos:

- `CustomSkyMod-1.20.1-forge.jar`
- `CustomSkyMod-1.20.1-fabric.jar`

Abre [GitHub Actions](https://github.com/MrLion303/CustomSkyMod/actions), entra en la ejecución más reciente que haya terminado correctamente y descarga los artefactos. La compilación automática se ejecuta con los cambios del repositorio y puede ejecutarse manualmente si el workflow lo permite.

## Cómo se integra CustomSkyBoxes2

La carpeta `CustomSkyBoxes2/` contiene las clases compiladas de la versión de referencia actualizada. Durante la compilación, esas clases se aplican sobre `legacy/classes/`: las clases nuevas se añaden, las clases con cambios sustituyen a su versión anterior y las demás se conservan.

El proceso de renombrado sigue generando los paquetes esperados por este proyecto y mantiene JAR separados para Forge y Fabric. Fabric excluye las clases específicas de Forge.
