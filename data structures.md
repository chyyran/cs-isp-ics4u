# Data Structures and Classes

## Serialization Framework (`serialization.srsf.*`)

The following data structures involve the serialization and organization of data in the program. The `serialization.srsf.*` API supports saving and loading data to and from *Simple Relational String Format* data files, see **Appendix A** for format specifications.

### _serialization.srsf.**SerializationContext**_
#### Description
The context in which the collection of objects is loaded. Lazy should be used in conjunction with LazyResolver to resolve object references within the context, thus, object references in serialized SRSF text format must be self-contained within the SerializationContext.

#### Fields
| Field Name  | Field Type                    | Field Description                        |
| ----------- | ----------------------------- | ---------------------------------------- |
| directory   | `String`                      | The root directory of the serialization context |
| serializers | `HashMap<String, Serializer>` | The serializers, keyed on the name of the class it is responsible for. See `Serializer` |
| collection  | `HashMap<String, List>`       | The loaded collections of objects, keyed on the name of the class of each collection. |

#### Methods



#####`<T> void addSerializer(Serializer<T>, Class<T>)`
Adds a Serializer of type T to the serialization context. All serializers must be added before loading any object collections.

```
serializers.put(class.getName(), serializer)
```

#####`<T> void loadCollection(Class<T>)`
Loads the collection of type T from the file Name.srsf in the context directory, where Name is the simple name of the class T. For example, the class pokemon.data.PokemonType must load its information from PokemonType.srsf in the context directory. 

```
lines = loadFile(directory + class.getName() + ".srsf");
currentBlock = new List<>();
for line in lines:
    if line is "===" getCollection(class).add(currentMap)
    keyValue = 
```

#####`<T> void saveCollection(Class<T>)`
Saves the collection of type T to the file Name.srsf in the context directory, where Name is the simple name of the class T. For example, the class pokemon.data.PokemonType will save it’s information to  PokemonType.srsf in the context directory.


#####`<T> List<T> getCollection(Class<T>)`
Gets the loaded collection of items of type T as a List. 

### <u>interface</u> _serialization.srsf.**LazyResolver\<T\>**_

#### Description
An interface that represents a factory object used to resolve, or provide an instance of, an object at a later time, thus deferring initialization after object initialization for use with Lazy. 

#### Methods

##### `T resolve()`
Resolves the lazy object by creating a new instance of the object. This method acts as a factory for objects of type _T_.

### _serialization.srsf.**Lazy\<T\>**_

#### Description
Represents a lazily evaluated object type. By providing a `LazyResolver<T>` as an object factory, `Lazy<T>` provides an object only upon request, this can be thought of as a promise to instantiate an object. Once instantiated, the instance is cached, and subsequent requests for the object must return the same instance.


#### Fields

| Field Name | Field Type        | Field Description                        |
| ---------- | ----------------- | ---------------------------------------- |
| resolver   | `LazyResolver<T>` | The resolver for this `Lazy<T>`.         |
| isCreated  | `boolean`         | Whether the value has been created or not. |
| value      | `T`               | The cached value.                        |

#### Methods

##### `T getValue()`
Returns the cached or instantiates a new value of type T with the provided LazyResolver

##### `boolean isValueCreated()`
Returns whether or not the instance or value has been created with the LazyResolver, in other words whether getValue() had previously been called.

### *<u>abstract</u>* serialization.srsf.**Serializer\<T\>**

#### Description 
Represents a serializer to convert from the key value pair representation of an SRSF block to objects of type T, and vice versa. 
#### Fields
| Field Name | Field Type             | Field Description  |
| ---------- | ---------------------- | ------------------ |
| context    | `SerializationContext` | See `getContext()` |
#### Methods

#####`protected serialization.srsf.SerializationContext getContext()`
Gets the SerializationContext associated with this serializer.

##### `abstract T deserialize(HashMap<String, KeyValuePair>)`
Converts from the key value pair representation of an SRSF block to an instance of T.

##### `abstract HashMap<String, String> serialize(T)`
Converts from the instance of T to a hashmap of String/Value pairs ready for saving.



### _serialization.srsf._**KeyValuePair**

#### Description

A helper class to represent a single SRSF key value pair (`$name|value`).

#### Fields

| Field Name | Field Type | Field Description            |
| ---------- | ---------- | ---------------------------- |
| value      | `String`   | The string value of the pair |
| key        | `String`   | The string key of the pair   |
|            |            |                              |

