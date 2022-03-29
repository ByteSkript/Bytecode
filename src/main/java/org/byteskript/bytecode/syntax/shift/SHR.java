package org.byteskript.bytecode.syntax.shift;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class SHR extends Effect {
    
    public SHR() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "ishr", "lshr");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.endsWith("shr")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = switch (match.matchedPattern) {
            case 1 -> 123;
            default -> 122;
        };
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
    
}
