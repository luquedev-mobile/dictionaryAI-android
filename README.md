# Dictionary AI
Dictionary AI es una aplicación de Android que te permite conocer el significado de frases o palabras en inglés y proporciona una explicación en español. Esta app utiliza Gemini como modelo de inteligencia artificial para realizar las traducciones y explicaciones, aplicando una arquitectura limpia y principios de desarrollo de software sólidos.

## Capturas de Pantalla

<img width="441" alt="image" src="https://github.com/user-attachments/assets/0e0e9daa-61e7-48dc-b764-635d367296e6">
<img width="441" alt="image" src="https://github.com/user-attachments/assets/1809413d-5742-4451-9f81-297d5b94f179">

## Características

- Traducción y explicación de frases y palabras en inglés: La app permite ingresar cualquier palabra o frase en inglés para obtener su significado en español.
- Gemini AI: Utiliza Gemini para la traducción y explicación de frases, asegurando resultados precisos y coherentes.
- Arquitectura limpia (Clean Architecture): Organización del código basada en Clean Architecture, mejorando la escalabilidad y mantenibilidad del proyecto.
- Principios SOLID y MVVM: Implementación de principios de diseño SOLID y patrón MVVM para un código más estructurado y fácil de probar.
- Testing: Cobertura de pruebas en las principales capas del proyecto para garantizar la estabilidad y fiabilidad de la aplicación.

  ## Requisitos

Para poder ejecutar el proyecto es necesario obtener una **API Key** para Gemini. Sigue los pasos a continuación:

1. Crea una API Key en [Google AI Studio](https://aistudio.google.com/app/apikey?hl=es-419).
2. Instalar Android Studio
5. Crea un archivo **local.properties** a nivel del proyecto y agrega la siguiente variable:

    ```properties
    API_KEY="YOUR_API_KEY"
    ```
## Arquitectura

Este proyecto sigue una arquitectura limpia (Clean Architecture) con las siguientes capas principales:
-	Data: Contiene las implementaciones de las fuentes de datos y los repositorios.
-	Domain: Contiene la lógica de negocio y los casos de uso.
-	Presentation (App): Gestiona la UI y sigue el patrón MVVM, con ViewModels que exponen datos a la UI y gestionan la lógica de presentación.

## Tecnologías y herramientas
-	Kotlin
-	MVVM: Patrón de arquitectura para la separación de la lógica de negocio de la UI.
-	Retrofit: Para las solicitudes de red.
-	Gemini AI: Inteligencia Artificial de Google para la traducción y explicación de textos.
-	Coroutines: Para la gestión de tareas asíncronas.
-	Testing: Pruebas unitarias y de UI.
