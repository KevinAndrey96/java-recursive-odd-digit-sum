# Suma recursiva de los dígitos impares de una cifra (Java 17+)

[![CI](https://github.com/KevinAndrey96/java-recursive-odd-digit-sum/actions/workflows/ci.yml/badge.svg)](https://github.com/KevinAndrey96/java-recursive-odd-digit-sum/actions/workflows/ci.yml)
![Java 17 | 21 | 25](https://img.shields.io/badge/Java-17%20%7C%2021%20%7C%2025-ED8B00?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-6-25A162?logo=junit5&logoColor=white)
[![Licencia MIT](https://img.shields.io/badge/licencia-MIT-green)](LICENSE)

Programa en **Java** que suma los **dígitos impares** de una cifra de **N dígitos** usando **solo funciones y condicionales**, como exige el enunciado: sin ciclos `for` ni `while`. La recursión divide la cifra por mitades, así que la profundidad de llamadas es **logarítmica** y funciona con cifras de millones de dígitos.

## Enunciado

> Escriba un programa en lenguaje Java partiendo de su algoritmo, en donde un usuario ingresa un entero N mayor o igual a 4, seguido una cifra C entera de la misma cantidad de dígitos de N. Por ejemplo, si N es igual a 4, una cifra C puede ser 5674. El programa debe identificar solo con el uso de funciones y condicionales la suma de los valores de los dígitos impares. Por ejemplo, si N=5 y C=16796, la respuesta que debe dar el programa es 17.

## Uso

```bash
mvn -B verify
java -jar target/odd-digit-sum.jar 5 16796
```

```text
Cifra           : 16796 (5 dígitos)
Dígitos impares : 1 + 7 + 9
Suma            : 17
```

Sin argumentos, el programa pide N y C por consola. Ante una entrada inválida responde con un mensaje en español y código de salida `2`.

## Algoritmo

```text
suma(cifra, desde, hasta):
    si hay un solo dígito: devolver el dígito si es impar, si no 0
    mitad = (desde + hasta) / 2
    devolver suma(cifra, desde, mitad) + suma(cifra, mitad, hasta)
```

| Enfoque | Llamadas | Profundidad de la pila | Cifra de 1 000 000 dígitos |
| --- | --- | --- | --- |
| Recursión dígito a dígito | N | N | `StackOverflowError` |
| **Divide y vencerás (este proyecto)** | 2N - 1 | ⌈log₂ N⌉ + 1 | 21 marcos de pila |

Las pruebas comparan el resultado con un ciclo convencional sobre 500 cifras aleatorias y suman una cifra de un millón de dígitos.

## Validaciones

- N debe ser un entero **mayor o igual a 4**.
- C solo puede contener dígitos ASCII del 0 al 9, sin signo, espacios internos ni separadores.
- C debe tener **exactamente N dígitos** y, por ser un entero, no puede empezar por 0.

## Defectos de la versión de 2017 corregidos

| Defecto original | Corrección |
| --- | --- |
| La condición `N <= 4` rechazaba N = 4, permitido por el enunciado | Mínimo de 4 dígitos, con prueba de regresión |
| Usaba un ciclo `for`, contrario a "solo funciones y condicionales" | Recursión por mitades sin ciclos |
| Aceptaba letras y signos en la cifra y fallaba con `NumberFormatException` | Validación de dígitos con mensajes claros |
| Un N no numérico terminaba el programa con una excepción | Código de salida `2` y mensaje en español |
| Interfaz con `JOptionPane`, imposible de probar o automatizar | CLI sin interfaz gráfica, con modo interactivo y por argumentos |

## Estructura

| Clase | Responsabilidad |
| --- | --- |
| `Figure` | Cifra validada (`record`) contra el N declarado |
| `OddDigitSum` | Suma y lista de dígitos impares por recursión divide y vencerás |
| `cli.OddDigitSumCli` | Entrada por argumentos o consola y salida en español |

## Calidad

```bash
mvn -B verify   # compila con -Xlint:all -Werror y ejecuta 25 pruebas JUnit
```

GitHub Actions compila y prueba en Java 17, 21 y 25, y ejecuta una prueba de humo del JAR.

## Historia del proyecto

Ejercicio de programación de septiembre de 2017 hecho en NetBeans con ventanas `JOptionPane`. El código original se conserva en la etiqueta [`v1.0-netbeans-swing-2017`](https://github.com/KevinAndrey96/java-recursive-odd-digit-sum/tree/v1.0-netbeans-swing-2017). En 2026 se reescribió con recursión, validaciones, pruebas y CI.

## Licencia

[MIT](LICENSE) © 2017-2026 Kevin Andrey Herrera Silva
