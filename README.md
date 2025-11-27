# POO Final Project - Smart Parking System

## Integrantes

- Sergio Andrés Arango Arango #63723
- Jorge Santiago Correa Valencia #65997

## Descripción general

Es un sistema de parqueadero desarrollado en java, que permite administrar la entrada y salida de vehículos automáticamente.

El programa busca optimizar la asignación de espacios, asignándoles un parqueadero automáticamente según  el espacio que ocupe el vehículo.

## Objetivo

- Controlar automáticamente la entrada y salida de vehículos
- Mantener un registro de ganancias mediante archivos CSV
- Gestionar dinámicamente los espacios de parqueo y sus configuraciones
  
## Alcance

El sistema soporta:
- Vehículos, que se clasifican en carro y moto
- Espacios en los que se puede parquear un carro o una cantidad dinámica de motos (ajustada por el usuario)
- Configuración de tarifas
- Persistencia de datos con un historial de carros parqueados en un CSV

## Estructura

El ejecutable de la app se encuentra dentro de la carpeta src, llamado ParkingApp.java

El código de la app se encuentra distribuido en tres capas: ui, domain y data

- UI: contiene todas las operaciones relacionadas con lo que el usuario ingresa y lo que se le muestra
- Domain: Contiene las clases que representan el modelo del parqueadero: `Vehicle`, `ParkingSpot` y `ParkingLot`
- Data: Contiene lo relacionado con la persistencia de datos. Allí se encuentran los métodos para guardar y cargar archivos serializados y CSV.

# Instrucciones para ejecutar el programa

1. Clonar o descargar el proyecto
2. Abrir el repositorio desde la raíz
3. **Ejecutar ParkingSystem.java con toda la raíz abierta.** Si no se hace de esa manera, el programa puede no funcionar de la manera adecuada.
4. Seguir las instrucciones del menú mostrado en la consola
5. Agregar todos los espacios con sus nombres respectivos


## Ejemplo de entrada y salida
```text
--- Primera ejecución detectada (o archivo de datos no encontrado) ---
Por favor, configure los datos iniciales del sistema:
>> Ingrese el nombre para el archivo de reportes CSV (ej: 'ganancias'): noviembre2025
>> Ingrese cantidad de motos por espacio (ej: 5): 5
>> Ingrese costo por hora para Motos: 2000
>> Ingrese costo por hora para Carros: 5000
Sistema configurado y guardado exitosamente.

### Ejemplo de agregación de espacios
--- SMART PARKING SYSTEM ---
Estado: 0 Ocupados | 0 Disponibles
------------------------------
1. Gestión de Vehículos (Entrada/Salida/Buscar)
2. Gestión de Espacios (Crear/Eliminar/Listar)
3. Reportes y Ganancias (CSV)
4. Configuración del Sistema
5. Salir
>> Seleccione una opción: 2

--- GESTIÓN DE ESPACIOS ---
1. Ver espacios disponibles
2. Ver ocupación general
3. Listar espacios del parquedaero(Espacios y Placas)
4. Agregar un nuevo espacio (Construir)
5. Eliminar un espacio
6. Volver
>> Seleccione: 4
>> Nombre del nuevo espacio (ej: B05): B05
Espacio creado exitosamente.
(Autoguardado exitoso)
```
### Ejemplo de agregación de vehículos
````text
Estado: 0 Ocupados | 2 Disponibles
------------------------------
1. Gestión de Vehículos (Entrada/Salida/Buscar)
2. Gestión de Espacios (Crear/Eliminar/Listar)
3. Reportes y Ganancias (CSV)
4. Configuración del Sistema
5. Salir
>> Seleccione una opción: 1

--- GESTIÓN DE VEHÍCULOS ---
1. Ingresar Vehículo
2. Retirar Vehículo
3. Buscar Vehículo
4. Volver
>> Seleccione: 1
--- Ingreso de Vehículo ---
>> Ingrese Placa: HHW130
>> Tipo de Vehículo (c = Carro, m = Moto): c
ÉXITO: Vehículo agregado en el espacio: B05
(Autoguardado exitoso)

--- SMART PARKING SYSTEM ---
Estado: 1 Ocupados | 1 Disponibles
------------------------------
1. Gestión de Vehículos (Entrada/Salida/Buscar)
2. Gestión de Espacios (Crear/Eliminar/Listar)
3. Reportes y Ganancias (CSV)
4. Configuración del Sistema
5. Salir
>> Seleccione una opción: 2

--- GESTIÓN DE ESPACIOS ---
1. Ver espacios disponibles
2. Ver ocupación general
3. Listar espacios del parquedaero(Espacios y Placas)
4. Agregar un nuevo espacio (Construir)
5. Eliminar un espacio
6. Volver
>> Seleccione: 3
--- Mapa del Parqueadero ---
Espacio [B05]:
   -> Vehículo: HHW130 (Car)
Espacio [B06]:
   -> (Vacío)

```

### Ejemplo de eliminación de carros
```text
--- Bienvenido de nuevo. Datos cargados correctamente. ---

--- SMART PARKING SYSTEM ---
Estado: 4 Ocupados | 6 Disponibles
------------------------------
1. Gestión de Vehículos (Entrada/Salida/Buscar)
2. Gestión de Espacios (Crear/Eliminar/Listar)
3. Reportes y Ganancias (CSV)
4. Configuración del Sistema
5. Salir
>> Seleccione una opción: 1

--- GESTIÓN DE VEHÍCULOS ---
1. Ingresar Vehículo
2. Retirar Vehículo
3. Buscar Vehículo
4. Volver
>> Seleccione: 2
--- Retiro de Vehículo ---
>> Ingrese la placa del vehículo a retirar: COZ92E
Tiempo parqueado: 0 horas.
TOTAL A PAGAR: $2000
Vehículo retirado y ticket guardado.
(Autoguardado exitoso)
```