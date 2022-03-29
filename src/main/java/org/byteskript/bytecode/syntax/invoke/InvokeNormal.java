package org.byteskript.bytecode.syntax.invoke;

import mx.kenzie.foundation.MethodBuilder;
import mx.kenzie.foundation.Type;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.ElementTree;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class InvokeNormal extends Effect {
    
    public InvokeNormal() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT,
            "invokestatic %Type%, %String%, %String%, %Boolean%",
            "invokevirtual %Type%, %String%, %String%, %Boolean%",
            "invokespecial %Type%, %String%, %String%, %Boolean%",
            "invokeinterface %Type%, %String%, %String%, %Boolean%"
        );
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("invoke")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void preCompile(Context context, Pattern.Match match) throws Throwable {
        context.getCompileCurrent().disableChildren();
        super.preCompile(context, match);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final ElementTree[] nested = context.getCompileCurrent().nested();
        final Type owner = nested[0].match().meta();
        final String name = nested[1].getLiteralValue();
        final String erasure = nested[2].getLiteralValue();
        final Boolean value = nested[3].getLiteralValue();
        final MethodBuilder method = context.getMethod();
        final int opcode = switch (match.matchedPattern) {
            case 1 -> 182;
            case 2 -> 183;
            case 3 -> 185;
            default -> 184;
        };
        assert method != null;
        method.writeCode((writer, visitor) -> visitor.visitMethodInsn(opcode, owner.internalName(), name, erasure, value));
        context.setState(CompileState.CODE_BODY);
    }
}
