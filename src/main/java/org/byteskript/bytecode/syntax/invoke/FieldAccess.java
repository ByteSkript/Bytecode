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

public class FieldAccess extends Effect {
    
    public FieldAccess() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT,
            "getstatic %Type%, %String%, %String%",
            "putstatic %Type%, %String%, %String%",
            "getfield %Type%, %String%, %String%",
            "putfield %Type%, %String%, %String%"
        );
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.contains("get") && !thing.contains("put")) return null;
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
        final MethodBuilder method = context.getMethod();
        final int opcode = match.matchedPattern + 178;
        assert opcode >= 178 && opcode <= 181;
        assert method != null;
        method.writeCode((writer, visitor) -> visitor.visitFieldInsn(opcode, owner.internalName(), name, erasure));
        context.setState(CompileState.CODE_BODY);
    }
}
