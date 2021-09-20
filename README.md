![GitHub Workflow Status](https://img.shields.io/github/workflow/status/joezwet/dice/Main?logo=github-actions&logoColor=white&style=flat-square)
![Maven metadata URL](https://img.shields.io/maven-metadata/v?label=release&logo=apache-maven&metadataUrl=https%3A%2F%2Fmvn.pyrrha.dev%2Fdev%2Fpyrrha%2Fdice%2Fmaven-metadata.xml&style=flat-square)
![Maven metadata URL](https://img.shields.io/maven-metadata/v?label=snapshot&logo=apache-maven&metadataUrl=https%3A%2F%2Fmvn.pyrrha.dev%2Fsnapshots%2Fdev%2Fpyrrha%2Fdice%2Fmaven-metadata.xml&style=flat-square)

# dice
Kotlin TTRPG dice library

## Installing
Gradle:
```groovy
repositories {
    maven {
        url "https://mvn.pyrrha.dev/"
    }
}

dependencies {
    implementation "dev.pyrrha:dice:$version"
}
```

<details>
<summary>Gradle - Kotlin DSL</summary>
<br>

```kotlin
repositories {
    maven("https://mvn.pyrrha.dev/")
}

dependencies {
    implementation("dev.pyrrha:dice:$version")
}

```
</details>

Maven:
```xml
 <repositories>
    <repository>
        <url>https://mvn.pyrrha.dev/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
      <groupId>dev.pyrrha</groupId>
      <artifactId>dice</artifactId>
      <version>$version</version>
    </dependency>
</dependencies>
```
Replace `$version` with the desired version of the library.

## Dice Syntax
This library uses the same dice syntax as [d20](https://d20.readthedocs.io/en/latest/start.html#dice-syntax) which is as follows:

### Numbers
These are the atoms used at the base of the syntax tree.

| **Name** | **Syntax** | **Description** | **Examples** |
| --- | --- | --- | --- |
| literal | `INT`, `DECIMAL` | A literal number. | `1`, `0.5`, `3.14` |
| dice | <code>INT? "d" (INT &#124; "%")</code> | A set of die. | `d20`, `3d6` |
| set | `"(" (num ("," num)* ","?)? ")"` | A set of expressions. | `()`, `(2,)`, `(1, 3+3, 1d20)` |

Note that `(3d6)` is equivalent to `3d6`, but `(3d6,)` is a set containing one element.

### Set Operations
These operations can be performed on dice and sets.

#### Grammar
| **Name** | **Syntax**           | **Description**                    | **Examples**    |
| ---      | ---                  | ---                                | ---             |
| set_op   | `operation selector` | An operation on a set. (See below) | `kh3`, `ro<3`   |
| selector | `seltype INT`        | A selection on a set. (See below)  | `3`, `h1`, `>2` |

#### Operators
Operators are always followed by as selector, and operate on the items in the set that match the selector.

| **Syntax** | **Name**        | **Description**                                                          |
| ---        | ---             | ---                                                                      |
| `k`        | Keep            | Keeps all matched values.                                                |
| `p`        | Drop            | Drops all matched values.                                                |
| `rr`       | Re-roll         | Re-rolls all matched values until none match. (Dice only)                |
| `ro`       | Re-roll once    | Re-rolls all matched values once. (Dice only)                            |
| `ra`       | Re-roll and add | Re-roll up to matched value once, keeping the original roll. (Dice only) |
| `e`        | Explode         | Rolls another die for each matched value. (Dice only)                    |
| `mi`       | Minimum         | Sets minimum value of each die. (Dice only)                              |
| `ma`       | Maximum         | Set maximum value of each die. (Dice only)                               |

#### Unary Operations
| **Syntax** | **Name** | **Description**           |
| ---        | ---      | ---                       |
| `+`        | Positive | Does nothing.             |
| `-`        | Negative | Makes the value negative. |

#### Binary Operations
| **Syntax** | **Name**         |
| ---        | ---              |
| `*`        | Multiplication   |
| `/`        | Division         |
| `//`       | Int Division     |
| `%`        | Modulo           |
| `+`        | Addition         |
| `-`        | Subtraction      |
| `==`       | Equality         |
| `>=`       | Greater or equal |
| `<=`       | Less or equal    |
| `>`        | Greater than     |
| `<`        | Less than        |
| `!=`       | Inequality       |