#### Methods

##### `String getKey()`

Gets the key of the key value pair.

##### `String asString()`

Gets the value as a string. If the string `value` is equal to the magic string `@@NULL@@`, returns `null`.

##### `String[] asStringArray()`

Gets the value as a string array. If the string `value` is equal to the magic string `@@NULL@@`, returns `null`.

##### `int asInt()`

Attempts to get the value as an integer. If the string `value` is equal to the magic string `@@NULL@@`, returns `0`.


##### `int[] asIntArray()`

Attempts to get the value as an integer array. If the string `value` is equal to the magic string `@@NULL@@`, returns `null`.

##### `double asDouble()`

Attempts to get the value as a double. If the string `value` is equal to the magic string `@@NULL@@`, returns `0`.

##### `double[] asDoubleArray()`
Attempts to get the value as a double array. If the string `value` is equal to the magic string `@@NULL@@`, returns `null`.

##### `boolean asBoolean()`

Attempts to get the value as a boolean. If the string `value` is equal to the magic string `@@NULL@@`, returns false.

##### `boolean[] asBooleanArray()`
Attempts to get the value as a boolean array. If the string `value` is equal to the magic string `@@NULL@@`, returns `null`.


## Simple Relational String Format Schema (`serialization.srsf.schema.*`)

### _serialization.srsf.schema_.**Schema**
#### Description
Represents the SRSF Schema. See **Appendix A** for details.
#### Fields
| Field Name       | Field Type                | Field Description           |
| ---------------- | ------------------------- | --------------------------- |
| schemaProperties | `HashMap<String, String>` | See `getSchemaProperties()` |
| schemaName       | `String`                  | See `getSchemaName()`       |
| outputType       | `String`                  | See `getOutputType()`       |

#### Methods
##### `HashMap<String, String> getSchemaProperties()`
Gets the field names (properties) and types as String/String key value pairs. See **Appendix A** for details.

##### `String getSchemaName()`
Gets the name of the schema.

##### `String getOutputType()`
Gets the output type of the schema.

### _serializations.srsf.schema._**SchemaSerializer** _extends Serializer\<Schema\>_
#### Description

A serializer to convert from the schema SRSF format to a `Schema` object.

#### Methods
##### `@Override Schema deserialize(HashMap<String, KeyValuePair>)`
Converts schema SRSF files to `Schema` objects

#### `@Override HashMap<String, String> serialize()` 

Converts the schema to string/string key value pairs.





## Text-based Menu Framework (`menu.text.*`)

The following classes assist with building text-based menus, allowing options to be separately run and tested.

### _menu.text.**MenuBuilder**_

#### Description
Represents a menu with various options. Menus may contain options that spawn sub-menus. Exposes a fluent API to prepare various menu options, and a menu loop to display and execute the menu options. 

#### Fields
| Field Name | Field Type         | Field Description                        |
| ---------- | ------------------ | ---------------------------------------- |
| options    | `List<MenuOption>` | The list of all menu options for this menu |
| exit       | `MenuOption`       | The menu option used to exit this menu. Will cease the menu loop, and go back to the previous menu, or exit the application. The exit option is always run when the user selects a menu option less than 1. |
| error      | `ErrorHandler`     | The general handler for uncaught exceptions, usually displays an error message to the user |

#### Methods

##### `MenuBuilder option(MenuOption)`
Adds the given option to this menu, and returns the updated `MenuBuilder`

##### `MenuBuilder exit(MenuOption)`
Sets the given option to the menu exit option, and returns the updated `MenuBuilder`

##### `MenuBuilder error(ErrorHandler)`
Sets the given error handler menu error handler, and returns the updated `MenuBuilder`.

##### `void run()`
Starts the menu loop, and run until the user chooses to exit.

##### `@Override toString()`
Returns the string representation of the menu to be displayed to the user.

### _*<u>abstract</u>* menu.text.**MenuOption**_

#### Description 
An abstract class to represent an option in a menu. 

#### Fields

| Field Name | Field Type | Field Description |
| ---------- | ---------- | ----------------- |
| name       | `String`   | See `getName()`   |

#### Methods

##### `String getName()`
Gets the name of the menu option to be displayed to the user

##### `abstract void run()`
Runs the menu option once.

### *<u>abstract</u>* _menu.text.**ErrorHandler**_

#### Description
An abstract class to represent an error handler.

#### Methods

##### `abstract void handle(Exception)`
Handles the given exception.




