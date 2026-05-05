
# Contributing to Harmony (Communications Application)  

This guide covers mostly coding style for submitting pull requests. 

[Styleguides](#styleguides)
  * [Overview](#overview)
  * [Indentation](#indentation)
  * [Width](#width)
  * [Identifiers](#identifiers)
  * [Expressions](#expressions)
  * [If / for / while etc](#if-for-while-etc)
  * [Functions](#functions)
  * [Comments](#comments)
  * [Header files](#header-files)
  * [Whitespace](#whitespace)

# Styleguides

## Overview

We have established a set of coding style guidelines in order to
clean up the code consistently and keep it consistent in the future.
Look around and respect the same style.

## Indentation

Utilize either tabs or 4 spaces for formatting. 

## Width

Width or length of lines should be reasonable, not extending across the
screen or be unreasonably broken. This comes down to mainly judgement, but
a good cut off is about 70 or so characters. 

## Identifiers

Functions, local variables, and arguments are all named using camelCase, no spaces. 
Global variables should be avoided all-together.

Single-character variables are a bad idea. Except for when utilized in repetition
structures or passed in as arguments for accessors, mutators, or constructors. Do 
not do it for more complex functions. 

## Expressions

In general, use whitespace around binary operators - no unspaced blobs of an
expression. `make style` will take care of whitespaces around operators.

For example,
```c
    if (5 * a < b && some_bool_var)
```
but not
```c
    if (5*a<b&&some_bool_var)
```

## If / for / while etc

Put the opening brace on the same or next line, with or without a space before it.
There can or can not be a space between the construct name (if/for/whatever) and the
opening parenthesis, and there should be a space between the closing parenthesis
and the opening brace, and no space between parenthesis and expression.

For generic `for()` iterator variables, declare them in-line:
```c
    for (int i = 0; i < 10; i++) {
        ...
    }
```
Note the spaces after the semicolons.

if/else should be laid out as follows:
```c
    if (foo) {
        ...
    } else if (bar) {
        ...
    } else {
        ...
    }
```
You can skip braces around 1-line statements but don't mix braces vs. no braces.

## Functions

Put the return type on the same line.
Put a space after a comma in argument lists.
Open the brace after the declaration (with or without a space).
OR have the brace under the function declaration. 
```c
void foo(int aThing, int somethingElse) {
    ...
}
```
Functions with no arguments are declared as`f()`.
```c
void baz(void) {
    foo(buh, guh);
}
```
Function names should be done in camelCase.

Don't use single-character arguments.
Exception: very short functions with one argument that's really obvious:
```c
void setFoo(int c) {
    someAttribute = c;
}
```

## Comments

Use //, it's shorter:
```c
// this does foo
...

// baz:
// This does blah blah blah .....
// blah blah...
```
`/* */` can be used to comment blocks of code or describe things in more
detail. Either style of comment is allowed.

## Header files

Keep imports grouped together at the top of the file.
Remove unused imports. 

## Whitespace

Avoid trailing whitespace (no line should end in tab or space).
Keep a newline (blank line) at the end of each file.


