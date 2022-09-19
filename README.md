# Ternareso
This is an esoteric language taking inspiration from Brainf***, but focussing on nested ternary statements to implement control flow and looping.

The name is a portmanteau of Ternary and Esoteric. 

## Usage

Build the application using `./gradlew build`, then execute the packaged jar file against an input file using `java -jar build/libs/ternareso-<version>.jar /path/to/input/file`, replacing `<version` with the appropriate version.

## The Language

The language can be described as a single Ternary with the form `<preamble>?success:failure`. Where the preamble is a list of operations as defined below, and the success/failure paths are themselves Ternaries. The `?success:failure` can be omitted to create a simple list of actions in the preamble. 

A Ternary is executed by first running the preamble. If the Ternary is a `test` Ternary, then the `?` operator indicates testing if the state of the current address on the tape is 0. If the value is 0, the success Ternary is executed, else the failure Ternary is executed.

The program state is represented by an array of `int` - the tape - and an address on the tape. The tape doubles in length when attempting to move beyond the right-hand side, and negative addresses are not supported.

### Operators

- `+ ` - Increment the value at the current memory address.
- `- ` - Decrement the value at the current memory address.
- `< ` - Move the pointer one address to the left.
- `> ` - Move the pointer one address to the right.
- `\ ` - Return 1 level. A single `\ ` indicates that the current ternary will be re-executed, multiple slashes will iterate up the tree. If there are more slashes than layers, the program will exit.

All other characters (barring `?` and `:`) are ignored.

### Examples

A set of functional examples can be found in the test directories, however here are some program flow examples.

#### Preamble only

```
++++>++++>++++>++++<-<--<---
```
The program above adds 4 to each of the first four cells, then subtracts 1,2,3 moving backwards to produce the output `[1, 2, 3, 4]`

#### Single Level Ternary

```
+++>++>?+:-
```
The program above creates the tape `[3, 2, 0, 0]` in its preamble, then tests the third cell. As the third cell is 0, the "success" path will execute, adding one to result in `[3, 2, 1, 0]`. If an extra `+` is added to the preamble, the third cell would equal 1, so the failure path would execute, resulting in `[3, 2, -1, 0]`.

#### Double Level Ternary with Return

```
+++?\\:-?\\\:\
```
The program above creates the tape `[3]` in its preamble. The first layer Ternary then tests, if it were zero, it would execute `\\` and return 2 levels (i.e. end the program). As the value is not zero, the failure path executes, which itself is a Ternary. The second level Ternary subtracts 1 from the cell, and then tests. If the value is 0, it returns 3 levels, ending the program with output `[0]`, otherwise, it returns 1 level, and repeats the second level ternary.
This will loop the second level ternary until the output is `[0]` and then exit, regardless of the starting value.