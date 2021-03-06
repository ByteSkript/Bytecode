package org.byteskript.bytecode.syntax.error;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class Throw extends Effect {
    
    public Throw() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "athrow");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.equals("athrow")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        method.writeCode((writer, visitor) -> visitor.visitInsn(191));
        context.setState(CompileState.CODE_BODY);
    }
    
}
