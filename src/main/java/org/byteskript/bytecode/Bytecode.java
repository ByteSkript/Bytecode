package org.byteskript.bytecode;

import org.byteskript.bytecode.syntax.constant.Const;
import org.byteskript.bytecode.syntax.constant.LDC;
import org.byteskript.bytecode.syntax.constant.Push;
import org.byteskript.bytecode.syntax.generic.DUP;
import org.byteskript.bytecode.syntax.generic.NOP;
import org.byteskript.bytecode.syntax.generic.POP;
import org.byteskript.bytecode.syntax.generic.SWAP;
import org.byteskript.bytecode.syntax.invoke.InvokeNormal;
import org.byteskript.bytecode.syntax.logic.AND;
import org.byteskript.bytecode.syntax.logic.OR;
import org.byteskript.bytecode.syntax.logic.XOR;
import org.byteskript.bytecode.syntax.maths.*;
import org.byteskript.bytecode.syntax.shift.SHL;
import org.byteskript.bytecode.syntax.shift.SHR;
import org.byteskript.bytecode.syntax.shift.USHR;
import org.byteskript.bytecode.syntax.value.New;
import org.byteskript.bytecode.syntax.value.Return;
import org.byteskript.bytecode.syntax.variable.Load;
import org.byteskript.bytecode.syntax.variable.Store;
import org.byteskript.skript.api.ModifiableLibrary;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.runtime.Skript;

public class Bytecode extends ModifiableLibrary {
    public static final Bytecode LIBRARY = new Bytecode();
    
    public Bytecode() {
        super("bytecode");
        registerSyntax(CompileState.CODE_BODY,
            new SWAP(),
            new DUP(),
            new POP(),
            new NOP()
        );
        registerSyntax(CompileState.CODE_BODY,
            new Load(),
            new Store(),
            new Return(),
            new New()
        );
        registerSyntax(CompileState.CODE_BODY,
            new Const(),
            new Push(),
            new LDC()
        );
        registerSyntax(CompileState.CODE_BODY,
            new ADD(),
            new SUB(),
            new MUL(),
            new DIV(),
            new REM(),
            new NEG()
        );
        registerSyntax(CompileState.CODE_BODY,
            new SHL(),
            new SHR(),
            new USHR()
        );
        registerSyntax(CompileState.CODE_BODY,
            new AND(),
            new OR(),
            new XOR()
        );
        registerSyntax(CompileState.CODE_BODY,
            new InvokeNormal()
        );
    }
    
    public static void main(String... args) {
        System.out.println("This goes in the ByteSkript libraries/ folder.");
    }
    
    public static void load(Skript skript) {
        skript.registerLibrary(LIBRARY);
    }
}
