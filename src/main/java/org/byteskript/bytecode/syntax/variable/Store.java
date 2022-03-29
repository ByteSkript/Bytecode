package org.byteskript.bytecode.syntax.variable;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class Store extends Effect {
    
    public Store() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "istore %Integer%", "lstore %Integer%", "fstore %Integer%", "dstore %Integer%", "astore %Integer%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.contains("store")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void preCompile(Context context, Pattern.Match match) throws Throwable {
        context.getCompileCurrent().disableChildren();
        super.preCompile(context, match);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final Integer value = context.getCompileCurrent().nested()[0].getLiteralValue();
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = switch (match.matchedPattern) {
            case 0 -> 54;
            case 1 -> 55;
            case 2 -> 56;
            case 3 -> 57;
            default -> 58;
        };
        if (value == null) method.writeCode((writer, visitor) -> visitor.visitVarInsn(opcode, 0));
        else method.writeCode((writer, visitor) -> visitor.visitVarInsn(opcode, value));
        context.setState(CompileState.CODE_BODY);
    }
    
}