## Pokemon Data Structures (`pokemon.data.*`)

### _pokemon.data._**PokemonSpecies**

#### Description

**PokemonSpecies** defines a single species of Pokemon, and include all relevant information. **PokemonSpecies** should be immutable upon creation, none of the properties of the object or child objects should be publically mutable after creation.

#### Fields

| Field Name    | Field Type             | Field Description        |
| ------------- | ---------------------- | ------------------------ |
| number        | `int`                  | See `getNumber()`        |
| name          | `String`               | See `getName()`          |
| primaryType   | `Lazy<PokemonType>`    | See `getPrimaryType()`   |
| secondaryType | `Lazy<PokemonType>`    | See `getSecondaryType()` |
| weight        | `double`               | See `getWeight()`        |
| evolution     | `Lazy<PokemonSpecies>` | See `getEvolution()`     |
| preEvolution  | `Lazy<PokemonSpecies>` | See `getPreEvolution()`  |
|               |                        |                          |

#### Methods

##### `String getName()`
The name of the Pokemon species, for example, ‘Pikachu’. This name should be the same for instances of the same Pokemon Species. 

##### `PokemonType getPrimaryType()`
The primary type of the Pokemon species.

##### `PokemonType getSecondaryType()`
The secondary type of the Pokemon species.

##### `double getWeight()`
The average weight of the Pokemon

##### `int getNumber()`
The unique Pokedex number of the Pokemon species. This number must be unique for each different species.

##### `PokemonSpecies getNextEvolution()`
The Pokemon species next in the evolutionary chain of the Pokemon. This merely indicates the Pokemon next in the evolutionary chain, and does not specify the requirements for the Pokemon species to evolve into it’s evolution. If this Pokemon species is the final stage in the evolutionary chain, then this property returns null.

##### `PokemonSpecies getPreviousEvolution()` 
The Pokemon species previous in the evolutionary chain of the Pokemon. This merely indicates the Pokemon previous in the evolutonary chain, and does not specify the requirements for the previous evolution species to evolve into the current Pokemon species. If this Pokemon species is the first in its evolutionary chain, then this property returns null. 

##### `boolean equals(PokemonSpecies)`

`PokemonSpecies` defines it's own equality by comparing the species number `getNumber()`. Two `PokemonSpecies` are equal if they have the same number, although in practice there should only be one instance of each unique `PokemonSpecies` throughout the lifetime of the application.

##### `@Override boolean equals(Object)`

Default `equals(Object)` override, defers to `equals(PokemonSpecies)`.

### _pokemon.data.**Move**_

#### Description 
Represents a single move along with its base damage, type, and name of the move.

#### Fields
| Field Name          | Field Type          | Field Description     |
| ------------------- | ------------------- | --------------------- |
| name                | `String`            | See `getName()`       |
| type                | `Lazy<PokemonType>` | See `getType()`       |
| baseDamage          | `double`            | See `getBaseDamage()` |
| selfAfflictedDamage | `double`            | See `getSelfDamage()` |

#### Methods
##### `String getName()`
The name of the move, for example, ‘Hyper Beam’.

##### `int getDamage()`
The damage of a move before any multipliers. Can be negative to apply a healing effect.

##### `int getSelfDamage()`
The damage applied to the Pokemon that uses this move. Can be negative to apply a healing effect.

##### `PokemonType getType()`
The type of the move. The type is important in figuring out the damage given to the other Pokemon in battles as each type is strong and weak to other types.

##### `boolean equals(Move)`

`Move` defines it's own equality by it's name. Two moves are equal if they have the same name `getName()`, ignoring case, however in practice there should only be a single instance of a unique move throughout the lifetime of the application.

##### `@Override boolean equals(Object)`

Default `equals(Object)` override. Defers to `equals(Move)` for equality.

### _pokemon.data.**Team**_ 
#### Description
The team is a collection of individual `Pokemon`, usually up to 6.
#### Fields
| Field Name | Field Type                 | Field Description                        |
| ---------- | -------------------------- | ---------------------------------------- |
| pokemon    | `Lazy<ArrayList<Pokemon>>` | The `ArrayList` holding the six pokemon. |

#### Methods
##### `void switchPosition(int, int)`
Swaps the positions of the pokemon in the two specified slots.
##### `List<Pokemon> getPokemon()`
Get all Pokemon in the team.
##### `Pokemon getActivePokemon()`
Gets the first Pokemon in the team.
##### `void setActivePokemon(int)`
Sets the active Pokemon to the Pokemon at the given index. This has the effect of swapping the Pokemon at the given index with the first Pokemon in the team.

