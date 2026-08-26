# Proyecto Móviles - Sistema de Seguimiento de Autobuses en Tiempo Real

Este proyecto consiste en una aplicación móvil desarrollada para Android que permite a los usuarios visualizar y realizar un seguimiento en tiempo real de diversas rutas de autobuses urbanos. La aplicación integra tecnologías modernas de geolocalización y autenticación para ofrecer una experiencia de usuario fluida y segura.

## Características Principales

- **Autenticación de Usuarios**: Sistema completo de registro, inicio de sesión y gestión de contraseñas mediante Firebase Auth.
- **Seguimiento en Tiempo Real**: Simulación de movimiento de autobuses a través de rutas predefinidas utilizando flujos de datos reactivos.
- **Mapas Interactivos**: Integración con Google Maps para visualizar la ubicación actual de los vehículos y el trazado de las rutas.
- **Gestión de Rutas**: Visualización detallada de múltiples rutas (Hamacas, Caracolí, Cumbre, entre otras) con información sobre conductores y capacidad.
- **Personalización**: Configuración de preferencias del usuario, incluyendo el ajuste del tamaño de texto para mejorar la accesibilidad.
- **Seguridad y Privacidad**: Pantallas dedicadas a políticas de privacidad y gestión segura de credenciales.

## Arquitectura y Tecnologías

La aplicación sigue los principios de arquitectura recomendados por Google (MVVM) y utiliza las siguientes herramientas:

- **Lenguaje**: Kotlin.
- **Interfaz de Usuario**: Jetpack Compose (Declarativa).
- **Gestión de Estado**: ViewModel y StateFlow para reactividad en tiempo real.
- **Navegación**: Compose Navigation para la gestión de flujos entre pantallas.
- **Backend**: Firebase Authentication para el control de acceso.
- **Mapas**: Google Maps SDK for Android y Maps Compose.
- **Permisos**: Accompanist Permissions para la gestión de acceso a la ubicación.

## Requisitos del Sistema

- Dispositivo con Android 8.0 (API 26) o superior.
- Conexión a Internet para autenticación y carga de mapas.
- Servicios de Google Play instalados.
