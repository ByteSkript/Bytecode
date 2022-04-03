package org.byteskript.bytecode;

import org.byteskript.bytecode.syntax.comparison.CMP;
import org.byteskript.bytecode.syntax.constant.Const;
import org.byteskript.bytecode.syntax.constant.LDC;
import org.byteskript.bytecode.syntax.constant.Push;
import org.byteskript.bytecode.syntax.conversion.NumberConversion;
import org.byteskript.bytecode.syntax.error.Throw;
import org.byteskript.bytecode.syntax.error.TryCatch;
import org.byteskript.bytecode.syntax.generic.*;
import org.byteskript.bytecode.syntax.invoke.FieldAccess;
import org.byteskript.bytecode.syntax.invoke.InvokeNormal;
import org.byteskript.bytecode.syntax.jump.*;
import org.byteskript.bytecode.syntax.logic.AND;
import org.byteskript.bytecode.syntax.logic.OR;
import org.byteskript.bytecode.syntax.logic.XOR;
import org.byteskript.bytecode.syntax.maths.*;
import org.byteskript.bytecode.syntax.shift.SHL;
import org.byteskript.bytecode.syntax.shift.SHR;
import org.byteskript.bytecode.syntax.shift.USHR;
import org.byteskript.bytecode.syntax.value.*;
import org.byteskript.bytecode.syntax.variable.*;
import org.byteskript.skript.api.ModifiableLibrary;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.runtime.Skript;

public class Bytecode extends ModifiableLibrary {
    public static final Bytecode LIBRARY = new Bytecode();
    
    public Bytecode() {
        super("bytecode");
        registerSyntax(CompileState.STATEMENT,
            new TOP(),
            new LabelEffect()
        );
        registerSyntax(CompileState.CODE_BODY,
            new LabelEffect(),
            new Jump(),
            new IfNull(),
            new IfSimple(),
            new IfSmallComparison(),
            new IfObjectComparison()
        );
        registerSyntax(CompileState.CODE_BODY,
            new SWAP(),
            new DUP(),
            new POP(),
            new NOP(),
            new CheckCast(),
            new InstanceOf()
        );
        registerSyntax(CompileState.CODE_BODY,
            new ArrayLoad(),
            new ArrayStore(),
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
            new Throw(),
            new TryCatch()
        );
        registerSyntax(CompileState.CODE_BODY,
            new NewArray(),
            new ANewArray(),
            new MultiANewArray(),
            new ArrayLength()
        );
        registerSyntax(CompileState.CODE_BODY,
            new NumberConversion()
        );
        registerSyntax(CompileState.CODE_BODY,
            new CMP()
        );
        registerSyntax(CompileState.CODE_BODY,
            new ADD(),
            new SUB(),
            new MUL(),
            new DIV(),
            new REM(),
            new NEG(),
            new INC()
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
            new FieldAccess(),
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