### _pokemon.data.**PokemonType**_
#### Description
Representing a certain typing of Pokemon, `PokemonType` stores the name of the type and it’s weaknesses to other types (e.g Fire weak to Water, Rock, Ground), its strengths (e.g Fire strong against Grass, Ice, Bug), and its immunity (e.g. Ground is immune to Electric type). `PokemonType` is immutable upon creation.
#### Fields
| Field Name    | Field Type                | Field Description     |
| ------------- | ------------------------- | --------------------- |
| strongAgainst | `Lazy<List<PokemonType>>` | See `getStrengths()`  |
| weakAgainst   | `Lazy<List<PokemonType>>` | See `getWeaknesses()` |
| immuneAgainst | `Lazy<List<PokemonType>>` | See `getImmunity()`   |
| name          | `String`                  | See `getTypeName()`   |
#### Methods
##### `List<PokemonType> getStrengths()`
Gets the types this type is strong against.

####`List<PokemonType> getWeaknesses()`
Gets the types this type is weak against.

##### `List<PokemonType> getImmunities()`
Gets the types this type is immune against.

##### `boolean isWeakAgainst(PokemonType)`

Checks if the given type is weak against this type.

##### `boolean isStrongAgainst(PokemonType)`

Checks if the given type is strong against this type.

##### `boolean isImmuneAgainst(PokemonType)`

Checks if the given type is immune against this type.

##### `String getName()`
Gets the name of the type.

##### `boolean equals(PokemonType)`

`PokemonType` defines it's own equality based on whether or not two types have the same name `getName()`, ignoring case, although in practice there should only be one instance of a certain `PokemonType` throughout the instance of the application.

##### `@Override boolean equals(Object)`

Default `equals(Object)` override. Defers to `equals(PokemonType)` for equality.


### _pokemon.data.**Pokemon**_
#### Description
Represents an individual Pokemon belonging to the user or player character, including all information, such as species, move set, nickname, level, and health points.

#### Fields
| Field Name | Field Type             | Field Description   |
| ---------- | ---------------------- | ------------------- |
| species    | `Lazy<PokemonSpecies>` | See `getSpecies()`  |
| moves      | `Lazy<List<Move>>`     | See `getMoves()`    |
| name       | `String`               | See `getNickname()` |
| level      | `int`                  | See `getLevel()`    |
| id         | String                 |                     |
#### Methods

##### `PokemonSpecies getSpecies()`
Gets the species of this Pokemon

##### `List<Move> getMoves()`
Gets the list of moves for this Pokemon

##### `String getNickname()`
Gets the given nickname of this Pokemon. If not set, then return the name of the Pokemon species.

##### `void setNickname(String)` 
Sets the given nickname of this Pokemon. To unset, set the nickname to the empty string.

##### `int getLevel()` 
The level of this Pokemon. The higher the level is, the greater the base damage of each Pokemon. Also, the health point increases every time the Pokemon levels up.

##### `void setLevel(int)`
Sets the level of this Pokemon. 

##### `int getHP()` 
Gets the amount of health points of this Pokemon.

##### `void setHP(int)`
Sets the amount of health points of this Pokemon.

##### `boolean isFainted()`
The status of the Pokemon that indicates whether it is alive or fainted. If the Pokemon’s the health point is zero, the Pokemon is fainted thus it returns true. Otherwise, it returns false.

## Pokemon Core Facilities (`pokemon.core.*`)

The following classes contain the behavior of the program regarding Pokedex and Battle functionality, and are not meant to be saved to a file. 

### _pokemon.core.**Pokedex**_

#### Description

Represents a sortable and searchable collection of `PokemonSpecies`.



#### Fields

| Field Name     | Field Type             | Field Description                        |
| -------------- | ---------------------- | ---------------------------------------- |
| pokemonSpecies | `List<PokemonSpecies>` | The list of Pokemon species defined in this Pokedex. |

#### Methods

##### `List<PokemonSpecies> searchPokemonByType(PokemonType)`

Searches for and returns all `PokemonSpecies` with the given `PokemonType`.

##### `List<PokemonSpecies> searchPokemonByName(String)`

Searches for and returns all `PokemonSpecies` with a name matching the given string.

##### `List<PokemonSpecies> getAllPokemon()`

Gets all the Pokemon in the Pokedex.

