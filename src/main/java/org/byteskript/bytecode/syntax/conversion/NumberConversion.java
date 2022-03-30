package org.byteskript.bytecode.syntax.conversion;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class NumberConversion extends Effect {
    
    public NumberConversion() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT,
            "i2l", "i2f", "i2d",
            "l2i", "l2f", "l2d",
            "f2i", "f2l", "f2d",
            "d2i", "d2l", "d2f",
            "i2b", "i2c", "i2s"
        );
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.contains("2")) return null;
        if (thing.length() != 3) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = match.matchedPattern + 133;
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
    
}
