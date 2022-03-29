# Bytecode

A language library for [ByteSkript](https://docs.byteskript.org) that allows the writing of raw bytecodes in triggers.
This allows very advanced developers to define exactly how the compiler output will be structured.

```bsk
function test:
    trigger:
        ldc "hello there"
        astore 0
        aload 0
        pop
        iconst_1
        invokestatic org/byteskript/bytecode/Test, "test", "(I)I", false
        invokestatic java/lang/Boolean, "valueOf", "(Z)Ljava/lang/Boolean;", false
        areturn
```

This library is not designed for general use, since it is natively unstable.

## Design Goals

This library was designed for situations where exact execution is needed, or efficiency of a particular step is a priority.
As it allows the mixing of raw JVM bytecode with regular Skript code, almost anything is made possible to the user.

There is no safety net or error handling so any errors must be triaged by the user.