##### `PokemonSpecies getPokemon(int)`

Gets the Pokemon with the given number.

### _pokemon.core.battle.**BattleState**_

#### Description
An enumeration of possible battle states.

#### Enumeration
| Enumeration          | Description                              |
| -------------------- | ---------------------------------------- |
| `PLAYER_ONE_MOVE`    | The battle is waiting for the first player to select their move |
| `PLAYER_TWO_MOVE`    | The battle is waiting for second player  |
| `PLAYER_ONE_FAINTED` | Player one has a fainted Pokemon and must select a new one from their team. |
| `PLAYER_TWO_FAINTED` | Player two has  a fainted Pokemon and must select a new one from their team. |
| `PLAYER_ONE_VICTORY` | The battle has been completed, player two has exhausted their team, and player one is the victor. |
| `PLAYER_TWO_VICTORY` | The battle has been completed, player one has exhausted their team, and player two is the victor. |

### _pokemon.core.battle.**BattleManager**_

#### Description
Manages a battle between two Pokemon `Teams`. `BattleManager` is a state machine intended to be run in a loop until one Team has encountered a victory state. 

#### Fields
| Field Name | Field Type    | Field Description                     |
| ---------- | ------------- | ------------------------------------- |
| state      | `BattleState` | The current state of the battle       |
| teamOne    | `Team`        | The Pokemon team of the first player  |
| teamTwo    | `Team`        | The Pokemon team of the second player |
|            |               |                                       |

#### Methods

##### `BattleState getState()`
Gets the current state of the battle.

##### `Team getTeamOne()`
Gets the first team of Pokemon in the battle.

##### `Team getTeamTwo()`
Gets the second team of Pokemon in the battle.

##### `void applyMove(Move, Pokemon, Pokemon)`
Applies the effects of the given move to the casting and target Pokemon. This method calculates the amount of damage dealt to each Pokemon after the move is applied, and determines the new `BattleState` after damage calculations.

## Pokemon Serialization Classes (`pokemon.serialization.*`)

**Pokemon Serializer Classes**

The following classes extend `serialization.srsf.Serializer` and are used to serialize their specified types.

### _pokemon.serialization._**PokemonTypeSerializer** extends Serializer\<PokemonType\>
Serializes `pokemon.data.PokemonType`

#### Schema
```
$schemaName|PokemonType
$outputType|pokemon.data.PokemonType
@name|string
@strongAgainst|[string!!PokemonType]
@weakAgainst|[string!!PokemonType]
---
```
#### Example
```
$name|WATER
$strongAgainst|[FIRE]
$weakAgainst|[GRASS]
---
```
#### Methods
##### `@Override PokemonType deserialize(HashMap<String, KeyValuePair)`
Converts from a block of the above example to a `PokemonType`.

##### `@Override HashMap<String, String> serialize(PokemonType)`
Converts from a `PokemonType` object to it's key value pair representation.

### _pokemon.serialization._**PokemonSpeciesSerializer** extends Serializer\<PokemonSpecies\>
Serializes `pokemon.data.PokemonSpecies`

#### Schema
```
$schemaName|PokemonSpecies
$outputType|pokemon.data.PokemonSpecies
@name|string
@index|int
@primaryType|string!!PokemonType
@secondaryType|string!!PokemonType
@weight|double
@evolution|int!!PokemonSpecies
@preevolution|int!!PokemonSpecies
---
```
#### Example
```
$index|1
$name|Bulbasaur
$primaryType|GRASS
$secondaryType|@@NUL@@
$weight|10
$evolution|2
$preevolution|0
---
```
#### Methods
##### `@Override PokemonSpecies deserialize(HashMap<String, KeyValuePair)`
Converts from a block of the above example to a `PokemonSpecies`. `$evolution` and `$preevlotion` are represented as the Pokemon number.

##### `@Override HashMap<String, String> serialize(PokemonSpecies)`
Converts from a `PokemonSpecies` object to it's key value pair representation.

### _pokemon.serialization._**MoveSerializer** extends Serializer\<Move\>
Serializes `pokemon.data.Move`

#### Schema
```
$schemaName|Move
$outputType|pokemon.data.Move
@name|string
@type|string!!PokemonType
@baseDamage|int
@selfDamage|int
---
```
#### Example
```
$name|Hyper Beam
$type|NORMAL
$baseDamage|100
$selfDamage|50
---
```
#### Methods
##### `@Override Move deserialize(HashMap<String, KeyValuePair)`
Converts from a block of the above example to a `Move`. `$evolution` and `$preevlotion` are represented as the Pokemon number.

