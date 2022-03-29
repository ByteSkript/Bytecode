package org.byteskript.bytecode.syntax.maths;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class NEG extends Effect {
    
    public NEG() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "ineg", "lneg", "fneg", "dneg");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.endsWith("neg")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = switch (match.matchedPattern) {
            case 1 -> 117;
            case 2 -> 118;
            case 3 -> 119;
            default -> 116;
        };
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
    
}
