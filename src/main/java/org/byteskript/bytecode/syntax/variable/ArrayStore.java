package org.byteskript.bytecode.syntax.variable;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class ArrayStore extends Effect {
    
    public ArrayStore() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "iastore", "lastore", "fastore", "dastore", "aastore", "bastore", "castore", "sastore");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.contains("astore")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = switch (match.matchedPattern) {
            case 0 -> 79;
            case 1 -> 80;
            case 2 -> 81;
            case 3 -> 82;
            default -> 83;
            case 5 -> 84;
            case 6 -> 85;
            case 7 -> 86;
        };
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
    
}