##### `@Override HashMap<String, String> serialize(Move)`
Converts from a `Move` object to it's key value pair representation.


### _pokemon.serialization._**PokemonSerializer** extends Serializer\<Pokemon\>
Serializes `pokemon.data.Pokemon`

#### Schema
```
$schemaName|Pokemon
$outputType|pokemon.data.Pokemon
@species|int!!PokemonSpecies
@nickName|string
@level|int
@moves|[string!!Move]
@hp|int
@id|string
---
```

#### Example
```
$species|1
$nickName|Bulby
$level|10
$moves|[LEECHSEED,TACKLE,@@NULL@@,@@NULL@@]
$hp|100
$id|A9810DJ12D
---
```

#### Methods
##### `@Override Pokemon deserialize(HashMap<String, KeyValuePair)`
Converts from a block of the above example to a `Pokemon`

##### `@Override HashMap<String, String> serialize(Pokemon)`
Converts from a `Pokemon` object to it's key value pair representation.

### _*pokemon.serialization.*_**TeamSerializer** extends Serializer\<Team\>

Serializes `pokemon.data.Team`

#### Schema
```
$schemaName|Team
$outputType|pokemon.data.Team
@pokemon|[string!!Pokemon]
---
```

#### Example
```
$pokemon|[A9810DJ12D]
---
```

#### Methods
##### `@Override Team deserialize(HashMap<String, KeyValuePair)`
Converts from a block of the above example to a `Team`. Pokemon are stored as references by the unique Pokemon ID.

##### `@Override HashMap<String, String> serialize(Pokemon)`
Converts from a `Team` object to it's key value pair representation.



**Pokemon Resolver Classes**

Resolves loaded Pokemon information from a serialization context.


### _*pokemon.serialization.*_**PokemonTypeListResolver** implements \<LazyResolver\<List\<PokemonType\>\>

Lazily resolves a list of Pokemon types from their type names.

#### Fields
| Field Name | Field Type             | Field Description                     |
| ---------- | ---------------------- | ------------------------------------- |
| context    | `SerializationContext` | The context to resolve the types from |
| typeNames  | `String[]`             | The names of the types                |

#### Methods
##### `@Override List<PokemonType> resolve()`
Resolves the list of loaded types from the serialization context


### _*pokemon.serialization.*_**PokemonSpeciesResolver** implements \<LazyResolver\<PokemonSpecies\>

Lazily resolves a Pokemon species from the Pokemon number

#### Fields
| Field Name | Field Type             | Field Description                     |
| ---------- | ---------------------- | ------------------------------------- |
| context    | `SerializationContext` | The context to resolve the types from |
| number     | `int`                  | The Pokemon number                    |

#### Methods
##### `@Override PokemonSpecies resolve()`
Resolves the Pokemon species from the serialization context


### _*pokemon.serialization.*_**PokemonIdResolver** implements \<LazyResolver\<PokemonSpecies\>

Lazily resolves a Pokemon from the Pokemon ID

#### Fields
| Field Name | Field Type             | Field Description                     |
| ---------- | ---------------------- | ------------------------------------- |
| context    | `SerializationContext` | The context to resolve the types from |
| id         | `String`               | The Pokemon ID                        |

#### Methods
##### `@Override Pokemon resolve()`
Resolves the Pokemon from the serialization context

### _*pokemon.serialization.*_**PokemonTypeResolver** implements \<LazyResolver\<PokemonType\>

Lazily resolves a single Pokemon types from their type names.

#### Fields
| Field Name | Field Type             | Field Description                     |
| ---------- | ---------------------- | ------------------------------------- |
| context    | `SerializationContext` | The context to resolve the types from |
| typeName   | `String`               | The name of the type                  |

#### Methods
##### `@Override PokemonType resolve()`
Resolves the types from the serialization context



## _Menu Options (`pokemon.menu.*`)_

The following classes extend `menu.text.MenuOption` to provide the UI for the application.

### _pokemon.menu.PokedexMenu_
A menu option to display and search the Pokedex. 

#### Fields

| Field Name | Field Type | Field Description  |
| ---------- | ---------- | ------------------ |
| pokedex    | `Pokedex`  | The Pokedex to use |

#### Methods

##### `@Override run()`

Runs the Pokedex menu option. 

