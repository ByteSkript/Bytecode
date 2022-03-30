package org.byteskript.bytecode.syntax.variable;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class ArrayLoad extends Effect {
    
    public ArrayLoad() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "iaload", "laload", "faload", "daload", "aaload", "baload", "caload", "saload");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.contains("aload")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = switch (match.matchedPattern) {
            case 0 -> 46;
            case 1 -> 47;
            case 2 -> 48;
            case 3 -> 49;
            default -> 50;
            case 5 -> 51;
            case 6 -> 52;
            case 7 -> 53;
        };
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
    
}
