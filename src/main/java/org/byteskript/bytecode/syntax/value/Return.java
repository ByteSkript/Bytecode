package org.byteskript.bytecode.syntax.value;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class Return extends Effect {
    
    public Return() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "ireturn", "lreturn", "freturn", "dreturn", "areturn", "return");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.endsWith("return")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = switch (match.matchedPattern) {
            case 0 -> 172;
            case 1 -> 173;
            case 2 -> 174;
            case 3 -> 175;
            case 4 -> 176;
            default -> 177;
        };
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
}