### _pokemon.menu.TeamMenu_
A menu option to manage your Pokemon Team

#### Fields

| Field Name | Field Type | Field Description          |
| ---------- | ---------- | -------------------------- |
| team       | `Team`     | The Pokemon team to manage |

#### Methods

##### `@Override run()`

Runs the Pokemon team menu option. 

### _pokemon.menu.BattleMenu_
A menu option to start a Pokemon Battle. Is responsible for managing the `pokemon.core.battle.BattleManager`

#### Fields

| Field Name    | Field Type      | Field Description                        |
| ------------- | --------------- | ---------------------------------------- |
| battleManager | `BattleManager` | The Pokemon battle manager to run the battle menu. |

#### Methods

##### `@Override run()`

Runs the Pokemon battle with the battle manager. 



## Pokemon Comparators (`pokemon.data.comparators.*`)

The following classes implement `Comparator<T>` for Pokemon Data Types

### _pokemon.data.comparators_.**PokemonSpeciesNameComparator** implements Comparator\<PokemonSpecies\>

Compares two `PokemonSpecies` by their name in lexicographical order.

#### Methods

##### `int compare(PokemonSpecies, PokemonSpecies)`

Compares two Pokemon species by their name `getName()` in lexicographical order. Defers to `String.compare` for comparison.



### _pokemon.data.comparators_.**PokemonSpeciesNumberComparator** implements Comparator\<PokemonSpecies\>

Compares two `PokemonSpecies` by their Pokemon number.

#### Methods

##### `int compare(PokemonSpecies, PokemonSpecies)`

Compares two Pokemon species by their number `getNumber()` Defers to `Integer.compare` for comparison.

### _pokemon.data.comparators_.**PokemonSpeciesWeightComparator** implements Comparator\<PokemonSpecies\>

Compares two `PokemonSpecies` by their weight.

#### Methods

##### `int compare(PokemonSpecies, PokemonSpecies)`

Compares two Pokemon species by their weight `getWeight()` Defers to `Double.compare` for comparison.

## Appendix A — Simple Relational String Format 

*Simple Relational String Format*, or SRSF defines a generic method to load and save a collection of objects into text files. Objects are loaded into a root serialization context from formatted ‘.srsf’ record files, and are accessible as collections of loaded objects. In order to resolve self-references, object references are lazily evaluated and resolved within the context before being produced into objects by the converter. Record files are expected to contain a collection of objects of the same type, and will be serialized as a list or array.

## Serialization Context

The serialization context is the root context in which a set of SRSF files are loaded into. Each serialization context should be self contained in a folder, and contain at least a `Schema.srsf`, specifying the schema of the data files contained in the folder, and one or more `.srsf` record files describing the actual records. Saving and loading should occur only in the specified context folder, and all `.srsf` files in the context folder must be named as the simple name of the class it represents, for example, data representing instances of `pokemon.data.PokemonType` must be saved and loaded from `PokemonType.srsf`. 



## SRSF Tokens

| Token       | Example                             | Description                              |
| ----------- | ----------------------------------- | ---------------------------------------- |
| `~~!srsf~~` | `~~!srsf~~`                         | The magic header required at the beginning of all valid SRSF record files. |
| `!!`        | `!!PokemonType`                     | The type descriptor, describing the Java type this record file is a collection of. If  used after a primitive descriptor in a schema, indicates an object reference of the type. |
| `$`         | `$name|Pikachu`                     | A semantic marker for a required field, indicates that this value will be used to provide a bean property of the object to be serialized. |
| `@`         | `@name|string`                      | A semantic marker for a schema property, valid only in schema definitions, indicates that this value is a typing for a field defined in a sep |
| `|`         | `$name|Pikachu`                     | The separator character separating the semantic marker from the string value. |
| `[]`        | `[WATER, GRASS]`                    | For a value, indicates that the value represents an array of primitives. |
| `,`         | `[WATER, GRASS]`                    | In an array value, separates the values of the array. |
| `---`       | `---`                               | Block marker delimiter, indicates the end of a record block. |
| `@@NULL@@`  | `$name|@@NULL@@`                    | Indicates a reference to the null or default value. To represent the string literal of "@@NULL@@", enclose the literal shall be enclosed in quotes. |
| `#`         | `#Some comment is is written here.` | Indicates that the line begins a comment. |



## SRSF Record Format

