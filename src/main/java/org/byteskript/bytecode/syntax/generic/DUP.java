package org.byteskript.bytecode.syntax.generic;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class DUP extends Effect {
    
    public DUP() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "dup", "dup 2", "dup x1", "dup x2", "dup 2x1", "dup 2x2");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("dup")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = switch (match.matchedPattern) {
            case 1 -> 92;
            case 2 -> 90;
            case 3 -> 91;
            case 4 -> 93;
            case 5 -> 94;
            default -> 89;
        };
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
    
}
