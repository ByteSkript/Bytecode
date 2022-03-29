package org.byteskript.bytecode.syntax.generic;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class SWAP extends Effect {
    
    public SWAP() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "swap");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("swap")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        method.writeCode((writer, visitor) -> visitor.visitInsn(95));
        context.setState(CompileState.CODE_BODY);
    }
    
}