An SRSF file begins with the magic string `~~!srsf~~`, followed by the type marker `!!`, a type, and the block end marker. Thus, the first "block" in any SRSF format specifies the schema type for the rest of the file. Files may contain any amount of blocks delimited by the block marker delimiter, and each block must contain the data required to instantiate one instance of the type specified. 

```
~~!srsf~~
!!PokemonType
---
$name|GRASS
$strongAgainst|[WATER,GROUND]
$weakAgainst|[FIRE]
#todo add more types
---
```

This example file denotes a collection of `PokemonType` objects, with a single instance. Containing the header block and one record block, the record block has the properties `name`, `strongAgainst`, and `weakAgainst`. `strongAgainst` and `weakAgainst` are string arrays, but as specified in a separate schema file, are in actuality typed to be `PokemonType`s themselves, and must be deserialized accordingly.

## SRSF Schema Files

The SRSF Schema format defines a schema for every single record file in the serialization context. Named `Schema.srsf`, this file should be the first to be loaded in any serialization context. One can consider the schema file to be a special case of Record, as they are serialized just the same as a normal record. However, they are privileged in that the `Schema.srsf` need not a schema specified for itself, and the Schema format is the only format specified to have metadata in it's definitions; thus each record shape will be different, unlike normal records.

```
!!schema
---
$schemaName|PokemonType
$outputType|pokemon.data.PokemonType
@name|string
@strongAgainst|[string!!PokemonType]
@weakAgainst|[string!!PokemonType]
---
```

This schema defines the type for the previous `PokemonType` example. A schema has two required field, `schemaName`, indicating the name of the schema, generally the simple name of the type, and `outputType`, indicating the fully qualified name of the Java type it represents. The entries that begin with `@` are metadata properties and are used to define the shape of the schema. Rather than have values, they must contain either a primitive type, or a primitive that will be serialized into an object reference. SRSF primitives are not exactly the same as Java primitives, the list of SRSF primitives is defined as follows

| SRSF Primitive | Definition                               | Java Equivalent    |
| -------------- | ---------------------------------------- | ------------------ |
| `string`       | A string-like type.                      | `java.lang.String` |
| `bool`         | A true or false value represented by the string literals `true` or `false`. | `boolean`          |
| `int`          | A machine-length integer guaranteed to be at least 30 bits large. | `int`              |
| double         | An IEEE-754 double precision floating-point number | `double`           |
|                |                                          |                    |

Thus, each SRSF must be composed of, or have objects references derived, on the responsibility of the Serializer, these four SRSF primitive types.

#Algorithms and concepts

## Accessors
Methods to get an access or to get the value of a field within a class
```
	<data type> get<fieldName>()
		return <fieldName>
```

## Mutators
Methods to change the value of a field within a class
```
<data type> set<fieldName>(<variable>) 
```

## Binary Search
This will be used when searching for a specific Pokémon using the ID in a list of Pokémon sorted by ID.
```
<item> BinarySearch(list L, item)
	middle = middle value of list 
if middle’s Field = item’s Field
		Then return item;
	If middle’s Field > item’s Field
		Then 
list L2 = first half of L
		return BinarySearch(L2, item)
	If middle’s Field <  item’s Field
		Then
list L2 = second half of L
		return BinarySearch(L2, item)
```

## Sequential Search
This will be used search for a Pokémon by name and type. 
```
	SequentialSearch(list L, String x)
		For each item in the list
			If (y matches x) 
				Return y;
```

## Bubble Sort
This will be used to sort Pokémon in Pokedex by name, ID, and weight

```
	BubbleSort (List L)
		for upperbound equals L.length - 1 down to 1 and sorted is false
Set sorted to true
for j from 0 to upperbound - 1
	If j > j + 1
		set sorted to false
		swap j and j + 1
```

## Recursion
This will be used as part of the Binary Search
Refer to Binary Search

## File Input/Output
This will be used to store or load teams of Pokémon.


## Generics
This concept is used during serialization for Lazy instantiation (`Lazy<PokemonType>`), use of dynamic arrays (`List<Pokemon>`), and hash maps (`HashMap<String, String>`). A generic class or algorithm allows the types to be specified later when needed.

## Dynamic Arrays
A dynamic array or a List is a resizable array of indefinite size. Used to store Pokemon, types, and allow lazy instanatiation of array-like types.

## Interfaces
A contract for a class to implement. Used to define the `LazyResolver<T>` interface used during serialization. Also used for `PokemonSpecies` comparators.

## Hash Maps
A data structure that maps keys to values. Used during serialization to represent key value pairs.